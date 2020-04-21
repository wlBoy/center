package com.kl.ar.report.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kl.ar.common.cfg.ConfigUtil;
import com.kl.ar.common.constant.Constant;
import com.kl.ar.common.constant.ReportStatus;
import com.kl.ar.common.constant.ReportType;
import com.kl.ar.common.constant.View;
import com.kl.ar.common.controller.BaseController;
import com.kl.ar.common.db.BizDb;
import com.kl.ar.common.util.DataUtil;
import com.kl.ar.common.util.DateUtil;
import com.kl.ar.common.util.ExcelUtil;
import com.kl.ar.common.util.FileUtil;
import com.kl.ar.report.entity.Report;
import com.kl.ar.report.service.ReportService;
import com.kl.ar.user.entity.CrmUser;

import koal.urm.client.util.StringUtil;

/**
 * 
 * @package:com.kl.ar.report.controller
 * @Description: 报告的控制器
 * @author: wanlei
 * @date: 2019年12月21日下午3:03:18
 */
@WebServlet("/rs")
public class ReportServlet extends BaseController {
	private static final Log logger = LogFactory.getLog(ReportServlet.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter(Constant.OP);
		// 获取登录的用户ID
		String userId = getUserId(request);
		try {
			if ("toIndex".equals(op)) {
				// 转到首页
				toIndex(request, response, userId);
			} else if ("toSaveReport".equals(op)) {
				// 转到保存报告页面
				toSaveReport(request, response);
			} else if ("saveReport".equals(op)) {
				// 保存报告
				saveReport(request, response, userId);
			} else if ("updateReport".equals(op)) {
				// 修改报告
				updateReport(request, response);
			} else if ("delReport".equals(op)) {
				// 删除报告
				delReport(request, response);
			} else if ("submitReport".equals(op)) {
				// 提交报告
				submitReport(request, response);
			} else if ("denyReport".equals(op)) {
				// 驳回报告
				denyReport(request, response);
			} else if ("getReportData".equals(op)) {
				// 查询所有总结列表
				getReportData(request, response);
			} else if ("toReportDetail".equals(op)) {
				// 转到报告详情页面
				toReportDetail(request, response);
			} else if ("toUserReportDetail".equals(op)) {
				// 转到用户所有报告详情页面
				toUserReportDetail(request, response);
			} else if ("exportReport".equals(op)) {
				// 导出本部门的所有报告数据至EXCEL中
				exportReport(request, response);
			} else if ("toReportList".equals(op)) {
				// 转到所有总结列表首页
				request.getRequestDispatcher(View.REPORT_LIST_JSP).forward(request, response);
			} else if ("toExportAll".equals(op)) {
				// 转到ECXEL导出页面
				request.getRequestDispatcher(View.EXPORT_ALL_JSP).forward(request, response);
			}
		} catch (Exception e) {
			logger.error("报告控制器内部错误，原因为:" + e);
			request.setAttribute(Constant.ERROR_INFO, Constant.TIP_SYSTEM_ERROR);
			request.getRequestDispatcher(View.ERROR_JSP).forward(request, response);
		}
	}

