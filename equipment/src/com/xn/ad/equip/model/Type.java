package com.xn.ad.equip.model;

import java.io.Serializable;

/**
 * 
 * @ClassName: Type
 * @PackageName: com.xn.ad.equip.model
 * @Description: 设备类型的实体类
 * @author wanlei
 * @date 2018年5月11日 下午5:21:41
 */
public class Type implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer typeId;// 设备类型ID
	private String typeName;// 设备类型名称
	private Integer isOk;// 是否有效：0：未删状态(默认)，1：已删状态
	private String createTime;// 创建时间
	private String updateTime;// 更新时间
	private String remark;// 备注信息

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

	public Integer getIsOk() {
		return isOk;
	}

	public void setIsOk(Integer isOk) {
		this.isOk = isOk;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
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
		return "Type [typeId=" + typeId + ", typeName=" + typeName + ", isOk="
				+ isOk + ", createTime=" + createTime + ", updateTime="
				+ updateTime + ", remark=" + remark + "]";
	}

}
