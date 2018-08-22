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
import com.xn.hk.common.utils.string.StringUtil;
import com.xn.hk.exam.model.QuestionType;
import com.xn.hk.exam.service.QuestionTypeService;

/**
 * 
 * @Title: QuestionTypeController
 * @Package: com.xn.hk.exam.controller
 * @Description: 处理题型的控制层
 * @Author: wanlei
 * @Date: 2018年1月8日 上午10:42:01
 */
@Controller
@RequestMapping(value = "/exam/type")
public class QuestionTypeController {
	/**
	 * 记录日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(QuestionTypeController.class);
	private static final ModelAndView QUESTION_TYPE_REDITRCT_ACTION = new ModelAndView("redirect:showAllType.do");// 重定向分页所有题型的Action
	/**
	 * 注入service层
	 */
	@Autowired
	private QuestionTypeService qts;

	/**
	 * 显示所有题型
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/showAllType.do")
	public ModelAndView showAllType() {
		ModelAndView mv = new ModelAndView("exam/showAllType");
		List<QuestionType> types = qts.findAll();
		mv.addObject(Constant.TYPES_VALUE, types);
		logger.info("所有的题型个数为:{}", types.size());
		return mv;
	}

	/**
	 * 添加题型
	 * 
	 * @param type
	 *            题型实体
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/add.do")
	public ModelAndView add(QuestionType type, HttpSession session) {
		int result = qts.add(type);
		if (result == 0) {
			logger.error("添加题型{}失败!", type.getTypeName());
		} else {
			logger.info("添加题型{}成功!", type.getTypeName());
			session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("添加题型成功!", "success"));
		}
		return QUESTION_TYPE_REDITRCT_ACTION;
	}

	/**
	 * 更新题型
	 * 
	 * @param type
	 *            题型实体
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/update.do")
	public ModelAndView update(QuestionType type, HttpSession session) {
		int result = qts.update(type);
		if (result == 0) {
			logger.error("修改题型{}失败!", type.getTypeName());
		} else {
			logger.info("修改题型{}成功!", type.getTypeName());
			session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("修改题型成功!", "success"));
		}
		return QUESTION_TYPE_REDITRCT_ACTION;
	}

	/**
	 * 根据题型ID删除该题型
	 * 
	 * @param typeIds
	 *            题型ID数组
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/delete.do")
	public ModelAndView delete(Integer[] typeIds, HttpSession session) {
		int result = qts.delete(typeIds);
		if (result == 0) {
			logger.error("删除失败,该题型ID不存在!");
		} else {
			logger.info("删除题型成功!");
			session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("删除题型成功!", "success"));
		}
		return QUESTION_TYPE_REDITRCT_ACTION;
	}
}
