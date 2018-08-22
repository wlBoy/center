package com.xn.hk.system.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xn.hk.system.model.Module;
import com.xn.hk.system.model.Role;
import com.xn.hk.system.model.User;
import com.xn.hk.system.service.ModuleService;
import com.xn.hk.system.service.RoleService;
import com.xn.hk.system.service.UserService;

/**
 * 
 * @Title: RestController
 * @Package: com.xn.ad.system.controller
 * @Description: 处理系统管理中的所有ajax请求
 * @Author: wanlei
 * @Date: 2017-11-30 下午01:12:25
 */
@RestController
@RequestMapping("/system/rest")
public class SystemRestController {
	/**
	 * 记录日志
	 */
	private static final Logger log = Logger.getLogger(SystemRestController.class);
	@Autowired
	private UserService us;
	@Autowired
	private RoleService rs;
	@Autowired
	private ModuleService ms;

	/**
	 * 根据用户Id查询该用户
	 * 
	 * @param userId
	 *            用户Id
	 * @return 用户实体
	 */
	@RequestMapping(value = "/findByUserId.do", method = RequestMethod.GET)
	public User findByUserId(Integer userId) {
		User u = us.findById(userId);
		log.info("用户的信息为:" + u);
		return u;
	}

	/**
	 * 根据用户名查询该用户
	 * 
	 * @param userName
	 *            用户名
	 * @return 用户实体
	 */
	@RequestMapping(value = "/findByUserName.do", method = RequestMethod.GET)
	public User findByUserName(String userName) {
		User u = us.findByName(userName);
		log.info("该用户的信息为:" + u);
		return u;
	}

	/**
	 * 根据角色Id查询该角色
	 * 
	 * @param roleId
	 *            角色Id
	 * @return 角色实体
	 */
	@RequestMapping(value = "/findByRoleId.do", method = RequestMethod.GET)
	public Role findByRoleId(Integer roleId) {
		Role r = rs.findById(roleId);
		log.info("该角色的信息为:" + r);
		return r;
	}

	/**
	 * 根据角色名查询该角色
	 * 
	 * @param roleName
	 *            角色名
	 * @return 角色实体
	 */
	@RequestMapping(value = "/findByRoleName.do", method = RequestMethod.GET)
	public Role findByRoleName(String roleName) {
		Role r = rs.findByName(roleName);
		log.info("该角色的信息为:" + r);
		return r;
	}

	/**
	 * 根据模块Id查询该模块
	 * 
	 * @param moduleId
	 *            模块Id
	 * @return 模块实体
	 */
	@RequestMapping(value = "/findByModuleId.do", method = RequestMethod.GET)
	public Module findByModuleId(Integer moduleId) {
		Module m = ms.findById(moduleId);
		log.info("该模块的信息为:" + m);
		return m;
	}

	/**
	 * 根据模块名查询该模块
	 * 
	 * @param moduleName
	 *            模块名
	 * @return 模块实体
	 */
	@RequestMapping(value = "/findByModuleName.do", method = RequestMethod.GET)
	public Module findByModuleName(String moduleName) {
		Module m = ms.findByName(moduleName);
		log.info("该模块的信息为:" + m);
		return m;
	}
}
