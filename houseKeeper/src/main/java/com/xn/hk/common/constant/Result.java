package com.xn.hk.common.constant;

import java.io.Serializable;

/**
 * 
 * @ClassName: Result
 * @Package: com.xn.hk.common.constant
 * @Description: 用于接口返回json数据的实体类(信息提示实体)
 * @Author: wanlei
 * @Date: 2018年8月23日 上午11:51:58
 */
public class Result implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int SUCCESS_CODE = 0;// 返回的状态码,0代表成功
	public static final int FAILURE_CODE = 1;// 返回的状态码,1代表失败
	/**
	 * 返回的状态码,0代表成功，1代表失败
	 */
	private Integer code;
	/**
	 * 提示信息
	 */
	private String msg;
	/**
	 * 返回的对象数据
	 */
	private Object data;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 当参数非空时，返回的提示信息实体
	 * 
	 * @param result
	 *            提示信息实体类
	 * @param desc
	 *            参数提示语
	 * @return 返回提示信息实体
	 */
	public static Result genNullValueTip(Result result, String desc) {
		result.setCode(FAILURE_CODE);
		result.setMsg(desc + "参数不能为空!");
		result.setData(null);
		return result;
	}
}
