package com.xn.ad.system.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @ClassName: Module
 * @PackageName: com.xn.ad.system.model
 * @Description: 模块实体类
 * @author wanlei
 * @date 2018年5月11日 下午4:43:07
 */
public class Module implements Serializable {
	private static final long serialVersionUID = -8882842938952346369L;

	private Integer moduleId;// 模块ID
	private String moduleName;// 模块名称
	private Integer parentId;// 父级模块ID
	private Integer moduleLevel;// 菜单级别
	private String actionUrl;// 请求动作URL
	private Integer isAllowed;// 是否可分配：0：可以(默认)，1：不可以
	private Integer isOk;// 是否有效：0：未删状态(默认)，1：已删状态
	private String icon;// 模块图标
	private String createTime;// 创建时间
	private String updateTime;// 更新时间
	private String remark;// 备注信息
	private String parentName;// 父级模块名称(供页面显示使用)
	private Integer roleId; // 角色ID
	private List<Module> children = new ArrayList<Module>(); // 包含的子菜单

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

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public List<Module> getChildren() {
		return children;
	}

	public void setChildren(List<Module> children) {
		this.children = children;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return "Module [actionUrl=" + actionUrl + ", children=" + children
				+ ", createTime=" + createTime + ", icon=" + icon
				+ ", isAllowed=" + isAllowed + ", isOk=" + isOk + ", moduleId="
				+ moduleId + ", moduleLevel=" + moduleLevel + ", moduleName="
				+ moduleName + ", parentId=" + parentId + ", parentName="
				+ parentName + ", remark=" + remark + ", roleId=" + roleId
				+ ", updateTime=" + updateTime + "]";
	}

}
