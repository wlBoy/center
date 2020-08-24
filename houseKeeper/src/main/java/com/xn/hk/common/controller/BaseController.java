package com.xn.hk.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xn.hk.common.constant.AjaxResult;
import com.xn.hk.common.utils.encryption.InterfaceSignUtil;

/**
 * 
 * @Title: BaseService
 * @Package: com.xn.ad.common.service
 * @Description: 接口基础类
 * @Author: wanlei
 * @Date: 2017-11-28 下午03:47:05
 */
public class BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	/**
	 * 接口签名验证
	 * 
	 * @param request
	 * @return
	 */
	public AjaxResult interfaceProtect(HttpServletRequest request) {
		try {
			InterfaceSignUtil.protect(request);
			return null;
		} catch (Exception e) {
			logger.error("接口签名验证失败，原因为:", e);
			return new AjaxResult(AjaxResult.Type.ERROR, e.getMessage());
		}
	}
}
