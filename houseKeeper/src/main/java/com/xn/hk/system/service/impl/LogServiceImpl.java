package com.xn.hk.system.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xn.hk.common.dao.BaseDao;
import com.xn.hk.common.service.impl.BaseServiceImpl;
import com.xn.hk.system.dao.LogDao;
import com.xn.hk.system.model.Log;
import com.xn.hk.system.service.LogService;
/**
 * 
 * @Title: LogServiceImpl
 * @Package: com.xn.hk.system.service.impl
 * @Description:
 * @Author: wanlei
 * @Date: 2018年1月23日 下午3:58:41
 */
@Service
public class LogServiceImpl extends BaseServiceImpl<Log> implements LogService {
	@Autowired
	private LogDao ld;

	/**
	 * 实现父类的方法，指定所用的dao
	 */
	public BaseDao<Log> getDao() {
		return ld;
	}
}
