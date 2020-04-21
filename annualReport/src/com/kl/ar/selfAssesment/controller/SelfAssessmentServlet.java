package com.kl.ar.selfAssesment.controller;

import com.kl.ar.common.cfg.ConfigUtil;
import com.kl.ar.common.constant.Constant;
import com.kl.ar.common.constant.View;
import com.kl.ar.common.controller.BaseController;
import com.kl.ar.common.db.BizDb;
import com.kl.ar.common.util.DateUtil;
import com.kl.ar.common.util.ExcelUtil;
import com.kl.ar.common.util.FileUtil;
import com.kl.ar.org.controller.OrgServlet;
import com.kl.ar.selfAssesment.entity.SelfAssessment;
import com.kl.ar.selfAssesment.service.AssessmentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @package:com.kl.ar.selfAssesment.controller
 * @Description: 自评的控制器
 * @author: wanlei
 * @date: 2019年12月21日下午3:13:19
 */
@WebServlet("/self")
public class SelfAssessmentServlet extends BaseController {
	private static final Log logger = LogFactory.getLog(OrgServlet.class);
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter(Constant.OP);
		String userId = getUserId(request);
		try {
			switch (op) {
			// 转到我的自评列表页面
			case "toMyValueList":
				toMyValueList(request, response, userId);
				break;
			// 转到我的自评页面
			case "toMyValue":
				toMyValue(request, response, userId);
				break;
			// 转到部门评分页面
			case "toDepAssessment":
				toDepAssessment(request, response);
				break;
			// 转到自评详情页面
			case "toAssessmentDetail":
				toAssessmentDetail(request, response);
				break;
			// 保存自评
			case "saveAssessment":
				saveAssessment(request, response, userId);
				break;
			// 保存部门评分
			case "saveDepAssessment":
				saveDepAssessment(request, response, userId);
				break;
			// 转到部门评分列表
			case "toAssessmentList":
				request.getRequestDispatcher(View.ASSESSMENT_LIST_JSP).forward(request, response);
				break;
			// 获取本部门所有的评分数据
			case "getAssessmentData":
				getAssessmentData(request, response);
				break;
			// 导出本部门所有的评分数据
			case "exportAll":
				exportAll(request, response);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			logger.error("自评控制器内部错误，原因为:" + e);
			request.setAttribute(Constant.ERROR_INFO, Constant.TIP_SYSTEM_ERROR);
			request.getRequestDispatcher(View.ERROR_JSP).forward(request, response);
		}

	}

	/**
	 * 转到我的自评列表页面
	 * 
	 * @param request
	 * @param response
	 * @param userId
	 * @throws IOException
	 * @throws ServletException
	 */
	private void toMyValueList(HttpServletRequest request, HttpServletResponse response, String userId)
			throws Exception {
		// 从配置文件中取出当前年份
		String currentYear = ConfigUtil.getInstance().loadCfgMap().get(Constant.CURRENT_YEAR);
		// 根据用户ID查询该用户的所有自评
		List<SelfAssessment> assessmentList = BizDb.queryList(
				"select * from " + SelfAssessment.TABLE + " where USER_ID=? order by add_time desc",
				SelfAssessment.class, userId);
		request.setAttribute(Constant.ASSESSMENT_LIST, assessmentList);
		boolean hasAssessment = hasAssessment(currentYear, assessmentList);
		// 判断该用户今年有没有填写自评
		request.setAttribute(Constant.HAS_ASSESSMENT, hasAssessment);
		logger.info("查询到该用户总共填写了" + assessmentList.size() + "个自评，今年填写自评情况为:hasAssessment->" + hasAssessment);
		request.getRequestDispatcher(View.MY_VALUE_LIST_JSP).forward(request, response);
	}

