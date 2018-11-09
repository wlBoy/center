package com.xn.hk.common.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName: QuestionTypeEnum
 * @Package: com.xn.hk.common.constant
 * @Description: 题型枚举类
 * @Author: wanlei
 * @Date: 2018年8月23日 下午1:00:57
 */
public enum QuestionType {
	singleType(1, "单选题"), multipleType(2, "多选题"), briefType(3, "简答题");

	private Integer typeId;// 题型ID
	private String typeName;// 题型名字

	private QuestionType(Integer typeId, String typeName) {
		this.typeId = typeId;
		this.typeName = typeName;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	/**
	 * 通过类型ID拿到其类型名
	 * 
	 * @param typeId
	 *            类型ID
	 * @return 类型名
	 */
	public static String getTypeNameById(Integer typeId) {
		String typeName = null;
		for (QuestionType type : QuestionType.values()) {
			if (typeId.intValue() == type.getTypeId()) {
				typeName = type.getTypeName();
				break;
			}
		}
		return typeName;
	}

	/**
	 * 拿到所有的类型枚举map
	 * 
	 * @return 所有的类型枚举map
	 */
	public static Map<Integer, String> getChoiceMap() {
		Map<Integer, String> typeMaps = new HashMap<Integer, String>();
		for (QuestionType item : QuestionType.values()) {
			typeMaps.put(item.getTypeId(), item.getTypeName());
		}
		return typeMaps;
	}

	/**
	 * 拿到所有的类型枚举List
	 * 
	 * @return 所有的类型枚举List
	 */
	public static List<QuestionType> getChoiceList() {
		List<QuestionType> typeList = new ArrayList<QuestionType>();
		for (QuestionType item : QuestionType.values()) {
			typeList.add(item);
		}
		return typeList;
	}
}
