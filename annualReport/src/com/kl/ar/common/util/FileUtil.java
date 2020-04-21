package com.kl.ar.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.codec.binary.Base64;

import com.kl.ar.common.constant.Constant;

/**
 * 
 * @package:com.kl.ar.common.util
 * @Description:  文件工具类
 * @author: wanlei
 * @date: 2019年12月21日下午2:58:05
 */
public class FileUtil {
	/**
	 * 兼容各大浏览器，获取文件下载名，防止中文乱码
	 * 
	 * @param request
	 * @param fileName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getFileName(HttpServletRequest request, String fileName) throws UnsupportedEncodingException {
		// 判断是否是IE11
		Boolean flag = request.getHeader("User-Agent").indexOf("like Gecko") > 0;
		// 兼容IE和非IE,下载文件名中文乱码问题
		if (request.getHeader("user-agent").toLowerCase().contains("msie") || flag) {
			// IE
			fileName = URLEncoder.encode(fileName, Constant.UTF8);
		} else if (request.getHeader("user-agent").contains("Firefox")) {
			// 火狐浏览器
			fileName = "=?UTF-8?B?" + Base64.encodeBase64String(fileName.getBytes( Constant.UTF8)) + "?=";
		} else {
			// 非IE
			fileName = new String(fileName.getBytes( Constant.UTF8), Constant.ISO_8859_1);
		}
		return fileName;
	}
}
