package com.xn.hk.account.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xn.hk.account.model.Money;
import com.xn.hk.account.service.MoneyService;
import com.xn.hk.common.utils.page.BasePage;
import com.xn.hk.system.model.User;
import com.xn.hk.system.service.UserService;
/**
 * 
 * @Title: MoneyController
 * @Package: com.xn.hk.account.controller
 * @Description: 处理资产的控制层
 * @Company: 杭州讯牛 
 * @Author: wanlei
 * @Date: 2018年1月4日 下午1:51:27
 */
@Controller
@RequestMapping(value = "/account/money")
public class MoneyController {
	/**
	 * 记录日志
	 */
	private static final Logger log = Logger.getLogger(MoneyController.class);
	@Autowired
	private MoneyService ms;
	@Autowired
	private UserService us;
	/**
	 * 实现总资产分页
	 * 
	 * @param Money
	 *            资产实体(用来封装查询条件)
	 * @param pages
	 *            分页对象
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/showAllMoney.do")
	public ModelAndView showAllMoney(Money m, BasePage<Money> pages) {
		ModelAndView mv = new ModelAndView("account/showAllMoney");
		// 封装查询条件
		pages.setBean(m);
		List<Money> moneys = ms.pageList(pages);
		// 将list封装到分页对象中
		pages.setList(moneys);
		mv.addObject("pages", pages);
		// 查询所有用户信息，供下拉框显示
		List<User> users = us.findAll();
		mv.addObject("users", users);
		log.info("所有用户个数为:"+users.size());
		return mv;
	}

}
