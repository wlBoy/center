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

import com.xn.hk.common.constant.Constant;
import com.xn.hk.common.utils.string.StringUtil;

/**
 * 
 * @Title: ManagerLoginFilter
 * @Package: com.xn.ad.common.utils
 * @Description: 登录过滤器工具类
 * @Author: wanlei
 * @Date: 2017-12-5 上午09:37:29
 */
public class ManagerLoginFilter implements Filter {
	/**
	 * <!-- 配置后台登录过滤器 --> <filter> <filter-name>ManagerLoginFilter</filter-name>
	 * <filter-class>com.xn.hk.common.utils.filter.ManagerLoginFilter</filter-class>
	 * </filter> <filter-mapping> <filter-name>ManagerLoginFilter</filter-name>
	 * <url-pattern>*.do</url-pattern> </filter-mapping>
	 */
	// 转向登录页面的Action
	private static final String TOLOGIN_ACTION = "/system/user/tologin.do";
	// 转向后台首页的Action
	private static final String TOHOME_ACTION = "/system/user/tohome.do";
	// 请求的IP地址
	public static String IP = null;

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws ServletException, IOException {
		// 先将ServletRequest转换为HttpServletRequest对象
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		// 获取项目名称
		String ctx = request.getContextPath();
		// 初始化IP地址
		IP = request.getRemoteAddr();
		// 除掉项目名称后的访问页面的当前路径
		String targetURL = request.getRequestURI().substring(ctx.length());
		HttpSession session = request.getSession();
		// 当访问的是后台首页，则进行session判断，没登录转向登录页面
		if (TOHOME_ACTION.equals(targetURL)) {
			if (session == null || session.getAttribute(Constant.SESSION_USER) == null) {
				session.setAttribute(Constant.TIP_MSG, StringUtil.genTipMsg("您还没有登录,请先登录!", Constant.ERROR_TIP));
				response.sendRedirect(ctx + TOLOGIN_ACTION);
				return;
			}
		}
		// 其他情况放行
		chain.doFilter(request, response);
		return;
	}

	public void destroy() {

	}
}
