package com.kl.ar.vote.service;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kl.ar.common.cache.MapCache;
import com.kl.ar.common.constant.Constant;
import com.kl.ar.common.constant.PrizeType;
import com.kl.ar.common.db.BizDb;
import com.kl.ar.common.db.DBUtils;
import com.kl.ar.common.util.DataUtil;
import com.kl.ar.common.util.ExcelUtil;
import com.kl.ar.vote.entity.CandidateUser;
import com.kl.ar.vote.entity.VoteResult;
import com.kl.ar.vote.entity.VoteResultByWeight;
import com.kl.ar.vote.entity.VoteResultSum;
import com.kl.ar.vote.entity.VwVoteResultSum;

import koal.urm.client.util.StringUtil;

/**
 * 
 * @package:com.kl.ar.vote.service
 * @Description: 评选的service层
 * @author: wanlei
 * @date: 2019年12月21日下午3:24:59
 */
public class VoteService {
	// 每年的候选人名单缓存MAP,一天清理一次
	private static final String CANDIDATE_USER_CACHE = "candidateUserCache";
	public static MapCache<String, List<CandidateUser>> candidateUserMapCache = new MapCache<String, List<CandidateUser>>(
			-1, 1440);

	/**
	 * 查出所有的候选人List，经过缓存查询
	 * 
	 * @param selectYear
	 * @param userVoteType
	 * @return
	 * @throws Exception
	 */
	private static List<CandidateUser> getCandidateUserListFromCache() throws Exception {
		if (!Objects.isNull(candidateUserMapCache.get(CANDIDATE_USER_CACHE))) {
			return candidateUserMapCache.get(CANDIDATE_USER_CACHE);
		}
		String sql = "SELECT * FROM " + CandidateUser.TABLE;
		List<CandidateUser> list = BizDb.queryList(sql, CandidateUser.class);
		candidateUserMapCache.cache(CANDIDATE_USER_CACHE, list);
		return list;
	}

	/**
	 * 查出某一年中的某类型的候选人List
	 * 
	 * @param selectYear
	 * @param userVoteType
	 * @return
	 * @throws Exception
	 */
	public static List<CandidateUser> getCandidateUserListByYearAndVoteType(Integer selectYear, String userVoteType)
			throws Exception {
		List<CandidateUser> list = getCandidateUserListFromCache();
		List<CandidateUser> resultList = new ArrayList<CandidateUser>();
		for (CandidateUser candidateUser : list) {
			if (userVoteType.equals(candidateUser.getUserVoteType())
					&& selectYear.intValue() == candidateUser.getSelectYear().intValue()) {
				resultList.add(candidateUser);
			}
		}
		return resultList;
	}

	/**
	 * 保存投票结果及结果关系表
	 * 
	 * @param result
	 * @throws Exception
	 */
	public static void addVoteResult(VoteResult result) throws Exception {
		Connection conn = null;
		try {
			conn = DBUtils.getConn();
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			// 插入投票结果表
			BizDb.insert(result, conn, VoteResult.TABLE);
			// 保存投票结果关系表，方便投票统计
			saveVoteResult(conn, result, qr);
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			DBUtils.close(conn);
		}
	}

	/**
	 * 保存投票结果关系表
	 * 
	 * @param conn
	 * @param result
	 * @param qr
	 * @throws Exception
	 */
	private static void saveVoteResult(Connection conn, VoteResult result, QueryRunner qr) throws Exception {
		String voteId = result.getId();
		List<PrizeType> prizeTypeList = PrizeType.getAllPrizeTypeList();
		for (PrizeType prizeType : prizeTypeList) {
			String prizeTypeCode = prizeType.getCode();
			insertResult(conn, invokeGetMethod(result, prizeTypeCode), prizeTypeCode, voteId);
		}
	}

