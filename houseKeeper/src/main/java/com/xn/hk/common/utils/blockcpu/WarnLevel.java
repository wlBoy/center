package com.xn.hk.common.utils.blockcpu;

/**
 * 
 * @ClassName: SystemWarnLevel
 * @Package: com.xn.hk.common.utils.blockcpu
 * @Description: 系统告警级别枚举类
 * @Author: wanlei
 * @Date: 2018年9月29日 下午4:20:11
 */
public enum WarnLevel {
	LEVE_NORMAL(0, "正常"), LEVE_WARNING(1, "警告"), LEVE_YELLOW(2, "一般"), LEVE_ORANGE(4, "较重"), LEVE_RED(8, "严重");

	private Integer value;
	private String desc;

	private WarnLevel(Integer value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	/**
	 * @return the value
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

}
