package com.xn.hk.system.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.xn.hk.common.constant.EnabledEnum;

/**
 * 
 * @Title: Role
 * @Package: com.xn.ad.system.model
 * @Description: 角色实体类
 * @Author: wanlei
 * @Date: 2017-11-28 下午03:11:15
 */
public class Role implements Serializable {
	private static final long serialVersionUID = 1581253148380211202L;
	/**
	 * 角色ID
	 */
	private Integer roleId;
	/**
	 * 角色名称
	 */
	private String roleName;
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
	 * 该角色可访问的模块列表
	 */
	private List<Module> modules;
	/**
	 * 该角色可访问的所有模块名称(供页面显示)
	 */
	private String moduleName;

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
		return createTime.substring(0,createTime.length()-2);
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime.substring(0,updateTime.length()-2);
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

	public List<Module> getModules() {
		if (modules == null) {
			modules = new ArrayList<Module>();
		}
		return modules;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", roleName=" + roleName + ", isOk=" + isOk + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", remark=" + remark + ", modules=" + modules + ", moduleName="
				+ moduleName + "]";
	}
	/**
	 * 拼接日志内容(排除空信息和可指定记录内容字段)
	 * 
	 * @return 日志内容
	 */
	public String getLogContent() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		if (getRoleId() != null) {
			sb.append("角色ID=" + getRoleId());
		}
		if (getRoleName() != null) {
			sb.append("角色姓名=" + getRoleName());
		}
		if (getIsOk() != null) {
			sb.append("是否可用=" + EnabledEnum.getDescByCode(getIsOk()));
		}
		if (getCreateTime() != null) {
			sb.append("创建时间=" + getCreateTime());
		}
		if (getUpdateTime() != null) {
			sb.append("更新时间=" + getUpdateTime());
		}
		if (getRemark() != null) {
			sb.append("备注信息=" + getRemark());
		}
		sb.append("]");
		return sb.toString();
	}
}
