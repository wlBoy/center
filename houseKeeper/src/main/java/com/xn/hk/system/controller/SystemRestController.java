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
	 * @return 用户实体
	 */
	@RequestMapping(value = "/findByUserId.do", method = RequestMethod.POST)
	public Result findByUserId(Integer userId) {
		Result result = new Result();
		// 校验参数非空性
		if (userId == null) {
			return Result.genNullValueTip(result, "userId");
		}
		User user = userService.findById(userId);
		if (user == null) {
			logger.info("该用户不存在!");
			result.setCode(Result.FAILURE_CODE);
			result.setMsg("该用户不存在!");
			result.setData(null);
			return result;
		}
		logger.info("查到该用户!");
		result.setCode(Result.SUCCESS_CODE);
		result.setMsg("查到该用户!");
		result.setData(user);
		return result;
	}

	/**
	 * 根据用户名查询该用户
	 * 
	 * @param userName
	 *            用户名
	 * @return 用户实体
	 */
	@RequestMapping(value = "/findByUserName.do", method = RequestMethod.POST)
	public Result findByUserName(String userName) {
		Result result = new Result();
		// 校验参数非空性
		if (StringUtil.isEmpty(userName)) {
			return Result.genNullValueTip(result, "userName");
		}
		User user = userService.findByName(userName);
		if (user == null) {
			logger.info("该用户不存在!");
			result.setCode(Result.FAILURE_CODE);
			result.setMsg("该用户不存在!");
			result.setData(null);
			return result;
		}
		logger.info("查到该用户!");
		result.setCode(Result.SUCCESS_CODE);
		result.setMsg("查到该用户!");
		result.setData(user);
		return result;
	}

	/**
	 * 根据角色Id查询该角色
	 * 
	 * @param roleId
	 *            角色Id
	 * @return 角色实体
	 */
	@RequestMapping(value = "/findByRoleId.do", method = RequestMethod.POST)
	public Result findByRoleId(Integer roleId) {
		Result result = new Result();
		// 校验参数非空性
		if (roleId == null) {
			return Result.genNullValueTip(result, "roleId");
		}
		Role role = roleService.findById(roleId);
		if (role == null) {
			logger.info("该角色不存在!");
			result.setCode(Result.FAILURE_CODE);
			result.setMsg("该角色不存在!");
			result.setData(null);
			return result;
		}
		logger.info("查到该角色!");
		result.setCode(Result.SUCCESS_CODE);
		result.setMsg("查到该角色!");
		result.setData(role);
		return result;
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
		Result result = new Result();
		// 校验参数非空性
		if (StringUtil.isEmpty(roleName)) {
			return Result.genNullValueTip(result, "roleName");
		}
		Role role = roleService.findByName(roleName);
		if (role == null) {
			logger.info("该角色不存在!");
			result.setCode(Result.FAILURE_CODE);
			result.setMsg("该角色不存在!");
			result.setData(null);
			return result;
		}
		logger.info("查到该角色!");
		result.setCode(Result.SUCCESS_CODE);
		result.setMsg("查到该角色!");
		result.setData(role);
		return result;
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
		Result result = new Result();
		// 校验参数非空性
		if (moduleId == null) {
			return Result.genNullValueTip(result, "moduleId");
		}
		Module module = moduleService.findById(moduleId);
		if (module == null) {
			logger.info("该模块不存在!");
			result.setCode(Result.FAILURE_CODE);
			result.setMsg("该模块不存在!");
			result.setData(null);
			return result;
		}
		logger.info("查到该模块!");
		result.setCode(Result.SUCCESS_CODE);
		result.setMsg("查到该模块!");
		result.setData(module);
		return result;
	}

	/**
	 * 根据模块名查询该模块
	 * 
	 * @param moduleName
	 *            模块名
	 * @return 模块实体
	 */
	@RequestMapping(value = "/findByModuleName.do", method = RequestMethod.POST)
	public Result findByModuleName(String moduleName) {
		Result result = new Result();
		// 校验参数非空性
		if (StringUtil.isEmpty(moduleName)) {
			return Result.genNullValueTip(result, "moduleName");
		}
		Module module = moduleService.findByName(moduleName);
		if (module == null) {
			logger.info("该模块不存在!");
			result.setCode(Result.FAILURE_CODE);
			result.setMsg("该模块不存在!");
			result.setData(null);
			return result;
		}
		logger.info("查到该模块!");
		result.setCode(Result.SUCCESS_CODE);
		result.setMsg("查到该模块!");
		result.setData(module);
		return result;
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
	@RequestMapping(value = "/checkOldPwd.do", method = RequestMethod.POST)
	public Result checkOldPwd(Integer userId, String newPwd) {
		Result result = new Result();
		// 校验参数非空性
		if (userId == null) {
			return Result.genNullValueTip(result, "userId");
		}
		if (StringUtil.isEmpty(newPwd)) {
			return Result.genNullValueTip(result, "newPwd");
		}
		User user = userService.findById(userId);
		String userPwd = MD5Util.MD5(newPwd + CfgConstant.USER_PWD_KEY);// 新密码加密
		if (userPwd.equals(user.getUserPwd())) {
			logger.info("新密码与旧密码一致!");
			result.setCode(Result.FAILURE_CODE);
			result.setMsg("新密码与旧密码一致!");
			result.setData(newPwd);
		} else {
			logger.info("新密码与旧密码不一致!");
			result.setCode(Result.SUCCESS_CODE);
			result.setMsg("新密码与旧密码不一致!");
			result.setData(newPwd);
		}
		return result;
	}

	/**
	 * 测试方法
	 * 
	 * @param userId
	 * @param userName
	 * @return
	 */
	@RequestMapping(value = "/test.do")
	public Result test(Integer userId, String userName) {
		Result result = new Result();
		// 校验参数非空性
		if (userId == null) {
			return Result.genNullValueTip(result, "userId");
		}
		if (StringUtil.isEmpty(userName)) {
			return Result.genNullValueTip(result, "userName");
		}
		result.setCode(Result.SUCCESS_CODE);
		result.setMsg("请求成功!");
		result.setData(userId + "-" + userName);
		return result;
	}
}
