package com.xn.hk.account.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xn.hk.account.model.Account;
import com.xn.hk.account.model.AccountType;
import com.xn.hk.account.service.AccountService;
import com.xn.hk.account.service.AccountTypeService;
import com.xn.hk.common.constant.Constant;
import com.xn.hk.common.constant.View;
import com.xn.hk.common.utils.page.BasePage;
import com.xn.hk.common.utils.string.StringUtil;
import com.xn.hk.system.model.User;
import com.xn.hk.system.service.UserService;

/**
 * 
 * @Title: AccountController
 * @Package: com.xn.hk.account.controller
 * @Description: 处理账务的控制层
 * @Author: wanlei
 * @Date: 2018年1月4日 下午1:51:27
 */
@Controller
@RequestMapping(value = "/account/account")
public class AccountController {
	/**
	 * 记录日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
	
	/**
	 * 注入service层
	 */
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountTypeService accountTypeService;
	@Autowired
	private UserService userService;

	/**
	 * 实现个人账务分页
	 * 
	 * @param account
	 *            账务实体(用来封装查询条件)
	 * @param pages
	 *            分页对象
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/showPersonalAccount.do")
	public ModelAndView showPersonalAccount(Account account, BasePage<Account> pages, HttpSession session) {
		ModelAndView mv = new ModelAndView("account/showPersonalAccount");
		// 从session中拿出当前用户信息,将它塞入分页对象中去
		User user = (User) session.getAttribute(Constant.SESSION_USER_KEY);
		account.setUserId(user.getUserId());
		// 封装查询条件
		pages.setBean(account);
		List<Account> accounts = accountService.pagePersonalList(pages);
		// 将list封装到分页对象中
		pages.setList(accounts);
		mv.addObject(Constant.PAGE_KEY, pages);
		// 查询父级别为"收入"的子账务类别,供页面下拉框显示
		List<AccountType> comeInTypes = accountTypeService.findChildType(Constant.COMEIN_VALUE, user.getUserId());
		mv.addObject(Constant.COMEIN_TYPES_KEY, comeInTypes);
		// 查询父级别为"支出"的子账务类别,供页面下拉框显示
		List<AccountType> comeOutTypes = accountTypeService.findChildType(Constant.COMEOUT_VALUE, user.getUserId());
		mv.addObject(Constant.COMEOUT_TYPES_KEY, comeOutTypes);
		return mv;
	}

	/**
	 * 实现总账务分页
	 * 
	 * @param account
	 *            账务实体(用来封装查询条件)
	 * @param pages
	 *            分页对象
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/showAllAccount.do")
	public ModelAndView showAllAccount(Account account, BasePage<Account> pages) {
		ModelAndView mv = new ModelAndView("account/showAllAccount");
		// 封装查询条件
		pages.setBean(account);
		List<Account> accounts = accountService.pageList(pages);
		// 将list封装到分页对象中
		pages.setList(accounts);
		mv.addObject(Constant.PAGE_KEY, pages);
		// 查询所有用户信息，供下拉框显示
		List<User> users = userService.findAll();
		mv.addObject(Constant.USER_KEY, users);
		return mv;
	}

	/**
	 * 添加个人账务(记住要用户ID)
	 * 
	 * @param account
	 *            账务实体(用来封装查询条件)
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/add.do")
	public ModelAndView addAccount(Account account, HttpSession session) {
		// 从session中拿出当前用户信息,将它塞入对象中去
		User user = (User) session.getAttribute(Constant.SESSION_USER_KEY);
		account.setUserId(user.getUserId());
		int result = accountService.addAccount(account);
		if (result == Constant.ZERO_VALUE) {
			logger.error("添加个人账务{}失败!", account.getAccountTitle());
		} else {
			logger.info("添加个人账务{}成功!", account.getAccountTitle());
			session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("添加个人账务成功!", Constant.SUCCESS_TIP_KEY));
		}
		return View.PERSONAL_ACCOUNT_REDITRCT_ACTION;
	}

	/**
	 * 修改账务
	 * 
	 * @param account
	 *            账务(用来封装查询条件)
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/update.do")
	public ModelAndView updateAccount(Account account, HttpSession session) {
		int result = accountService.updateAccount(account);
		if (result == Constant.ZERO_VALUE) {
			logger.error("修改个人账务{}失败!", account.getAccountTitle());
		} else {
			logger.info("修改个人账务{}成功!", account.getAccountTitle());
			session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("修改个人账务成功!", Constant.SUCCESS_TIP_KEY));
		}
		return View.PERSONAL_ACCOUNT_REDITRCT_ACTION;
	}

	/**
	 * 根据ID数组删除一个或多个个人账务
	 * 
	 * @param accountIds
	 *            账务ID数组
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/delete.do")
	public ModelAndView deleteAccount(Integer[] accountIds, HttpSession session) {
		int result = accountService.deleteAccount(accountIds);
		if (result == Constant.ZERO_VALUE) {
			logger.error("删除失败,该数组不存在!");
		} else {
			logger.info("删除个人账务成功!");
			session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("删除个人账务成功!", Constant.SUCCESS_TIP_KEY));
		}
		return View.PERSONAL_ACCOUNT_REDITRCT_ACTION;
	}

}
