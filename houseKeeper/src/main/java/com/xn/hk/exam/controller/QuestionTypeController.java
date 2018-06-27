package com.xn.hk.exam.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xn.hk.exam.model.QuestionType;
import com.xn.hk.exam.service.QuestionTypeService;

/**
 * 
 * @Title: QuestionTypeController
 * @Package: com.xn.hk.exam.controller
 * @Description: 处理题型的控制层
 * @Company: 杭州讯牛
 * @Author: wanlei
 * @Date: 2018年1月8日 上午10:42:01
 */
@Controller
@RequestMapping(value = "/exam/type")
public class QuestionTypeController {
	/**
	 * 记录日志
	 */
	private static final Logger log = Logger.getLogger(QuestionTypeController.class);
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
		mv.addObject("types", types);
		log.info("所有的题型个数为:" + types.size());
		return mv;
	}

	/**
	 * 添加题型
	 * 
	 * @param role
	 *            题型实体
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/add.do")
	public ModelAndView add(QuestionType type, HttpSession session) {
		ModelAndView mv = new ModelAndView("redirect:showAllType.do");
		int result = qts.add(type);
		if (result == 0) {
			log.error("添加题型失败!");
		} else {
			log.info("添加题型成功!");
			session.setAttribute("msg", "<script>$(function(){swal('Good!', '添加题型成功!', 'success');});</script>");
		}
		return mv;
	}

	/**
	 * 更新角色
	 * 
	 * @param role
	 *            角色实体
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/update.do")
	public ModelAndView update(QuestionType type, HttpSession session) {
		ModelAndView mv = new ModelAndView("redirect:showAllType.do");
		int result = qts.update(type);
		if (result == 0) {
			log.error("修改题型失败!");
		} else {
			log.info("修改题型成功!");
			session.setAttribute("msg", "<script>$(function(){swal('Good!', '修改题型成功!', 'success');});</script>");
		}
		return mv;
	}

	/**
	 * 根据角色ID删除该角色
	 * 
	 * @param roleId
	 *            角色ID
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/delete.do")
	public ModelAndView delete(Integer[] typeIds, HttpSession session) {
		ModelAndView mv = new ModelAndView("redirect:showAllType.do");
		int result = qts.delete(typeIds);
		if (result == 0) {
			log.error("删除失败,该角色ID不存在!");
		} else {
			log.info("删除题型成功!");
			session.setAttribute("msg", "<script>$(function(){swal('Good!', '删除题型成功!', 'success');});</script>");
		}
		return mv;
	}
}
