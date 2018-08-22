package com.xn.hk.account.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xn.hk.account.dao.MoneyDao;
import com.xn.hk.account.model.Money;
import com.xn.hk.account.service.MoneyService;
import com.xn.hk.common.dao.BaseDao;
import com.xn.hk.common.service.impl.BaseServiceImpl;

/**
 * 
 * @Title: MoneyServiceImpl
 * @Package: com.xn.hk.account.service.impl
 * @Description: 处理资产的service实现层
 * @Author: wanlei
 * @Date: 2018年1月4日 下午1:49:01
 */
@Service
public class MoneyServiceImpl extends BaseServiceImpl<Money> implements MoneyService {
	@Autowired
	private MoneyDao md;

	/**
	 * 实现父类的方法，指定所用的dao
	 */
	public BaseDao<Money> getDao() {
		return md;
	}

}
