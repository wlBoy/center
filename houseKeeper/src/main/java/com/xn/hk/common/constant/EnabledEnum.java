package com.xn.hk.common.constant;

/**
 * 
 * @ClassName: UserStatus
 * @Package: com.xn.hk.common.utils
 * @Description: 是否可用枚举类
 * @Author: wanlei
 * @Date: 2018年8月22日 上午9:14:11
 */
public enum EnabledEnum {

	ENABLED(0, "正常"), DISABLED(1, "已删除");

	private EnabledEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	private Integer code;// 状态码
	private String desc;// 描述

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
	 * 通过状态码拿到其描述
	 * 
	 * @param code
	 *            状态码
	 * @return 描述语
	 */
	public static String getDescByCode(Integer code) {
		String desc = null;
		for (EnabledEnum status : EnabledEnum.values()) {
			if (code.intValue() == status.getCode()) {
				desc = status.getDesc();
				break;
			}
		}
		return desc;
	}

}
