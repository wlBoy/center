package com.xn.hk.system.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Title: Module
 * @Package: com.xn.ad.system.model
 * @Description: 模块实体类
 * @Company: 杭州讯牛
 * @Author: wanlei
 * @Date: 2017-11-28 下午03:14:50
 */
public class Module implements Serializable {
	private static final long serialVersionUID = -8882842938952346369L;
	/**
	 * 模块ID
	 */
	private Integer moduleId;
	/**
	 * 模块名称
	 */
	private String moduleName;
	/**
	 * 父级模块ID
	 */
	private Integer parentId;
	/**
	 * 菜单级别
	 */
	private Integer moduleLevel;
	/**
	 * 请求动作URL
	 */
	private String actionUrl;
	/**
	 * 是否可分配：0：可以(默认)，1：不可以
	 */
	private Integer isAllowed;
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
	 * 可访问该模块的角色列表
	 */
	private List<Role> roles;

	/**
	 * 父级模块名称(供页面显示使用)
	 */
	private String parentName;

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Integer getParentId() {
		if (parentId == null) {
			parentId = 0;
		}
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getModuleLevel() {
		return moduleLevel;
	}

	public void setModuleLevel(Integer moduleLevel) {
		this.moduleLevel = moduleLevel;
	}

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public Integer getIsAllowed() {
		return isAllowed;
	}

	public void setIsAllowed(Integer isAllowed) {
		this.isAllowed = isAllowed;
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

	public List<Role> getRoles() {
		if (roles == null) {
			roles = new ArrayList<Role>();
		}
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "Module [actionUrl=" + actionUrl + ", createTime=" + createTime + ", isAllowed=" + isAllowed + ", isOk="
				+ isOk + ", moduleId=" + moduleId + ", moduleLevel=" + moduleLevel + ", moduleName=" + moduleName
				+ ", parentId=" + parentId + ", parentName=" + parentName + ", remark=" + remark + ", roles=" + roles
				+ ", updateTime=" + updateTime + "]";
	}

}
