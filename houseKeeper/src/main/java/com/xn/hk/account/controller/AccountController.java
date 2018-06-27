package com.xn.hk.account.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xn.hk.account.model.Account;
import com.xn.hk.account.model.AccountType;
import com.xn.hk.account.service.AccountService;
import com.xn.hk.account.service.AccountTypeService;
import com.xn.hk.common.constant.Constant;
import com.xn.hk.common.utils.page.BasePage;
import com.xn.hk.system.model.User;
import com.xn.hk.system.service.UserService;

/**
 * 
 * @Title: AccountController
 * @Package: com.xn.hk.account.controller
 * @Description: 处理账务的控制层
 * @Company: 杭州讯牛
 * @Author: wanlei
 * @Date: 2018年1月4日 下午1:51:27
 */
@Controller
@RequestMapping(value = "/account/account")
public class AccountController {
	/**
	 * 记录日志
	 */
	private static final Logger log = Logger.getLogger(AccountController.class);
	@Autowired
	private AccountService as;
	@Autowired
	private AccountTypeService ats;
	@Autowired
	private UserService us;

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
		User user = (User) session.getAttribute(Constant.LOGIN_SESSION_USER_KEY);
		account.setUserId(user.getUserId());
		// 封装查询条件
		pages.setBean(account);
		List<Account> accounts = as.pagePersonalList(pages);
		// 将list封装到分页对象中
		pages.setList(accounts);
		mv.addObject("pages", pages);
		// 查询父级别为"收入"的子账务类别,供页面下拉框显示
		List<AccountType> comeInTypes = ats.findChildType("收入", user.getUserId());
		mv.addObject("comeInTypes", comeInTypes);
		log.info("父级别为'收入'的子账务类别个数为:" + comeInTypes.size());
		// 查询父级别为"支出"的子账务类别,供页面下拉框显示
		List<AccountType> comeOutTypes = ats.findChildType("支出", user.getUserId());
		mv.addObject("comeOutTypes", comeOutTypes);
		log.info("父级别为'支出'的子账务类别个数为:" + comeInTypes.size());
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
		List<Account> accounts = as.pageList(pages);
		// 将list封装到分页对象中
		pages.setList(accounts);
		mv.addObject("pages", pages);
		// 查询所有用户信息，供下拉框显示
		List<User> users = us.findAll();
		mv.addObject("users", users);
		log.info("用户的个数为:" + users.size());
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
		ModelAndView mv = new ModelAndView("redirect:showPersonalAccount.do");
		// 从session中拿出当前用户信息,将它塞入对象中去
		User user = (User) session.getAttribute(Constant.LOGIN_SESSION_USER_KEY);
		account.setUserId(user.getUserId());
		int result = as.addAccount(account);
		if (result == 0) {
			log.error("添加个人账务失败!");
		} else {
			log.info("添加个人账务成功!");
			session.setAttribute("msg", "<script>$(function(){swal('Good!', '添加个人账务成功!', 'success');});</script>");
		}
		return mv;
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
		ModelAndView mv = new ModelAndView("redirect:showPersonalAccount.do");
		int result = as.updateAccount(account);
		if (result == 0) {
			log.error("修改个人账务失败!");
		} else {
			log.info("修改个人账务成功!");
			session.setAttribute("msg", "<script>$(function(){swal('Good!', '修改个人账务成功!', 'success');});</script>");
		}
		return mv;
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
		ModelAndView mv = new ModelAndView("redirect:showPersonalAccount.do");
		int result = as.deleteAccount(accountIds);
		if (result == 0) {
			log.error("删除失败,该数组不存在!");
		} else {
			log.info("删除个人账务成功!");
			session.setAttribute("msg", "<script>$(function(){swal('Good!', '删除个人账务成功!', 'success');});</script>");
		}
		return mv;
	}

}
