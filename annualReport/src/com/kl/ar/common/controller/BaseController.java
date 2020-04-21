package com.kl.ar.common.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;


import com.google.gson.Gson;
import com.kl.ar.common.constant.Constant;
import com.kl.ar.user.entity.CrmUser;
/**
 * 
 * @package:com.kl.ar.common.controller
 * @Description:  基础控制层
 * @author: wanlei
 * @date: 2019年12月21日下午2:17:28
 */
public abstract class BaseController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static Gson gson = new Gson();

	/**
	 * 获取当前登录用户
	 * 
	 * @param request
	 * @return
	 */
	public CrmUser getUser(HttpServletRequest request) {
		return (CrmUser) request.getSession().getAttribute(Constant.USER_IN_SESSION);
	}

	/**
	 * 获取当前登录用户ID
	 * 
	 * @param request
	 * @return
	 */
	public String getUserId(HttpServletRequest request) {
		return getUser(request).getUSERID();
	}
	/**
	 * 获取当前登录的用户名称
	 * 
	 * @param request
	 * @return
	 */
	public String getUserName(HttpServletRequest request) {
		return getUser(request).getsName();
	}

	/**
	 * 获取当前登录用户主管部门
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getOrgs(HttpServletRequest request) {
		Object attribute = request.getSession().getAttribute(Constant.USER_LEADER_ORG);
		if (attribute == null) {
			return null;
		} else {
			return (List<Map<String, Object>>) attribute;
		}
	}
	
	/**
	 * 获取cookie中的值
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getCookieByName(HttpServletRequest request, String key) throws UnsupportedEncodingException {
		String value = null;
		// 获取cookie中的证书SN项作为登录用户名
		Cookie cookies[] = request.getCookies();
		if (cookies != null && cookies.length != 0) {
			for (Cookie cookie : cookies) {
				if (key.equals(cookie.getName())) {
					value = URLDecoder.decode(cookie.getValue(), Constant.GBK);
				}
			}
		}
		return value;
	}

}
