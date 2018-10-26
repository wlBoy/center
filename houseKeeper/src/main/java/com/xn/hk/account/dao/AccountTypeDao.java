package com.xn.hk.account.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xn.hk.account.model.AccountType;
import com.xn.hk.common.dao.BaseDao;
import com.xn.hk.common.utils.page.BasePage;

/**
 * 
 * @Title: AccountTypeDao
 * @Package: com.xn.hk.account.dao
 * @Description: 处理账务类别的dao数据访问层
 * @Author: wanlei
 * @Date: 2018年1月4日 下午1:35:19
 */
public interface AccountTypeDao extends BaseDao<AccountType> {
	/**
	 * 查询个人账务类别的总记录数
	 * 
	 * @param pages
	 *            分页对象
	 * @return
	 */
	public int pagePersonalCount(BasePage<AccountType> pages);

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
	public List<AccountType> findChildType(@Param(value = "parentType") String parentType,
			@Param(value = "userId") Integer userId);

	/**
	 * 根据账务类别名和用户ID查询该用户的个人账务类别
	 * 
	 * @param typeName
	 *            账务类别名
	 * @param userId
	 *            用户ID
	 * @return 个人账务类别
	 */
	public AccountType findByNameAndUserId(@Param(value = "typeName") String typeName,
			@Param(value = "userId") Integer userId);
}
