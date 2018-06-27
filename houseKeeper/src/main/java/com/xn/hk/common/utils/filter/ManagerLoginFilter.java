package com.xn.hk.common.utils.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 
 * @Title: ManagerLoginFilter
 * @Package: com.xn.ad.common.utils
 * @Description: 登录过滤器工具类
 * @Company: 杭州讯牛 
 * @Author: wanlei
 * @Date: 2017-12-5 上午09:37:29
 */
public class ManagerLoginFilter implements Filter {
	/*
	           在web.xml中进行配置
		<!-- 配置后台登录过滤器 -->
		<filter>
			<filter-name>ManagerLoginFilter</filter-name>
			<filter-class>com.seecen.elearning.utils.ManagerLoginFilter</filter-class>
		</filter>
		<filter-mapping>
			<filter-name>ManagerLoginFilter</filter-name>
			<!--项目根路径下的manager文件夹下面的所有文件-->
			<url-pattern>/manager/*</url-pattern>
		</filter-mapping>
	*/
	//登录页面的路径
	public static final String MANAGER_LOGIN_PAGE = "/manager/login.jsp";
	
	
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws ServletException, IOException {
		// 先将ServletRequest转换为HttpServletRequest对象
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		// 获取项目名称
		String ctx = request.getContextPath();
		// 除掉项目名称后的访问页面的当前路径
		String targetURL = request.getRequestURI().substring(ctx.length());
		HttpSession session = request.getSession();
		// 对当前页面进行判断，如果当前页面不为登录页面
		if (!(MANAGER_LOGIN_PAGE.equals(targetURL))) {
			// 在不为登陆页面时，如果不是登陆页面或session中也没有已登录的用户信息，则跳转到登录页面让用户先登录
			if (session == null || session.getAttribute("user") == null) {
				session.setAttribute("msg", "<script>$(function(){swal('OMG!', '您还没有登录,请先登录!', 'error');});</script>");
				response.sendRedirect(ctx + MANAGER_LOGIN_PAGE);
				return;
			} else {
				// 已经登录，会去寻找下一个链，如果不存在，则进行正常的页面跳转
				chain.doFilter(request, response);
				return;
			}
		}else {
			// 此页为登录页面，正常通过，放行
			chain.doFilter(request, response);
			return;
		}
	}
	
	public void destroy() {

	}
}
