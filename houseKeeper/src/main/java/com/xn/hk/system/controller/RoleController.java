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
import com.xn.hk.common.utils.page.BasePage;
import com.xn.hk.common.utils.string.StringUtil;
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
	private RoleService rs;
	@Autowired
	private ModuleService ms;

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
		PageInfo<Role> roles = new PageInfo<Role>(rs.pageAll(pages));
		pages.setList(roles.getList());
		pages.setCount((int) roles.getTotal());
		mv.addObject(Constant.PAGE_KEY, pages);
		// 查询所有的一级模块和二级模块和它们的个数,以便角色分配时模块联动
		List<Module> oneMenus = ms.findModuleByLevel(Constant.ONE_MODULES_VALUE);
		List<Module> twoMenus = ms.findModuleByLevel(Constant.TWO_MODULES_VALUE);
		mv.addObject(Constant.ONE_MODULES_KEY, oneMenus);
		mv.addObject(Constant.ONE_MODULES_SIZE, oneMenus.size());
		logger.info("一级模块总个数:{}", oneMenus.size());
		mv.addObject(Constant.TWO_MODULES_KEY, twoMenus);
		mv.addObject(Constant.TWO_MODULES_SIZE, twoMenus.size());
		logger.info("二级模块总个数:{}", twoMenus.size());
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
		int result = rs.insert(role);
		if (result == 0) {
			logger.error("添加角色{}失败!", role.getRoleName());
		} else {
			logger.info("添加角色{}成功!", role.getRoleName());
			session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("添加角色成功!", "success"));
		}
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
		int result = rs.update(role);
		if (result == 0) {
			logger.error("修改角色{}失败!", role.getRoleName());
		} else {
			logger.info("修改角色{}成功!", role.getRoleName());
			session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("修改角色成功!", "success"));
		}
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
		int result = rs.deleteRole(roleIds);
		if (result == 0) {
			logger.error("删除失败,该角色ID不存在!");
		} else {
			logger.info("删除角色成功!");
			session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("删除角色成功!", "success"));
		}
		return View.ROLE_REDITRCT_ACTION;
	}
}
