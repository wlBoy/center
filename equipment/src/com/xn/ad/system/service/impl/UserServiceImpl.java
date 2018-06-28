package com.xn.ad.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xn.ad.common.dao.BaseDao;
import com.xn.ad.common.service.impl.BaseServiceImpl;
import com.xn.ad.system.dao.UserDao;
import com.xn.ad.system.model.User;
import com.xn.ad.system.service.UserService;

/**
 * 
 * @ClassName: UserServiceImpl
 * @PackageName: com.xn.ad.system.service.impl
 * @Description: 用户管理的service实现层
 * @author wanlei
 * @date 2018年5月11日 下午4:52:18
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements
		UserService {
	@Autowired
	private UserDao ud;

	/**
	 * 实现父类的方法，指定所用的dao
	 */
	public BaseDao<User> getDao() {
		return ud;
	}

	/**
	 * 账户登录
	 * 
	 * @param user
	 *            用户实体
	 * @return 登录成功返回该实体，失败返回null
	 */
	public User login(User user) {
		return ud.login(user);
	}

}
