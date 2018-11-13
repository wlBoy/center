package com.xn.hk.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xn.hk.common.constant.Result;
import com.xn.hk.common.utils.cfg.SystemCfg;
import com.xn.hk.common.utils.encryption.HashUtil;
import com.xn.hk.common.utils.string.StringUtil;
import com.xn.hk.system.model.FileEntity;
import com.xn.hk.system.model.Module;
import com.xn.hk.system.model.Role;
import com.xn.hk.system.model.User;
import com.xn.hk.system.service.FileService;
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
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private FileService fileService;

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
			return Result.genErrorTip("用户ID不能为空!");
		}
		User user = userService.findById(userId);
		if (user == null) {
			Result.genErrorTip("该用户不存在!");
		}
		return Result.genSuccessTip("查到该用户!", user);
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
			return Result.genErrorTip("用户名不能为空!");
		}
		User user = userService.findByName(userName);
		if (user == null) {
			Result.genErrorTip("该用户不存在!");
		}
		return Result.genSuccessTip("查到该用户!", user);
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
			return Result.genErrorTip("角色ID不能为空!");
		}
		Role role = roleService.findById(roleId);
		if (role == null) {
			Result.genErrorTip("该角色不存在!");
		}
		return Result.genSuccessTip("查到该角色!", role);
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
			return Result.genErrorTip("角色名不能为空!");
		}
		Role role = roleService.findByName(roleName);
		if (role == null) {
			Result.genErrorTip("该角色不存在!");
		}
		return Result.genSuccessTip("查到该角色!", role);
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
			return Result.genErrorTip("模块ID不能为空!");
		}
		Module module = moduleService.findById(moduleId);
		if (module == null) {
			return Result.genErrorTip("该模块不存在!");
		}
		return Result.genSuccessTip("查到该模块!", module);
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
			return Result.genErrorTip("模块名不能为空!");
		}
		Module module = moduleService.findByName(moduleName);
		if (module == null) {
			return Result.genErrorTip("该模块不存在!");
		}
		return Result.genSuccessTip("查到该模块!", module);
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
			return Result.genErrorTip("用户ID不能为空!");
		}
		if (StringUtil.isEmpty(newPwd)) {
			return Result.genErrorTip("新密码不能为空!");
		}
		User user = userService.findById(userId);
		String userPwd = HashUtil.encryptStr(newPwd + SystemCfg.USER_PWD_KEY);// 新密码加密
		if (!userPwd.equals(user.getUserPwd())) {
			return Result.genErrorTip("新密码与旧密码不一致!");
		}
		return Result.genSuccessTip("新密码与旧密码一致!", newPwd);
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
			return Result.genErrorTip("用户ID不能为空!");
		}
		if (StringUtil.isEmpty(userName)) {
			return Result.genErrorTip("用户姓名不能为空!");
		}
		return Result.genSuccessTip("请求成功!", userId + "-" + userName);
	}

	/**
	 * 根据文件Id查询该文件
	 * 
	 * @param fileId
	 *            文件Id
	 * @return 结果提示信息实体
	 */
	@RequestMapping(value = "/findByFileId.do", method = RequestMethod.POST)
	public Result findByFileId(String fileId) {
		// 校验参数非空性
		if (fileId == null) {
			return Result.genErrorTip("文件ID不能为空!");
		}
		FileEntity file = fileService.findById(fileId);
		if (file == null) {
			Result.genErrorTip("该文件不存在!");
		}
		return Result.genSuccessTip("查到该文件!", file);
	}
}
