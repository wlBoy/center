package com.xn.ad.common.enums;

/**
 * 
 * @ClassName: IsAllowed 
 * @PackageName: com.xn.ad.common.enums
 * @Description: 模块是否分配的枚举类
 * @author wanlei
 * @date 2018年5月11日 下午4:45:22
 */
public enum IsAllowed {

	YES(0, "可分配"), NO(1, "不可分配");
	public int key;
	public String value;

	private IsAllowed(int key, String value) {
		this.key = key;
		this.value = value;
	}

	public static IsAllowed get(int key) {
		IsAllowed[] values = IsAllowed.values();
		for (IsAllowed object : values) {
			if (object.key == key) {
				return object;
			}
		}
		return null;
	}

}
