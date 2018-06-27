package com.xn.hk.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xn.hk.common.dao.BaseDao;
import com.xn.hk.system.model.Module;

/**
 * 
 * @Title: ModuleDao
 * @Package: com.xn.ad.system.dao
 * @Description: 处理模块的dao数据访问层
 * @Company: 杭州讯牛
 * @Author: wanlei
 * @Date: 2017-11-28 下午04:25:04
 */
public interface ModuleDao extends BaseDao<Module> {

	/**
	 * 切换模块状态
	 * 
	 * @param status
	 *            模块状态 0或1
	 * @param moduleId
	 *            模块ID
	 * @return 影响条数
	 */
	public int changeState(@Param(value = "status") Integer status, @Param(value = "moduleId") Integer moduleId);

	/**
	 * 根据模块级别查询该级别的模块列表
	 * 
	 * @param level
	 *            模块级别
	 * @return 模块列表
	 */
	public List<Module> findModuleByLevel(Integer level);

	/**
	 * 根据已登录用户的角色和模块级别查询可访问的模块列表
	 * 
	 * @param level
	 *            模块级别
	 * @param roleId
	 *            用户的角色
	 * @return 模块列表
	 */
	public List<Module> findModuleByRoleId(@Param(value = "level") Integer level,
			@Param(value = "roleId") Integer roleId);

}
