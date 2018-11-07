package com.xn.hk.data.dic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xn.hk.common.dao.BaseDao;
import com.xn.hk.common.service.impl.BaseServiceImpl;
import com.xn.hk.data.dic.dao.DataDicDao;
import com.xn.hk.data.dic.model.DataDic;
import com.xn.hk.data.dic.service.DataDicService;

/**
 * 
 * @ClassName: DataDicServiceImpl
 * @Package: com.xn.hk.data.dic.service.impl
 * @Description: 数据字典的service实现层
 * @Author: wanlei
 * @Date: 2018年11月7日 下午6:51:21
 */
@Service
public class DataDicServiceImpl extends BaseServiceImpl<DataDic> implements DataDicService {
	@Autowired
	private DataDicDao dataDicDao;

	/**
	 * 实现父类的方法，指定所用的dao
	 */
	public BaseDao<DataDic> getDao() {
		return dataDicDao;
	}

}
