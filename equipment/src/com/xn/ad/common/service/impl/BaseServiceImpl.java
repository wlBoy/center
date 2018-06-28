package com.xn.ad.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xn.ad.common.dao.BaseDao;
import com.xn.ad.common.service.BaseService;

/**
 * 
 * @ClassName: BaseServiceImpl
 * @Package: com.xn.ad.common.service.impl
 * @Description: 业务接口实现基础类
 * @Author: wanlei
 * @Date: 2017-11-28 下午03:50:38
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {
	/**
	 * 抽象方法，用于具体的调用某个dao
	 * 
	 * @return
	 */
	public abstract BaseDao<T> getDao();

	/**
	 * 添加实体
	 * 
	 * @param t
	 * @return
	 */
	public int add(T t) {
		return getDao().add(t);
	}

	/**
	 * 更新实体
	 * 
	 * @param t
	 * @return
	 */
	public int update(T t) {
		return getDao().update(t);
	}

	/**
	 * 删除实体，支持批量删除
	 * 
	 * @param t
	 * @return
	 */
	public int delete(Object... ids) {
		if (ids == null || ids.length < 1) {
			return -1;
		}
		// 循环调用删除方法，次数为数组长度
		for (Object id : ids) {
			getDao().delete(id);
		}
		return ids.length;
	}
	/**
	 * 查询分页总记录数
	 * 
	 * @param param
	 *            分页对象
	 * @return 分页总记录数
	 */
	public int pageCount(Map<String, Object> param) {
		return getDao().pageCount(param);
	}
	/**
	 * 查询分页列表
	 * 
	 * @param t
	 * @return
	 */
	public Map<String,Object> pageList(Map<String, Object> param) {
	    //bootstrap-table要求服务器返回的json须包含：totlal，rows
        Map<String,Object> result = new HashMap<String,Object>();
        List<T> rows=getDao().pageList(param);
        int total=getDao().pageCount(param);
        result.put("total",total);
        result.put("rows",rows);
		return result;
	}

	/**
	 * 查询所有
	 * 
	 * @param t
	 * @return
	 */
	public List<T> findAll() {
		return getDao().findAll();
	};

	/**
	 * 根据实体ID查找该实体
	 * 
	 * @param t
	 * @return
	 */
	public T findById(Object id) {
		return getDao().findById(id);
	}

	/**
	 * 根据实体名字查找该实体
	 * 
	 * @param t
	 * @return
	 */
	public T findByName(Object name) {
		return getDao().findByName(name);
	}
}
