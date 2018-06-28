package com.xn.ad.equip.model;

import java.io.Serializable;

/**
 * 
 * @ClassName: Equip
 * @PackageName: com.xn.ad.equip.model
 * @Description: 设备信息的实体类
 * @author wanlei
 * @date 2018年5月11日 下午5:24:00
 */
public class Equip implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer equipId;// 设备ID
	private String equipName;// 设备名称
	private Integer typeId;// 设备类型ID
	private String typeName;// 设备类别名称
	private double equipPrice;// 设备价格
	private String equipProducer;// 设备厂家
	private String addPeople;// 添加设备人
	private Integer isOk;// 是否有效：0：未删状态(默认)，1：已删状态
	private String createTime;// 创建时间
	private String updateTime;// 更新时间
	private String remark;// 备注信息

	public Integer getEquipId() {
		return equipId;
	}

	public void setEquipId(Integer equipId) {
		this.equipId = equipId;
	}

	public String getEquipName() {
		return equipName;
	}

	public void setEquipName(String equipName) {
		this.equipName = equipName;
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

	public double getEquipPrice() {
		return equipPrice;
	}

	public void setEquipPrice(double equipPrice) {
		this.equipPrice = equipPrice;
	}

	public String getEquipProducer() {
		return equipProducer;
	}

	public void setEquipProducer(String equipProducer) {
		this.equipProducer = equipProducer;
	}

	public String getAddPeople() {
		return addPeople;
	}

	public void setAddPeople(String addPeople) {
		this.addPeople = addPeople;
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
		return "Equip [equipId=" + equipId + ", equipName=" + equipName
				+ ", typeId=" + typeId + ", typeName=" + typeName
				+ ", equipPrice=" + equipPrice + ", equipProducer="
				+ equipProducer + ", addPeople=" + addPeople + ", isOk=" + isOk
				+ ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", remark=" + remark + "]";
	}

}
