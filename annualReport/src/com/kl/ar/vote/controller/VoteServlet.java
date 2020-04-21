package com.kl.ar.vote.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
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
import com.kl.ar.common.constant.PrizeType;
import com.kl.ar.common.constant.View;
import com.kl.ar.common.controller.BaseController;
import com.kl.ar.common.db.BizDb;
import com.kl.ar.common.util.DateUtil;
import com.kl.ar.common.util.ExcelUtil;
import com.kl.ar.common.util.FileUtil;
import com.kl.ar.vote.entity.CandidateUser;
import com.kl.ar.vote.entity.VoteResult;
import com.kl.ar.vote.entity.VwVoteResultSum;
import com.kl.ar.vote.service.VoteService;

import koal.urm.client.util.JsonUtil;
import koal.urm.client.util.StringUtil;

/**
 * 
 * @package:com.kl.ar.vote.controller
 * @Description: 评选控制器
 * @author: wanlei
 * @date: 2019年12月21日下午3:22:34
 */
@WebServlet("/vote")
public class VoteServlet extends BaseController {
	private static final Log logger = LogFactory.getLog(VoteServlet.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter(Constant.OP);
		try {
			if ("toVoteList".equals(op)) {
				// 转到年度评选列表页面
				toVoteList(request, response);
			} else if ("toAnnualVote".equals(op)) {
				// 转到年度评选页面
				toAnnualVote(request, response);
			} else if ("toVoteDetail".equals(op)) {
				// 转到评选详细页面
				toVoteDetail(request, response);
			} else if ("getVoteDetail".equals(op)) {
				// 获取投票结果详情数据
				getVoteDetail(request, response);
			} else if ("getCandidateUsers".equals(op)) {
				// 获取当前年的获选人员名单
				getCandidateUsers(request, response);
			} else if ("voteUser".equals(op)) {
				// 用户投票
				voteUser(request, response);
			} else if ("toSumVoteByWeight".equals(op)) {
				// 转到权重投票汇总页
				request.getRequestDispatcher(View.SUM_VOTE_BY_WEIGHT_JSP).forward(request, response);
			} else if ("getSumVoteByWeight".equals(op)) {
				// 获取权重投票汇总
				getSumVoteByWeight(request, response);
			} else if ("toSumVoteByPrize".equals(op)) {
				// 转到奖项汇总页面
				toSumVoteByPrize(request, response);
			} else if ("getSumVoteByPrize".equals(op)) {
				// 获取奖项投票汇总页
				getSumVoteByPrize(request, response);
			} else if ("exportVoteResultByPrize".equals(op)) {
				// 导出奖项汇总数据
				exportVoteResultByPrize(request, response);
			} else if ("exportVoteResultByWeight".equals(op)) {
				// 导出排名总分汇总数据
				exportVoteResultByWeight(request, response);
			}
		} catch (Exception e) {
			logger.error("投票控制器内部错误，原因为:" + e);
			request.setAttribute(Constant.ERROR_INFO, Constant.TIP_SYSTEM_ERROR);
			request.getRequestDispatcher(View.ERROR_JSP).forward(request, response);
		}
	}

