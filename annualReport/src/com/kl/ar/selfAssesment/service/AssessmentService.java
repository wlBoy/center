package com.kl.ar.selfAssesment.service;

import java.text.DecimalFormat;
import java.util.*;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kl.ar.common.cfg.ConfigUtil;
import com.kl.ar.common.constant.Constant;
import com.kl.ar.common.constant.ReviewResult;
import com.kl.ar.common.constant.RoleType;
import com.kl.ar.common.constant.SelfAssessmentStatus;
import com.kl.ar.common.db.BizDb;
import com.kl.ar.common.util.DataUtil;
import com.kl.ar.common.util.ExcelUtil;
import com.kl.ar.org.entity.CrmOrg;
import com.kl.ar.selfAssesment.entity.SelfAssessment;
import com.kl.ar.user.entity.CrmUser;

import koal.urm.client.util.StringUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @package:com.kl.ar.selfAssesment.service
 * @Description: 自评的service层
 * @author: wanlei
 * @date: 2019年12月21日下午3:15:12
 */
public class AssessmentService {
	private static final Log logger = LogFactory.getLog(AssessmentService.class);

	/**
	 * 获取本部门的所有人员的自评列表并进行分页处理
	 * 
	 * @param condMap
	 * @param user
	 * @param orgs
	 * @return
	 * @throws Exception
	 */
	public static String getAssessmentData(Map<String, Object> condMap, CrmUser user, List<Map<String, Object>> orgs)
			throws Exception {
		int limit = Integer.valueOf(String.valueOf(condMap.get(Constant.LIMIT)));
		int offset = Integer.valueOf(String.valueOf(condMap.get(Constant.OFFSET)));
		// 查询条件
		String userName = String.valueOf(condMap.get(Constant.PARAM_USER_NAME));
		String pathid = String.valueOf(condMap.get(Constant.PARAM_PATHID));
		String status = String.valueOf(condMap.get(Constant.PARAM_STATUS));
		String assessmentYear = String.valueOf(condMap.get(Constant.PARAM_ASSESSMENT_YEAR));
		// 动态拼接查询SQL语句
		StringBuffer sb = new StringBuffer("SELECT s.*,u.sName AS userName,o.sName AS orgName FROM "
				+ SelfAssessment.TABLE + " s LEFT JOIN " + CrmUser.TABLE + " u ON u.USERID = s.USER_ID LEFT JOIN "
				+ CrmOrg.TABLE + " o ON o.ORGID = u.idDep WHERE 1=1 ");
		if (StringUtils.isNotBlank(userName)) {
			sb.append(" AND u.sName LIKE '%" + userName + "%'");
		}
		if (StringUtils.isNotBlank(status)) {
			sb.append(" AND s.STATUS = " + status);
		}
		if (StringUtils.isNotBlank(assessmentYear)) {
			sb.append(" AND s.ASSESSMENT_YEAR = '" + assessmentYear + "'");
		}
		if (StringUtils.isNotBlank(pathid)) {
			sb.append(" AND o.pathid LIKE '" + pathid + "%'");
		}
		// 当权限不为全部时，对数据进行过滤
		if (user != null && !RoleType.ALL.getCode().equals(user.getRole())) {
			if (CollectionUtils.isEmpty(orgs)) {
				return DataUtil.bulidJsonData(0, "");
			}
			sb.append(" AND (");
			for (int i = 0; i < orgs.size(); i++) {
				Map<String, Object> org = orgs.get(i);
				sb.append("o.pathid LIKE '" + org.get("pathid") + "%'");
				if (i + 1 < orgs.size()) {
					sb.append(" OR ");
				}
			}
			sb.append(")");
		}
		// 查询总数和分页数据列表
		int total = BizDb.queryCount(sb.toString());
		sb.append(" LIMIT " + (offset - 1) * limit + "," + limit);
		List<SelfAssessment> assessments = BizDb.queryList(sb.toString(), SelfAssessment.class);
		return DataUtil.bulidJsonData(total, assessments);
	}

	/**
	 * 通过查询条件查询自评
	 * 
	 * @param cond
	 * @param params
	 * @return
	 */
	public static SelfAssessment getSelfAssessmentByCond(String cond, Object... params) {
		SelfAssessment selfAssessment;
		try {
			selfAssessment = BizDb.queryOneRow(cond, SelfAssessment.class, params);
		} catch (Exception e) {
			e.printStackTrace();
			selfAssessment = null;
		}
		return selfAssessment;
	}

