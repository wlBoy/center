package com.xn.ad.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xn.ad.common.dao.BaseDao;
import com.xn.ad.common.service.impl.BaseServiceImpl;
import com.xn.ad.system.dao.RoleModuleDao;
import com.xn.ad.system.model.RoleModule;
import com.xn.ad.system.service.RoleModuleService;

/**
 * 
 * @ClassName: RoleModuleServiceImpl
 * @PackageName: com.xn.ad.system.service.impl
 * @Description: 角色模块管理的service实现层
 * @author wanlei
 * @date 2018年5月11日 下午4:51:44
 */
@Service
public class RoleModuleServiceImpl extends BaseServiceImpl<RoleModule>
		implements RoleModuleService {
	@Autowired
	private RoleModuleDao rmd;

	/**
	 * 实现父类的方法，指定所用的dao
	 */
	public BaseDao<RoleModule> getDao() {
		return rmd;
	}

	/**
	 * 根据角色ID查询该角色及模块信息list集合
	 * 
	 * @param role_id
	 *            角色ID
	 * @return 该角色及模块信息list集合
	 */
	public List<RoleModule> findByRoleId(Integer role_id) {
		return rmd.findByRoleId(role_id);
	}

	/**
	 * 根据角色ID查询该角色及模块信息然后把模块ID封装成map集合的key,关联ID为value
	 * 
	 * @param role_id
	 *            角色ID
	 * @return map集合的key,关联ID为value
	 */
	public Map<Integer, Integer> getMapRoleModule(Integer role_id) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		List<RoleModule> list = rmd.findByRoleId(role_id);
		for (RoleModule roleModule : list) {
			map.put(roleModule.getModule_id(), roleModule.getId());
		}
		return map;
	}

}
