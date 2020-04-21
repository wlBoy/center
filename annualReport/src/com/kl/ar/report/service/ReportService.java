package com.kl.ar.report.service;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kl.ar.common.constant.Constant;
import com.kl.ar.common.constant.ReportStatus;
import com.kl.ar.common.constant.ReportType;
import com.kl.ar.common.constant.RoleType;
import com.kl.ar.common.db.BizDb;
import com.kl.ar.common.db.DBUtils;
import com.kl.ar.common.util.DataUtil;
import com.kl.ar.common.util.ExcelUtil;
import com.kl.ar.common.util.ResolveHtmlUtil;
import com.kl.ar.org.entity.CrmOrg;
import com.kl.ar.report.entity.AdminSuggest;
import com.kl.ar.report.entity.ProductSuggest;
import com.kl.ar.report.entity.ProjectSuggest;
import com.kl.ar.report.entity.Report;
import com.kl.ar.user.entity.CrmUser;

import koal.urm.client.util.StringUtil;

/**
 * 
 * @package:com.kl.ar.report.service
 * @Description: 报告的service层
 * @author: wanlei
 * @date: 2019年12月21日下午3:10:55
 */
public class ReportService {
	/**
	 * 获取本部门的所有人员的报告列表并进行分页处理
	 * 
	 * @param condMap
	 * @param user
	 * @param orgs
	 * @return
	 * @throws Exception
	 */
	public static String getReportData(Map<String, Object> condMap, CrmUser user, List<Map<String, Object>> orgs)
			throws Exception {
		int limit = Integer.valueOf(String.valueOf(condMap.get(Constant.LIMIT)));
		int offset = Integer.valueOf(String.valueOf(condMap.get(Constant.OFFSET)));
		// 查询条件
		String userName = String.valueOf(condMap.get(Constant.PARAM_USER_NAME));
		String type = String.valueOf(condMap.get(Constant.PARAM_TYPE));
		String pathid = String.valueOf(condMap.get(Constant.PARAM_PATHID));
		String status = String.valueOf(condMap.get(Constant.PARAM_STATUS));
		String reportYear = String.valueOf(condMap.get(Constant.PARAM_REPORT_YEAR));
		// 动态拼接查询SQL语句
		StringBuffer sb = new StringBuffer("SELECT s.*,u.sName AS userName,o.sName AS orgName FROM " + Report.TABLE
				+ " s LEFT JOIN " + CrmUser.TABLE + " u ON u.USERID = s.USER_ID LEFT JOIN " + CrmOrg.TABLE
				+ " o ON o.ORGID = u.idDep WHERE 1=1 ");
		if (StringUtils.isNotBlank(userName)) {
			sb.append(" AND u.sName LIKE '%" + userName + "%'");
		}
		if (StringUtils.isNotBlank(status)) {
			sb.append(" AND s.STATUS = " + status);
		} else {
			sb.append(" AND s.STATUS IN (" + ReportStatus.SUBMITED.getCode() + ","
					+ ReportStatus.APPROVAL_DENY.getCode() + ")");
		}
		if (StringUtils.isNotBlank(type)) {
			sb.append(" AND s.TYPE = '" + type + "'");
		}
		if (StringUtils.isNotBlank(reportYear)) {
			sb.append(" AND s.REPORT_YEAR = '" + reportYear + "'");
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
		List<Report> reports = BizDb.queryList(sb.toString(), Report.class);
		return DataUtil.bulidJsonData(total, reports);
	}

	/**
	 * 修改总结状态为提交，并解析提交表格中的数据
	 * 
	 * @param repotId
	 * @param userId
	 * @throws Exception
	 */
	public static void submitReport(String repotId, String userId) throws Exception {
		Connection conn = null;
		try {
			conn = DBUtils.getConn();
			conn.setAutoCommit(false);
			// 更新状态
			QueryRunner qr = new QueryRunner();
			String sql = "UPDATE " + Report.TABLE + " SET STATUS=? WHERE ID=?";
			BizDb.log(sql, ReportStatus.SUBMITED.getCode(), repotId);
			qr.update(conn, sql, ReportStatus.SUBMITED.getCode(), repotId);
			// 解析数据并分别保存
			resolveDataAndSave(conn, repotId, userId, qr);
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			DBUtils.close(conn);
		}
	}

	/**
	 * 更新总结
	 * 
	 * @param repotId
	 * @param userId
	 * @throws Exception
	 */
	public static void updateReport(String sql, String userId, String reportId, String status, Object... params)
			throws Exception {
		Connection conn = null;
		try {
			conn = DBUtils.getConn();
			conn.setAutoCommit(false);
			// 更新状态
			QueryRunner qr = new QueryRunner();
			BizDb.log(sql, params);
			qr.update(conn, sql, params);
			if (status.equals(ReportStatus.SUBMITED.getCode())) {
				// 解析数据并分别保存
				resolveDataAndSave(conn, reportId, userId, qr);
			}
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			DBUtils.close(conn);
		}
	}

	/**
	 * 解析各模块的html内容并分表存储
	 * 
	 * @param conn
	 * @param repotId
	 * @param userId
	 * @param qr
	 * @throws Exception
	 */
	private static void resolveDataAndSave(Connection conn, String repotId, String userId, QueryRunner qr)
			throws Exception {
		String sql = "SELECT * FROM " + Report.TABLE + " WHERE ID=?";
		Report report = BizDb.queryOneRow(conn, sql, Report.class, repotId);
		// 解析管理建议
		// 后期需要添加时间条件
		qr.update(conn, "DELETE FROM " + AdminSuggest.TABLE + " WHERE REPORT_ID=?", repotId);
		List<AdminSuggest> adminSuggestList = ResolveHtmlUtil.resolveAdminSuggest(report.getADMIN_SUGGEST(), userId,
				repotId);
		for (AdminSuggest adminSuggest : adminSuggestList) {
			BizDb.insert(adminSuggest, conn, AdminSuggest.TABLE);
		}
		// 解析项目建议
		// 后期需要添加时间条件
		qr.update(conn, "DELETE FROM " + ProjectSuggest.TABLE + " WHERE REPORT_ID=?", repotId);
		List<ProjectSuggest> projectSuggestList = ResolveHtmlUtil.resolvePorjectSuggest(report.getPROJECT_SUGGEST(),
				userId, repotId);
		for (ProjectSuggest projectSuggest : projectSuggestList) {
			BizDb.insert(projectSuggest, conn, ProjectSuggest.TABLE);
		}
		// 接下产品建议
		// 后期需要添加时间条件
		qr.update(conn, "DELETE FROM " + ProductSuggest.TABLE + " WHERE REPORT_ID=?", repotId);
		List<ProductSuggest> productSuggestList = ResolveHtmlUtil.resolveProductSuggest(report.getPRODUCT_SUGGEST(),
				userId, repotId);
		for (ProductSuggest productSuggest : productSuggestList) {
			BizDb.insert(productSuggest, conn, ProductSuggest.TABLE);
		}

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
	 * 查询所有的岗位职责数据列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public static List<Report> findReport(String searchYear, CrmUser user, List<Map<String, Object>> orgs)
			throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT r.*,u.sName AS userName,o.sName AS orgName FROM " + Report.TABLE + " r LEFT JOIN "
				+ CrmUser.TABLE + " u ON u.USERID = r.USER_ID LEFT JOIN " + CrmOrg.TABLE
				+ " o ON o.ORGID = u.idDep WHERE STATUS = ?");
		if (!StringUtil.isEmpty(searchYear)) {
			sql.append(" AND report_year = " + Integer.parseInt(searchYear));
		}
		filterOrgData(user, orgs, sql);
		sql.append(" order by o.sName desc");
		return BizDb.queryList(sql.toString(), Report.class, ReportStatus.SUBMITED.getCode());
	}

