package com.xn.hk.account.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xn.hk.account.dao.AccountTypeDao;
import com.xn.hk.account.model.AccountType;
import com.xn.hk.account.service.AccountTypeService;
import com.xn.hk.common.dao.BaseDao;
import com.xn.hk.common.service.impl.BaseServiceImpl;
import com.xn.hk.common.utils.page.BasePage;

/**
 * 
 * @Title: AccountTypeServiceImpl
 * @Package: com.xn.hk.account.service.impl
 * @Description: 处理账务类别的service实现层
 * @Author: wanlei
 * @Date: 2018年1月4日 下午1:49:01
 */
@Service
public class AccountTypeServiceImpl extends BaseServiceImpl<AccountType> implements AccountTypeService {
	@Autowired
	private AccountTypeDao accountTypeDao;

	/**
	 * 实现父类的方法，指定所用的dao
	 */
	public BaseDao<AccountType> getDao() {
		return accountTypeDao;
	}

	/**
	 * 个人账务类别的分页列表
	 * 
	 * @param pages
	 *            分页对象
	 * @return 个人账务类别列表
	 */
	public List<AccountType> pagePersonalList(BasePage<AccountType> pages) {
		pages.setCount(accountTypeDao.pagePersonalCount(pages));
		return accountTypeDao.pagePersonalList(pages);
	}

	/**
	 * 根据父级类别查询该用户的账务类别
	 * 
	 * @param parentType
	 *            父级类别
	 * @param userId
	 *            用户ID
	 * @return 个人账务类别列表
	 */
	public List<AccountType> findChildType(String parentType, Integer userId) {
		return accountTypeDao.findChildType(parentType, userId);
	}

	/**
	 * 根据账务类别名和用户ID查询该用户的个人账务类别
	 * 
	 * @param typeName
	 *            账务类别名
	 * @param userId
	 *            用户ID
	 * @return 个人账务类别
	 */
	public AccountType findByNameAndUserId(String typeName, Integer userId) {
		return accountTypeDao.findByNameAndUserId(typeName, userId);
	}

}
