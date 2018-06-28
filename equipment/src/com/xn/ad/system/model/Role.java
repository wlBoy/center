package com.xn.ad.system.model;

import java.io.Serializable;

/**
 * 
 * @ClassName: Role
 * @PackageName: com.xn.ad.system.model
 * @Description: 角色实体类
 * @author wanlei
 * @date 2018年5月11日 下午4:43:46
 */
public class Role implements Serializable {
	private static final long serialVersionUID = 1581253148380211202L;

	private Integer roleId;// 角色ID
	private String roleName;// 角色名称
	private Integer isOk;// 是否有效：0：未删状态(默认)，1：已删状态
	private String createTime;// 创建时间
	private String updateTime;// 更新时间
	private String remark;// 备注信息

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
}
