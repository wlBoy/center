package com.xn.hk.account.model;

import java.io.Serializable;

import com.xn.hk.common.utils.string.StringUtil;
/**
 * 
 * @Title: Money
 * @Package: com.xn.hk.account.model
 * @Description: 资产实体类
 * @Author: wanlei
 * @Date: 2018年1月4日 下午1:12:22
 */
public class Money implements Serializable {
	private static final long serialVersionUID = 1515789883679084005L;
	/**
	 * 资产ID
	 */
	private Integer moneyId;
	/**
	 * 资产所属的用户ID
	 */
	private Integer userId;
	/**
	 * 收入金额
	 */
	private double inFee;
	/**
	 * 支出金额
	 */
	private double outFee;
	/**
	 * 总资产
	 */
	private double TotalFee;
	/**
	 * 是否有效：0：未删状态(默认)，1：已删状态
	 */
	private int isOk;
	/**
	 * 创建月份,固定8位(格式:YYYYmm)
	 */
	private String curmonth;
	/**
	 * 创建日期,固定11位(格式:YYYYmmdd)
	 */
	private String curday;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 最后更新时间
	 */
	private String updateTime;
	/**
	 * 资产所属的用户名,供页面显示
	 */
	private String userName;

	public Integer getMoneyId() {
		return moneyId;
	}

	public void setMoneyId(Integer moneyId) {
		this.moneyId = moneyId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public double getInFee() {
		return inFee;
	}

	public void setInFee(double inFee) {
		this.inFee = inFee;
	}

	public double getOutFee() {
		return outFee;
	}

	public void setOutFee(double outFee) {
		this.outFee = outFee;
	}

	public double getTotalFee() {
		// 总资产 = 收入 - 支出
		TotalFee = inFee - outFee;
		return TotalFee;
	}

	public void setTotalFee(double totalFee) {
		TotalFee = totalFee;
	}

	public int getIsOk() {
		return isOk;
	}

	public void setIsOk(int isOk) {
		this.isOk = isOk;
	}

	public String getCurmonth() {
		return curmonth;
	}

	public void setCurmonth(String curmonth) {
		this.curmonth = curmonth;
	}

	public String getCurday() {
		return curday;
	}

	public void setCurday(String curday) {
		this.curday = curday;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "Money [moneyId=" + moneyId + ", userId=" + userId + ", inFee=" + inFee + ", outFee=" + outFee
				+ ", TotalFee=" + TotalFee + ", isOk=" + isOk + ", curmonth=" + curmonth + ", curday=" + curday
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", userName=" + userName + "]";
	}

}
