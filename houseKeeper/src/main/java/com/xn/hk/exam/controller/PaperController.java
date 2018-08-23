package com.xn.hk.exam.controller;

import java.util.List;

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
import com.xn.hk.exam.model.QuestionType;
import com.xn.hk.exam.service.PaperService;
import com.xn.hk.exam.service.QuestionService;
import com.xn.hk.exam.service.QuestionTypeService;
import com.xn.hk.system.model.User;
import com.xn.hk.system.service.UserService;

/**
 * 
 * @Title: PaperController
 * @Package: com.xn.hk.exam.controller
 * @Description: 处理后台试卷的控制层
 * @Author: wanlei
 * @Date: 2018年1月8日 上午10:42:01
 */
@Controller
@RequestMapping(value = "/exam/paper")
public class PaperController {
	/**
	 * 记录日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(PaperController.class);

	/**
	 * 注入service层
	 */
	@Autowired
	private PaperService paperService;
	@Autowired
	private QuestionTypeService questionTypeService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private UserService userService;

	/**
	 * 后台实现试卷分页
	 * 
	 * @param paper
	 *            试卷实体(用来封装查询条件)
	 * @param pages
	 *            分页对象
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/showAllPaper.do")
	public ModelAndView showAllPaper(Paper paper, BasePage<Paper> pages) {
		ModelAndView mv = new ModelAndView("exam/showAllPaper");
		// 封装查询条件
		pages.setBean(paper);
		// 试卷分页
		List<Paper> papers = paperService.pageList(pages);
		// 将list封装到分页对象中
		pages.setList(papers);
		mv.addObject(Constant.PAGE_KEY, pages);
		// 查询所有的题型,供添加试卷时使用
		List<QuestionType> types = questionTypeService.findAll();
		mv.addObject(Constant.TYPES_KEY, types);
		// 查询所有用户信息，供下拉框显示
		List<User> users = userService.findAll();
		mv.addObject(Constant.USER_KEY, users);
		return mv;
	}

	/**
	 * 后台添加试卷
	 * 
	 * @param question
	 *            试卷实体
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/add.do")
	public ModelAndView addQuestion(Paper paper, HttpSession session) {
		// 从session中取出当前用户
		User user = (User) session.getAttribute(Constant.SESSION_USER_KEY);
		paper.setCreatePaperId(user.getUserId());
		int result = paperService.addPaper(paper);
		if (result == Constant.ZERO_VALUE) {
			logger.error("添加试卷{}失败!", paper.getPaperName());
		} else {
			logger.info("添加试卷{}成功!", paper.getPaperName());
			session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("添加试卷成功!", Constant.SUCCESS_TIP_KEY));
		}
		return View.PAPER_REDITRCT_ACTION;
	}

	/**
	 * 后台修改试卷(只修改试卷基本信息)
	 * 
	 * @param paper
	 *            试卷实体
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/update.do")
	public ModelAndView updateQuestion(Paper paper, HttpSession session) {
		int result = paperService.update(paper);
		if (result == Constant.ZERO_VALUE) {
			logger.error("修改试卷{}失败!", paper.getPaperName());
		} else {
			logger.info("修改试卷{}成功!", paper.getPaperName());
			session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("修改试卷成功!", Constant.SUCCESS_TIP_KEY));
		}
		return View.PAPER_REDITRCT_ACTION;
	}

	/**
	 * 后台根据ID数组删除一个或多个试卷
	 * 
	 * @param paperIds
	 *            试卷ID数组
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/delete.do")
	public ModelAndView deleteQuestion(Integer[] paperIds, HttpSession session) {
		int result = paperService.deletePaper(paperIds);
		if (result == Constant.ZERO_VALUE) {
			logger.error("删除失败,该数组不存在!");
		} else {
			logger.info("删除试卷成功!");
			session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("删除试卷成功!", Constant.SUCCESS_TIP_KEY));
		}
		return View.PAPER_REDITRCT_ACTION;
	}

	/**
	 * 后台根据试卷ID切换试卷状态
	 * 
	 * @param paperId
	 *            试卷ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/changeState.do")
	public ModelAndView changeState(Integer paperId) {
		int result = paperService.changeState(paperId);
		if (result == Constant.ZERO_VALUE) {
			logger.error("试卷{}切换状态失败!", paperId);
		} else {
			logger.info("试卷{}切换状态成功!", paperId);
		}
		return View.PAPER_REDITRCT_ACTION;
	}

	/**
	 * 后台根据试卷ID和用户ID批阅试卷
	 * 
	 * @param paperId
	 *            试卷ID
	 * @param useId
	 *            用户ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/readPaper.do")
	public ModelAndView readPaper(Integer paperId, Integer userId) {
		ModelAndView mv = new ModelAndView("exam/readPaper");
		// 根据试卷ID和用户ID查询学生简答题答案和正确答案信息(只要简答题的信息)
		List<Question> qList = questionService.findAnswerByPaperIdAndUserId(paperId, userId);
		// 在试卷配置表中根据试卷ID查询该试卷简答题的分数(即每到简答题分数)
		Integer score = paperService.findScoreByPaperId(paperId);
		mv.addObject(Constant.SCORE_VALUES, score);
		mv.addObject(Constant.PAPERID_KEY, paperId);
		mv.addObject(Constant.USERID_KEY, userId);
		mv.addObject(Constant.QLIST_VALUES, qList);
		return mv;
	}

}
