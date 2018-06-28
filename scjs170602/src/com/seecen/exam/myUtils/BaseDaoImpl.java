package com.seecen.exam.myUtils;

import java.util.List;

/**
 * dao实现类的工具类,实现dao接口工具类，类型为T类型
 * 
 * 还没有具体实现，以后学到hibernate框架后再实现
 * @scjs170602_J2EE
 * @author 【万磊】
 * @2017年8月25日
 */
public class BaseDaoImpl<T> implements BaseDao<T> {
	/**
	 * 添加T类型
	 * 
	 * @param t
	 *            T类型
	 * @return 添加成功返回该T类型,失败返回null
	 */
	@Override
	public T add(T t) {
		return null;
	}
	/**
	 * 根据T类型id删除T类型
	 * 
	 * @param id
	 *            根据T类型id删除T类型
	 * @return 删除成功返回影响条数,失败返回0
	 */
	@Override
	public Long deleteById(Long id) {
		return null;
	}
	/**
	 * 修改T类型
	 * 
	 * @param t
	 *            T类型
	 * @return 修改成功返回影响条数,失败返回0
	 */
	@Override
	public Long update(T t) {
		return null;
	}
	/**
	 * 查询所有T类型
	 * 
	 * @return 返回查到的T类型List集合
	 */
	@Override
	public List<T> findAll() {
		return null;
	}
	/**
	 * 根据id查询T类型
	 * 
	 * @param id
	 *            T类型id
	 * @return 查找返回该T类型,没查到返回null
	 */
	@Override
	public T getById(Long id) {
		return null;
	}
	/**
	 * 根据id数组(多个id)查询多个T类型
	 * @param ids id数组
	 * @return 查找返回T类型List集合
	 */
	@Override
	public List<T> getByIds(Long[] ids) {
		return null;
	}
	
}
