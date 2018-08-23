package com.xn.hk.system.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xn.hk.common.constant.Constant;
import com.xn.hk.common.constant.Result;
import com.xn.hk.common.utils.encryption.MD5Util;
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
	private static final Logger logger = LoggerFactory.getLogger(SystemRestController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private ModuleService moduleService;

	/**
	 * 根据用户Id查询该用户
	 * 
	 * @param userId
	 *            用户Id
	 * @return 用户实体
	 */
	@RequestMapping(value = "/findByUserId.do", method = RequestMethod.GET)
	public User findByUserId(Integer userId) {
		return userService.findById(userId);
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
		return userService.findByName(userName);
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
		return roleService.findById(roleId);
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
		return roleService.findByName(roleName);
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
		return moduleService.findById(moduleId);
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
		return moduleService.findByName(moduleName);
	}

	/**
	 * 修改密码时检查新密码是否与旧密码一致
	 * 
	 * @param userId
	 *            用户ID
	 * @param newPwd
	 *            新密码
	 * @return
	 */
	@RequestMapping(value = "/checkOldPwd.do", method = RequestMethod.GET)
	public Result checkOldPwd(Integer userId, String newPwd) {
		Result result = new Result();
		User user = userService.findById(userId);
		String userPwd = MD5Util.MD5(newPwd + Constant.PASSWORD_KEY);// 新密码加密
		if (userPwd.equals(user.getUserPwd())) {
			logger.info("新密码与旧密码一致!");
			result.setCode(0);
			result.setDesc("新密码与旧密码一致!");
		} else {
			logger.info("新密码与旧密码不一致!");
			result.setCode(1);
			result.setDesc("新密码与旧密码不一致!");
		}
		return result;
	}
}
