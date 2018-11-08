package com.xn.hk.dataDic.model;

import java.io.Serializable;

import com.xn.hk.common.constant.EnabledEnum;
import com.xn.hk.common.utils.string.StringUtil;

/**
 * 
 * @ClassName: DataDic
 * @Package: com.xn.hk.data.dic.model
 * @Description: 数据字典实体类
 * @Author: wanlei
 * @Date: 2018年11月7日 下午6:30:10
 */
public class DataDic implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 数据字典ID
	 */
	private Integer dataDicId;
	/**
	 * 数据字典名称
	 */
	private String dataDicName;
	/**
	 * 数据字典代码
	 */
	private String dataDicCode;
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

	public Integer getDataDicId() {
		return dataDicId;
	}

	public void setDataDicId(Integer dataDicId) {
		this.dataDicId = dataDicId;
	}

	public String getDataDicName() {
		return dataDicName;
	}

	public void setDataDicName(String dataDicName) {
		this.dataDicName = dataDicName;
	}

	public String getDataDicCode() {
		return dataDicCode;
	}

	public void setDataDicCode(String dataDicCode) {
		this.dataDicCode = dataDicCode;
	}

	public Integer getIsOk() {
		return isOk;
	}

	public void setIsOk(Integer isOk) {
		this.isOk = isOk;
	}

	public String getCreateTime() {
		// 解决mysql数据库datetime类型取出时间多个.0的问题，截取前19位即可，格式为:yyyy-MM-dd HH:mm:ss
		return StringUtil.subMysqlTimeStr(createTime);
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		// 解决mysql数据库datetime类型取出时间多个.0的问题，截取前19位即可，格式为:yyyy-MM-dd HH:mm:ss
		return StringUtil.subMysqlTimeStr(updateTime);
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
		return "DataDic [dataDicId=" + dataDicId + ", dataDicName=" + dataDicName + ", dataDicCode=" + dataDicCode
				+ ", isOk=" + isOk + ", createTime=" + createTime + ", updateTime=" + updateTime + ", remark=" + remark
				+ "]";
	}

	/**
	 * 拼接日志内容(排除空信息和可指定记录内容字段)
	 * 
	 * @return 日志内容
	 */
	public String getLogContent() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		if (getDataDicId() != null) {
			sb.append("数据字典ID=" + getDataDicId() + ",");
		}
		if (getDataDicName() != null) {
			sb.append("数据字典名称=" + getDataDicName());
		}
		if (getDataDicCode() != null) {
			sb.append("数据字典代码=" + getDataDicCode() + ",");
		}
		if (getIsOk() != null) {
			sb.append("是否可用=" + EnabledEnum.getDescByCode(getIsOk()) + ",");
		}
		if (createTime != null) {
			sb.append("创建时间=" + getCreateTime() + ",");
		}
		if (updateTime != null) {
			sb.append("更新时间=" + getUpdateTime() + ",");
		}
		if (getRemark() != null) {
			sb.append("备注信息=" + getRemark() + ",");
		}
		// 去除最后一个,后再拼接]
		String str = sb.toString();
		str = str.substring(0, str.length() - 1) + "]";
		return str;
	}
}
