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
import com.xn.hk.system.model.User;

/**
 * 
 * @Title: AccountRestController
 * @Package: com.xn.hk.account.controller
 * @Description: 处理账务管理中的所有ajax请求
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
	 * 根据账务类别Id和用户ID查询该用户的个人账务类别
	 * 
	 * @param typeId
	 *            账务类别Id
	 * @return 账务类别实体
	 */
	@RequestMapping(value = "/findByTypeId.do", method = RequestMethod.GET)
	public AccountType findByTypeId(Integer typeId) {
		return accountTypeService.findById(typeId);
	}

	/**
	 * 根据个人账务类别ID和用户ID查找该用户的个人账务类别(需要用户ID)
	 * 
	 * @param typeName
	 *            账务类别名
	 * @return 账务类别实体
	 */
	@RequestMapping(value = "/findByTypeName.do", method = RequestMethod.GET)
	public AccountType findByTypeName(String typeName, HttpSession session) {
		// 从session中拿出当前用户信息
		User user = (User) session.getAttribute(Constant.SESSION_USER_KEY);
		return accountTypeService.findByNameAndUserId(typeName, user.getUserId());
	}

	/**
	 * 根据账务Id查询该账务
	 * 
	 * @param accountId
	 *            账务Id
	 * @return 账务类别实体
	 */
	@RequestMapping(value = "/findByAccountId.do", method = RequestMethod.GET)
	public Account findByAccountId(Integer accountId) {
		return accountService.findById(accountId);
	}

	/**
	 * 根据账务标题和用户ID查询该用户的个人账务标题(需要用户ID)
	 * 
	 * @param accountTitle
	 *            账务标题
	 * @return 账务类别实体
	 */
	@RequestMapping(value = "/findByAccountTitle.do", method = RequestMethod.GET)
	public Account findByAccountTitle(String accountTitle, HttpSession session) {
		// 从session中拿出当前用户信息
		User user = (User) session.getAttribute(Constant.SESSION_USER_KEY);
		return accountService.findByNameAndUserId(accountTitle, user.getUserId());
	}
}
