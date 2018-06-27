package com.xn.hk.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xn.hk.common.dao.BaseDao;
import com.xn.hk.common.service.impl.BaseServiceImpl;
import com.xn.hk.system.dao.UserDao;
import com.xn.hk.system.model.User;
import com.xn.hk.system.service.UserService;

/**
 * 
 * @Title: UserServiceImpl
 * @Package: com.xn.ad.system.service.impl
 * @Description: 处理账户的service业务逻辑实现层
 * @Company: 杭州讯牛
 * @Author: wanlei
 * @Date: 2017-11-28 下午03:30:15
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
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

	/**
	 * 切换用户状态
	 * 
	 * @param userId
	 *            用户ID
	 * @return 返回影响条数
	 */
	public int changeState(Integer userId) {
		Integer userState = ud.findById(userId).getUserState();
		// 拿到当前用户的状态，判断调用不同的方法切换用户状态
		if (userState == 0) {
			return ud.changeState(1, userId);
		} else {
			return ud.changeState(0, userId);
		}
	}

	/**
	 * 上传用户头像
	 * 
	 * @param user
	 *            用户实体
	 * @return 返回影响条数
	 */
	public int uploadFace(User user) {
		return ud.uploadFace(user);
	}

}
