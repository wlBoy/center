package com.xn.hk.dataDic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xn.hk.common.dao.BaseDao;
import com.xn.hk.common.service.impl.BaseServiceImpl;
import com.xn.hk.dataDic.dao.DataDicTermDao;
import com.xn.hk.dataDic.model.DataDicTerm;
import com.xn.hk.dataDic.service.DataDicTermService;

/**
 * 
 * @ClassName: DataDicTermServiceImpl
 * @Package: com.xn.hk.dataDic.service.impl
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

	/**
	 * 根据数据字典代码查找该数据字典项个数
	 * 
	 * @param dataDicCode
	 *            数据字典代码
	 * @return 返回数据字典项个数
	 */
	public int findCountByDataDicCode(String dataDicCode) {
		return dataDicTermDao.findCountByDataDicCode(dataDicCode);
	}

	/**
	 * 根据数据字典项代码和所属数据字典代码查找该数据字典项
	 * 
	 * @param dataDicTermCode
	 *            数据字典项代码
	 * @param dataDicCode
	 *            数据字典代码
	 * @return 数据字典项
	 */
	public DataDicTerm findTermCodeAndDataDicCode(String dataDicTermCode, String dataDicCode) {
		return dataDicTermDao.findTermCodeAndDataDicCode(dataDicTermCode, dataDicCode);
	}

}
