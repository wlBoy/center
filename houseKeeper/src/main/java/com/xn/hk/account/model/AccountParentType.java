package com.xn.hk.account.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName: AccountParentType
 * @Package: com.xn.hk.account.model
 * @Description: 账务父类别的枚举类
 * @Author: wanlei
 * @Date: 2018年10月15日 下午4:43:39
 */
public enum AccountParentType {
	COME_IN(0, "收入"), COME_OUT(1, "支出");

	private Integer id;// ID值
	private String desc;// 描述

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private AccountParentType(Integer id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	/**
	 * 通过类型ID拿到对应类型描述
	 * 
	 * @param id
	 *            类型ID
	 * @return 返回类型描述
	 */
	public static String getTypeDesc(Integer id) {
		String desc = null;
		for (AccountParentType type : AccountParentType.values()) {
			if (id.intValue() == type.getId().intValue()) {
				desc = type.getDesc();
				break;
			}
		}
		return desc;
	}

	/**
	 * 拿到所有的类型枚举map
	 * 
	 * @return 所有的类型枚举map
	 */
	public static Map<Integer, String> getChoiceMap() {
		Map<Integer, String> typeMaps = new HashMap<Integer, String>();
		for (AccountParentType item : AccountParentType.values()) {
			typeMaps.put(item.getId(), item.getDesc());
		}
		return typeMaps;
	}

	/**
	 * 拿到所有的类型枚举List
	 * 
	 * @return 所有的类型枚举List
	 */
	public static List<AccountParentType> getChoiceList() {
		List<AccountParentType> typeList = new ArrayList<AccountParentType>();
		for (AccountParentType item : AccountParentType.values()) {
			typeList.add(item);
		}
		return typeList;
	}
}
