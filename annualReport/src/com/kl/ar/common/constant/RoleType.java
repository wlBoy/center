package com.kl.ar.common.constant;

/**
 * 
 * @package:com.kl.ar.common.constant
 * @Description:  系统角色枚举类
 * @author: wanlei
 * @date: 2019年12月21日下午2:15:52
 */
public enum RoleType {
	ALL("all", "全部"), ONLY_READ("read", "只读");
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

	private RoleType(String code, String desc) {
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
		RoleType[] types = RoleType.values();
		for (RoleType type : types) {
			if (code.equals(type.getCode())) {
				desc = type.getDesc();
			}
		}
		return desc;
	}
}
