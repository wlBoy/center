package com.xn.hk.common.utils.cookie;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Objects;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xn.hk.common.constant.Constant;

/**
 * 
 * @ClassName: CookieUtil
 * @Package: com.xn.hk.common.utils.cookie
 * @Description: Cookie操作工具类
 * @Author: wanlei
 * @Date: 2018年11月1日 上午10:53:29
 */
public final class CookieUtil {
	/**
	 * 记录日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(CookieUtil.class);
	private static final String LOCALHOST = "localhost";

	/**
	 * 得到Cookie的值, 默认不URL编码
	 * 
	 * @param request
	 *            request请求
	 * @param cookieName
	 *            cookie名字
	 * @return cookie值
	 */
	public static String getCookieValue(HttpServletRequest request, String cookieName) {
		return getCookieValue(request, cookieName, false, null);
	}

	/**
	 * 得到Cookie的值
	 * 
	 * @param request
	 *            request请求
	 * @param cookieName
	 *            cookie名字
	 * @param isDecoder
	 *            是否需要进行URL编码，true指定字符集编码，false不指定，传null
	 * @param charSet
	 *            字符串编码，例如GBK，UTF-8
	 * @return cookie值
	 */
	public static String getCookieValue(HttpServletRequest request, String cookieName, boolean isDecoder,
			String charSet) {
		Cookie[] cookieList = request.getCookies();
		if (cookieList == null || cookieName == null) {
			return null;
		}
		String retValue = null;
		try {
			for (int i = 0; i < cookieList.length; i++) {
				if (cookieList[i].getName().equals(cookieName)) {
					if (isDecoder) {
						retValue = URLDecoder.decode(cookieList[i].getValue(), charSet);
					} else {
						retValue = cookieList[i].getValue();
					}
					break;
				}
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("URL编码失败，原因为:{}", e);
		}
		return retValue;
	}

	/**
	 * 设置Cookie的值 不设置生效时间默认浏览器关闭即失效,也不编码
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
			String cookieValue) {
		setCookie(request, response, cookieName, cookieValue, -1);
	}

	/**
	 * 设置Cookie的值 在指定时间内生效,但不编码
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
			String cookieValue, int cookieMaxage) {
		setCookie(request, response, cookieName, cookieValue, cookieMaxage, false);
	}

	/**
	 * 设置Cookie的值 不设置生效时间,但编码
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
			String cookieValue, boolean isEncode) {
		setCookie(request, response, cookieName, cookieValue, -1, isEncode);
	}

	/**
	 * 设置Cookie的值 在指定时间内生效, 编码参数
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
			String cookieValue, int cookieMaxage, boolean isEncode) {
		doSetCookie(request, response, cookieName, cookieValue, cookieMaxage, isEncode);
	}

	/**
	 * 设置Cookie的值 在指定时间内生效, 编码参数(指定编码)
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
			String cookieValue, int cookieMaxage, String encodeString) {
		doSetCookie(request, response, cookieName, cookieValue, cookieMaxage, encodeString);
	}

	/**
	 * 删除Cookie带cookie域名
	 */
	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
		doSetCookie(request, response, cookieName, "", -1, false);
	}

