package com.xn.ad.common.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @ClassName: CookieUtil 
 * @PackageName: com.xn.ad.common.utils
 * @Description: cookie工具类
 * @author wanlei
 * @date 2018年5月11日 下午4:46:50
 */
public final class CookieUtil {

	/**
	 * 设置cookie
	 * @param response HttpServletResponse 对象
	 * @param name  cookie名字
	 * @param value cookie值
	 * @param domain 域名
	 * @param expiry cookie生命周期  以秒为单位
	 */
	public static void addCookie(HttpServletResponse response, String name, String value, String domain, int expiry) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		if (domain.trim() != "") {
			cookie.setDomain(domain);
		}
		if (expiry > 0) {
			cookie.setMaxAge(expiry);
		}
		response.addCookie(cookie);
	}
	
	/**
	 * 根据名字获取cookie
	 * @param request
	 * @param name cookie名字
	 * @return
	 */
	public static Cookie getCookieByName(HttpServletRequest request,String name){
	    Map<String,Cookie> cookieMap = ReadCookieMap(request);
	    if(cookieMap.containsKey(name)){
	        Cookie cookie = (Cookie)cookieMap.get(name);
	        return cookie;
	    }else{
	        return null;
	    }   
	}
	
	/**
	 * 将cookie封装到Map里面
	 * @param request
	 * @return
	 */
	public static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){  
	    Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
	    Cookie[] cookies = request.getCookies();
	    if(null!=cookies){
	        for(Cookie cookie : cookies){
	            cookieMap.put(cookie.getName(), cookie);
	        }
	    }
	    return cookieMap;
	}
}
