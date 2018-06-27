package com.xn.hk.system.service;

import java.util.List;

import com.xn.hk.common.service.BaseService;
import com.xn.hk.system.model.Module;

/**
 * 
 * @Title: ModuleService
 * @Package: com.xn.ad.system.service
 * @Description: 处理模块的service接口层
 * @Company: 杭州讯牛
 * @Author: wanlei
 * @Date: 2017-11-29 上午10:03:09
 */
public interface ModuleService extends BaseService<Module> {
	/**
	 * 切换模块状态
	 * 
	 * @param moduleId
	 *            模块ID
	 * @return 返回影响条数
	 */
	public int changeState(Integer moduleId);

	/**
	 * 根据模块级别查询该级别的模块列表
	 * 
	 * @param level
	 *            模块级别
	 * @return 模块列表
	 */
	public List<Module> findModuleByLevel(Integer level);

	/**
	 * 根据已登录用户的角色和模块级别查询可访问的模块数组
	 * 
	 * @param level
	 *            模块级别
	 * @param roleId
	 *            用户的角色
	 * @return 模块列表
	 */
	public List<Module> findModuleByRoleId(Integer level, Integer roleId);

}
