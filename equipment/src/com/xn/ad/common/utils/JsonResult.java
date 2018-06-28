package com.xn.ad.common.utils;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 
 * @ClassName: JsonResult
 * @PackageName: com.xn.ad.common.utils
 * @Description: 系统中ajax请求的返回JSON结果实体类
 * @author wanlei
 * @date 2018年5月11日 下午4:47:51
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("serial")
public class JsonResult<T> implements Serializable {

	private int code; // 返回状态码 -1:错误状态,0:没有数据, 1:成功状态

	private String msg; // 返回提示信息

	private T data; // 返回数据

	public JsonResult() {
		super();
	}

	public JsonResult(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public JsonResult(int code, String msg, T data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
