package com.xn.ad.common.enums;
/**
 * 
 * @ClassName: ModuleLevel 
 * @PackageName: com.xn.ad.common.enums
 * @Description: 菜单级别的枚举类
 * @author wanlei
 * @date 2018年5月11日 下午4:45:46
 */
public enum ModuleLevel {

	ONE(1, "一级（模块级）"), TWO(2, "二级（页面级）");
	public int key;
	public String value;

	private ModuleLevel(int key, String value) {
		this.key = key;
		this.value = value;
	}

	public static ModuleLevel get(int key) {
		ModuleLevel[] values = ModuleLevel.values();
		for (ModuleLevel object : values) {
			if (object.key == key) {
				return object;
			}
		}
		return null;
	}

}
