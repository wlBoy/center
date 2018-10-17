package com.xn.hk.system.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xn.hk.common.constant.Result;
import com.xn.hk.common.utils.cfg.CfgConstant;
import com.xn.hk.common.utils.encryption.MD5Util;
import com.xn.hk.common.utils.string.StringUtil;
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
 * @Description: 处理系统管理中的所有ajax请求(接口)
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
	 * @return 结果提示信息实体
	 */
	@RequestMapping(value = "/findByUserId.do", method = RequestMethod.POST)
	public Result findByUserId(Integer userId) {
		// 校验参数非空性
		if (userId == null) {
			return Result.genNullParamTip("用户ID");
		}
		User user = userService.findById(userId);
		if (user == null) {
			logger.info("findByUserId-->{}用户不存在!", userId);
			Result.genTip(Result.FAILURE, "该用户不存在!", null);
		}
		logger.info("findByUserId-->查到{}用户!", userId);
		return Result.genTip(Result.SUCCESS, "查到该用户!", user);
	}

	/**
	 * 根据用户名查询该用户
	 * 
	 * @param userName
	 *            用户名
	 * @return 结果提示信息实体
	 */
	@RequestMapping(value = "/findByUserName.do", method = RequestMethod.POST)
	public Result findByUserName(String userName) {
		// 校验参数非空性
		if (StringUtil.isEmpty(userName)) {
			return Result.genNullParamTip("用户名");
		}
		User user = userService.findByName(userName);
		if (user == null) {
			logger.info("findByUserName-->{}用户不存在!", userName);
			Result.genTip(Result.FAILURE, "该用户不存在!", null);
		}
		logger.info("findByUserName-->查到{}用户!", userName);
		return Result.genTip(Result.SUCCESS, "查到该用户!", user);
	}

	/**
	 * 根据角色Id查询该角色
	 * 
	 * @param roleId
	 *            角色Id
	 * @return 结果提示信息实体
	 */
	@RequestMapping(value = "/findByRoleId.do", method = RequestMethod.POST)
	public Result findByRoleId(Integer roleId) {
		// 校验参数非空性
		if (roleId == null) {
			return Result.genNullParamTip("角色ID");
		}
		Role role = roleService.findById(roleId);
		if (role == null) {
			logger.info("findByRoleId-->{}角色不存在!", roleId);
			Result.genTip(Result.FAILURE, "该角色不存在!", null);
		}
		logger.info("findByRoleId-->查到{}角色!", roleId);
		return Result.genTip(Result.SUCCESS, "查到该角色!", role);
	}

	/**
	 * 根据角色名查询该角色
	 * 
	 * @param roleName
	 *            角色名
	 * @return 角色实体
	 */
	@RequestMapping(value = "/findByRoleName.do", method = RequestMethod.POST)
	public Result findByRoleName(String roleName) {
		// 校验参数非空性
		if (StringUtil.isEmpty(roleName)) {
			return Result.genNullParamTip("角色名");
		}
		Role role = roleService.findByName(roleName);
		if (role == null) {
			logger.info("findByRoleName-->{}角色不存在!", roleName);
			Result.genTip(Result.FAILURE, "该角色不存在!", null);
		}
		logger.info("findByRoleName-->查到{}角色!", roleName);
		return Result.genTip(Result.SUCCESS, "查到该角色!", role);
	}

	/**
	 * 根据模块Id查询该模块
	 * 
	 * @param moduleId
	 *            模块Id
	 * @return 模块实体
	 */
	@RequestMapping(value = "/findByModuleId.do", method = RequestMethod.POST)
	public Result findByModuleId(Integer moduleId) {
		// 校验参数非空性
		if (moduleId == null) {
			return Result.genNullParamTip("模块ID");
		}
		Module module = moduleService.findById(moduleId);
		if (module == null) {
			logger.info("findByModuleId-->{}模块不存在!", moduleId);
			return Result.genTip(Result.FAILURE, "该模块不存在!", null);
		}
		logger.info("findByModuleId-->查到{}模块!", moduleId);
		return Result.genTip(Result.SUCCESS, "查到该模块!", module);
	}

	/**
	 * 根据模块名查询该模块
	 * 
	 * @param moduleName
	 *            模块名
	 * @return 结果提示信息实体
	 */
	@RequestMapping(value = "/findByModuleName.do", method = RequestMethod.POST)
	public Result findByModuleName(String moduleName) {
		// 校验参数非空性
		if (StringUtil.isEmpty(moduleName)) {
			return Result.genNullParamTip("模块名");
		}
		Module module = moduleService.findByName(moduleName);
		if (module == null) {
			logger.info("findByModuleName-->{}模块不存在!", moduleName);
			return Result.genTip(Result.FAILURE, "该模块不存在!", null);
		}
		logger.info("findByModuleName-->查到{}模块!", moduleName);
		return Result.genTip(Result.SUCCESS, "查到该模块!", module);
	}

	/**
	 * 修改密码时检查新密码是否与旧密码一致
	 * 
	 * @param userId
	 *            用户ID
	 * @param newPwd
	 *            新密码
	 * @return 结果提示信息实体
	 */
	@RequestMapping(value = "/checkOldPwd.do", method = RequestMethod.POST)
	public Result checkOldPwd(Integer userId, String newPwd) {
		// 校验参数非空性
		if (userId == null) {
			return Result.genNullParamTip("用户ID");
		}
		if (StringUtil.isEmpty(newPwd)) {
			return Result.genNullParamTip("新密码");
		}
		User user = userService.findById(userId);
		String userPwd = MD5Util.MD5(newPwd + CfgConstant.USER_PWD_KEY);// 新密码加密
		if (userPwd.equals(user.getUserPwd())) {
			logger.info("checkOldPwd-->新密码与旧密码一致!用户ID为:{}", userId);
			return Result.genTip(Result.FAILURE, "新密码与旧密码一致!", newPwd);
		}
		logger.info("checkOldPwd-->新密码与旧密码不一致!用户ID为:{}", userId);
		return Result.genTip(Result.SUCCESS, "新密码与旧密码不一致!", newPwd);
	}

	/**
	 * 测试方法，测试接口
	 * 
	 * @param userId
	 *            用户ID
	 * @param userName
	 *            用户姓名
	 * @return 结果提示信息实体
	 */
	@RequestMapping(value = "/test.do")
	public Result test(Integer userId, String userName) {
		// 校验参数非空性
		if (userId == null) {
			return Result.genNullParamTip("用户ID");
		}
		if (StringUtil.isEmpty(userName)) {
			return Result.genNullParamTip("用户姓名");
		}
		return Result.genTip(Result.SUCCESS, "请求成功!", userId + "-" + userName);
	}
}