	/**
	 * 导出排名总分汇总数据
	 * 
	 * @param request
	 * @param response
	 */
	private void exportVoteResultByWeight(HttpServletRequest request, HttpServletResponse response) {
		OutputStream output = null;
		try {
			Map<String, Object> condMap = new HashMap<String, Object>();
			String voteYear = request.getParameter(Constant.PARAM_VOTE_YEAR);
			String goldWeight = request.getParameter(Constant.PARAM_GOLD_WEIGHT);
			String silverWeight = request.getParameter(Constant.PARAM_SILVER_WEIGHT);
			String bronzeWeight = request.getParameter(Constant.PARAM_BRONZE_WEIGHT);
			condMap.put(Constant.PARAM_GOLD_WEIGHT, goldWeight);
			condMap.put(Constant.PARAM_SILVER_WEIGHT, silverWeight);
			condMap.put(Constant.PARAM_BRONZE_WEIGHT, bronzeWeight);
			condMap.put(Constant.PARAM_VOTE_YEAR, voteYear);
			// 获取文件下载名
			String fileName = FileUtil.getFileName(request,
					"排名总分汇总_" + DateUtil.formatDateTime(DateUtil.SHORT_PATTERN_DATE_TIME));
			// 防止下载的文件名中文乱码
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ExcelUtil.excel2003L);
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			// 初始化servlet输出流
			output = response.getOutputStream();
			BufferedOutputStream bot = new BufferedOutputStream(output);
			// 将生成的EXCEL写到输出流中去
			XSSFWorkbook workbook = VoteService.exportVoteResultByWeight(condMap);
			workbook.write(bot);
			bot.flush();
			workbook.close();
			bot.close();
		} catch (Exception e) {
			logger.error("导出排名总分汇总数据至EXCEL中失败，原因为:" + e);
		}
	}

	/**
	 * 导出各奖项票数汇总数据
	 * 
	 * @param request
	 * @param response
	 */
	private void exportVoteResultByPrize(HttpServletRequest request, HttpServletResponse response) {
		OutputStream output = null;
		try {
			String searchYear = request.getParameter(Constant.PARAM_VOTE_YEAR);
			// 获取文件下载名
			String fileName = FileUtil.getFileName(request,
					"各奖项票数汇总_" + DateUtil.formatDateTime(DateUtil.SHORT_PATTERN_DATE_TIME));
			// 防止下载的文件名中文乱码
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ExcelUtil.excel2003L);
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			// 初始化servlet输出流
			output = response.getOutputStream();
			BufferedOutputStream bot = new BufferedOutputStream(output);
			// 将生成的EXCEL写到输出流中去
			XSSFWorkbook workbook = VoteService.exportVoteResultByPrize(searchYear);
			workbook.write(bot);
			bot.flush();
			workbook.close();
			bot.close();
		} catch (Exception e) {
			logger.error("导出各奖项票数汇总数据至EXCEL中失败，原因为:" + e);
		}
	}

	/**
	 * 转到评选详细页面
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void toVoteDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter(Constant.ID);
		VoteResult voteResult = BizDb.queryOneRow("select * from " + VoteResult.TABLE + " WHERE ID=?", VoteResult.class,
				id);
		setAllPrizeTypeToRequest(request);
		request.getSession().setAttribute(Constant.VOTE_RESULT, voteResult);
		request.getRequestDispatcher(View.VOTE_DETAIL_JSP).forward(request, response);
	}

	/**
	 * 获取投票结果详细
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void getVoteDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		VoteResult voteResult = (VoteResult) request.getSession().getAttribute(Constant.VOTE_RESULT);
		List<VwVoteResultSum> vwVoteResultSumList = new ArrayList<VwVoteResultSum>();
		// 获取所有的奖项枚举list
		List<PrizeType> prizeTypeList = PrizeType.getAllPrizeTypeList();
		for (PrizeType prizeType : prizeTypeList) {
			String candidateIdStr = VoteService.invokeGetMethod(voteResult, prizeType.getCode());
			if (!StringUtil.isEmpty(candidateIdStr)) {
				String[] candidateIdArray = candidateIdStr.split(",");
				for (String candidateId : candidateIdArray) {
					VwVoteResultSum vwVoteResultSum = new VwVoteResultSum();
					vwVoteResultSum.setCandidateId(candidateId);
					vwVoteResultSum.setCandidateName(VoteService.getCandidateUserById(candidateId).getUserName());
					vwVoteResultSum.setCandidateDept(VoteService.getCandidateUserById(candidateId).getUserDept());
					vwVoteResultSum.setPrizeType(prizeType.getCode());
					vwVoteResultSumList.add(vwVoteResultSum);
				}
			}
		}
		response.getWriter().write(gson.toJson(vwVoteResultSumList));
	}

	/**
	 * 转到年度评选页面
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void toAnnualVote(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		setAllPrizeTypeToRequest(request);
		request.getRequestDispatcher(View.ANNUAL_VOTE_JSP).forward(request, response);
	}

	/**
	 * 转到奖项汇总页面
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void toSumVoteByPrize(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		setAllPrizeTypeToRequest(request);
		request.getRequestDispatcher(View.SUM_VOTE_BY_PRIZE_JSP).forward(request, response);
	}

	/**
	 * 往request中放入所有的奖项list和code
	 * 
	 * @param request
	 */
	private void setAllPrizeTypeToRequest(HttpServletRequest request) {
		// 获取所有的奖项枚举list
		List<PrizeType> prizeTypeList = PrizeType.getAllPrizeTypeList();
		String prizeTypeCode = PrizeType.getAllPrizeCodeList();
		request.setAttribute(Constant.PRIZE_TYPE_LIST, prizeTypeList);
		request.setAttribute(Constant.PRIZE_TYPE_CODE, prizeTypeCode);
	}

	/**
	 * 获取权重投票汇总列表并分页
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void getSumVoteByWeight(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> condMap = new HashMap<String, Object>();
		int limit = Integer.valueOf(request.getParameter(Constant.LIMIT));
		int offset = Integer.valueOf(request.getParameter(Constant.OFFSET));
		condMap.put(Constant.LIMIT, limit);
		condMap.put(Constant.OFFSET, offset);
		// 查询条件
		String goldWeight = request.getParameter(Constant.PARAM_GOLD_WEIGHT);
		String silverWeight = request.getParameter(Constant.PARAM_SILVER_WEIGHT);
		String bronzeWeight = request.getParameter(Constant.PARAM_BRONZE_WEIGHT);
		String voteYear = request.getParameter(Constant.PARAM_VOTE_YEAR);
		condMap.put(Constant.PARAM_GOLD_WEIGHT, goldWeight);
		condMap.put(Constant.PARAM_SILVER_WEIGHT, silverWeight);
		condMap.put(Constant.PARAM_BRONZE_WEIGHT, bronzeWeight);
		condMap.put(Constant.PARAM_VOTE_YEAR, voteYear);
		logger.info("获取权重投票汇总列表时=>查询条件为:" + condMap);
		response.getWriter().write(VoteService.getSumVoteByWeightAndPage(condMap));

	}

	/**
	 * 转到年度评选列表页面
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void toVoteList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取配置文件的当前年份
		Integer currentYear = Integer.parseInt(ConfigUtil.getInstance().loadCfgMap().get(Constant.CURRENT_YEAR));
		String voterId = getUserId(request);
		List<VoteResult> voteList = null;
		try {
			voteList = BizDb.queryList("select * from " + VoteResult.TABLE + " where voterId=?", VoteResult.class,
					voterId);
		} catch (Exception e) {
			logger.error("查询用户所有投票失败，原因为:" + e);
		}
		request.setAttribute(Constant.VOTE_LIST, voteList);
		// 遍历所有的投票记录，看今年有没有投票
		boolean votedCurrentYear = false;
		for (VoteResult voteResult : voteList) {
			if (voteResult.getVoteYear().intValue() == currentYear.intValue()) {
				votedCurrentYear = true;
			}
		}
		// 转到年度评选列表页面
		request.setAttribute(Constant.VOTED_CURRENT_YEAR, votedCurrentYear);
		request.getRequestDispatcher(View.VOTE_LIST_JSP).forward(request, response);
	}

	/**
	 * 根据类型获取当前年份的候选人名单
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void getCandidateUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String type = request.getParameter(Constant.USER_VOTE_TYPE);// 用户投票类型
		// 配置文件中的当前年份
		Integer currentYear = Integer.parseInt(ConfigUtil.getInstance().loadCfgMap().get(Constant.CURRENT_YEAR));
		List<CandidateUser> users = VoteService.getCandidateUserListByYearAndVoteType(currentYear, type);
		response.getWriter().write(gson.toJson(users));
	}

	/**
	 * 用户投票
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void voteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer currentYear = Integer.parseInt(ConfigUtil.getInstance().loadCfgMap().get(Constant.CURRENT_YEAR));
		Map<String, String> data = new HashMap<String, String>();
		data.put(Constant.AJAX_RESULT, Constant.SUCCESS_RESULT);
		String params = request.getParameter(Constant.PARAMS);
		logger.info("用户投票参数为:" + params);
		VoteResult result = JsonUtil.str2Bean(params, VoteResult.class);
		result.setId(UUID.randomUUID().toString());
		result.setVoterId(getUserId(request));
		result.setVoterName(getUser(request).getsName());
		result.setVoteYear(currentYear);
		result.setVoteTime(new Date());
		try {
			VoteService.addVoteResult(result);
		} catch (Exception e) {
			data.put(Constant.AJAX_RESULT, Constant.FAILURE_RESULT);
			logger.error("用户投票保存失败，原因为:" + e);
		}
		response.getWriter().write(gson.toJson(data));
	}

	/**
	 * 获取当前年份的奖类型汇总页面
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void getSumVoteByPrize(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String voteYear = request.getParameter(Constant.PARAM_VOTE_YEAR);
		List<VwVoteResultSum> list = VoteService.getSumVoteResult(voteYear);
		response.getWriter().write(gson.toJson(list));
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
