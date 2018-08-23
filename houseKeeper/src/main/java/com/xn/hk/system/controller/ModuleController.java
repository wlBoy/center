package com.xn.hk.system.controller;

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
import com.xn.hk.system.model.Module;
import com.xn.hk.system.service.ModuleService;

/**
 * 
 * @Title: UserController
 * @Package: com.xn.ad.system.controller
 * @Description: 处理模块的控制层
 * @Author: wanlei
 * @Date: 2017-11-28 下午03:22:28
 */
@Controller
@RequestMapping(value = "/system/module")
public class ModuleController {
	/**
	 * 记录日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(ModuleController.class);

	/**
	 * 注入service层
	 */
	@Autowired
	private ModuleService moduleService;

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
		List<Module> modules = moduleService.pageList(pages);
		// 将list封装到分页对象中
		pages.setList(modules);
		mv.addObject(Constant.PAGE_KEY, pages);
		// 查询所有的一级模块
		List<Module> oneModules = moduleService.findModuleByLevel(Constant.ONE_MODULES_VALUE);
		mv.addObject(Constant.ONE_MODULES_KEY, oneModules);
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
		int result = moduleService.insert(module);
		if (result == Constant.ZERO_VALUE) {
			logger.error("添加模块{}失败!", module.getModuleName());
		} else {
			logger.info("添加模块{}成功!", module.getModuleName());
			session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("添加模块成功!", Constant.SUCCESS_TIP_KEY));
		}
		return View.MODULE_REDITRCT_ACTION;
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
		int result = moduleService.update(module);
		if (result == Constant.ZERO_VALUE) {
			logger.error("修改模块{}失败!", module.getModuleName());
		} else {
			logger.info("修改模块{}成功!", module.getModuleName());
			session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("修改模块成功!", Constant.SUCCESS_TIP_KEY));
		}
		return View.MODULE_REDITRCT_ACTION;
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
		int result = moduleService.batchDelete(moduleIds);
		if (result == Constant.ZERO_VALUE) {
			logger.error("删除失败,该数组不存在!");
		} else {
			logger.info("删除模块成功!");
			session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("删除模块成功!", Constant.SUCCESS_TIP_KEY));
		}
		return View.MODULE_REDITRCT_ACTION;
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
		int result = moduleService.changeState(moduleId);
		if (result == Constant.ZERO_VALUE) {
			logger.error("模块{}切换状态失败!", moduleId);
		} else {
			logger.info("模块{}切换状态成功!", moduleId);
		}
		return View.MODULE_REDITRCT_ACTION;
	}
}
