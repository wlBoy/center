package com.xn.hk.exam.model;

import java.io.Serializable;

import com.xn.hk.common.constant.EnabledEnum;

/**
 * @Title: QuestionType
 * @Package: com.xn.hk.exam.model
 * @Description: 题型实体类
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
	private Integer isOk;
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

	public Integer getIsOk() {
		return isOk;
	}

	public void setIsOk(Integer isOk) {
		this.isOk = isOk;
	}

	public String getCreateTime() {
		// 解决mysql数据库datetime类型取出时间多个.0的问题，截取前19位即可，格式为:yyyy-MM-dd HH:mm:ss
		return createTime.length() == 19 ? createTime : createTime.substring(0, 19);
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		// 解决mysql数据库datetime类型取出时间多个.0的问题，截取前19位即可，格式为:yyyy-MM-dd HH:mm:ss
		return updateTime.length() == 19 ? updateTime : updateTime.substring(0, 19);
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

	/**
	 * 拼接日志内容(排除空信息和可指定记录内容字段)
	 * 
	 * @return 日志内容
	 */
	public String getLogContent() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		if (getTypeId() != null) {
			sb.append("题型ID=" + getTypeId() + ",");
		}
		if (getTypeName() != null) {
			sb.append("题型名称=" + getTypeName() + ",");
		}
		if (getIsOk() != null) {
			sb.append("是否可用=" + EnabledEnum.getDescByCode(getIsOk()) + ",");
		}
		if (getCreateTime() != null) {
			sb.append("创建时间=" + getCreateTime() + ",");
		}
		if (createTime != null) {
			sb.append("创建时间=" + getCreateTime() + ",");
		}
		if (updateTime != null) {
			sb.append("更新时间=" + getUpdateTime() + ",");
		}
		// 去除最后一个,后再拼接]
		String str = sb.toString();
		str = str.substring(0, str.length() - 1) + "]";
		return str;
	}

}
