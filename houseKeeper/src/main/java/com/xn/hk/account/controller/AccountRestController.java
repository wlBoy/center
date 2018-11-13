package com.xn.hk.account.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xn.hk.account.model.Account;
import com.xn.hk.account.model.AccountType;
import com.xn.hk.account.service.AccountService;
import com.xn.hk.account.service.AccountTypeService;
import com.xn.hk.common.constant.Constant;
import com.xn.hk.common.constant.Result;
import com.xn.hk.common.utils.string.StringUtil;
import com.xn.hk.system.model.User;

/**
 * 
 * @Title: AccountRestController
 * @Package: com.xn.hk.account.controller
 * @Description: 处理账务管理中的所有ajax请求(接口)
 * @Author: wanlei
 * @Date: 2018年1月8日 上午9:17:36
 */
@RestController
@RequestMapping("/account/rest")
public class AccountRestController {
	/**
	 * 注入service层
	 */
	@Autowired
	private AccountTypeService accountTypeService;
	@Autowired
	private AccountService accountService;

	/**
	 * 根据账务类别Id查询该个人账务类别
	 * 
	 * @param typeId
	 *            账务类别Id
	 * @return 结果提示信息实体
	 */
	@RequestMapping(value = "/findByTypeId.do", method = RequestMethod.POST)
	public Result findByTypeId(Integer typeId) {
		// 校验参数非空性
		if (typeId == null) {
			return Result.genErrorTip("账务类别ID不能为空!");
		}
		AccountType type = accountTypeService.findById(typeId);
		if (type == null) {
			return Result.genErrorTip("该账务类别不存在!");
		}
		return Result.genSuccessTip("查到该账务类别!", type);
	}

	/**
	 * 根据个人账务类别名称和用户ID查找该用户的个人账务类别(需要用户ID)
	 * 
	 * @param typeName
	 *            账务类别名
	 * @param userId
	 *            用户ID
	 * @return 结果提示信息实体
	 */
	@RequestMapping(value = "/findByTypeName.do", method = RequestMethod.POST)
	public Result findByTypeName(String typeName, Integer userId, HttpSession session) {
		if (userId == null) {
			// 从session中拿出当前用户信息
			User user = (User) session.getAttribute(Constant.SESSION_USER);
			if (user == null) {
				return Result.genErrorTip("用户ID不能为空!");
			}
			userId = user.getUserId();
		}
		// 校验参数非空性
		if (StringUtil.isEmpty(typeName)) {
			return Result.genErrorTip("账务类别名称不能为空!");
		}
		AccountType type = accountTypeService.findByNameAndUserId(typeName, userId);
		if (type == null) {
			return Result.genErrorTip("该个人账务类别不存在!");
		}
		return Result.genSuccessTip("查到该个人账务类别!", type);
	}

	/**
	 * 根据账务Id查询该账务
	 * 
	 * @param accountId
	 *            账务Id
	 * @return 结果提示信息实体
	 */
	@RequestMapping(value = "/findByAccountId.do", method = RequestMethod.POST)
	public Result findByAccountId(Integer accountId) {
		// 校验参数非空性
		if (accountId == null) {
			return Result.genErrorTip("账务ID不能为空!");
		}
		Account account = accountService.findById(accountId);
		if (account == null) {
			return Result.genErrorTip("该账务不存在!");
		}
		return Result.genSuccessTip("查到该账务!", account);
	}

	/**
	 * 根据账务标题和用户ID查询该用户的个人账务标题(需要用户ID)
	 * 
	 * @param accountTitle
	 *            账务标题
	 * @param userId
	 *            用户ID
	 * @return 结果提示信息实体
	 */
	@RequestMapping(value = "/findByAccountTitle.do", method = RequestMethod.POST)
	public Result findByAccountTitle(String accountTitle, Integer userId, HttpSession session) {
		if (userId == null) {
			// 从session中拿出当前用户信息
			User user = (User) session.getAttribute(Constant.SESSION_USER);
			if (user == null) {
				return Result.genErrorTip("用户ID不能为空!");
			}
			userId = user.getUserId();
		}
		// 校验参数非空性
		if (StringUtil.isEmpty(accountTitle)) {
			return Result.genErrorTip("账务标题不能为空!");
		}
		Account account = accountService.findByNameAndUserId(accountTitle, userId);
		if (account == null) {
			return Result.genErrorTip("该个人账务不存在!");
		}
		return Result.genSuccessTip("查到该个人账务!", account);
	}
}
