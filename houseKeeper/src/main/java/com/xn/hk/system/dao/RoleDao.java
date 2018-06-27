package com.xn.hk.system.dao;

import org.apache.ibatis.annotations.Param;

import com.xn.hk.common.dao.BaseDao;
import com.xn.hk.system.model.Role;

/**
 * 
 * @Title: RoleDao
 * @Package: com.xn.ad.system.dao
 * @Description: 处理角色的dao数据访问层
 * @Company: 杭州讯牛
 * @Author: wanlei
 * @Date: 2017-11-28 下午04:23:54
 */
public interface RoleDao extends BaseDao<Role> {
	/**
	 * 添加角色模块关系表(批处理)
	 * 
	 * @param roleId
	 *            角色ID
	 * @param moduleId
	 *            角色ID
	 * @return 返回影响条数
	 */
	public int addModuleForRole(@Param("roleId") Integer roleId,
			@Param("moduleId") Integer moduleId);

	/**
	 * 根据角色ID删除角色模块关系表(批处理)
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 返回影响条数
	 */
	public int deleteModuleForRole(Integer roleId);

}
