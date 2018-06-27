package com.xn.hk.common.service;

import java.util.List;

import com.xn.hk.common.utils.page.BasePage;

/**
 * 
 * @Title: BaseService
 * @Package: com.xn.ad.common.service
 * @Description: 业务接口基础类，所有service接口类的父类
 * @Company: 杭州讯牛
 * @Author: wanlei
 * @Date: 2017-11-28 下午03:47:05
 */
public interface BaseService<T> {
	/**
	 * 添加实体
	 * 
	 * @param t
	 *            实体对象
	 * @return 返回影响条数
	 */
	public int add(T t);

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
	public int delete(Object[] ids);

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
}
