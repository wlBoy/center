package com.xn.ad.system.model;

import java.io.Serializable;

/**
 * 
 * @ClassName: RoleModule
 * @PackageName: com.xn.ad.system.model
 * @Description: 角色模块实体类
 * @author wanlei
 * @date 2018年5月11日 下午4:44:02
 */
public class RoleModule implements Serializable {
	private static final long serialVersionUID = -8882842938952346369L;
	private Integer id;// 主键ID
	private Integer role_id;// 角色ID
	private Integer module_id;// 模块ID
	private Integer is_ok;// 是否有效，默认为0代表有效

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRole_id() {
		return role_id;
	}

	public void setRole_id(Integer roleId) {
		role_id = roleId;
	}

	public Integer getModule_id() {
		return module_id;
	}

	public void setModule_id(Integer moduleId) {
		module_id = moduleId;
	}

	public Integer getIs_ok() {
		return is_ok;
	}

	public void setIs_ok(Integer isOk) {
		is_ok = isOk;
	}
}
