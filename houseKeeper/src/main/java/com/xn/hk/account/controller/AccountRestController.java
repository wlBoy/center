package com.xn.hk.account.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	 * 记录日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(AccountRestController.class);
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
	 * @return 账务类别实体
	 */
	@RequestMapping(value = "/findByTypeId.do", method = RequestMethod.POST)
	public Result findByTypeId(Integer typeId) {
		Result result = new Result();
		// 校验参数非空性
		if (typeId == null) {
			return Result.genNullValueTip(result, "typeId");
		}
		AccountType type = accountTypeService.findById(typeId);
		if (type == null) {
			logger.info("该账务类别不存在!");
			result.setCode(Result.FAILURE_CODE);
			result.setMsg("该账务类别不存在!");
			result.setData(null);
			return result;
		}
		logger.info("查到该账务类别!");
		result.setCode(Result.SUCCESS_CODE);
		result.setMsg("查到该账务类别!");
		result.setData(type);
		return result;
	}

	/**
	 * 根据个人账务类别名称和用户ID查找该用户的个人账务类别(需要用户ID)
	 * 
	 * @param typeName
	 *            账务类别名
	 * @param userId
	 *            用户ID
	 * @return 账务类别实体
	 */
	@RequestMapping(value = "/findByTypeName.do", method = RequestMethod.POST)
	public Result findByTypeName(String typeName, Integer userId, HttpSession session) {
		Result result = new Result();
		if (userId == null) {
			// 从session中拿出当前用户信息
			User user = (User) session.getAttribute(Constant.SESSION_USER);
			if (user == null) {
				return Result.genNullValueTip(result, "userId");
			}
			userId = user.getUserId();
		}
		// 校验参数非空性
		if (StringUtil.isEmpty(typeName)) {
			return Result.genNullValueTip(result, "typeName");
		}
		AccountType type = accountTypeService.findByNameAndUserId(typeName, userId);
		if (type == null) {
			logger.info("该个人账务类别不存在!");
			result.setCode(Result.FAILURE_CODE);
			result.setMsg("该个人账务类别不存在!");
			result.setData(null);
			return result;
		}
		logger.info("查到该个人账务类别!");
		result.setCode(Result.SUCCESS_CODE);
		result.setMsg("查到该个人账务类别!");
		result.setData(type);
		return result;
	}

	/**
	 * 根据账务Id查询该账务
	 * 
	 * @param accountId
	 *            账务Id
	 * @return 账务类别实体
	 */
	@RequestMapping(value = "/findByAccountId.do", method = RequestMethod.POST)
	public Result findByAccountId(Integer accountId) {
		Result result = new Result();
		// 校验参数非空性
		if (accountId == null) {
			return Result.genNullValueTip(result, "accountId");
		}
		Account account = accountService.findById(accountId);
		if (account == null) {
			logger.info("该账务不存在!");
			result.setCode(Result.FAILURE_CODE);
			result.setMsg("该账务不存在!");
			result.setData(null);
			return result;
		}
		logger.info("查到该账务!");
		result.setCode(Result.SUCCESS_CODE);
		result.setMsg("查到该账务!");
		result.setData(account);
		return result;
	}

	/**
	 * 根据账务标题和用户ID查询该用户的个人账务标题(需要用户ID)
	 * 
	 * @param accountTitle
	 *            账务标题
	 * @param userId
	 *            用户ID
	 * @return 账务类别实体
	 */
	@RequestMapping(value = "/findByAccountTitle.do", method = RequestMethod.POST)
	public Result findByAccountTitle(String accountTitle, Integer userId, HttpSession session) {
		Result result = new Result();
		if (userId == null) {
			// 从session中拿出当前用户信息
			User user = (User) session.getAttribute(Constant.SESSION_USER);
			if (user == null) {
				return Result.genNullValueTip(result, "userId");
			}
			userId = user.getUserId();
		}
		// 校验参数非空性
		if (StringUtil.isEmpty(accountTitle)) {
			return Result.genNullValueTip(result, "accountTitle");
		}
		Account account = accountService.findByNameAndUserId(accountTitle, userId);
		if (account == null) {
			logger.info("该个人账务不存在!");
			result.setCode(Result.FAILURE_CODE);
			result.setMsg("该个人账务不存在!");
			result.setData(null);
			return result;
		}
		logger.info("查到该个人账务!");
		result.setCode(Result.SUCCESS_CODE);
		result.setMsg("查到该个人账务!");
		result.setData(account);
		return result;
	}
}
