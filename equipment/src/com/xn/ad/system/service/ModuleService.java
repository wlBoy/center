package com.xn.ad.system.service;

import java.util.List;

import com.xn.ad.common.service.BaseService;
import com.xn.ad.system.model.Module;

/**
 * 
 * @ClassName: ModuleService
 * @PackageName: com.xn.ad.system.service
 * @Description: 模块管理的service接口层
 * @author wanlei
 * @date 2018年5月11日 下午4:49:41
 */
public interface ModuleService extends BaseService<Module> {

	/**
	 * 根据已登录用户的角色查询可访问的所有模块
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 可访问的所有模块列表
	 */
	public List<Module> getRoleModule(Integer roleId);

	/**
	 * 根据已登录用户的角色查询可访问的一级模块
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 可访问的一级模块列表
	 */
	public List<Module> getParentList();
}