	/**
	 * 设置Cookie的值，并使其在指定时间内生效
	 * 
	 * @param cookieMaxage
	 *            cookie生效的最大秒数
	 */
	private static final void doSetCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
			String cookieValue, int cookieMaxage, boolean isEncode) {
		try {
			if (cookieValue == null) {
				cookieValue = "";
			} else if (isEncode) {
				cookieValue = URLEncoder.encode(cookieValue, Constant.UTF8);
			}
			Cookie cookie = new Cookie(cookieName, cookieValue);
			if (cookieMaxage > 0)
				cookie.setMaxAge(cookieMaxage);
			if (null != request) {// 设置域名的cookie
				String domainName = getDomainName(request);
				if (!LOCALHOST.equals(domainName)) {
					cookie.setDomain(domainName);
				}
			}
			cookie.setPath("/");
			response.addCookie(cookie);
		} catch (Exception e) {
			logger.error("设置cookie的值失败，原因为:{}", e);
		}
	}

	/**
	 * 设置Cookie的值，并使其在指定时间内生效
	 * 
	 * @param cookieMaxage
	 *            cookie生效的最大秒数
	 */
	private static final void doSetCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
			String cookieValue, int cookieMaxage, String encodeString) {
		try {
			if (cookieValue == null) {
				cookieValue = "";
			} else {
				cookieValue = URLEncoder.encode(cookieValue, encodeString);
			}
			Cookie cookie = new Cookie(cookieName, cookieValue);
			if (cookieMaxage > 0)
				cookie.setMaxAge(cookieMaxage);
			if (null != request) {// 设置域名的cookie
				String domainName = getDomainName(request);
				if (!LOCALHOST.equals(domainName)) {
					cookie.setDomain(domainName);
				}
			}
			cookie.setPath("/");
			response.addCookie(cookie);
		} catch (Exception e) {
			logger.error("设置cookie的值失败，原因为:{}", e);
		}
	}

	/**
	 * 得到cookie的域名
	 */
	private static final String getDomainName(HttpServletRequest request) {
		String domainName = null;
		String serverName = request.getRequestURL().toString();
		if (serverName == null || serverName.equals("")) {
			domainName = "";
		} else {
			serverName = serverName.toLowerCase();
			serverName = serverName.substring(7);
			final int end = serverName.indexOf("/");
			serverName = serverName.substring(0, end);
			final String[] domains = serverName.split("\\.");
			int len = domains.length;
			if (len > 3) {
				// www.xxx.com.cn
				domainName = "." + domains[len - 3] + "." + domains[len - 2] + "." + domains[len - 1];
			} else if (len <= 3 && len > 1) {
				// xxx.com or xxx.cn
				domainName = "." + domains[len - 2] + "." + domains[len - 1];
			} else {
				domainName = serverName;
			}
		}
		if (domainName != null && domainName.indexOf(":") > 0) {
			String[] ary = domainName.split("\\:");
			domainName = ary[0];
		}
		return domainName;
	}

	/**
	 * 根据name获取cookies中的值
	 *
	 * @param request
	 *            请求
	 * @param name
	 *            key的名称
	 * @return String 值
	 */
	public static String getValueByName(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (Objects.isNull(cookies)) {
			return null;
		}
		Cookie cookie = Arrays.stream(cookies).filter(data -> data.getName().equals(name)).findFirst().orElse(null);
		if (Objects.nonNull(cookie)) {
			return cookie.getValue();
		}
		return "";
	}

	/**
	 * 设置cookies信息
	 *
	 * @param key
	 *            key值
	 * @param value
	 *            设置的内容
	 * @param domain
	 *            cookie域名
	 * @param ttl
	 *            失效时间
	 * @param response
	 *            返回请求
	 */
	public static void setValueByName(String key, String value, String domain, Integer ttl,
			HttpServletResponse response) {
		Cookie cookie = new Cookie(key, value);
		if (StringUtils.isNotBlank(domain)) {
			cookie.setDomain(domain);
		}
		cookie.setMaxAge(Objects.nonNull(ttl) ? ttl : 1000);
		cookie.setPath("/");
		cookie.setSecure(false);
		cookie.setVersion(0);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
	}

	/**
	 * 设置cookies信息
	 *
	 * @param key
	 *            key值
	 * @param value
	 *            设置的内容
	 * @param ttl
	 *            失效时间
	 * @param response
	 *            返回请求
	 */
	public static void setValueByName(String key, String value, Integer ttl, HttpServletResponse response) {
		setValueByName(key, value, null, ttl, response);
	}

	/**
	 * 删除cookies信息
	 *
	 * @param name
	 *            key的名称
	 * @param request
	 * @param response
	 */
	public static void deleteValueByName(String name, HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (Objects.nonNull(cookies)) {
			Cookie cookie = Arrays.stream(cookies).filter(data -> data.getName().equals(name)).findFirst().orElse(null);
			if (Objects.nonNull(cookie)) {
				cookie.setMaxAge(1);
				cookie.setPath("/");
				cookie.setSecure(false);
				cookie.setVersion(0);
				cookie.setHttpOnly(true);
				response.addCookie(cookie);
			}
		}
	}
}
