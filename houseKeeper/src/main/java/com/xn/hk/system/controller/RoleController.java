package com.xn.hk.system.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xn.hk.common.utils.page.BasePage;
import com.xn.hk.system.model.Module;
import com.xn.hk.system.model.Role;
import com.xn.hk.system.service.ModuleService;
import com.xn.hk.system.service.RoleService;

/**
 * 
 * @Title: RoleController
 * @Package: com.xn.ad.system.controller
 * @Description: 处理角色实体的控制层
 * @Company: 杭州讯牛
 * @Author: wanlei
 * @Date: 2017-11-30 下午03:14:19
 */
@Controller
@RequestMapping(value = "/system/role")
public class RoleController {
	/**
	 * 记录日志
	 */
	private static final Logger log = Logger.getLogger(RoleController.class);
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
		mv.addObject("pages", pages);
		// 查询所有的一级模块和二级模块和它们的个数,以便角色分配时模块联动
		List<Module> oneMenus = ms.findModuleByLevel(1);
		List<Module> twoMenus = ms.findModuleByLevel(2);
		mv.addObject("oneMenus", oneMenus);
		mv.addObject("oneMenuSize", oneMenus.size());
		log.info("一级模块总个数:" + oneMenus.size());
		mv.addObject("twoMenus", twoMenus);
		mv.addObject("twoMenuSize", twoMenus.size());
		log.info("二级模块总个数:" + oneMenus.size());
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
		ModelAndView mv = new ModelAndView("redirect:showAllRole.do");
		int result = rs.add(role);
		if (result == 0) {
			log.error("添加角色失败!");
		} else {
			log.info("添加角色成功!");
			session.setAttribute("msg", "<script>$(function(){swal('Good!', '添加角色成功!', 'success');});</script>");
		}
		return mv;
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
		ModelAndView mv = new ModelAndView("redirect:showAllRole.do");
		int result = rs.update(role);
		if (result == 0) {
			log.error("修改角色失败!");
		} else {
			log.info("修改角色成功!");
			session.setAttribute("msg", "<script>$(function(){swal('Good!', '修改角色成功!', 'success');});</script>");
		}
		return mv;
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
		ModelAndView mv = new ModelAndView("redirect:showAllRole.do");
		int result = rs.deleteRole(roleIds);
		if (result == 0) {
			log.error("删除失败,该角色ID不存在!");
		} else {
			log.info("删除角色成功!");
			session.setAttribute("msg", "<script>$(function(){swal('Good!', '删除角色成功!', 'success');});</script>");
		}
		return mv;
	}
}
