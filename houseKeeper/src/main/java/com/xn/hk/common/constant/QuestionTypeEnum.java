package com.xn.hk.common.constant;

/**
 * 
 * @ClassName: QuestionTypeEnum
 * @Package: com.xn.hk.common.constant
 * @Description: 题型枚举类
 * @Author: wanlei
 * @Date: 2018年8月23日 下午1:00:57
 */
public enum QuestionTypeEnum {
	singleType(1, "单选题"), multipleType(2, "多选题"), briefType(3, "简答题");

	private Integer typeId;// 题型ID
	private String typeName;// 题型名字

	private QuestionTypeEnum(Integer typeId, String typeName) {
		this.typeId = typeId;
		this.typeName = typeName;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}