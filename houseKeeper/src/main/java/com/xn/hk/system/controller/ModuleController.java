package com.xn.hk.system.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xn.hk.common.utils.page.BasePage;
import com.xn.hk.system.model.Module;
import com.xn.hk.system.service.ModuleService;

/**
 * 
 * @Title: UserController
 * @Package: com.xn.ad.system.controller
 * @Description: 处理模块的控制层
 * @Company: 杭州讯牛
 * @Author: wanlei
 * @Date: 2017-11-28 下午03:22:28
 */
@Controller
@RequestMapping(value = "/system/module")
public class ModuleController {
	/**
	 * 记录日志
	 */
	private static final Logger log = Logger.getLogger(ModuleController.class);
	@Autowired
	private ModuleService ms;

	/**
	 * 实现模块分页
	 * 
	 * @param module
	 *            模块实体(用来封装查询条件)
	 * @param pages
	 *            分页对象
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/showAllModule.do")
	public ModelAndView showAllModule(Module module, BasePage<Module> pages) {
		ModelAndView mv = new ModelAndView("system/showAllModule");
		// 封装查询条件
		pages.setBean(module);
		List<Module> modules = ms.pageList(pages);
		// 将list封装到分页对象中
		pages.setList(modules);
		mv.addObject("pages", pages);
		// 查询所有的一级模块
		List<Module> oneMenus = ms.findModuleByLevel(1);
		mv.addObject("oneMenus", oneMenus);
		log.info("一级模块总个数:" + oneMenus.size());
		return mv;
	}

	/**
	 * 添加模块
	 * 
	 * @param module
	 *            模块实体
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/add.do")
	public ModelAndView addModule(Module module, HttpSession session) {
		ModelAndView mv = new ModelAndView("redirect:showAllModule.do");
		int result = ms.add(module);
		if (result == 0) {
			log.error("添加模块失败!");
		} else {
			log.info("添加模块成功!");
			session.setAttribute("msg", "<script>$(function(){swal('Good!', '删除模块成功!', 'success');});</script>");
		}
		return mv;
	}

	/**
	 * 修改模块
	 * 
	 * @param module
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/update.do")
	public ModelAndView updateModule(Module module, HttpSession session) {
		ModelAndView mv = new ModelAndView("redirect:showAllModule.do");
		int result = ms.update(module);
		if (result == 0) {
			log.error("修改模块失败!");
		} else {
			log.info("修改模块成功!");
			session.setAttribute("msg", "<script>$(function(){swal('Good!', '修改模块成功!', 'success');});</script>");
		}
		return mv;
	}

	/**
	 * 根据ID数组删除一个或多个模块
	 * 
	 * @param moduleIds
	 *            模块ID数组
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/delete.do")
	public ModelAndView deleteModule(Integer[] moduleIds, HttpSession session) {
		ModelAndView mv = new ModelAndView("redirect:showAllModule.do");
		int result = ms.delete(moduleIds);
		if (result == 0) {
			log.error("删除失败,该数组不存在!");
		} else {
			log.info("删除模块成功!");
			session.setAttribute("msg", "<script>$(function(){swal('Good!', '删除模块成功!', 'success');});</script>");
		}
		return mv;
	}

	/**
	 * 根据模块ID切换模块状态
	 * 
	 * @param moduleId
	 *            模块ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/changeState.do")
	public ModelAndView changeState(Integer moduleId) {
		ModelAndView mv = new ModelAndView("redirect:showAllModule.do");
		ms.changeState(moduleId);
		log.info("切换模块状态成功!");
		return mv;
	}
}
