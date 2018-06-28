package com.xn.ad.system.service;

import java.util.List;
import java.util.Map;

import com.xn.ad.common.service.BaseService;
import com.xn.ad.system.model.RoleModule;

/**
 * 
 * @ClassName: RoleModuleService
 * @PackageName: com.xn.ad.system.service
 * @Description: 角色模块关系的service接口层
 * @author wanlei
 * @date 2018年5月11日 下午4:50:10
 */
public interface RoleModuleService extends BaseService<RoleModule> {

	/**
	 * 根据角色ID查询该角色及模块信息list集合
	 * 
	 * @param role_id
	 *            角色ID
	 * @return 该角色及模块信息list集合
	 */
	public List<RoleModule> findByRoleId(Integer role_id);

	/**
	 * 根据角色ID查询该角色及模块信息然后把模块ID封装成map集合的key,关联ID为value
	 * 
	 * @param role_id
	 *            角色ID
	 * @return map集合的key,关联ID为value
	 */
	public Map<Integer, Integer> getMapRoleModule(Integer role_id);
}
