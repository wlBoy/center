package com.xn.ad.system.dao;

import java.util.List;

import com.xn.ad.common.dao.BaseDao;
import com.xn.ad.system.model.Module;

/**
 * 
 * @ClassName: ModuleDao 
 * @PackageName: com.xn.ad.system.dao
 * @Description: 模块管理的dao接口层
 * @author wanlei
 * @date 2018年5月11日 下午4:41:03
 */
public interface ModuleDao extends BaseDao<Module> {
	/**
	 * 根据已登录用户的角色查询可访问的所有模块
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 所有模块数组
	 */
	public List<Module> getModuleList(Module model);
	/**
	 * 根据已登录用户的角色查询可访问的一级模块
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 一级模块数组
	 */
	public List<Module> getParentList(Module model);
	

}
