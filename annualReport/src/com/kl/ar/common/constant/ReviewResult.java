package com.kl.ar.common.constant;

/**
 * 
 * @package:com.kl.ar.common.constant
 * @Description:  部门评分结果枚举类
 * @author: wanlei
 * @date: 2019年12月21日下午2:15:31
 */
public enum ReviewResult {
	KeepOn(1, "留用"), Promotion(2, "晋升"), Commend(3, "推优"), Other(4, "其他");
	private Integer code;
	private String desc;

	ReviewResult(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

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

	/**
	 * 通过code拿到其描述
	 * 
	 * @param code
	 * @return
	 */
	public static String getDescByCode(Integer code) {
		for (ReviewResult result : ReviewResult.values()) {
			if (code != null) {
				if (result.getCode().intValue() == code.intValue()) {
					return result.getDesc();
				}
			}
		}
		return null;
	}
}
