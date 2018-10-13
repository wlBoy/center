package com.xn.hk.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xn.hk.common.constant.Constant;
import com.xn.hk.common.utils.log.LogType;
import com.xn.hk.common.utils.page.BasePage;
import com.xn.hk.system.model.AdminLog;
import com.xn.hk.system.model.Log;
import com.xn.hk.system.model.User;
import com.xn.hk.system.service.AdminLogService;
import com.xn.hk.system.service.LogService;
import com.xn.hk.system.service.UserService;

/**
 * 
 * @Title: LogController
 * @Package: com.xn.hk.system.controller
 * @Description: 处理记录日志的控制层
 * @Author: wanlei
 * @Date: 2018年1月23日 下午4:00:22
 */
@Controller
@RequestMapping(value = "/system/log")
public class LogController {
	/**
	 * 注入service层
	 */
	@Autowired
	private LogService logService;
	@Autowired
	private UserService userService;
	@Autowired
	private AdminLogService adminLogService;

	/**
	 * 分页显示所有的日志
	 * 
	 * @param log
	 *            日志对象
	 * @param pages
	 *            分页对象
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/showAllLog.do")
	public ModelAndView showAllLog(Log log, BasePage<Log> pages) {
		ModelAndView mv = new ModelAndView("system/showAllLog");
		// 封装查询条件
		pages.setBean(log);
		List<Log> logs = logService.pageList(pages);
		// 将list封装到分页对象中
		pages.setList(logs);
		mv.addObject(Constant.PAGE_KEY, pages);
		// 查询所有的用户
		List<User> users = userService.findAll();
		mv.addObject(Constant.USER_KEY, users);
		return mv;
	}

	/**
	 * 分页显示所有管理员操作的日志
	 * 
	 * @param log
	 *            日志对象
	 * @param pages
	 *            分页对象
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/showAllAdminLog.do")
	public ModelAndView showAllAdminLog(AdminLog adminLog, BasePage<AdminLog> pages) {
		ModelAndView mv = new ModelAndView("system/showAllAdminLog");
		// 封装查询条件
		pages.setBean(adminLog);
		List<AdminLog> logs = adminLogService.pageList(pages);
		// 填充日志类型描述语
		for (AdminLog log : logs) {
			log.setLogTypeDesc(LogType.getLogType(log.getLogType()));
		}
		// 将list封装到分页对象中
		pages.setList(logs);
		mv.addObject(Constant.PAGE_KEY, pages);
		// 查询所有的用户
		List<User> users = userService.findAll();
		mv.addObject(Constant.USER_KEY, users);
		return mv;
	}
}
