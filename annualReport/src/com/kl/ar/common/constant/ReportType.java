package com.kl.ar.common.constant;

/**
 * 
 * @package:com.kl.ar.common.constant
 * @Description:  报告类型枚举类
 * @author: wanlei
 * @date: 2019年12月21日下午2:15:12
 */
public enum ReportType {
	TEC("tec", "技术类"), SALE("sale", "销售类"), ADMINISTRATION("administration", "行政类");
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

	private ReportType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	/**
	 * 通过code取到其描述
	 *
	 * @param type
	 * @return
	 */
	public static String getCodeByDesc(String code) {
		String desc = null;
		ReportType[] types = ReportType.values();
		for (ReportType type : types) {
			if (code.equals(type.getCode())) {
				desc = type.getDesc();
			}
		}
		return desc;
	}
}