	/**
	 * 保存自评
	 * 
	 * @param request
	 * @param userId
	 * @return
	 */
	public static Map<String, String> saveAssessment(HttpServletRequest request, String userId) {
		// 从配置文件中取出当前年份
		String currentYear = ConfigUtil.getInstance().loadCfgMap().get(Constant.CURRENT_YEAR);
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put(Constant.AJAX_RESULT, Constant.SUCCESS_RESULT);
		try {
			// 解析数据
			int PM = Integer.valueOf(request.getParameter("PM"));
			int IC = Integer.valueOf(request.getParameter("IC"));
			int SV = Integer.valueOf(request.getParameter("SV"));
			int RP = Integer.valueOf(request.getParameter("RP"));
			int SD = Integer.valueOf(request.getParameter("SD"));
			int TS = Integer.valueOf(request.getParameter("TS"));
			int SI = Integer.valueOf(request.getParameter("SI"));
			int CW = Integer.valueOf(request.getParameter("CW"));
			String excellentExamplesContent = request.getParameter("excellentExamples");
			String selfComment = request.getParameter("selfComment");
			// 封装自评对象
			SelfAssessment sa = new SelfAssessment();
			sa.setSELF_ASSESSMENT_ID(UUID.randomUUID().toString());
			sa.setUSER_ID(userId);
			sa.setPM(PM);
			sa.setIC(IC);
			sa.setSV(SV);
			sa.setRP(RP);
			sa.setSD(SD);
			sa.setTS(TS);
			sa.setSI(SI);
			sa.setCW(CW);
			sa.setEXCELLENT_EXAMPLES(excellentExamplesContent);
			sa.setSELF_COMMENT(selfComment);
			sa.setADD_TIME(new Date());
			// 保留一位小数即可
			DecimalFormat df = new DecimalFormat("#.0");
			sa.setSELF_SUM(Double.valueOf(df.format((IC + SV) * 0.2 + (PM + RP + SD + TS + SI + CW) * 0.1)));
			sa.setSTATUS(SelfAssessmentStatus.STATUS_2SIGN_2REVIEW.getCode());
			sa.setASSESSMENT_YEAR(currentYear);
			// 保存数据库
			BizDb.insert(sa, SelfAssessment.TABLE);
		} catch (Exception e) {
			logger.error("保存自评数据失败，原因为:" + e);
			dataMap.put(Constant.AJAX_RESULT, Constant.FAILURE_RESULT);
		}
		return dataMap;
	}

	/**
	 * 保存部门评分数据
	 * 
	 * @param request
	 * @param userId
	 * @return
	 */
	public static Map<String, String> saveDepAssessment(HttpServletRequest request, String userId) {
		Map<String, String> depMap = new HashMap<String, String>();
		depMap.put(Constant.AJAX_RESULT, Constant.SUCCESS_RESULT);
		try {
			String asId = request.getParameter("assessmentId");
			int PM = Integer.valueOf(request.getParameter("depPM"));
			int IC = Integer.valueOf(request.getParameter("depIC"));
			int SV = Integer.valueOf(request.getParameter("depSV"));
			int RP = Integer.valueOf(request.getParameter("depRP"));
			int SD = Integer.valueOf(request.getParameter("depSD"));
			int TS = Integer.valueOf(request.getParameter("depTS"));
			int SI = Integer.valueOf(request.getParameter("depSI"));
			int CW = Integer.valueOf(request.getParameter("depCW"));
			String leaderComment = request.getParameter("leaderComment");
			String departmentComment = request.getParameter("departmentComment");
			int status = SelfAssessmentStatus.STATUS_2SIGN_2REVIEW_OK.getCode();
			int reviewResult = Integer.valueOf(request.getParameter("result"));
			String reviewLevel = request.getParameter("reviewLevel");
			String reviewBy = userId;
			// 保留一位小数即可
			DecimalFormat df = new DecimalFormat("#.0");
			double depSum = Double.valueOf(df.format((IC + SV) * 0.2 + (PM + RP + SD + TS + SI + CW) * 0.1));
			BizDb.update("UPDATE " + SelfAssessment.TABLE
					+ " SET DEP_PM=?, DEP_IC=?, DEP_SV=?, DEP_RP=?, DEP_SD=?, DEP_TS=?, DEP_SI=?, DEP_CW=?, "
					+ "LEADER_COMMENT=?, DEPARTMENT_COMMENT=?, UPDATE_TIME=?, STATUS=?, DEP_SUM=?, REVIEW_BY=?, REVIEW_RESULT=?, REVIEW_LEVEL=? WHERE SELF_ASSESSMENT_ID=?",
					PM, IC, SV, RP, SD, TS, SI, CW, leaderComment, departmentComment, new Date(), status, depSum,
					reviewBy, reviewResult, reviewLevel, asId);
		} catch (Exception e) {
			logger.error("保存部分评分数据失败，原因为:" + e);
			depMap.put(Constant.AJAX_RESULT, Constant.FAILURE_RESULT);
		}
		return depMap;
	}