	/**
	 * 根据属性调用对应的get方法获取值
	 * 
	 * @param ob
	 *            对象
	 * @param name
	 *            属性名
	 * @return
	 * @throws Exception
	 */
	public static String invokeGetMethod(Object ob, String name) throws Exception {
		Method[] m = ob.getClass().getMethods();
		for (int i = 0; i < m.length; i++) {
			if (("get" + name).toLowerCase().equals(m[i].getName().toLowerCase())) {
				return String.valueOf(m[i].invoke(ob));
			}
		}
		return null;
	}

	/**
	 * 将各奖项的数据ID字符串以，分隔，批量入库
	 * 
	 * @param conn
	 * @param prizeData
	 * @param prizeType
	 * @param voteId
	 * @throws Exception
	 */
	private static void insertResult(Connection conn, String prizeData, String prizeType, String voteId)
			throws Exception {
		// 如果奖项ID为空，则不执行插入操作
		if (StringUtils.isEmpty(prizeData)) {
			return;
		}
		for (String candidateId : prizeData.split(",")) {
			VoteResultSum voteResultSum = new VoteResultSum();
			voteResultSum.setCandidateId(candidateId);
			voteResultSum.setPrizeType(prizeType);
			voteResultSum.setVoteId(voteId);
			BizDb.insert(voteResultSum, conn, VoteResultSum.TABLE);
		}
	}

	/**
	 * 获取权重投票汇总列表并分页
	 * 
	 * @param condMap
	 * @param user
	 * @param orgs
	 * @return
	 * @throws Exception
	 */
	public static String getSumVoteByWeightAndPage(Map<String, Object> condMap) throws Exception {
		// 1.获取页面传过来的查询条件
		int limit = Integer.valueOf(String.valueOf(condMap.get(Constant.LIMIT)));
		int offset = Integer.valueOf(String.valueOf(condMap.get(Constant.OFFSET)));
		// 权重当为空时默认为:goldWeight:5,silverWeight:3,bronzeWeight:1
		String goldWeight = String.valueOf(condMap.get(Constant.PARAM_GOLD_WEIGHT)) == null ? "5"
				: String.valueOf(condMap.get(Constant.PARAM_GOLD_WEIGHT));
		String silverWeight = String.valueOf(condMap.get(Constant.PARAM_SILVER_WEIGHT)) == null ? "3"
				: String.valueOf(condMap.get(Constant.PARAM_SILVER_WEIGHT));
		String bronzeWeight = String.valueOf(condMap.get(Constant.PARAM_BRONZE_WEIGHT)) == null ? "1"
				: String.valueOf(condMap.get(Constant.PARAM_BRONZE_WEIGHT));
		String voteYear = String.valueOf(condMap.get(Constant.PARAM_VOTE_YEAR));
		// 2.根据查询条件动态拼接查询SQL，查询vw_vote_result_sum视图
		List<VwVoteResultSum> resultList = queryVoteResultSum(voteYear);
		// 3.缓存VoteResultByWeight对象的MAP集合
		List<VoteResultByWeight> weightList = calSumScoreByWeight(goldWeight, silverWeight, bronzeWeight, resultList);
		// 4.按照总分数从高到低排序list
		sortList(weightList);
		// 5.根据limit，offset实现list分页效果
		List<VoteResultByWeight> weightPageList = pageList(limit, offset, weightList);
		return DataUtil.bulidJsonData(weightList.size(), weightPageList);
	}

