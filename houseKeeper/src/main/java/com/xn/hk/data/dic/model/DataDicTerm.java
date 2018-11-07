package com.xn.hk.data.dic.model;

import java.io.Serializable;

import com.xn.hk.common.constant.EnabledEnum;
import com.xn.hk.common.utils.string.StringUtil;

/**
 * 
 * @ClassName: DataDicTerm
 * @Package: com.xn.hk.data.dic.model
 * @Description: 数据字典项的实体类
 * @Author: wanlei
 * @Date: 2018年11月7日 下午6:38:30
 */
public class DataDicTerm implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 数据字典项ID
	 */
	private Integer dataDicTermId;
	/**
	 * 数据字典代码
	 */
	private String dataDicCode;
	/**
	 * 数据字典项代码
	 */
	private String dataDicTermCode;
	/**
	 * 数据字典项值
	 */
	private String dataDicTermValue;
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

	public Integer getDataDicTermId() {
		return dataDicTermId;
	}

	public void setDataDicTermId(Integer dataDicTermId) {
		this.dataDicTermId = dataDicTermId;
	}

	public String getDataDicCode() {
		return dataDicCode;
	}

	public void setDataDicCode(String dataDicCode) {
		this.dataDicCode = dataDicCode;
	}

	public String getDataDicTermCode() {
		return dataDicTermCode;
	}

	public void setDataDicTermCode(String dataDicTermCode) {
		this.dataDicTermCode = dataDicTermCode;
	}

	public String getDataDicTermValue() {
		return dataDicTermValue;
	}

	public void setDataDicTermValue(String dataDicTermValue) {
		this.dataDicTermValue = dataDicTermValue;
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
		return "DataDicTerm [dataDicTermId=" + dataDicTermId + ", dataDicCode=" + dataDicCode + ", dataDicTermCode="
				+ dataDicTermCode + ", dataDicTermValue=" + dataDicTermValue + ", isOk=" + isOk + ", createTime="
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
		if (getDataDicTermId() != null) {
			sb.append("数据字典项ID=" + getDataDicTermId() + ",");
		}
		if (getDataDicCode() != null) {
			sb.append("数据字典代码=" + getDataDicCode() + ",");
		}
		if (getDataDicTermCode() != null) {
			sb.append("数据字典项代码=" + getDataDicTermCode() + ",");
		}
		if (getDataDicTermValue() != null) {
			sb.append("数据字典项值=" + getDataDicTermValue() + ",");
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
