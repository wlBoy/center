package com.xn.hk.common.constant;

import java.io.Serializable;

/**
 * 
 * @ClassName: Result
 * @Package: com.xn.hk.common.constant
 * @Description: 用于接口返回json数据的实体类
 * @Author: wanlei
 * @Date: 2018年8月23日 上午11:51:58
 */
public class Result implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 返回状态码
	 */
	private Integer code;
	/**
	 * 描述语
	 */
	private String desc;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
