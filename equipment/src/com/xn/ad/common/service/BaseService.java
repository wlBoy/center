package com.xn.ad.common.service;

import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName: BaseService
 * @Package: com.xn.ad.common.service
 * @Description: 业务接口基础类，所有service类的父类
 * @Author: wanlei
 * @Date: 2017-11-28 下午03:47:05
 */
public interface BaseService<T> {
	/**
	 * 添加实体
	 * 
	 * @param t
	 * @return
	 */
	public int add(T t);

	/**
	 * 更新实体
	 * 
	 * @param t
	 * @return
	 */
	public int update(T t);

	/**
	 * 删除实体，支持批量删除
	 * 
	 * @param t
	 * @return
	 */
	public int delete(Object... ids);

	/**
	 * 查询分页总记录数
	 * 
	 * @param param
	 *            分页对象
	 * @return 分页总记录数
	 */
	public int pageCount(Map<String, Object> param);

	/**
	 * 查询分页列表
	 * 
	 * @param t
	 * @return
	 */
	public Map<String, Object> pageList(Map<String, Object> param);

	/**
	 * 查询所有
	 * 
	 * @param t
	 * @return
	 */
	public List<T> findAll();

	/**
	 * 根据实体ID查找该实体
	 * 
	 * @param t
	 * @return
	 */
	public T findById(Object id);

	/**
	 * 根据实体名字查找该实体
	 * 
	 * @param t
	 * @return
	 */
	public T findByName(Object name);
}
