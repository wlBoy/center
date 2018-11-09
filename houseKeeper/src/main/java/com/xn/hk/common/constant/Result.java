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
	public static final int SUCCESS = 0;// 返回的状态码,0代表成功
	public static final int FAILURE = 1;// 返回的状态码,1代表失败
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

	public Result() {
		super();
	}

	public Result(Integer code, String msg, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

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

	@Override
	public String toString() {
		return "Result [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}

	/**
	 * 当参数为空时，返回参数为空的提示信息
	 * 
	 * @param desc
	 *            参数提示语
	 * @return 返回提示信息实体
	 */
	public static Result genNullParamTip(String desc) {
		return new Result(FAILURE, desc + "参数不能为空!", null);
	}

	/**
	 * 生成信息实体
	 * 
	 * @param code
	 *            状态码
	 * @param desc
	 *            提示信息
	 * @param data
	 *            返回数据
	 * @return 返回提示信息实体
	 */
	public static Result genTip(Integer code, String desc, Object data) {
		return new Result(code, desc, data);
	}
}