	/**
	 * 查询所有的产品建议数据列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public static List<ProductSuggest> findProductSuggest(String searchYear, CrmUser user,
			List<Map<String, Object>> orgs) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT s.*,u.sName AS userName,o.sName AS orgName,r.type AS type,r.report_year AS reportYear FROM "
				+ ProductSuggest.TABLE + " s JOIN " + Report.TABLE + " r ON s.REPORT_ID=r.ID LEFT JOIN " + CrmUser.TABLE
				+ " u ON u.USERID = s.USER_ID LEFT JOIN " + CrmOrg.TABLE + " o ON o.ORGID = u.idDep where 1=1");
		if (!StringUtil.isEmpty(searchYear)) {
			sql.append(" AND r.report_year = " + Integer.parseInt(searchYear));
		}
		filterOrgData(user, orgs, sql);
		sql.append(" order by o.sName desc");
		return BizDb.queryList(sql.toString(), ProductSuggest.class);
	}

	/**
	 * 查询所有的项目建议数据列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public static List<ProjectSuggest> findProjectSuggest(String searchYear, CrmUser user,
			List<Map<String, Object>> orgs) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT s.*,u.sName AS userName,o.sName AS orgName,r.type AS type,r.report_year AS reportYear FROM "
				+ ProjectSuggest.TABLE + " s JOIN " + Report.TABLE + " r ON s.REPORT_ID=r.ID LEFT JOIN " + CrmUser.TABLE
				+ " u ON u.USERID = s.USER_ID LEFT JOIN " + CrmOrg.TABLE + " o ON o.ORGID = u.idDep where 1=1");
		if (!StringUtil.isEmpty(searchYear)) {
			sql.append(" AND r.report_year = " + Integer.parseInt(searchYear));
		}
		filterOrgData(user, orgs, sql);
		sql.append(" order by o.sName desc");
		return BizDb.queryList(sql.toString(), ProjectSuggest.class);
	}

	/**
	 * 查询所有的管理建议数据列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public static List<AdminSuggest> findAdminSuggest(String searchYear, CrmUser user, List<Map<String, Object>> orgs)
			throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT s.*,u.sName AS userName,o.sName AS orgName,r.type AS type,r.report_year AS reportYear FROM "
				+ AdminSuggest.TABLE + " s JOIN " + Report.TABLE + " r ON s.REPORT_ID=r.ID LEFT JOIN " + CrmUser.TABLE
				+ " u ON u.USERID = s.USER_ID LEFT JOIN " + CrmOrg.TABLE + " o ON o.ORGID = u.idDep where 1=1");
		if (!StringUtil.isEmpty(searchYear)) {
			sql.append(" AND r.report_year = " + Integer.parseInt(searchYear));
		}
		filterOrgData(user, orgs, sql);
		sql.append(" order by o.sName desc");
		return BizDb.queryList(sql.toString(), AdminSuggest.class);
	}

	/**
	 * 导出多个sheet的EXCEL表格
	 * 
	 * @param bot
	 * @return
	 * @throws Exception
	 */
	public static XSSFWorkbook exportExcel(String searchYear, CrmUser user, List<Map<String, Object>> orgs)
			throws Exception {
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 定义EXCEL表格的头标题
		String[] reportHeaders = { "序号", "姓名", "部门", "岗位职责", "个人目标", "进步收获", "问题不足", "明年目标", "报告类型", "报告年份" };
		String[] productSuggestHeaders = { "序号", "姓名", "部门", "产品名称", "问题与缺陷", "改进方向与建议措施", "借鉴来源", "报告类型", "报告年份" };
		String[] projectSuggestHeaders = { "序号", "姓名", "部门", "项目名称", "营销、研发、检测、实施的不足", "管理、合作建议", "报告类型", "报告年份" };
		String[] adminSuggestHeaders = { "序号", "姓名", "部门", "涉及部门与内容", "制度、措施、执行不全之处", "您的建议", "报告类型", "报告年份" };

		// 管理建议表格数据列表
		List<AdminSuggest> adminSuggestList = findAdminSuggest(searchYear, user, orgs);
		// 项目建议表格数据列表
		List<ProjectSuggest> projectSuggestList = findProjectSuggest(searchYear, user, orgs);
		// 产品建议表格数据列表
		List<ProductSuggest> productSuggestList = findProductSuggest(searchYear, user, orgs);
		// 报告其他表格数据列表
		List<Report> reportList = findReport(searchYear, user, orgs);

		// 生成多个sheet
		ExcelUtil.createExcel(workbook, 0, "报告内容", reportHeaders, covertReportData(reportList));
		ExcelUtil.createExcel(workbook, 1, "产品建议", productSuggestHeaders, covertProductSuggestData(productSuggestList));
		ExcelUtil.createExcel(workbook, 2, "项目建议", projectSuggestHeaders, covertProjectSuggestData(projectSuggestList));
		ExcelUtil.createExcel(workbook, 3, "管理建议", adminSuggestHeaders, covertAdminSuggestData(adminSuggestList));

		return workbook;
	}

