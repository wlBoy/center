package com.xn.hk.system.model;

import java.io.Serializable;

/**
 * 
 * @Title: User
 * @Package: com.xn.ad.system.model
 * @Description: 账户户实体类
 * @Company: 杭州讯牛
 * @Author: wanlei
 * @Date: 2018年1月3日 上午10:15:35
 */
public class User implements Serializable {
	private static final long serialVersionUID = -6409898269389067193L;
	/**
	 * 账户ID
	 */
	private Integer userId;
	/**
	 * 账号名称
	 */
	private String userName;
	/**
	 * 账号密码
	 */
	private String userPwd;
	/**
	 * 用户头像
	 */
	private String userFace;
	/**
	 * 用户状态：0：激活状态，1：冻结状态
	 */
	private Integer userState;
	/**
	 * 是否有效：0：未删状态(默认)，1：已删状态
	 */
	private Integer isOk;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 更新时间
	 */
	private String updateTime;
	/**
	 * 备注信息
	 */
	private String remark;
	/**
	 * 该用户拥有的角色
	 */
	private Role role;

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

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserFace() {
		return userFace;
	}

	public void setUserFace(String userFace) {
		this.userFace = userFace;
	}

	public Integer getUserState() {
		return userState;
	}

	public void setUserState(Integer userState) {
		this.userState = userState;
	}

	public Integer getIsOk() {
		return isOk;
	}

	public void setIsOk(Integer isOk) {
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

	public Role getRole() {
		if (role == null) {
			role = new Role();
		}
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", userPwd=" + userPwd + ", userState=" + userState
				+ ", isOk=" + isOk + ", createTime=" + createTime + ", updateTime=" + updateTime + ", remark=" + remark
				+ ", role=" + role + "]";
	}

}
