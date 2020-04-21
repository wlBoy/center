package com.kl.ar.common.controller;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.PropertyConfigurator;

/**
 * 
 * @package:com.kl.ar.common.controller
 * @Description: log4j初始化servlet
 * @author: wanlei
 * @date: 2019年12月21日下午2:18:04
 */
public class Log4jInit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Log4jInit() {
		super();
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init();
		String prefix = config.getServletContext().getRealPath("/");
		String file = config.getInitParameter("configPath");
		String filePath = prefix + file;
		PropertyConfigurator.configure(filePath);// 装入log4j配置信息
	}

}
