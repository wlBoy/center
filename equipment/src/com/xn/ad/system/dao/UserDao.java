package com.xn.ad.system.dao;

import com.xn.ad.common.dao.BaseDao;
import com.xn.ad.system.model.User;

/**
 * 
 * @ClassName: UserDao
 * @PackageName: com.xn.ad.system.dao
 * @Description: 用户管理的dao接口层
 * @author wanlei
 * @date 2018年5月11日 下午4:42:07
 */
public interface UserDao extends BaseDao<User> {
	/**
	 * 账户登录
	 * 
	 * @param user
	 *            用户实体
	 * @return 登录成功返回该实体，失败返回null
	 */
	public User login(User user);

}
