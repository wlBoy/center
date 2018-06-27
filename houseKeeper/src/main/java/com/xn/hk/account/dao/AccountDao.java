package com.xn.hk.account.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xn.hk.account.model.Account;
import com.xn.hk.common.dao.BaseDao;
import com.xn.hk.common.utils.page.BasePage;

/**
 * 
 * @Title: AccountDao
 * @Package: com.xn.hk.account.dao
 * @Description: 处理账务的dao数据访问层
 * @Company: 杭州讯牛
 * @Author: wanlei
 * @Date: 2018年1月4日 下午1:35:19
 */
public interface AccountDao extends BaseDao<Account> {
	/**
	 * 查询个人账务总记录数
	 * 
	 * @param pages
	 *            分页对象
	 * @return 个人账务标题列表
	 */
	public int pagePersonalCount(BasePage<Account> pages);

	/**
	 * 实现个人账务分页列表
	 * 
	 * @param pages
	 *            分页对象
	 * @return 个人账务标题列表
	 */
	public List<Account> pagePersonalList(BasePage<Account> pages);

	/**
	 * 根据账务标题和用户ID查询该用户的个人账务标题
	 * 
	 * @param accountTitle
	 *            账务标题
	 * @param userId
	 *            用户ID
	 * @return 个人账务标题
	 */
	public Account findByNameAndUserId(@Param(value = "accountTitle") String accountTitle,
			@Param(value = "userId") Integer userId);

}
