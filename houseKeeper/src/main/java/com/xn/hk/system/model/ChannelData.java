package com.xn.hk.system.model;

import java.io.Serializable;

/**
 * 
 * @Title: ChannelData
 * @Package: com.xn.ad.data.model
 * @Description: 渠道数据实体类
 * @Company: 杭州讯牛
 * @Author: wanlei
 * @Date: 2018-1-19 下午05:42:48
 */
public class ChannelData implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键ID
	 */
	private Integer id;
	/**
	 * 合作方
	 */
	private String partner;
	/**
	 * 应用名称
	 */
	private String appName;
	/**
	 * 渠道号
	 */
	private String channelNum;
	/**
	 * 合作方式
	 */
	private String partnerType;
	/**
	 * 创建日期
	 */
	private String curday;
	/**
	 * 激活数
	 */
	private int activeNum;
	/**
	 * 单价
	 */
	private double fee;
	/**
	 * 总金额
	 */
	private double sumFee;
	/**
	 * 开放状态
	 */
	private int status;
	/**
	 * 创建时间
	 */
	private String createtime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getChannelNum() {
		return channelNum;
	}

	public void setChannelNum(String channelNum) {
		this.channelNum = channelNum;
	}

	public String getPartnerType() {
		return partnerType;
	}

	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}

	public String getCurday() {
		return curday;
	}

	public void setCurday(String curday) {
		this.curday = curday;
	}

	public int getActiveNum() {
		return activeNum;
	}

	public void setActiveNum(int activeNum) {
		this.activeNum = activeNum;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public double getSumFee() {
		return sumFee;
	}

	public void setSumFee(double sumFee) {
		this.sumFee = sumFee;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Override
	public String toString() {
		return "ChannelData [activeNum=" + activeNum + ", appName=" + appName + ", channelNum=" + channelNum
				+ ", createtime=" + createtime + ", curday=" + curday + ", fee=" + fee + ", id=" + id + ", partner="
				+ partner + ", partnerType=" + partnerType + ", status=" + status + ", sumFee=" + sumFee + "]";
	}

}
