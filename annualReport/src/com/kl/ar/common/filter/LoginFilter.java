package com.kl.ar.common.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.kl.ar.common.cfg.ConfigUtil;
import com.kl.ar.common.constant.Constant;
import com.kl.ar.common.constant.View;

/**
 * 
 * @package:com.kl.ar.common.filter
 * @Description: 登录过滤器
 * @author: wanlei
 * @date: 2019年12月21日下午2:44:49
 */
public class LoginFilter implements Filter {
	// 检查的url地址
	private String[] urlArray = new String[] { "self", "rs", "view", "vote" };

	public LoginFilter() {
	}

	public void destroy() {
	}

	/**
	 * 过滤方法
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 获取配置文件中的管理员密码登录地址
		String adminPwdLoginUrl = ConfigUtil.getInstance().loadCfgMap().get(Constant.ADMIN_PWD_LOGIN_URL);
		HttpServletRequest req = (HttpServletRequest) request;
		String uri = req.getRequestURI();
		uri = req.getRequestURL().toString();
		// 管理员登录(密码登录)
		if (uri.contains(adminPwdLoginUrl)) {
			Object user = req.getSession().getAttribute(Constant.USER_IN_SESSION);
			if (user == null) {
				request.getRequestDispatcher(View.PWDLOGIN_TOLOGIN_ACTION).forward(request, response);
				return;
			}
		}
		// 普通登录(网关证书登录)
		boolean isCheck = false;
		List<String> urlList = Arrays.asList(urlArray);
		for (String str : urlList) {
			if (uri.contains(str)) {
				isCheck = true;
				break;
			}
		}
		if (isCheck) {
			Object user = req.getSession().getAttribute(Constant.USER_IN_SESSION);
			if (user == null) {
				request.getRequestDispatcher(View.PWDLOGIN_TOSESSION_ACTION).forward(request, response);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
