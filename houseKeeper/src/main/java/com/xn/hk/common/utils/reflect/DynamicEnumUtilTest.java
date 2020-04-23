package com.xn.hk.common.utils.reflect;

import com.xn.hk.common.constant.StatusEnum;

/**
 * @author wanlei
 * @description 测试-动态新增和修改枚举的工具类
 * @date 2020/4/23 15:00
 **/
public class DynamicEnumUtilTest {

	private static void saveTestEnum(String enumName, Integer code, String desc) {
		DynamicEnumUtil.saveEnum(StatusEnum.class, enumName, new Class<?>[] { Integer.class, String.class },
				new Object[] { code, desc });
	}

	/**
	 * 测试方法
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		
		saveTestEnum("ACTIVE", 2, "激活状态");
		saveTestEnum("DIED", 3, "休眠状态");

		for (StatusEnum testEnum : StatusEnum.values()) {
			System.out.println(testEnum.toString());
		}
	}

}
