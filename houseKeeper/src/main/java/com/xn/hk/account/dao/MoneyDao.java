package com.xn.hk.account.dao;

import com.xn.hk.account.model.Money;
import com.xn.hk.common.dao.BaseDao;

/**
 * 
 * @Title: MoneyDao
 * @Package: com.xn.hk.account.dao
 * @Description: 处理资产的dao数据访问层
 * @Author: wanlei
 * @Date: 2018年1月4日 下午1:35:19
 */
public interface MoneyDao extends BaseDao<Money> {
	/**
	 * 根据用户ID和当前日期查找该条资产是否存在(该方法在操作个人账务类别时用)
	 * 
	 * @param m
	 *            资产实体
	 * @return 资产实体
	 */
	public Money findByUserIdAndDate(Money m);

}
