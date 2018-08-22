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
	 * 记录日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(AccountRestController.class);
	@Autowired
	private AccountTypeService ats;
	@Autowired
	private AccountService as;

	/**
	 * 根据账务类别Id和用户ID查询该用户的个人账务类别
	 * 
	 * @param typeId
	 *            账务类别Id
	 * @return 账务类别实体
	 */
	@RequestMapping(value = "/findByTypeId.do", method = RequestMethod.GET)
	public AccountType findByTypeId(Integer typeId) {
		AccountType type = ats.findById(typeId);
		logger.info("该账务类别的信息为:" + type);
		return type;
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
		AccountType type = ats.findByNameAndUserId(typeName, user.getUserId());
		logger.info("该账务类别的信息为:" + type);
		return type;
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
		Account account = as.findById(accountId);
		logger.info("该账务的信息为:" + account);
		return account;
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
		Account account = as.findByNameAndUserId(accountTitle, user.getUserId());
		logger.info("该账务的信息为:" + account);
		return account;
	}
}
