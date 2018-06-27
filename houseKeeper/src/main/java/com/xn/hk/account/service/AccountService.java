package com.xn.hk.account.service;

import java.util.List;

import com.xn.hk.account.model.Account;
import com.xn.hk.common.service.BaseService;
import com.xn.hk.common.utils.page.BasePage;

/**
 * 
 * @Title: AccountService
 * @Package: com.xn.hk.account.service
 * @Description: 处理账务的service层
 * @Company: 杭州讯牛
 * @Author: wanlei
 * @Date: 2018年1月4日 下午1:35:19
 */
public interface AccountService extends BaseService<Account> {
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
	public Account findByNameAndUserId(String accountTitle, Integer userId);

	/**
	 * 添加账务
	 * 
	 * @param account
	 *            个人账务实体
	 * @return 影响条数
	 */
	public int addAccount(Account account);

	/**
	 * 更新账务
	 * 
	 * @param account
	 *            个人账务实体
	 * @return 影响条数
	 */
	public int updateAccount(Account account);

	/**
	 * 删除一条或多条账务
	 * 
	 * @param account
	 *            个人账务实体
	 * @return 影响条数
	 */
	public int deleteAccount(Integer[] accountIds);

}
