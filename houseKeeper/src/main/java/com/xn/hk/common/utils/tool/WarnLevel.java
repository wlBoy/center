package com.xn.hk.common.utils.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	private Integer id;// 对应等级ID
	private String desc;// 描述语

	private WarnLevel(Integer id, String desc) {
		this.id = id;
		this.desc = desc;
	}

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

	/**
	 * 通过ID拿到其描述
	 * 
	 * @param Id
	 *            ID
	 * @return 描述
	 */
	public static String getTypeNameById(Integer Id) {
		String desc = null;
		for (WarnLevel level : WarnLevel.values()) {
			if (Id.intValue() == level.getId()) {
				desc = level.getDesc();
				break;
			}
		}
		return desc;
	}

	/**
	 * 拿到所有的警告级别枚举map
	 * 
	 * @return 所有的警告级别枚举map
	 */
	public static Map<Integer, String> getChoiceMap() {
		Map<Integer, String> typeMaps = new HashMap<Integer, String>();
		for (WarnLevel item : WarnLevel.values()) {
			typeMaps.put(item.getId(), item.getDesc());
		}
		return typeMaps;
	}

	/**
	 * 拿到所有的警告级别枚举List
	 * 
	 * @return 所有的警告级别枚举List
	 */
	public static List<WarnLevel> getChoiceList() {
		List<WarnLevel> typeList = new ArrayList<WarnLevel>();
		for (WarnLevel item : WarnLevel.values()) {
			typeList.add(item);
		}
		return typeList;
	}
}