	/**
	 * 获取权重投票汇总列表
	 * 
	 * @param condMap
	 * @param user
	 * @param orgs
	 * @return
	 * @throws Exception
	 */
	public static List<VoteResultByWeight> getSumVoteByWeight(Map<String, Object> condMap) throws Exception {
		// 1.权重当为空时默认为:goldWeight:5,silverWeight:3,bronzeWeight:1
		String goldWeight = String.valueOf(condMap.get(Constant.PARAM_GOLD_WEIGHT)) == null ? "5"
				: String.valueOf(condMap.get(Constant.PARAM_GOLD_WEIGHT));
		String silverWeight = String.valueOf(condMap.get(Constant.PARAM_SILVER_WEIGHT)) == null ? "3"
				: String.valueOf(condMap.get(Constant.PARAM_SILVER_WEIGHT));
		String bronzeWeight = String.valueOf(condMap.get(Constant.PARAM_BRONZE_WEIGHT)) == null ? "1"
				: String.valueOf(condMap.get(Constant.PARAM_BRONZE_WEIGHT));
		String voteYear = String.valueOf(condMap.get(Constant.PARAM_VOTE_YEAR));
		// 2.根据查询条件动态拼接查询SQL，查询vw_vote_result_sum视图
		List<VwVoteResultSum> resultList = queryVoteResultSum(voteYear);
		// 3.缓存VoteResultByWeight对象的MAP集合
		List<VoteResultByWeight> weightList = calSumScoreByWeight(goldWeight, silverWeight, bronzeWeight, resultList);
		// 4.按照总分数从高到低排序list
		sortList(weightList);
		return weightList;
	}

	/**
	 * 根据查询条件查询VwVoteResultSum实体的List集合
	 * 
	 * @param voteYear
	 * @return
	 * @throws Exception
	 */
	private static List<VwVoteResultSum> queryVoteResultSum(String voteYear) throws Exception {
		// 动态拼接查询SQL语句
		StringBuffer sb = new StringBuffer("select * from " + VwVoteResultSum.TABLE + " where ticketStatus=0");
		// 查询条件
		if (!StringUtil.isEmpty(voteYear)) {
			sb.append(" AND voteYear=" + Integer.parseInt(voteYear));
		}
		// 只查询金，银，铜3奖项的票数
		String prizeTypeSql = String.format(" AND prizeType in ('%s','%s','%s')", PrizeType.GOLD_PRIZE.getCode(),
				PrizeType.SILVER_PRIZE.getCode(), PrizeType.BRONZE_PRIZE.getCode());
		sb.append(prizeTypeSql);
		List<VwVoteResultSum> list = BizDb.queryList(sb.toString(), VwVoteResultSum.class);
		return list;
	}

	/**
	 * 根据limit，offset实现list分页效果
	 * 
	 * @param limit
	 * @param offset
	 * @param dataList
	 * @return
	 */
	private static List<VoteResultByWeight> pageList(int limit, int offset, List<VoteResultByWeight> dataList) {
		List<VoteResultByWeight> pageList = null;
		// 当总记录数大于每页数量时，才进行分页，否则就一页
		if (dataList.size() > limit) {
			// 使用list自带的subList方法实现分页
			Integer fromIndex = (offset - 1) * limit;// 起始位置，从0开始
			Integer totalPageSize = (dataList.size() / limit) == 0 ? (dataList.size() / limit)
					: (dataList.size() / limit + 1);// 总页数
			Integer toIndex = null;
			if (offset < totalPageSize) {
				// 当前页面小于总页数时
				toIndex = (offset - 1) * limit + limit;// 起始位置+每页数量=结束位置
			} else if (offset == totalPageSize) {
				// 当前页码等于总页数是，即最后一页，结束位置则为总数
				toIndex = dataList.size();
			}
			pageList = dataList.subList(fromIndex, toIndex);
		} else {
			// 就一页
			pageList = dataList;
		}
		return pageList;
	}

	/**
	 * 按照总分数从高到低排序list
	 * 
	 * @param dataList
	 */
	private static void sortList(List<VoteResultByWeight> dataList) {
		Collections.sort(dataList, new Comparator<VoteResultByWeight>() {
			@Override
			public int compare(VoteResultByWeight o1, VoteResultByWeight o2) {
				return o2.getSumScore() - o1.getSumScore();
			}
		});
	}

