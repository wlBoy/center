package com.xn.hk.exam.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xn.hk.common.constant.Constant;
import com.xn.hk.common.constant.View;
import com.xn.hk.common.utils.page.BasePage;
import com.xn.hk.common.utils.string.StringUtil;
import com.xn.hk.exam.model.Paper;
import com.xn.hk.exam.model.Question;
import com.xn.hk.exam.model.Score;
import com.xn.hk.exam.service.PaperClientService;
import com.xn.hk.exam.service.PaperService;
import com.xn.hk.exam.service.QuestionService;
import com.xn.hk.exam.service.ScoreService;
import com.xn.hk.system.model.User;
import com.xn.hk.system.service.UserService;

/**
 * 
 * @Title: PaperClientController
 * @Package: com.xn.hk.exam.controller
 * @Description: 处理前台试卷的控制层
 * @Author: wanlei
 * @Date: 2018年1月8日 上午10:42:01
 */
@Controller
@RequestMapping(value = "/exam/exam")
public class PaperClientController {
	/**
	 * 记录日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(PaperClientController.class);
	/**
	 * 注入service层
	 */
	@Autowired
	private PaperClientService pcs;
	@Autowired
	private PaperService ps;
	@Autowired
	private UserService us;
	@Autowired
	private QuestionService qs;
	@Autowired
	private ScoreService ss;

	/**
	 * 前台显示所有的试卷分页(需要用户ID),可以实现不同用户登录看到自己的试卷列表信息
	 * 
	 * @param paper
	 *            试卷实体(用来封装查询条件)
	 * @param pages
	 *            分页对象
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/showAllClientPaper.do")
	public ModelAndView showAllClientPaper(Paper paper, BasePage<Paper> pages, HttpSession session) {
		ModelAndView mv = new ModelAndView("examClient/showAllClientPaper");
		// 从session中拿出当前用户信息,将它塞入分页对象中去
		User user = (User) session.getAttribute(Constant.SESSION_USER_KEY);
		paper.setExamPaperId(user.getUserId());
		// 封装查询条件
		pages.setBean(paper);
		// 试卷分页
		List<Paper> papers = pcs.pageList(pages);
		// 将list封装到分页对象中
		pages.setList(papers);
		mv.addObject(Constant.PAGE_KEY, pages);
		// 查询所有用户信息，供下拉框显示
		List<User> users = us.findAll();
		mv.addObject(Constant.USER_KEY, users);
		logger.info("用户的个数为:{}" , users.size());
		return mv;
	}

	/**
	 * 前台在线考试
	 * 
	 * @param paperId
	 *            试卷ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/onlineExam.do")
	public ModelAndView onlineExam(Integer paperId, HttpSession session) {
		ModelAndView mv = new ModelAndView("examClient/onlineExam");
		// 查找改试卷的基本信息
		Paper paper = ps.findById(paperId);
		mv.addObject(Constant.PAPER_KEY, paper);
		logger.info("该试卷的信息为:{}" ,paper);
		// 查询该试卷的所有题目信息放至session中,供考试使用和评分使用
		List<Question> qlist = qs.findQuestionByPaperId(paperId);
		session.setAttribute(Constant.QLIST_VALUES, qlist);
		return mv;
	}

	/**
	 * 前台提交试卷,为客观题打分数,只能使用原始的request获取用户填写的答案
	 * 
	 * @param paperId
	 *            试卷ID
	 * @param request
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitPaper.do")
	public ModelAndView submitPaper(Integer paperId, HttpServletRequest request, HttpSession session) {
		// 从session中拿出当前用户信息
		User user = (User) session.getAttribute(Constant.SESSION_USER_KEY);
		// 从session中拿出试卷所有题目信息
		List<Question> qlist = (List<Question>) session.getAttribute(Constant.QLIST_VALUES);
		List<String> userList = new ArrayList<String>();
		int singleScore = 0;
		int multipleScore = 0;
		// 取出用户的答案放入userList中去
		for (int i = 0; i < qlist.size(); i++) {
			// 计算并保存单选题分数
			if (qlist.get(i).getType().getTypeId() == 1) {
				userList.add(request.getParameter("answer" + (i + 1)));
				if (qlist.get(i).getAnswer().equals(userList.get(i))) {
					singleScore += qlist.get(i).getQuestionScore();
				}
			}
			// 计算并保存多选题分数
			if (qlist.get(i).getType().getTypeId() == 2) {
				String multipleSolution = "";
				String[] strs = request.getParameterValues("answer" + (i + 1));
				for (String s : strs) {
					// 拼接多选题答案
					multipleSolution += s;
				}
				userList.add(multipleSolution);
				if (qlist.get(i).getAnswer().equals(userList.get(i))) {
					multipleScore += qlist.get(i).getQuestionScore();
				}
			}
			// 保存用户简答题答案,方便后台批阅主观题分数
			if (qlist.get(i).getType().getTypeId() == 3) {
				userList.add(request.getParameter("answer" + (i + 1)));
			}
		}
		// 封装分数实体信息，并将试卷状态改为已考状态
		Score score = new Score();
		score.setMultipleScore(multipleScore);
		score.setSingleScore(singleScore);
		score.setBriefScore(0);
		score.getPaper().setPaperId(paperId);
		score.setExamPaperId(user.getUserId());
		score.setPaperStatus(1);
		// 添加分数和用户答案
		pcs.addScoreAndSolution(score, qlist, userList);
		session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("交卷成功!", "success"));
		return View.CLIENT_PAPER_REDITRCT_ACTION;
	}

	/**
	 * 前台查看已考试卷详情
	 * 
	 * @param paperId
	 *            试卷ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/showPaperDetail.do")
	public ModelAndView showPaperDetail(Integer paperId, HttpSession session) {
		ModelAndView mv = new ModelAndView("examClient/showPaperDetail");
		User user = (User) session.getAttribute(Constant.SESSION_USER_KEY);
		// 查询该用户该试卷的分数表和试卷基本信息
		Score score = ss.findByPaperIdAndUserId(paperId, user.getUserId());
		// 查询该用户该试卷的填写的用户答案
		List<String> ulist = pcs.findSolutionByPaperIdAndUserId(paperId, user.getUserId());
		// 查询该试卷的所有题目信息
		List<Question> qlist = qs.findQuestionByPaperId(paperId);
		mv.addObject(Constant.QLIST_VALUES, qlist);
		mv.addObject(Constant.ULIST_VALUES, ulist);
		mv.addObject(Constant.SCORE_VALUES, score);
		return mv;
	}
}
