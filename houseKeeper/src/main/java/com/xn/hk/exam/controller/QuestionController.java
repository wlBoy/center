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
import com.xn.hk.exam.model.Question;
import com.xn.hk.exam.model.QuestionType;
import com.xn.hk.exam.service.QuestionService;
import com.xn.hk.exam.service.QuestionTypeService;

/**
 * 
 * @Title: QuestionController
 * @Package: com.xn.hk.exam.controller
 * @Description: 处理题目的控制层
 * @Author: wanlei
 * @Date: 2018年1月8日 上午10:42:01
 */
@Controller
@RequestMapping(value = "/exam/question")
public class QuestionController {
	/**
	 * 记录日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
	
	/**
	 * 注入service层
	 */
	@Autowired
	private QuestionService qs;
	@Autowired
	private QuestionTypeService qts;

	/**
	 * 实现题目分页
	 * 
	 * @param question
	 *            题目实体(用来封装查询条件)
	 * @param pages
	 *            分页对象
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/showAllQuestion.do")
	public ModelAndView showAllQuestion(Question question, BasePage<Question> pages) {
		ModelAndView mv = new ModelAndView("exam/showAllQuestion");
		// 封装查询条件
		pages.setBean(question);
		// 题目分页
		List<Question> questions = qs.pageList(pages);
		// 将list封装到分页对象中
		pages.setList(questions);
		mv.addObject(Constant.PAGE_KEY, pages);
		// 查询所有的题型
		List<QuestionType> types = qts.findAll();
		mv.addObject(Constant.TYPES_KEY, types);
		logger.info("所有的题型个数为:{}", types.size());
		return mv;
	}

	/**
	 * 添加题目
	 * 
	 * @param question
	 *            题目实体
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/add.do")
	public ModelAndView addQuestion(Question question, HttpSession session) {
		int result = qs.insert(question);
		if (result == 0) {
			logger.error("添加题目{}失败!", question.getQuestionTitle());
		} else {
			logger.info("添加题目{}成功!", question.getQuestionTitle());
			session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("添加题目成功!", "success"));
		}
		return View.QUESTION_REDITRCT_ACTION;
	}

	/**
	 * 修改题目
	 * 
	 * @param question
	 *            题目实体
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/update.do")
	public ModelAndView updateQuestion(Question question, HttpSession session) {
		int result = qs.update(question);
		if (result == 0) {
			logger.error("修改题目{}失败!", question.getQuestionTitle());
		} else {
			logger.info("修改题目{}成功!", question.getQuestionTitle());
			session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("修改题目成功!", "success"));
		}
		return View.QUESTION_REDITRCT_ACTION;
	}

	/**
	 * 根据ID数组删除一个或多个题目
	 * 
	 * @param questionIds
	 *            题目ID数组
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/delete.do")
	public ModelAndView deleteQuestion(Integer[] questionIds, HttpSession session) {
		int result = qs.batchDelete(questionIds);
		if (result == 0) {
			logger.error("删除失败,该数组不存在!");
		} else {
			logger.info("删除题目成功!");
			session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("删除题目成功!", "success"));
		}
		return View.QUESTION_REDITRCT_ACTION;
	}

	/**
	 * 根据题目ID切换题目状态
	 * 
	 * @param questionId
	 *            题目ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/changeState.do")
	public ModelAndView changeState(Integer questionId) {
		qs.changeState(questionId);
		logger.info("题目{}切换状态成功!", questionId);
		return View.QUESTION_REDITRCT_ACTION;
	}
}
