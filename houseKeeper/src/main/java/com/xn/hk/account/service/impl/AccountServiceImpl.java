package com.xn.hk.account.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xn.hk.account.dao.AccountDao;
import com.xn.hk.account.dao.MoneyDao;
import com.xn.hk.account.model.Account;
import com.xn.hk.account.model.Money;
import com.xn.hk.account.service.AccountService;
import com.xn.hk.common.dao.BaseDao;
import com.xn.hk.common.service.impl.BaseServiceImpl;
import com.xn.hk.common.utils.page.BasePage;

/**
 * 
 * @Title: AccountServiceImpl
 * @Package: com.xn.hk.account.service.impl
 * @Description: 处理账务的service实现层
 * @Company: 杭州讯牛
 * @Author: wanlei
 * @Date: 2018年1月4日 下午1:49:01
 */
@Service
public class AccountServiceImpl extends BaseServiceImpl<Account> implements AccountService {
	/**
	 * 记录日志
	 */
	private static final Logger log = Logger.getLogger(AccountServiceImpl.class);
	@Autowired
	private AccountDao ad;
	@Autowired
	private MoneyDao md;

	/**
	 * 实现父类的方法，指定所用的dao
	 */
	public BaseDao<Account> getDao() {
		return ad;
	}

	/**
	 * 实现个人账务分页列表
	 * 
	 * @param pages
	 *            分页对象
	 * @return 个人账务标题列表
	 */
	public List<Account> pagePersonalList(BasePage<Account> pages) {
		pages.setCount(ad.pagePersonalCount(pages));
		return ad.pagePersonalList(pages);
	}

	/**
	 * 根据账务标题和用户ID查询该用户的个人账务标题
	 * 
	 * @param accountTitle
	 *            账务标题
	 * @param userId
	 *            用户ID
	 * @return 个人账务标题
	 */
	public Account findByNameAndUserId(String accountTitle, Integer userId) {
		return ad.findByNameAndUserId(accountTitle, userId);
	}

	/**
	 * 添加账务
	 * 
	 * @param account
	 *            个人账务实体
	 * @return 影响条数
	 */
	public int addAccount(Account account) {
		// 添加账务信息
		ad.add(account);
		// 查找刚添加的账务全部信息
		account = ad.findById(account.getAccountId());
		// 添加资产表信息
		int count = addData(account);
		return 1 + count;
	}

	/**
	 * 更新账务
	 * 
	 * @param account
	 *            个人账务实体
	 * @return 影响条数
	 */
	public int updateAccount(Account account) {
		// 更新之前先根据账务ID查询该账务的老价格和父类型
		Account oldAccount = ad.findById(account.getAccountId());
		// 更新账务信息
		ad.update(account);
		// 更新资产表信息
		int count = updateData(oldAccount, account);
		return count + 1;
	}

	/**
	 * 删除一条或多条账务
	 * 
	 * @param account
	 *            个人账务实体
	 * @return 影响条数
	 */
	public int deleteAccount(Integer[] accountIds) {
		for (int i = 0; i < accountIds.length; i++) {
			// 查找要删除的账务全部信息
			Account a = ad.findById(accountIds[i]);
			// 删除账务信息
			ad.delete(accountIds[i]);
			// 更新资产表数据
			deleteData(a);
		}
		return accountIds.length + 1;
	}

	/**
	 * 向资产表中添加或更新数据
	 * 
	 * @param a
	 *            账务信息
	 * @return 影响条数
	 */
	private int addData(Account a) {
		Money m = new Money();
		m.setUserId(a.getUserId());
		m.setCurmonth(a.getCurmonth());
		m.setCurday(a.getCurday());
		Money result = md.findByUserIdAndDate(m);
		// 根据用户ID和当前日期查找该条数据是否存在
		if (result == null) {
			// 不存在,执行插入操作
			if ("收入".equals(a.getType().getParentType())) {
				m.setInFee(a.getAccountFee());
			} else {
				m.setOutFee(a.getAccountFee());
			}
			return md.add(m);
		} else {
			// 存在,执行更新操作
			log.info("更新前的总收入金额为:" + result.getInFee() + ",更新前的总支出金额为:" + result.getOutFee());
			if ("收入".equals(a.getType().getParentType())) {
				result.setInFee(result.getInFee() + a.getAccountFee());
			} else {
				result.setOutFee(result.getOutFee() + a.getAccountFee());
			}
			log.info("更新后的总收入金额为:" + result.getInFee() + ",更新后的总支出金额为:" + result.getOutFee());
			md.update(result);
			return 0;
		}
	}

	/**
	 * 更新资产表数据
	 * 
	 * @param oldAccount
	 *            旧账务信息
	 * @param newAccount
	 *            新账务信息
	 * @return 影响条数
	 */
	private int updateData(Account oldAccount, Account newAccount) {
		Money m = new Money();
		m.setUserId(oldAccount.getUserId());
		m.setCurday(oldAccount.getCurday());
		Money result = md.findByUserIdAndDate(m);
		log.info("旧账务的总收入金额为:" + result.getInFee() + ",旧账务的总支出金额为:" + result.getOutFee());
		// 先减去之前账务的金额,再填充更新后账务的金额
		if ("收入".equals(oldAccount.getType().getParentType())) {
			result.setInFee(result.getInFee() - oldAccount.getAccountFee() + newAccount.getAccountFee());
		} else {
			result.setOutFee(result.getOutFee() - oldAccount.getAccountFee() + newAccount.getAccountFee());
		}
		log.info("更新后的总收入金额为:" + result.getInFee() + ",更新后的总支出金额为:" + result.getOutFee());
		return md.update(result);
	}

	/**
	 * 更新资产表数据
	 * 
	 * @param a
	 *            账务信息
	 * @return 影响条数
	 */
	private int deleteData(Account a) {
		Money m = new Money();
		m.setUserId(a.getUserId());
		m.setCurday(a.getCurday());
		Money result = md.findByUserIdAndDate(m);
		log.info("更新前的总收入金额为:" + result.getInFee() + ",更新前的总支出金额为:" + result.getOutFee());
		// 减去已删除账务的金额
		if ("收入".equals(a.getType().getParentType())) {
			result.setInFee(result.getInFee() - a.getAccountFee());
		} else {
			result.setOutFee(result.getOutFee() - a.getAccountFee());
		}
		log.info("更新后的总收入金额为:" + result.getInFee() + ",更新后的总支出金额为:" + result.getOutFee());
		return md.update(result);
	}

}
