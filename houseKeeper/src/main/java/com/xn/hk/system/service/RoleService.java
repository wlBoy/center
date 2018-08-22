package com.xn.hk.system.service;

import com.xn.hk.common.service.BaseService;
import com.xn.hk.system.model.Role;

/**
 * 
 * @Title: RoleService
 * @Package: com.xn.ad.system.service
 * @Description: 处理角色的service接口层
 * @Author: wanlei
 * @Date: 2017-11-29 上午10:02:05
 */
public interface RoleService extends BaseService<Role> {
	/**
	 * 根据角色id数组删除角色
	 * 
	 * @param roleIds
	 *            角色id数组
	 * @return 影响条数
	 */
	public int deleteRole(Integer[] roleIds);

}
