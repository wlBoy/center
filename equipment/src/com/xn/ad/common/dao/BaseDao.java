package com.xn.ad.common.dao;

import java.util.List;
import java.util.Map;
/**
 * 
 * @ClassName: BaseDao
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
	 * 根据实体ID删除实体
	 * 
	 * @param t
	 * @return
	 */
	public int delete(Object id);
	/**
	 * 查询分页总记录数
	 * 
	 * @param page
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
	public List<T> pageList(Map<String, Object> param);

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