	/**
	 * 导出本部门人员的评分
	 * 
	 * @param user
	 * @param orgs
	 * @return
	 * @throws Exception
	 */
	public static XSSFWorkbook exportExcel(String searchYear, CrmUser user, List<Map<String, Object>> orgs)
			throws Exception {
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 定义EXCEL表格的头标题
		String[] assessmentHeaders = { "序号", "姓名", "部门", "个人评分", "部门评分", "评估结果", "评估等级", "评分状态", "评分年份" };
		// 所有人员评分表格数据列表
		List<SelfAssessment> assessmentList = findAssessment(searchYear, user, orgs);
		// 生成多个sheet
		ExcelUtil.createExcel(workbook, 0, "部门评分", assessmentHeaders, covertAssessmentData(assessmentList));
		return workbook;
	}

	/**
	 * 将List<SelfAssessment>转换成List<List<String>>
	 * 
	 * @param reportList
	 * @return
	 */
	private static List<List<String>> covertAssessmentData(List<SelfAssessment> assessmentList) {
		int count = 0;
		List<List<String>> listSum = new ArrayList<List<String>>();
		for (SelfAssessment assessment : assessmentList) {
			count++;
			List<String> list = new ArrayList<String>();
			list.add(String.valueOf(count));
			list.add(assessment.getUserName());
			list.add(assessment.getOrgName());
			list.add(String.valueOf(assessment.getSELF_SUM()));
			list.add(assessment.getDEP_SUM() == null ? "0" : String.valueOf(assessment.getDEP_SUM()));
			list.add(ReviewResult.getDescByCode(assessment.getREVIEW_RESULT()) == null ? "暂无"
					: ReviewResult.getDescByCode(assessment.getREVIEW_RESULT()));
			list.add(assessment.getREVIEW_LEVEL());
			list.add(SelfAssessmentStatus.getDescByCode(assessment.getSTATUS()));
			list.add(assessment.getASSESSMENT_YEAR());
			listSum.add(list);
		}
		return listSum;
	}

	/**
	 * 拼接并根据机构过滤EXCEL导出的数据
	 * 
	 * @param user
	 * @param orgs
	 * @param sb
	 */
	private static void filterOrgData(CrmUser user, List<Map<String, Object>> orgs, StringBuffer sb) {
		// 当角色不为ALL的时候，对数据进行过滤
		if (user != null && !RoleType.ALL.getCode().equals(user.getRole())) {
			if (CollectionUtils.isEmpty(orgs)) {
				return;
			}
			sb.append(" AND (");
			for (int i = 0; i < orgs.size(); i++) {
				Map<String, Object> org = orgs.get(i);
				sb.append("o.pathid LIKE '" + org.get("pathid") + "%'");
				if (i + 1 < orgs.size()) {
					sb.append(" OR ");
				}
			}
			sb.append(")");
		}
	}

	/**
	 * 查询本部门人员的评分情况
	 * 
	 * @param user
	 * @param orgs
	 * @return
	 * @throws Exception
	 */
	private static List<SelfAssessment> findAssessment(String searchYear, CrmUser user, List<Map<String, Object>> orgs)
			throws Exception {
		StringBuffer sb = new StringBuffer("SELECT s.*,u.sName AS userName,o.sName AS orgName FROM "
				+ SelfAssessment.TABLE + " s LEFT JOIN " + CrmUser.TABLE + " u ON u.USERID = s.USER_ID LEFT JOIN "
				+ CrmOrg.TABLE + " o ON o.ORGID = u.idDep WHERE 1=1 ");
		if (!StringUtil.isEmpty(searchYear)) {
			sb.append(" AND s.assessment_year = " + Integer.parseInt(searchYear));
		}
		filterOrgData(user, orgs, sb);
		sb.append(" order by o.sName desc");
		List<SelfAssessment> assessments = BizDb.queryList(sb.toString(), SelfAssessment.class);
		return assessments;
	}
}
