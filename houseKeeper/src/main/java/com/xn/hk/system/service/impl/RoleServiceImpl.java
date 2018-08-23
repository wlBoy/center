package com.xn.hk.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xn.hk.common.dao.BaseDao;
import com.xn.hk.common.service.impl.BaseServiceImpl;
import com.xn.hk.system.dao.RoleDao;
import com.xn.hk.system.model.Module;
import com.xn.hk.system.model.Role;
import com.xn.hk.system.service.RoleService;

/**
 * 
 * @Title: UserServiceImpl
 * @Package: com.xn.ad.system.service.impl
 * @Description: 处理角色的service业务逻辑实现层
 * @Author: wanlei
 * @Date: 2017-11-28 下午03:30:15
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
	@Autowired
	private RoleDao rd;

	/**
	 * 实现父类的方法，指定所用的dao
	 */
	public BaseDao<Role> getDao() {
		return rd;
	}

	/**
	 * 添加角色(复写父类的方法)
	 * 
	 * @param role
	 *            角色实体
	 * @return 影响条数
	 */
	public int add(Role role) {
		List<Module> models = role.getModules();
		// 添加角色基本信息
		rd.insert(role);
		// 为该角色分配模块
		for (int i = 0; i < models.size(); i++) {
			if (models.get(i).getModuleId() != null && models.get(i).getModuleId() != 0) {
				// 将不为空的模块id塞入关系表中
				rd.addModuleForRole(role.getRoleId(), models.get(i).getModuleId());
			}
		}
		return models.size() + 1;
	}

	/**
	 * 修改角色 (复写父类的方法)
	 * 
	 * @param role
	 *            角色实体
	 * @return 影响条数
	 */
	public int update(Role role) {
		// 更新角色基本信息
		rd.update(role);
		// 删除该角色拥有的模块权限
		rd.deleteModuleForRole(role.getRoleId());
		// 为该角色重新分配模块
		for (int i = 0; i < role.getModules().size(); i++) {
			if (role.getModules().get(i).getModuleId() != null && role.getModules().get(i).getModuleId() != 0) {
				// 将不为空的模块id塞入关系表中
				rd.addModuleForRole(role.getRoleId(), role.getModules().get(i).getModuleId());
			}
		}
		return role.getModules().size() + 1;
	}

	/**
	 * 根据角色id数组删除角色 (复写父类的方法)
	 * 
	 * @param roleIds
	 *            角色id数组
	 * @return
	 */
	public int deleteRole(Integer[] roleIds) {
		for (int i = 0; i < roleIds.length; i++) {
			// 删除角色基本信息
			rd.delete(roleIds[i]);
			// 删除该角色拥有的模块权限
			rd.deleteModuleForRole(roleIds[i]);
		}
		return roleIds.length;
	}

}