	/**
	 * 判断今年是否已写自评
	 * 
	 * @param reports
	 *            报告列表中
	 * @return
	 */
	private boolean hasAssessment(String currentYear, List<SelfAssessment> assessmentList) {
		if (assessmentList != null) {
			for (SelfAssessment assessment : assessmentList) {
				if (currentYear.equals(assessment.getASSESSMENT_YEAR())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 保存部门评分
	 * 
	 * @param request
	 * @param response
	 * @param userId
	 * @throws IOException
	 */
	private void saveDepAssessment(HttpServletRequest request, HttpServletResponse response, String userId)
			throws IOException {
		Map<String, String> depMap = AssessmentService.saveDepAssessment(request, userId);
		response.getWriter().write(gson.toJson(depMap));
	}

	/**
	 * 保存自评
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void saveAssessment(HttpServletRequest request, HttpServletResponse response, String userId)
			throws IOException {
		Map<String, String> result = AssessmentService.saveAssessment(request, userId);
		response.getWriter().write(gson.toJson(result));
	}

	/**
	 * 转到自评详情页面
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void toAssessmentDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String assessmentId = request.getParameter(Constant.ID);
		SelfAssessment selfAssessment = AssessmentService.getSelfAssessmentByCond(
				"SELECT * FROM " + SelfAssessment.TABLE + " WHERE SELF_ASSESSMENT_ID=?", assessmentId);
		request.setAttribute(Constant.ASSESSMENT_DETAIL, selfAssessment);
		request.getRequestDispatcher(View.DETAIL_MY_VALUE_JSP).forward(request, response);
	}

	/**
	 * 转到部门评分页面
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void toDepAssessment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String assessmentId = request.getParameter(Constant.ID);
		SelfAssessment selfAssessment = AssessmentService.getSelfAssessmentByCond(
				"SELECT * FROM " + SelfAssessment.TABLE + " WHERE SELF_ASSESSMENT_ID=?", assessmentId);
		request.setAttribute(Constant.SELF_ASSESSMENT, selfAssessment);
		request.getRequestDispatcher(View.DEPT_MY_VALUE_JSP).forward(request, response);
	}

	/**
	 * 转到我的自评页面
	 * 
	 * @param request
	 * @param response
	 * @param userId
	 * @throws IOException
	 * @throws ServletException
	 */
	private void toMyValue(HttpServletRequest request, HttpServletResponse response, String userId)
			throws ServletException, IOException {
		// 从配置文件中取出当前年份
		Integer currentYear = Integer.parseInt(ConfigUtil.getInstance().loadCfgMap().get(Constant.CURRENT_YEAR));
		SelfAssessment selfAssessment = AssessmentService.getSelfAssessmentByCond(
				"SELECT * FROM " + SelfAssessment.TABLE + " WHERE USER_ID=? AND ASSESSMENT_YEAR=?", userId,
				currentYear);
		request.setAttribute(Constant.SELF_ASSESSMENT, selfAssessment);
		request.getRequestDispatcher(View.MY_VALUE_JSP).forward(request, response);

	}

	/**
	 * 导出本部门人员的评分情况
	 * 
	 * @param request
	 * @param response
	 */
	private void exportAll(HttpServletRequest request, HttpServletResponse response) {
		OutputStream output = null;
		try {
			String searchYear = request.getParameter(Constant.SEARCH_YEAR);
			// 获取文件下载名
			String fileName = FileUtil.getFileName(request,
					"部门评分汇总_" + DateUtil.formatDateTime(DateUtil.SHORT_PATTERN_DATE_TIME));
			// 防止下载的文件名中文乱码
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ExcelUtil.excel2003L);
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			// 初始化servlet输出流
			output = response.getOutputStream();
			BufferedOutputStream bot = new BufferedOutputStream(output);
			// 将生成的EXCEL写到输出流中去
			XSSFWorkbook workbook = AssessmentService.exportExcel(searchYear, getUser(request), getOrgs(request));
			workbook.write(bot);
			bot.flush();
			workbook.close();
			bot.close();
		} catch (Exception e) {
			logger.error("导出部门评分失败，原因为:", e);
		}

	}

	/**
	 * 获取评分列表json数据
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 * @throws IOException
	 */
	public void getAssessmentData(HttpServletRequest request, HttpServletResponse response)
			throws IOException, Exception {
		Map<String, Object> condMap = new HashMap<String, Object>();
		int limit = Integer.valueOf(request.getParameter(Constant.LIMIT));
		int offset = Integer.valueOf(request.getParameter(Constant.OFFSET));
		condMap.put(Constant.LIMIT, limit);
		condMap.put(Constant.OFFSET, offset);
		// 查询条件
		String userName = request.getParameter(Constant.PARAM_USER_NAME);
		String pathid = request.getParameter(Constant.PARAM_PATHID);
		String status = request.getParameter(Constant.PARAM_STATUS);
		String assessmentYear = request.getParameter(Constant.PARAM_ASSESSMENT_YEAR);
		condMap.put(Constant.PARAM_USER_NAME, userName);
		condMap.put(Constant.PARAM_PATHID, pathid);
		condMap.put(Constant.PARAM_STATUS, status);
		condMap.put(Constant.PARAM_ASSESSMENT_YEAR, assessmentYear);
		logger.info("获取部门评分列表时=>查询条件为:" + condMap);
		response.getWriter().write(AssessmentService.getAssessmentData(condMap, getUser(request), getOrgs(request)));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
