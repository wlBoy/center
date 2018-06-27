package com.xn.hk.exam.model;

import java.io.Serializable;

/**
 * @Title: QuestionType
 * @Package: com.xn.hk.exam.model
 * @Description: 题型实体类
 * @Company: 杭州讯牛
 * @Author: wanlei
 * @Date: 2018年1月8日 上午10:30:59
 */
public class QuestionType implements Serializable {
	private static final long serialVersionUID = -8944119252427842590L;
	/**
	 * 题型ID
	 */
	private Integer typeId;
	/**
	 * 题型名
	 */
	private String typeName;
	/**
	 * 是否有效：0：未删状态(默认)，1：已删状态
	 */
	private int isOk;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 最后更新时间
	 */
	private String updateTime;
	/**
	 * 备注信息
	 */
	private String remark;

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

	public int getIsOk() {
		return isOk;
	}

	public void setIsOk(int isOk) {
		this.isOk = isOk;
	}

	public String getCreateTime() {
		return createTime.substring(0, createTime.length() - 2);
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime.substring(0, updateTime.length() - 2);
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "QuestionType [typeId=" + typeId + ", typeName=" + typeName + ", isOk=" + isOk + ", createTime="
				+ createTime + ", updateTime=" + updateTime + ", remark=" + remark + "]";
	}

}
