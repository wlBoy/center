package com.xn.hk.data.dic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xn.hk.common.dao.BaseDao;
import com.xn.hk.common.service.impl.BaseServiceImpl;
import com.xn.hk.data.dic.dao.DataDicTermDao;
import com.xn.hk.data.dic.model.DataDicTerm;
import com.xn.hk.data.dic.service.DataDicTermService;

/**
 * 
 * @ClassName: DataDicTermServiceImpl
 * @Package: com.xn.hk.data.dic.service.impl
 * @Description: 数据字典项的service实现层
 * @Author: wanlei
 * @Date: 2018年11月7日 下午6:51:21
 */
@Service
public class DataDicTermServiceImpl extends BaseServiceImpl<DataDicTerm> implements DataDicTermService {
	@Autowired
	private DataDicTermDao dataDicTermDao;

	/**
	 * 实现父类的方法，指定所用的dao
	 */
	public BaseDao<DataDicTerm> getDao() {
		return dataDicTermDao;
	}

}