	/**
	 * 将List<AdminSuggest>转换成List<List<String>>
	 * 
	 * @param adminSuggestList
	 * @return
	 */
	private static List<List<String>> covertAdminSuggestData(List<AdminSuggest> adminSuggestList) {
		int count = 0;
		List<List<String>> listSum = new ArrayList<List<String>>();
		for (AdminSuggest adminSuggest : adminSuggestList) {
			count++;
			List<String> list = new ArrayList<String>();
			list.add(String.valueOf(count));
			list.add(adminSuggest.getUserName());
			list.add(adminSuggest.getOrgName());
			list.add(adminSuggest.getNAME());
			list.add(adminSuggest.getCONTENT());
			list.add(adminSuggest.getSUGGEST());
			list.add(ReportType.getCodeByDesc(adminSuggest.getTYPE()));
			list.add(String.valueOf(adminSuggest.getReportYear()));
			listSum.add(list);
		}
		return listSum;
	}

	/**
	 * 将List<ProjectSuggest>转换成List<List<String>>
	 * 
	 * @param projectSuggestList
	 * @return
	 */
	private static List<List<String>> covertProjectSuggestData(List<ProjectSuggest> projectSuggestList) {
		int count = 0;
		List<List<String>> listSum = new ArrayList<List<String>>();
		for (ProjectSuggest projectSuggest : projectSuggestList) {
			count++;
			List<String> list = new ArrayList<String>();
			list.add(String.valueOf(count));
			list.add(projectSuggest.getUserName());
			list.add(projectSuggest.getOrgName());
			list.add(projectSuggest.getNAME());
			list.add(projectSuggest.getCONTENT());
			list.add(projectSuggest.getSUGGEST());
			list.add(ReportType.getCodeByDesc(projectSuggest.getTYPE()));
			list.add(String.valueOf(projectSuggest.getReportYear()));
			listSum.add(list);
		}
		return listSum;
	}