	/**
	 * 根据金，银，铜3奖项对应的权重分数计算总分，返回VoteResultByWeight实体
	 * 
	 * @param goldWeight
	 *            金奖权重分
	 * @param silverWeight
	 *            银奖权重分
	 * @param bronzeWeight
	 *            铜奖权重分
	 * @param list
	 *            VwVoteResultSum实体
	 * @return
	 */
	private static List<VoteResultByWeight> calSumScoreByWeight(String goldWeight, String silverWeight,
			String bronzeWeight, List<VwVoteResultSum> list) {
		Map<String, VoteResultByWeight> map = new HashMap<String, VoteResultByWeight>();
		// 根据权重比分对数据计算并重新封装LIST
		for (VwVoteResultSum vwVoteResultSum : list) {
			// 当缓存MAP中有这个候选人对象，直接拿来，没有，则新建一个新的候选人对象
			VoteResultByWeight current = null;
			if (map.containsKey(vwVoteResultSum.getCandidateId())) {
				current = map.get(vwVoteResultSum.getCandidateId());
			} else {
				current = new VoteResultByWeight();
				current.setCandidateId(vwVoteResultSum.getCandidateId());
				current.setCandidateName(vwVoteResultSum.getCandidateName());
				current.setCandidateDept(vwVoteResultSum.getCandidateDept());
				current.setVoteYear(vwVoteResultSum.getVoteYear());
			}
			// 根据金，银，铜奖项设置不同的权重分数
			int weight = 0;
			if (PrizeType.GOLD_PRIZE.getCode().equals(vwVoteResultSum.getPrizeType())) {
				current.setGoldCount(vwVoteResultSum.getCount());// 设置金奖票数
				weight = Integer.parseInt(goldWeight);
			} else if (PrizeType.SILVER_PRIZE.getCode().equals(vwVoteResultSum.getPrizeType())) {
				current.setSilverCount(vwVoteResultSum.getCount());// 设置银奖票数
				weight = Integer.parseInt(silverWeight);
			} else if (PrizeType.BRONZE_PRIZE.getCode().equals(vwVoteResultSum.getPrizeType())) {
				current.setBronzeCount(vwVoteResultSum.getCount());// 设置铜奖票数
				weight = Integer.parseInt(bronzeWeight);
			}
			// 计算总分数
			int sumScore = vwVoteResultSum.getCount() * weight;
			// 当缓存MAP中有这个候选人对象，叠加总票数和总分数
			if (map.containsKey(vwVoteResultSum.getCandidateId())) {
				current.setTotalCount(current.getTotalCount() + vwVoteResultSum.getCount());
				current.setSumScore(current.getSumScore() + sumScore);
			} else {
				current.setTotalCount(vwVoteResultSum.getCount());// 设置总票数
				current.setSumScore(sumScore);// 设置总分数
				map.put(current.getCandidateId(), current);// 缓存VoteResultByWeight对象
			}
		}
		List<VoteResultByWeight> dataList = new ArrayList<VoteResultByWeight>(map.values());
		return dataList;
	}

	/**
	 * 按奖项统计某一年所有奖项的投票情况
	 * 
	 * @param currentYear
	 * @return
	 * @throws Exception
	 */
	public static List<VwVoteResultSum> getSumVoteResult(String currentYear) throws Exception {
		StringBuffer sb = new StringBuffer("select * from " + VwVoteResultSum.TABLE + " where ticketStatus=0");
		// 查询条件
		if (!StringUtil.isEmpty(currentYear)) {
			sb.append(" AND voteYear=" + Integer.parseInt(currentYear));
		}
		sb.append(" order by prizeType,count desc");
		List<VwVoteResultSum> list = BizDb.queryList(sb.toString(), VwVoteResultSum.class);
		return list;
	}

