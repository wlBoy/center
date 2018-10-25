package com.xn.hk.common.utils.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.xn.hk.common.constant.Constant;
import com.xn.hk.common.constant.View;
import com.xn.hk.common.utils.string.StringUtil;

/**
 * 
 * @ClassName: ExceptionHandle
 * @Package: com.xn.hk.common.utils.exception
 * @Description: 自定义处理异常类
 * @Author: wanlei
 * @Date: 2018年10月25日 下午1:47:16
 */

public class ExceptionHandler implements HandlerExceptionResolver {

	/**
	 * 处理上传文件大小超过限制抛出的异常
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse res, Object ob, Exception ex) {
		// 判断异常类型，来跳转不同页面
		if (ex instanceof MaxUploadSizeExceededException) {
			req.getSession().setAttribute(Constant.TIP_MSG, StringUtil.genTipMsg("上传文件过大!", Constant.ERROR_TIP));
			return View.FILE_REDITRCT_ACTION;
		}
		// 其他异常
		return null;
	}

}
