package com.xn.hk.account.model;

import java.io.Serializable;

/**
 * 
 * @Title: Account
 * @Package: com.xn.hk.account.model
 * @Description: 账务实体类
 * @Author: wanlei
 * @Date: 2018年1月4日 下午1:12:22
 */
public class Account implements Serializable {
	private static final long serialVersionUID = -1599614209606317819L;
	/**
	 * 财务ID
	 */
	private Integer accountId;
	/**
	 * 财务标题
	 */
	private String accountTitle;
	/**
	 * 账务所属的用户ID
	 */
	private Integer userId;
	/**
	 * 账务类别
	 */
	private AccountType type;
	/**
	 * 账务金额
	 */
	private double accountFee;
	/**
	 * 账务支付方式(现金或各大银行)
	 */
	private String accountType;
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
	 * 备注信息
	 */
	private String remark;
	/**
	 * 账务所属的用户名,供页面显示
	 */
	private String userName;

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getAccountTitle() {
		return accountTitle;
	}

	public void setAccountTitle(String accountTitle) {
		this.accountTitle = accountTitle;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public AccountType getType() {
		if (type == null) {
			type = new AccountType();
		}
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}

	public double getAccountFee() {
		return accountFee;
	}

	public void setAccountFee(double accountFee) {
		this.accountFee = accountFee;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
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
		return createTime.substring(0, createTime.length() - 2);
	}

	public void setCreateTime(String createtime) {
		this.createTime = createtime;
	}

	public String getUpdateTime() {
		return updateTime.substring(0, updateTime.length() - 2);
	}

	public void setUpdateTime(String updatetime) {
		this.updateTime = updatetime;
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
		return "Account [accountId=" + accountId + ", accountTitle=" + accountTitle + ", userId=" + userId + ", type="
				+ type + ", accountFee=" + accountFee + ", accountType=" + accountType + ", isOk=" + isOk
				+ ", curmonth=" + curmonth + ", curday=" + curday + ", createTime=" + createTime + ", updateTime="
				+ updateTime + ", remark=" + remark + ", userName=" + userName + "]";
	}

}