	/**
	 * 根据候选人ID查询候选人信息
	 * 
	 * @param id
	 *            候选人id
	 * @return
	 * @throws Exception
	 */
	public static CandidateUser getCandidateUserById(String id) throws Exception {
		List<CandidateUser> list = getCandidateUserListFromCache();
		for (CandidateUser candidateUser : list) {
			if (id.equals(String.valueOf(candidateUser.getId()))) {
				return candidateUser;
			}
		}
		return null;
	}

	/**
	 * 导出各奖项票数汇总数据
	 * 
	 * @param searchYear
	 * @return
	 * @throws Exception
	 */
	public static XSSFWorkbook exportVoteResultByPrize(String searchYear) throws Exception {
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 定义EXCEL表格的头标题
		String[] voteResultSumHeaders = { "序号", "候选人名称", "候选人部门", "奖项", "票数", "投票年份" };
		List<VwVoteResultSum> vwVoteResultSumList = getSumVoteResult(searchYear);
		// 生成多个sheet
		ExcelUtil.createExcel(workbook, 0, "各奖项票数汇总", voteResultSumHeaders,
				covertVoteResultSumData(vwVoteResultSumList));
		return workbook;
	}

	/**
	 * 转换各奖项票数汇总数据
	 * 
	 * @param vwVoteResultSumList
	 * @return
	 */
	private static List<List<String>> covertVoteResultSumData(List<VwVoteResultSum> vwVoteResultSumList) {
		int count = 0;
		List<List<String>> listSum = new ArrayList<List<String>>();
		for (VwVoteResultSum vwVoteResultSum : vwVoteResultSumList) {
			count++;
			List<String> list = new ArrayList<String>();
			list.add(String.valueOf(count));
			list.add(vwVoteResultSum.getCandidateName());
			list.add(vwVoteResultSum.getCandidateDept());
			list.add(PrizeType.getCodeByDesc(vwVoteResultSum.getPrizeType()));
			list.add(String.valueOf(vwVoteResultSum.getCount()));
			list.add(vwVoteResultSum.getVoteYear());
			listSum.add(list);
		}
		return listSum;
	}

	/**
	 * 导出排名总分汇总数据
	 * 
	 * @param searchYear
	 * @return
	 * @throws Exception
	 */
	public static XSSFWorkbook exportVoteResultByWeight(Map<String, Object> condMap) throws Exception {
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 定义EXCEL表格的头标题
		String[] voteResultByWeightHeaders = { "序号", "候选人名称", "候选人部门", "金奖票数", "银奖票数", "铜奖票数", "总票数", "总分数", "投票年份" };
		List<VoteResultByWeight> vwVoteResultByWeightList = getSumVoteByWeight(condMap);
		// 生成多个sheet
		ExcelUtil.createExcel(workbook, 0, "排名总分汇总", voteResultByWeightHeaders,
				covertVoteResultByWeightData(vwVoteResultByWeightList));
		return workbook;
	}

	/**
	 * 转换排名总分汇总数据
	 * 
	 * @param vwVoteResultByWeightList
	 * @return
	 */
	private static List<List<String>> covertVoteResultByWeightData(List<VoteResultByWeight> vwVoteResultByWeightList) {
		int count = 0;
		List<List<String>> listSum = new ArrayList<List<String>>();
		for (VoteResultByWeight voteResultByWeight : vwVoteResultByWeightList) {
			count++;
			List<String> list = new ArrayList<String>();
			list.add(String.valueOf(count));
			list.add(voteResultByWeight.getCandidateName());
			list.add(voteResultByWeight.getCandidateDept());
			list.add(String.valueOf(voteResultByWeight.getGoldCount()));
			list.add(String.valueOf(voteResultByWeight.getSilverCount()));
			list.add(String.valueOf(voteResultByWeight.getBronzeCount()));
			list.add(String.valueOf(voteResultByWeight.getTotalCount()));
			list.add(String.valueOf(voteResultByWeight.getSumScore()));
			list.add(String.valueOf(voteResultByWeight.getVoteYear()));
			listSum.add(list);
		}
		return listSum;
	}

}
