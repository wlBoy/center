package com.xn.hk.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xn.hk.common.dao.BaseDao;
import com.xn.hk.common.service.impl.BaseServiceImpl;
import com.xn.hk.system.dao.AdminLogDao;
import com.xn.hk.system.model.AdminLog;
import com.xn.hk.system.service.AdminLogService;

/**
 * 
 * @Title: AdminLogServiceImpl
 * @Package: com.xn.hk.system.service.impl
 * @Description:日志管理的service实现层
 * @Author: wanlei
 * @Date: 2018年1月23日 下午3:58:41
 */
@Service
public class AdminLogServiceImpl extends BaseServiceImpl<AdminLog> implements AdminLogService {
	@Autowired
	private AdminLogDao adminLogDao;

	/**
	 * 实现父类的方法，指定所用的dao
	 */
	public BaseDao<AdminLog> getDao() {
		return adminLogDao;
	}
}
