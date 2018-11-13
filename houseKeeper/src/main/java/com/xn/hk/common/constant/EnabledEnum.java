package com.xn.hk.common.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public String getDesc() {
		return desc;
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

	/**
	 * 拿到所有的状态枚举map
	 * 
	 * @return 所有的状态枚举map
	 */
	public static Map<Integer, String> getChoiceMap() {
		Map<Integer, String> typeMaps = new HashMap<Integer, String>();
		for (EnabledEnum item : EnabledEnum.values()) {
			typeMaps.put(item.getCode(), item.getDesc());
		}
		return typeMaps;
	}

	/**
	 * 拿到所有的状态枚举List
	 * 
	 * @return 所有的状态枚举List
	 */
	public static List<EnabledEnum> getChoiceList() {
		List<EnabledEnum> typeList = new ArrayList<EnabledEnum>();
		for (EnabledEnum item : EnabledEnum.values()) {
			typeList.add(item);
		}
		return typeList;
	}
	
}
