package com.xn.ad.system.model;

import java.io.Serializable;

/**
 * 
 * @ClassName: User
 * @PackageName: com.xn.ad.system.model
 * @Description: 用户实体类
 * @author wanlei
 * @date 2018年5月11日 下午4:44:25
 */
public class User implements Serializable {
	private static final long serialVersionUID = -6409898269389067193L;

	private Integer userId;// 账户ID
	private String userName;// 账号名称
	private String userPwd;// 账号密码
	private Integer roleId;// 角色ID
	private String roleName;// 角色名称
	private Integer userState;// 用户状态：0：激活状态，1：冻结状态
	private Integer isOk;// 是否有效：0：未删状态(默认)，1：已删状态
	private String createTime;// 创建时间
	private String updateTime;// 更新时间
	private String remark;// 备注信息

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

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	@Override
	public String toString() {
		return "User [createTime=" + createTime + ", isOk=" + isOk
				+ ", remark=" + remark + ", roleId=" + roleId + ", roleName="
				+ roleName + ", updateTime=" + updateTime + ", userId="
				+ userId + ", userName=" + userName + ", userPwd=" + userPwd
				+ ", userState=" + userState + "]";
	}

}
