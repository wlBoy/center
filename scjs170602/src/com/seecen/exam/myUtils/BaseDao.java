package com.seecen.exam.myUtils;

import java.util.List;
/**
 * dao接口的工具类
 * @author wanlei
 *
 * @param <T>
 */
public interface BaseDao<T> {
	/**
	 * 添加T类型
	 * 
	 * @param t
	 *            T类型
	 * @return 添加成功返回该T类型,失败返回null
	 */
	public T add(T t);

	/**
	 * 根据T类型id删除T类型
	 * 
	 * @param id
	 *            根据T类型id删除T类型
	 * @return 删除成功返回影响条数,失败返回0
	 */
	public Long deleteById(Long id);

	/**
	 * 修改T类型
	 * 
	 * @param t
	 *            T类型
	 * @return 修改成功返回影响条数,失败返回0
	 */
	public Long update(T t);

	/**
	 * 查询所有T类型
	 * 
	 * @return 返回查到的T类型List集合
	 */
	public List<T> findAll();

	/**
	 * 根据id查询T类型
	 * 
	 * @param id
	 *            T类型id
	 * @return 查找返回该T类型,没查到返回null
	 */
	public T getById(Long id);
	/**
	 * 根据id数组(多个id)查询多个T类型
	 * @param ids id数组
	 * @return 查找返回T类型List集合
	 */
	public List<T> getByIds(Long[] ids);
}