	/**
	 * 转到系统首页
	 * 
	 * @param request
	 * @param response
	 * @param userId
	 * @throws Exception
	 */
	private void toIndex(HttpServletRequest request, HttpServletResponse response, String userId) throws Exception {
		String userName = getUserName(request);
		// 从配置文件中取出当前年份
		String currentYear = ConfigUtil.getInstance().loadCfgMap().get(Constant.CURRENT_YEAR);
		String enableVote = ConfigUtil.getInstance().loadCfgMap().get(Constant.ENABLE_VOTE);
		request.getSession().setAttribute(Constant.CURRENT_YEAR, currentYear);
		request.getSession().setAttribute(Constant.ENABLE_VOTE, enableVote);
		// 获取可选择的年份MAP
		Map<String, String> selectYearMap = DataUtil.getSelectYearList();
		request.getSession().setAttribute(Constant.SELECT_YEAR_MAP, selectYearMap);
		// 根据用户ID查询该用户的所有报告
		List<Report> reportList = BizDb.queryList(
				"select * from " + Report.TABLE + " where USER_ID=? order by add_time desc", Report.class, userId);
		request.setAttribute(Constant.REPORT_LIST, reportList);
		// 判断该用户今年有没有填写各类型的报告
		boolean hasSale = hasCertianType(currentYear, ReportType.SALE.getCode(), reportList);
		boolean hasTec = hasCertianType(currentYear, ReportType.TEC.getCode(), reportList);
		boolean hasAdministration = hasCertianType(currentYear, ReportType.ADMINISTRATION.getCode(), reportList);
		request.setAttribute(Constant.HAS_SALE, hasSale);
		request.setAttribute(Constant.HAS_TEC, hasTec);
		request.setAttribute(Constant.HAS_ADMINISTRATION, hasAdministration);
		logger.info(userName + "登录成功，查询到该用户总共填写了" + reportList.size() + "个报告，今年填写报告情况为:hasSale->" + hasSale
				+ "，hasTec->" + hasTec + "，hasAdministration->" + hasAdministration);
		request.getRequestDispatcher(View.INDEX_JSP).forward(request, response);
	}

