package com.kl.ar.common.constant;

/**
 * 
 * @package:com.kl.ar.common.constant
 * @Description: 报告状态枚举类 
 * @author: wanlei
 * @date: 2019年12月21日下午2:14:54
 */
public enum ReportStatus {
	SUBMITED("2000", "已提交"), UN_SUBMITED("9000", "未提交"), APPROVAL_DENY("1010", "驳回");

	private String code;
	private String desc;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private ReportStatus(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
