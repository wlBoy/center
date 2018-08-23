package com.xn.hk.common.constant;

/**
 * 
 * @ClassName: UserStatus
 * @Package: com.xn.hk.common.utils
 * @Description: 状态枚举类
 * @Author: wanlei
 * @Date: 2018年8月22日 上午9:14:11
 */
public enum StatusEnum {

	NORMAL(0, "正常状态"), ISLOCKED(1, "冻结状态");

	private StatusEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	private Integer code;//状态码
	private String desc;//描述

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
