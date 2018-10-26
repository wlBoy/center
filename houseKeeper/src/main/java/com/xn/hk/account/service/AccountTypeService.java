package com.xn.hk.account.service;

import java.util.List;

import com.xn.hk.account.model.AccountType;
import com.xn.hk.common.service.BaseService;
import com.xn.hk.common.utils.page.BasePage;

/**
 * 
 * @Title: AccountTypeService
 * @Package: com.xn.hk.account.service
 * @Description: 处理账务类别的service层
 * @Author: wanlei
 * @Date: 2018年1月4日 下午1:35:19
 */
public interface AccountTypeService extends BaseService<AccountType> {
	/**
	 * 个人账务类别的分页列表
	 * 
	 * @param pages
	 *            分页对象
	 * @return 个人账务类别列表
	 */
	public List<AccountType> pagePersonalList(BasePage<AccountType> pages);

	/**
	 * 根据父级类别查询该用户的账务类别
	 * 
	 * @param parentType
	 *            父级类别
	 * @param userId
	 *            用户ID
	 * @return 个人账务类别列表
	 */
	public List<AccountType> findChildType(String parentType, Integer userId);

	/**
	 * 根据账务类别名和用户ID查询该用户的个人账务类别
	 * 
	 * @param typeName
	 *            账务类别名
	 * @param userId
	 *            用户ID
	 * @return 个人账务类别
	 */
	public AccountType findByNameAndUserId(String typeName, Integer userId);

}
