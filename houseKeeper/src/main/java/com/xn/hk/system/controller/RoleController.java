package com.xn.hk.system.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xn.hk.common.constant.Constant;
import com.xn.hk.common.constant.View;
import com.xn.hk.common.utils.log.LogHelper;
import com.xn.hk.common.utils.log.LogType;
import com.xn.hk.common.utils.page.BasePage;
import com.xn.hk.common.utils.string.StringUtil;
import com.xn.hk.system.dao.AdminLogDao;
import com.xn.hk.system.model.Module;
import com.xn.hk.system.model.Role;
import com.xn.hk.system.service.ModuleService;
import com.xn.hk.system.service.RoleService;

/**
 * 
 * @Title: RoleController
 * @Package: com.xn.ad.system.controller
 * @Description: 处理角色实体的控制层
 * @Author: wanlei
 * @Date: 2017-11-30 下午03:14:19
 */
@Controller
@RequestMapping(value = "/system/role")
public class RoleController {
	/**
	 * 记录日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

	/**
	 * 注入service层
	 */
	@Autowired
	private RoleService roleService;
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private AdminLogDao adminLogDao;

	/**
	 * 分页显示所有角色(使用分页插件mybatis分页插件pagehelper实现分页)
	 * 
	 * @param role
	 *            角色实体
	 * @param pages
	 *            分页对象
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/showAllRole.do")
	public ModelAndView showAllRole(Role role, BasePage<Role> pages) {
		ModelAndView mv = new ModelAndView("system/showAllRole");
		// 封装查询条件
		pages.setBean(role);
		// 使用分页插件开始分页
		PageHelper.startPage(pages.getPageNum(), pages.getSize());
		// 获取所有分页信息
		PageInfo<Role> roles = new PageInfo<Role>(roleService.pageAll(pages));
		pages.setList(roles.getList());
		pages.setCount((int) roles.getTotal());
		mv.addObject(Constant.PAGES, pages);
		// 查询所有的一级模块和二级模块和它们的个数,以便角色分配时模块联动
		List<Module> oneModules = moduleService.findModuleByLevel(Constant.ONE_MODULES_VALUE);
		List<Module> twoModules = moduleService.findModuleByLevel(Constant.TWO_MODULES_VALUE);
		mv.addObject(Constant.ONE_MODULES, oneModules);
		mv.addObject(Constant.TWO_MODULES, twoModules);
		mv.addObject(Constant.ONE_MODULES_SIZE, oneModules.size());
		mv.addObject(Constant.TWO_MODULES_SIZE, twoModules.size());
		return mv;
	}

	/**
	 * 添加角色
	 * 
	 * @param role
	 *            角色实体
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/add.do")
	public ModelAndView add(Role role, HttpSession session) {
		boolean logResult = true;
		int result = roleService.addRole(role);
		if (result == Constant.ZERO_VALUE) {
			logResult = false;
			logger.error("添加角色{}失败!", role.getRoleName());
		} else {
			logger.info("添加角色{}成功!", role.getRoleName());
			session.setAttribute(Constant.TIP_MSG, StringUtil.genTipMsg("添加角色成功!", true));
		}
		// 记录日志
		LogHelper.getInstance().saveLog(adminLogDao, session, "添加角色", logResult, LogType.ROLE_LOG.getType(), role);
		return View.ROLE_REDITRCT_ACTION;
	}

	/**
	 * 修改角色信息
	 * 
	 * @param role
	 *            角色实体
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/update.do")
	public ModelAndView update(Role role, HttpSession session) {
		boolean logResult = true;
		int result = roleService.updateRole(role);
		if (result == Constant.ZERO_VALUE) {
			logResult = false;
			logger.error("修改角色{}失败!", role.getRoleName());
		} else {
			logger.info("修改角色{}成功!", role.getRoleName());
			session.setAttribute(Constant.TIP_MSG, StringUtil.genTipMsg("修改角色成功!", true));
		}
		// 记录日志
		LogHelper.getInstance().saveLog(adminLogDao, session, "修改角色", logResult, LogType.ROLE_LOG.getType(), role);
		return View.ROLE_REDITRCT_ACTION;
	}

	/**
	 * 根据角色ID数组删除该角色
	 * 
	 * @param roleIds
	 *            角色ID数组
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/delete.do")
	public ModelAndView delete(Integer[] roleIds, HttpSession session) {
		boolean logResult = true;
		int result = roleService.deleteRole(roleIds);
		if (result == Constant.ZERO_VALUE) {
			logResult = false;
			logger.error("删除失败,该角色ID不存在!");
		} else {
			logger.info("删除角色成功!");
			session.setAttribute(Constant.TIP_MSG, StringUtil.genTipMsg("删除角色成功!", true));
		}
		for (Integer roleId : roleIds) {
			Role role = roleService.findById(roleId);
			// 记录日志
			LogHelper.getInstance().saveLog(adminLogDao, session, "删除角色", logResult, LogType.ROLE_LOG.getType(), role);
		}
		return View.ROLE_REDITRCT_ACTION;
	}
}
