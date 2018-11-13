package com.xn.hk.common.utils.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName: UnitEnum
 * @Package: com.xn.hk.common.utils.file
 * @Description: 国际单位的枚举类
 * @Author: wanlei
 * @Date: 2018年11月13日 下午3:27:40
 */
public enum UnitEnum {
	// 国际单位换算为:1YB=1024ZB,1ZB=1024EB,1EB=1024PB,1PB=1024TB,1TB=1024GB,1GB=1024MB,1MB=1024KB,1KB=1024BYTE,1BYTE=8BIT
	BIT_UNIT("BIT", "位"), BYTE_UNIT("BYTE", "字节"), KB_UNIT("KB", "KB"), MB_UNIT("MB", "兆"), GB_UNIT("GB",
			"GB"), TB_UNIT("TB",
					"TB"), PB_UNIT("PB", "PB"), EB_UNIT("EB", "EB"), ZB_UNIT("ZB", "ZB"), YB_UNIT("YB", "YB");
	private String code;// 单位代码
	private String desc;// 描述

	private UnitEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

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

	/**
	 * 通过单位代码拿到其描述
	 * 
	 * @param code
	 *            状态码
	 * @return 描述语
	 */
	public static String getDescByCode(String code) {
		String desc = null;
		for (UnitEnum unit : UnitEnum.values()) {
			if (code.equalsIgnoreCase(unit.getCode())) {
				desc = unit.getDesc();
				break;
			}
		}
		return desc;
	}

	/**
	 * 拿到所有的单位枚举map
	 * 
	 * @return 所有的单位枚举map
	 */
	public static Map<String, String> getChoiceMap() {
		Map<String, String> unitMaps = new HashMap<String, String>();
		for (UnitEnum item : UnitEnum.values()) {
			unitMaps.put(item.getCode(), item.getDesc());
		}
		return unitMaps;
	}

	/**
	 * 拿到所有的单位枚举List
	 * 
	 * @return 所有的单位枚举List
	 */
	public static List<UnitEnum> getChoiceList() {
		List<UnitEnum> unitList = new ArrayList<UnitEnum>();
		for (UnitEnum item : UnitEnum.values()) {
			unitList.add(item);
		}
		return unitList;
	}
}