	/**
	 * 判断今年的报告列表中是否含有指定类型的报告
	 * 
	 * @param type
	 *            指定类型
	 * @param reports
	 *            报告列表中
	 * @return
	 */
	private boolean hasCertianType(String currentYear, String type, List<Report> reports) {
		if (reports != null) {
			for (Report report : reports) {
				// 在当前年份的报告中判断含有指定类型的报告
				if (currentYear.equals(report.getREPORT_YEAR())) {
					if (type.equals(report.getTYPE())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 转到保存报告页面
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void toSaveReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 从配置文件中取出当前年份
		Integer currentYear = Integer.parseInt(ConfigUtil.getInstance().loadCfgMap().get(Constant.CURRENT_YEAR));
		String userId = getUserId(request);
		String type = request.getParameter(Constant.TYPE);// 报告类型
		// 查询数据库该用户今年是否有该类型报告的记录，有的话直接用该报告的id，没有的话生成新的UUID，防止用户刷新报告页面，生成多个同样类型的报告
		String uuid = "";
		Report report = BizDb.queryOneRow(
				"select * from " + Report.TABLE + " where USER_ID=? AND type=? AND REPORT_YEAR=?", Report.class, userId,
				type, currentYear);
		if (report == null) {
			uuid = UUID.randomUUID().toString();
		} else {
			// 不为空回显其保存的数据
			request.setAttribute(Constant.REPORT, report);
			uuid = report.getID();
		}
		request.setAttribute(Constant.ID, uuid);
		request.setAttribute(Constant.TYPE, type);
		request.setAttribute("add", "add");
		request.getRequestDispatcher(View.REPORT_JSP).forward(request, response);
	}

	/**
	 * 保存报告
	 * 
	 * @param request
	 * @param response
	 * @param userId
	 * @throws IOException
	 */
	private void saveReport(HttpServletRequest request, HttpServletResponse response, String userId)
			throws IOException {
		Map<String, String> data = new HashMap<String, String>();
		data.put(Constant.AJAX_RESULT, Constant.SUCCESS_RESULT);
		try {
			String id = request.getParameter(Constant.ID);// 模板ID
			String type = request.getParameter(Constant.TYPE);// 模板类型
			saveAllPart(request, userId, id, type);
		} catch (Exception e) {
			data.put(Constant.AJAX_RESULT, Constant.FAILURE_RESULT);
			logger.error("保存总结时失败，原因为:" + e);
		}
		response.getWriter().write(gson.toJson(data));
	}

	/**
	 * 保存全部的报告内容
	 * 
	 * @param request
	 * @param userId
	 * @param id
	 * @param type
	 * @throws Exception
	 */
	private void saveAllPart(HttpServletRequest request, String userId, String id, String type) throws Exception {
		// 从配置文件中取出当前年份
		String currentYear = ConfigUtil.getInstance().loadCfgMap().get(Constant.CURRENT_YEAR);
		// 前台传过来的报告状态和动作类型,第一次保存时没有值，初始化为未提交状态
		String reportStatus = request.getParameter("reportStatus");// 报告状态
		if(StringUtil.isEmpty(reportStatus)) {
			reportStatus = ReportStatus.UN_SUBMITED.getCode();
		}
		String actionType = request.getParameter("actionType");// 动作类型
		reportStatus = "save".equals(actionType) ? reportStatus : ReportStatus.SUBMITED.getCode();
		// 解析数据
		String RESPONSIBILITIES = request.getParameter("RESPONSIBILITIES");
		String DEVELOP_WORK = request.getParameter("DEVELOP_WORK");
		String DEPLOY_WORK = request.getParameter("DEPLOY_WORK");
		String TEST_WORK = request.getParameter("TEST_WORK");
		String SALE_HISTORY = request.getParameter("SALE_HISTORY");
		String NEW_CUSTOMER = request.getParameter("NEW_CUSTOMER");
		String BIG_PROJECT = request.getParameter("BIG_PROJECT");
		String ADMIN_WORK = request.getParameter("ADMIN_WORK");
		String SECRECT_WORK = request.getParameter("SECRECT_WORK");
		String PERSONAL_GOALS = request.getParameter("PERSONAL_GOALS");
		String PROGRESS = request.getParameter("PROGRESS");
		String PROBLEM = request.getParameter("PROBLEM");
		String TARGET = request.getParameter("TARGET");
		String PRODUCT_SUGGEST = request.getParameter("PRODUCT_SUGGEST");
		String PROJECT_SUGGEST = request.getParameter("PROJECT_SUGGEST");
		String ADMIN_SUGGEST = request.getParameter("ADMIN_SUGGEST");
		// 根据报告ID查询该报告是否存在
		Report report = BizDb.queryOneRow("select * from " + Report.TABLE + " where id=?", Report.class, id);
		if (report != null) {
			// 当报告状态为已提交时，不允许修改了，防止自动保存覆盖了已提交的数据
			if (ReportStatus.SUBMITED.getCode().equals(report.getSTATUS())) {
				return;
			}
			// 更新报告
			String sql = "update " + Report.TABLE
					+ " set RESPONSIBILITIES=?,DEVELOP_WORK=?,DEPLOY_WORK=?,TEST_WORK=?,SALE_HISTORY=?,NEW_CUSTOMER=?,BIG_PROJECT=?,ADMIN_WORK=?,SECRECT_WORK=?,PERSONAL_GOALS=?,PROGRESS=?,TARGET=?,PROBLEM=?,PRODUCT_SUGGEST=?,PROJECT_SUGGEST=?,ADMIN_SUGGEST=?,STATUS=? where ID=?";
			ReportService.updateReport(sql, userId, id, reportStatus, RESPONSIBILITIES, DEVELOP_WORK, DEPLOY_WORK,
					TEST_WORK, SALE_HISTORY, NEW_CUSTOMER, BIG_PROJECT, ADMIN_WORK, SECRECT_WORK, PERSONAL_GOALS,
					PROGRESS, TARGET, PROBLEM, PRODUCT_SUGGEST, PROJECT_SUGGEST, ADMIN_SUGGEST, reportStatus, id);
		} else {
			// 插入报告
			Report re = new Report();
			re.setID(id);
			re.setUSER_ID(userId);
			re.setTYPE(type);
			re.setRESPONSIBILITIES(RESPONSIBILITIES);
			re.setDEVELOP_WORK(DEVELOP_WORK);
			re.setDEPLOY_WORK(DEPLOY_WORK);
			re.setTEST_WORK(TEST_WORK);
			re.setSALE_HISTORY(SALE_HISTORY);
			re.setNEW_CUSTOMER(NEW_CUSTOMER);
			re.setBIG_PROJECT(BIG_PROJECT);
			re.setADMIN_WORK(ADMIN_WORK);
			re.setSECRECT_WORK(SECRECT_WORK);
			re.setPERSONAL_GOALS(PERSONAL_GOALS);
			re.setPROGRESS(PROGRESS);
			re.setTARGET(TARGET);
			re.setPROBLEM(PROBLEM);
			re.setPRODUCT_SUGGEST(PRODUCT_SUGGEST);
			re.setPROJECT_SUGGEST(PROJECT_SUGGEST);
			re.setADMIN_SUGGEST(ADMIN_SUGGEST);
			re.setSTATUS(reportStatus);
			re.setADD_TIME(new Date());
			re.setREPORT_YEAR(currentYear);
			BizDb.insert(re, Report.TABLE);
		}
	}

	/**
	 * 删除报告
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void delReport(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, String> data = new HashMap<String, String>();
		data.put(Constant.AJAX_RESULT, Constant.SUCCESS_RESULT);
		try {
			String id = request.getParameter(Constant.ID);
			BizDb.update("DELETE from " + Report.TABLE + " WHERE ID=?", id);
		} catch (Exception e) {
			data.put(Constant.AJAX_RESULT, Constant.FAILURE_RESULT);
			logger.error("删除总结时失败，原因为:" + e);
		}
		response.getWriter().write(gson.toJson(data));
	}

	/**
	 * 部门经理驳回该报告
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void denyReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> data = new HashMap<String, String>();
		data.put(Constant.AJAX_RESULT, Constant.SUCCESS_RESULT);
		String repotId = request.getParameter(Constant.ID);
		try {
			BizDb.update("update " + Report.TABLE + " set STATUS=? where ID=?", ReportStatus.APPROVAL_DENY.getCode(),
					repotId);
		} catch (Exception e) {
			data.put(Constant.AJAX_RESULT, Constant.FAILURE_RESULT);
			logger.error("驳回总结时失败，原因为:" + e);
		}
		response.getWriter().write(gson.toJson(data));
	}

	/**
	 * 提交报告
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void submitReport(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, String> data = new HashMap<String, String>();
		data.put(Constant.AJAX_RESULT, Constant.SUCCESS_RESULT);
		try {
			String repotId = request.getParameter(Constant.ID);
			ReportService.submitReport(repotId, getUserId(request));
		} catch (Exception e) {
			data.put(Constant.AJAX_RESULT, Constant.FAILURE_RESULT);
			logger.error("快速提交总结时失败，原因为:" + e);
		}
		response.getWriter().write(gson.toJson(data));
	}

	/**
	 * 获取总结列表json数据
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 * @throws IOException
	 */
	private void getReportData(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
		Map<String, Object> condMap = new HashMap<String, Object>();
		int limit = Integer.valueOf(request.getParameter(Constant.LIMIT));
		int offset = Integer.valueOf(request.getParameter(Constant.OFFSET));
		condMap.put(Constant.LIMIT, limit);
		condMap.put(Constant.OFFSET, offset);
		// 查询条件
		String userName = request.getParameter(Constant.PARAM_USER_NAME);
		String type = request.getParameter(Constant.TYPE);
		String pathid = request.getParameter(Constant.PARAM_PATHID);
		String status = request.getParameter(Constant.PARAM_STATUS);
		String reportYear = request.getParameter(Constant.PARAM_REPORT_YEAR);
		condMap.put(Constant.PARAM_USER_NAME, userName);
		condMap.put(Constant.TYPE, type);
		condMap.put(Constant.PARAM_PATHID, pathid);
		condMap.put(Constant.PARAM_STATUS, status);
		condMap.put(Constant.PARAM_REPORT_YEAR, reportYear);
		logger.info("获取部门总结列表时=>查询条件为:" + condMap);
		response.getWriter().write(ReportService.getReportData(condMap, getUser(request), getOrgs(request)));
	}

	/**
	 * 修改报告
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 * @throws ServletException
	 * @throws IOException
	 */
	private void updateReport(HttpServletRequest request, HttpServletResponse response)
			throws Exception, ServletException, IOException {
		String id = request.getParameter(Constant.ID);
		Report report = BizDb.queryOneRow("select * from " + Report.TABLE + " WHERE ID=?", Report.class, id);
		request.setAttribute(Constant.REPORT, report);
		request.setAttribute(Constant.TYPE, report.getTYPE());
		request.setAttribute(Constant.ID, id);
		request.getRequestDispatcher(View.REPORT_JSP).forward(request, response);
	}

	/**
	 * 转到报告详情页面
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 * @throws ServletException
	 * @throws IOException
	 */
	private void toReportDetail(HttpServletRequest request, HttpServletResponse response)
			throws Exception, ServletException, IOException {
		String id = request.getParameter(Constant.ID);
		Report report = BizDb.queryOneRow("select * from " + Report.TABLE + " WHERE ID=?", Report.class, id);
		request.setAttribute(Constant.REPORT, report);
		request.setAttribute(Constant.TYPE, report.getTYPE());
		request.setAttribute(Constant.ID, id);
		request.getRequestDispatcher(View.REPORT_DETAIL_JSP).forward(request, response);
	}

	/**
	 * 部门经理查看用户的报告
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 * @throws ServletException
	 * @throws IOException
	 */
	private void toUserReportDetail(HttpServletRequest request, HttpServletResponse response)
			throws Exception, ServletException, IOException {
		String userId = request.getParameter(Constant.USER_ID);
		// 根据用户ID查询用户信息和所有的报告信息
		CrmUser user = BizDb.queryOneRow("select * from " + CrmUser.TABLE + " WHERE USERID=?", CrmUser.class, userId);
		List<Report> reportList = BizDb.queryList("select * from " + Report.TABLE + " WHERE USER_ID=?", Report.class,
				userId);
		logger.info("查询到" + user.getsName() + "用户共有" + reportList.size() + "个报告!");
		if (reportList.size() == 1) {
			// 只有一个跳转到单个报告详情页面
			request.setAttribute(Constant.REPORT, reportList.get(0));
			request.setAttribute(Constant.TYPE, reportList.get(0).getTYPE());
			request.getRequestDispatcher(View.REPORT_DETAIL_JSP).forward(request, response);
		} else {
			// 多个报告时，展示该用户的所有报告
			request.setAttribute(Constant.REPORT_LIST, reportList);
			request.setAttribute(Constant.USER, user);
			request.getRequestDispatcher(View.REPORT_DETAIL_LIST_JSP).forward(request, response);
		}
	}

	/**
	 * 导出本部门的当前年份的所有报告数据至EXCEL中
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void exportReport(HttpServletRequest request, HttpServletResponse response) {
		OutputStream output = null;
		try {
			String searchYear = request.getParameter(Constant.SEARCH_YEAR);
			// 获取文件下载名
			String fileName = FileUtil.getFileName(request,
					"年终总结报告汇总_" + DateUtil.formatDateTime(DateUtil.SHORT_PATTERN_DATE_TIME));
			// 防止下载的文件名中文乱码
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ExcelUtil.excel2003L);
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			// 初始化servlet输出流
			output = response.getOutputStream();
			BufferedOutputStream bot = new BufferedOutputStream(output);
			// 将生成的EXCEL写到输出流中去
			XSSFWorkbook workbook = ReportService.exportExcel(searchYear, getUser(request), getOrgs(request));
			workbook.write(bot);
			bot.flush();
			workbook.close();
			bot.close();
		} catch (Exception e) {
			logger.error("导出报告至EXCEL中失败，原因为:" + e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
