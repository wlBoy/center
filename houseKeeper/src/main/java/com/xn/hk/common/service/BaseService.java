package com.xn.hk.common.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.xn.hk.common.utils.page.BasePage;

/**
 * 
 * @Title: BaseService
 * @Package: com.xn.ad.common.service
 * @Description: 业务接口基础类，所有service接口类的父类
 * @Author: wanlei
 * @Date: 2017-11-28 下午03:47:05
 */
public interface BaseService<T> {
	/**
	 * 添加实体
	 * 
	 * @param session
	 *            session
	 * @param logName
	 *            日志操作名称
	 * @param logType
	 *            日志操作类型
	 * @param t
	 *            实体对象
	 * @return 返回影响条数
	 */
	public int insert(HttpSession session, String logName, Integer logType, T t);

	/**
	 * 批量添加实体
	 * 
	 * @param session
	 *            session
	 * @param logName
	 *            日志操作名称
	 * @param logType
	 *            日志操作类型
	 * @param List<T>
	 *            实体对象列表
	 * @return 返回影响条数
	 */
	public int batchInsert(HttpSession session, String logName, Integer logType, List<T> list);

	/**
	 * 更新实体
	 * 
	 * @param session
	 *            session
	 * @param logName
	 *            日志操作名称
	 * @param logType
	 *            日志操作类型
	 * @param t
	 *            实体对象
	 * @return 返回影响条数
	 */
	public int update(HttpSession session, String logName, Integer logType, T t);

	/**
	 * 删除实体
	 * 
	 * @param session
	 *            session
	 * @param logName
	 *            日志操作名称
	 * @param logType
	 *            日志操作类型
	 * @param id
	 *            实体ID
	 * @return 返回影响条数
	 */
	public int delete(HttpSession session, String logName, Integer logType, Object id);

	/**
	 * 批量删除实体
	 * 
	 * @param session
	 *            session
	 * @param logName
	 *            日志操作名称
	 * @param logType
	 *            日志操作类型
	 * @param ids
	 *            实体ID数组
	 * @return 返回影响条数
	 */
	public int batchDelete(HttpSession session, String logName, Integer logType, Object[] ids);

	/**
	 * 查询分页对象列表
	 * 
	 * @param page
	 *            分页对象
	 * @return 分页对象列表
	 */
	public List<T> pageList(BasePage<T> page);

	/**
	 * 使用分页插件查询分页对象列表
	 * 
	 * @param page
	 *            分页对象
	 * @return 分页对象列表
	 */
	public List<T> pageAll(BasePage<T> page);

	/**
	 * 查询所有列表
	 * 
	 * @return 对象列表
	 */
	public List<T> findAll();

	/**
	 * 根据实体ID查找该实体
	 * 
	 * @param id
	 *            实体ID
	 * @return 实体对象
	 */
	public T findById(Object id);

	/**
	 * 根据实体名查找该实体
	 * 
	 * @param name
	 *            实体名
	 * @return 实体对象
	 */
	public T findByName(Object name);

	/**
	 * 根据自定义条件查找该实体
	 * 
	 * @param cond
	 *            自定义条件
	 * @return 实体对象
	 */
	public T findByCond(Object cond);

	/**
	 * 根据自定义条件查找实体列表
	 * 
	 * @param cond
	 *            自定义条件
	 * @return 实体列表
	 */
	public List<T> findListByCond(Object cond);

	/**
	 * 根据自定义条件查找结果总数
	 * 
	 * @param cond
	 *            自定义条件
	 * @return 结果总数
	 */
	public int findCountByCond(Object cond);

}
