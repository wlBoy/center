package com.xn.hk.common.utils.web;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wanlei
 * @description 浏览器工具类
 * @date 2020/12/9 14:30
 **/
public class BrowserUtil {
	/**
	 * 浏览器用户代理
	 */
	private static final String USER_AGENT = "User-Agent";
	/**
	 * 浏览器类型
	 */
	private static final String TYPE = "type";
	/**
	 * 浏览器版本
	 */
	private static final String VERSION = "version";
	/**
	 * 操作系统
	 */
	private static final String OS = "os";

	/**
	 * 获取各种浏览器版本号及类型，返回浏览器信息MAP<type,version>
	 *
	 * @param req
	 * @return
	 */
	public static Map<String, String> getBrowserInfoMap(HttpServletRequest req) {
		Map<String, String> browserMap = new HashMap<String, String>();
		String userAgent = req.getHeader(USER_AGENT);
		// 获取浏览器信息
		Browser browser = UserAgent.parseUserAgentString(userAgent).getBrowser();
		// 获取操作系统信息
		OperatingSystem os = UserAgent.parseUserAgentString(userAgent).getOperatingSystem();
		// 获取浏览器版本号
		Version version = browser.getVersion(userAgent);
		browserMap.put(TYPE, browser.getName());
		browserMap.put(VERSION, version.getVersion());
		browserMap.put(OS, os.getName());
		return browserMap;
	}

	/**
	 * 获取浏览器类型
	 *
	 * @param req
	 * @return
	 */
	public static String getBrowserType(HttpServletRequest req) {
		return getBrowserInfoMap(req).get(TYPE);
	}

	/**
	 * 获取浏览器版本号
	 *
	 * @param req
	 * @return
	 */
	public static String getBrowserVersion(HttpServletRequest req) {
		return getBrowserInfoMap(req).get(VERSION);
	}

	/**
	 * 获取操作系统信息
	 *
	 * @param req
	 * @return
	 */
	public static String getOs(HttpServletRequest req) {
		return getBrowserInfoMap(req).get(OS);
	}
}
