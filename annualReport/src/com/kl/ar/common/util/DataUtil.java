package com.kl.ar.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.kl.ar.common.cfg.ConfigUtil;
import com.kl.ar.common.constant.Constant;
import com.kl.ar.common.db.BizDb;
import com.kl.ar.vote.entity.CandidateUser;
import com.kl.ar.vote.entity.VoteResult;
import com.kl.ar.vote.entity.VoteResultSum;

import koal.urm.client.util.StringUtil;

/**
 * 
 * @package:com.kl.ar.common.util
 * @Description: 数据工具类
 * @author: wanlei
 * @date: 2019年12月21日下午2:05:19
 */
public class DataUtil {

	private static Gson gson = new Gson();

	/**
	 * 构建boostrap-table需要的json格式
	 * 
	 * @param total
	 * @param obj
	 * @return
	 */
	public static String bulidJsonData(int total, Object obj) {
		return "{\"total\":" + total + ",\"rows\":" + gson.toJson(obj) + "}";
	}

	/**
	 * 获取可选择的年份列表，最小值:minYear，最大值:currentYear
	 * 
	 * @return
	 */
	public static Map<String, String> getSelectYearList() {
		int currentYear = Integer.parseInt(ConfigUtil.getInstance().loadCfgMap().get(Constant.CURRENT_YEAR));
		int minYear = Integer.parseInt(ConfigUtil.getInstance().loadCfgMap().get(Constant.MIN_YEAR));
		Map<String, String> map = new HashMap<String, String>();
		for (int i = minYear; i <= currentYear; i++) {
			map.put(String.valueOf(i), i + "年");
		}
		return map;
	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// 修复年份
		int year = 2018;
		// updateVoteResult(year);
		// updateVoteResultSum(year);
	}

	/**
	 * 修复vote_result_sum表
	 * 
	 * @param year
	 * @throws Exception
	 */
	private static void updateVoteResultSum(int year) throws Exception {
		List<VoteResultSum> resultList = BizDb.queryList("SELECT * from " + VoteResultSum.TABLE + " where voteYear=?",
				VoteResultSum.class, year);
		for (VoteResultSum voteSum : resultList) {
			voteSum.setCandidateId(changeId(voteSum.getCandidateId(), year));
			String updateSql = "UPDATE " + VoteResultSum.TABLE + " SET candidateId = ? where id=?";
			BizDb.update(updateSql, voteSum.getCandidateId(), voteSum.getId());
		}
	}

	/**
	 * 修复vote_result表
	 * 
	 * @param year
	 * @throws Exception
	 */
	private static void updateVoteResult(int year) throws Exception {
		List<VoteResult> resultList = BizDb.queryList("SELECT * from " + VoteResult.TABLE + " where voteYear=?",
				VoteResult.class, year);
		for (VoteResult vote : resultList) {
			vote.setGoldPrize(changeId(vote.getGoldPrize(), year));
			vote.setSilverPrize(changeId(vote.getSilverPrize(), year));
			vote.setBronzePrize(changeId(vote.getBronzePrize(), year));
			vote.setBestTeamPrize(changeId(vote.getBestTeamPrize(), year));
			vote.setQualityPrize(changeId(vote.getQualityPrize(), year));
			vote.setCreativePrize(changeId(vote.getCreativePrize(), year));
			vote.setGoodWifePrize(changeId(vote.getGoodWifePrize(), year));
			vote.setMarketDevelopmentPrize(changeId(vote.getMarketDevelopmentPrize(), year));
			vote.setMostPotentialPrize(changeId(vote.getMostPotentialPrize(), year));
			vote.setDeligentPrize(changeId(vote.getDeligentPrize(), year));
			vote.setDemoProjectPrize(changeId(vote.getDemoProjectPrize(), year));
			vote.setProductInnovationPrize(changeId(vote.getProductInnovationPrize(), year));
			vote.setNewComerPrize(changeId(vote.getNewComerPrize(), year));
			String updateSql = "UPDATE " + VoteResult.TABLE
					+ " SET goldPrize = ?, silverPrize = ?, bronzePrize = ?, bestTeamPrize = ?, creativePrize = ?, qualityPrize = ?,"
					+ " marketDevelopmentPrize = ?, deligentPrize = ?, productInnovationPrize = ?, mostPotentialPrize = ?, demoProjectPrize = ?, newComerPrize = ?, goodWifePrize = ? WHERE id = ?";
			BizDb.update(updateSql, vote.getGoldPrize(), vote.getSilverPrize(), vote.getBronzePrize(),
					vote.getBestTeamPrize(), vote.getCreativePrize(), vote.getQualityPrize(),
					vote.getMarketDevelopmentPrize(), vote.getDeligentPrize(), vote.getProductInnovationPrize(),
					vote.getMostPotentialPrize(), vote.getDemoProjectPrize(), vote.getNewComerPrize(),
					vote.getGoodWifePrize(), vote.getId());
		}
	}

	/**
	 * 将老的userId转换为id
	 * 
	 * @param oldIds
	 * @param year
	 * @return
	 * @throws Exception
	 */
	public static String changeId(String oldIds, int year) throws Exception {
		if (StringUtil.isEmpty(oldIds)) {
			return "";
		}
		String oldId[] = oldIds.split(",");
		List<String> newIdList = new ArrayList<String>();
		for (String old : oldId) {
			String newId = getNewId(old, year);
			newIdList.add(newId);
		}
		return StringUtils.join(newIdList.toArray(new String[] {}), ",");
	}

	/**
	 * 查询候选人表，将userId转换成id
	 * 
	 * @param oldId
	 * @param year
	 * @return
	 * @throws Exception
	 */
	private static String getNewId(String oldId, int year) throws Exception {
		CandidateUser user = BizDb.queryOneRow(
				"select * from " + CandidateUser.TABLE + " where userId=? and selectYear=?", CandidateUser.class, oldId,
				year);
		return user.getId().toString();
	}
}
