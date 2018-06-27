package com.xn.hk.system.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xn.hk.common.utils.page.BasePage;
import com.xn.hk.system.model.Log;
import com.xn.hk.system.model.User;
import com.xn.hk.system.service.LogService;
import com.xn.hk.system.service.UserService;

/**
 * 
 * @Title: LogController
 * @Package: com.xn.hk.system.controller
 * @Description: 处理记录日志的控制层
 * @Company: 杭州讯牛
 * @Author: wanlei
 * @Date: 2018年1月23日 下午4:00:22
 */
@Controller
@RequestMapping(value = "/system/log")
public class LogController {
	/**
	 * 记录日志
	 */
	private static final Logger logger = Logger.getLogger(LogController.class);
	@Autowired
	private LogService ls;
	@Autowired
	private UserService us;

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
		List<Log> logs = ls.pageList(pages);
		// 将list封装到分页对象中
		pages.setList(logs);
		mv.addObject("pages", pages);
		// 查询所有的用户
		List<User> users = us.findAll();
		mv.addObject("users", users);
		logger.info("用户的个数为:" + users.size());
		return mv;
	}

	/**
	 * 批量删除日志
	 * 
	 * @param logIds
	 *            日志ID数组
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/delete.do")
	public ModelAndView deleteLog(String[] logIds, HttpSession session) {
		ModelAndView mv = new ModelAndView("redirect:showAllLog.do");
		int result = ls.delete(logIds);
		if (result == 0) {
			logger.error("删除失败,该数组不存在!");
		} else {
			logger.info("删除日志成功!");
			session.setAttribute("msg", "<script>$(function(){swal('Good!', '删除日志成功!', 'success');});</script>");
		}
		return mv;
	}
}
