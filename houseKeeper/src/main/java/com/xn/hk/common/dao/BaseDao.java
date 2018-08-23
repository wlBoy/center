package com.xn.hk.common.dao;

import java.util.List;

import com.xn.hk.common.utils.page.BasePage;

/**
 * 
 * @Title: BaseDao
 * @Package: com.xn.ad.common.dao
 * @Description: 数据库基本操作的dao工具类
 * @Author: wanlei
 * @Date: 2017-11-28 下午03:37:07
 */
public interface BaseDao<T> {
	/**
	 * 添加实体
	 * 
	 * @param t
	 *            实体对象
	 * @return 返回影响条数
	 */
	public int insert(T t);

	/**
	 * 更新实体
	 * 
	 * @param t
	 *            实体对象
	 * @return 返回影响条数
	 */
	public int update(T t);

	/**
	 * 删除实体(支持批量删除)
	 * 
	 * @param ids
	 *            实体ID数组
	 * @return 返回影响条数
	 */
	public int delete(Object id);

	/**
	 * 查询分页总记录数
	 * 
	 * @param page
	 *            分页对象
	 * @return 分页总记录数
	 */
	public int pageCount(BasePage<T> page);

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
	 * 查询所有
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