	/**
	 * 将List<productSuggestList>转换成List<List<String>>
	 * 
	 * @param productSuggestList
	 * @return
	 */
	private static List<List<String>> covertProductSuggestData(List<ProductSuggest> productSuggestList) {
		int count = 0;
		List<List<String>> listSum = new ArrayList<List<String>>();
		for (ProductSuggest productSuggest : productSuggestList) {
			count++;
			List<String> list = new ArrayList<String>();
			list.add(String.valueOf(count));
			list.add(productSuggest.getUserName());
			list.add(productSuggest.getOrgName());
			list.add(productSuggest.getNAME());
			list.add(productSuggest.getCONTENT());
			list.add(productSuggest.getSUGGEST());
			list.add(productSuggest.getSOURCE());
			list.add(ReportType.getCodeByDesc(productSuggest.getTYPE()));
			list.add(String.valueOf(productSuggest.getReportYear()));
			listSum.add(list);
		}
		return listSum;
	}

	/**
	 * 将List<reportList>转换成List<List<String>>
	 * 
	 * @param reportList
	 * @return
	 */
	private static List<List<String>> covertReportData(List<Report> reportList) {
		int count = 0;
		List<List<String>> listSum = new ArrayList<List<String>>();
		for (Report report : reportList) {
			count++;
			List<String> list = new ArrayList<String>();
			list.add(String.valueOf(count));
			list.add(report.getUserName());
			list.add(report.getOrgName());
			list.add(report.getRESPONSIBILITIES());
			list.add(report.getPERSONAL_GOALS());
			list.add(report.getPROGRESS());
			list.add(report.getPROBLEM());
			list.add(report.getTARGET());
			list.add(ReportType.getCodeByDesc(report.getTYPE()));
			list.add(report.getREPORT_YEAR());
			listSum.add(list);
		}
		return listSum;
	}

	/**
	 * 本地测试导出EXCEL
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		File file = new File("D:/test.xls");
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			XSSFWorkbook workbook = ReportService.exportExcel("2018", null, null);
			// 原理就是将所有的数据一起写入，然后再关闭输入流。
			workbook.write(out);
			workbook.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
