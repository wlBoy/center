package com.xn.hk.account.model;

import java.io.Serializable;
/**
 * 
 * @Title: AccountType
 * @Package: com.xn.hk.account.model
 * @Description: 账务类别实体类
 * @Author: wanlei
 * @Date: 2018年1月4日 下午1:19:03
 */
public class AccountType implements Serializable {
	private static final long serialVersionUID = 3422398391931431354L;
	/**
	 * 账务类别ID
	 */
	private Integer typeId;
	/**
	 * 账务类别所属的用户Id
	 */
	private Integer userId;
	/**
	 * 账务类别名称
	 */
	private String typeName;
	/**
	 * 所属父类别(支出或收入)
	 */
	private String parentType;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getParentType() {
		return parentType;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
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
		return "AccountType [typeId=" + typeId + ", userId=" + userId + ", typeName=" + typeName + ", parentType="
				+ parentType + ", isOk=" + isOk + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", remark=" + remark + "]";
	}

}
