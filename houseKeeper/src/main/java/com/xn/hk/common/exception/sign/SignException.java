package com.xn.hk.common.exception.sign;

import com.xn.hk.common.exception.base.BaseException;

/**
 * rest接口签名验证异常
 *
 * @Author: wanl
 * @Date: 2019/12/5 16:01
 */
public class SignException extends BaseException {
	private static final long serialVersionUID = 1L;

	public SignException(String code, Object[] args) {
		super("sign", code, args, null);
	}

	public SignException(String code, String msg) {
		super("sign", code, null, msg);
	}

	public SignException(String msg) {
		super("sign", null, null, msg);
	}

}
