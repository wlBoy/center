package com.xn.ad.system.dao;

import java.util.List;

import com.xn.ad.common.dao.BaseDao;
import com.xn.ad.system.model.RoleModule;


/**
 * 
 * @ClassName: RoleModuleDao 
 * @PackageName: com.xn.ad.system.dao
 * @Description: 角色模块管理的dao接口层
 * @author wanlei
 * @date 2018年5月11日 下午4:41:47
 */
public interface RoleModuleDao extends BaseDao<RoleModule> {
	
	/**
	 * 根据角色ID查询该角色及模块信息
	 * @return
	 */
	public List<RoleModule> findByRoleId(Integer role_id);
}
