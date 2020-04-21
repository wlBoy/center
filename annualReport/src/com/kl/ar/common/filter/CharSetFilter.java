package com.kl.ar.common.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kl.ar.common.constant.Constant;

/**
 * 
 * @package:com.kl.ar.common.filter
 * @Description:  系统字符集编码过滤器
 * @author: wanlei
 * @date: 2019年12月21日下午2:44:10
 */
public class CharSetFilter implements Filter {

	/**
	 */
	public CharSetFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * 以UTF-8编码进行过滤
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		req.setCharacterEncoding(Constant.UTF8);
		res.setCharacterEncoding(Constant.UTF8);
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
