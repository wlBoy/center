package com.xn.hk.common.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName: UserStatus
 * @Package: com.xn.hk.common.utils
 * @Description: 状态枚举类
 * @Author: wanlei
 * @Date: 2018年8月22日 上午9:14:11
 */
public enum StatusEnum {

	ACTIVE(0, "激活"), LOCKED(1, "冻结");

	private StatusEnum(Integer code, String desc) {
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
		for (StatusEnum status : StatusEnum.values()) {
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
		for (StatusEnum item : StatusEnum.values()) {
			typeMaps.put(item.getCode(), item.getDesc());
		}
		return typeMaps;
	}

	/**
	 * 拿到所有的状态枚举List
	 * 
	 * @return 所有的状态枚举List
	 */
	public static List<StatusEnum> getChoiceList() {
		List<StatusEnum> typeList = new ArrayList<StatusEnum>();
		for (StatusEnum item : StatusEnum.values()) {
			typeList.add(item);
		}
		return typeList;
	}
}
