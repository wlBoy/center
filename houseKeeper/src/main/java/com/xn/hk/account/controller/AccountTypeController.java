package com.xn.hk.account.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xn.hk.account.model.AccountType;
import com.xn.hk.account.service.AccountTypeService;
import com.xn.hk.common.constant.Constant;
import com.xn.hk.common.constant.View;
import com.xn.hk.common.utils.page.BasePage;
import com.xn.hk.common.utils.string.StringUtil;
import com.xn.hk.system.model.User;

/**
 * 
 * @Title: AccountTypeController
 * @Package: com.xn.hk.account.controller
 * @Description: 处理账务类别的控制层
 * @Author: wanlei
 * @Date: 2018年1月8日 上午9:18:25
 */
@Controller
@RequestMapping(value = "/account/type")
public class AccountTypeController {
	/**
	 * 记录日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(AccountTypeController.class);

	/**
	 * 注入service层
	 */
	@Autowired
	private AccountTypeService ats;

	/**
	 * 实现个人账务类别分页
	 * 
	 * @param type
	 *            账务类别实体(用来封装查询条件)
	 * @param pages
	 *            分页对象
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/showPersonalType.do")
	public ModelAndView showPersonalType(AccountType type, BasePage<AccountType> pages, HttpSession session) {
		ModelAndView mv = new ModelAndView("account/showPersonalType");
		// 从session中拿出当前用户信息,将它塞入分页对象中去
		User user = (User) session.getAttribute(Constant.SESSION_USER_KEY);
		type.setUserId(user.getUserId());
		// 封装查询条件
		pages.setBean(type);
		List<AccountType> types = ats.pagePersonalList(pages);
		// 将list封装到分页对象中
		pages.setList(types);
		mv.addObject(Constant.PAGE_KEY, pages);
		return mv;
	}

	/**
	 * 添加个人账务类别(需要用户ID)
	 * 
	 * @param type
	 *            账务类别实体(用来封装查询条件)
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/add.do")
	public ModelAndView addType(AccountType type, HttpSession session) {
		// 从session中拿出当前用户信息,将它塞入对象中去
		User user = (User) session.getAttribute(Constant.SESSION_USER_KEY);
		type.setUserId(user.getUserId());
		int result = ats.insert(type);
		if (result == 0) {
			logger.error("添加个人账务类别{}失败!", type.getTypeName());
		} else {
			logger.error("添加个人账务类别{}成功!", type.getTypeName());
			session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("添加个人账务类别成功!", "success"));
		}
		return View.ACCOUNT_TYPE_REDITRCT_ACTION;
	}

	/**
	 * 修改账务类别
	 * 
	 * @param type
	 *            账务类别实体(用来封装查询条件)
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/update.do")
	public ModelAndView updateType(AccountType type, HttpSession session) {
		int result = ats.update(type);
		if (result == 0) {
			logger.error("修改个人账务类别{}失败!", type.getTypeName());
		} else {
			logger.error("修改个人账务类别{}成功!", type.getTypeName());
			session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("修改个人账务类别成功!", "success"));
		}
		return View.ACCOUNT_TYPE_REDITRCT_ACTION;
	}

	/**
	 * 根据ID数组删除一个或多个个人账务类别
	 * 
	 * @param typeIds
	 *            账务类别ID数组
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/delete.do")
	public ModelAndView deleteType(Integer[] typeIds, HttpSession session) {
		int result = ats.batchDelete(typeIds);
		if (result == 0) {
			logger.error("删除失败,该数组不存在!");
		} else {
			logger.error("删除个人账务类别成功!");
			session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("删除个人账务类别成功!", "success"));
		}
		return View.ACCOUNT_TYPE_REDITRCT_ACTION;
	}
}
