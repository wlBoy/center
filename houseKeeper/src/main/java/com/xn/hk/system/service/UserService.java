package com.xn.hk.system.service;

import com.xn.hk.common.service.BaseService;
import com.xn.hk.system.model.User;

/**
 * 
 * @Title: UserService
 * @Package: com.xn.ad.system.service
 * @Description: 处理账户的service接口层
 * @Author: wanlei
 * @Date: 2017-11-28 下午03:24:36
 */
public interface UserService extends BaseService<User> {
	/**
	 * 账户登录
	 * 
	 * @param user
	 *            用户实体
	 * @return 登录成功返回该实体，失败返回null
	 */
	public User login(User user);

	/**
	 * 切换用户状态
	 * 
	 * @param userId
	 *            用户ID
	 * @return 返回影响条数
	 */
	public int changeState(Integer userId);

	/**
	 * 上传用户头像
	 * 
	 * @param user
	 *            用户实体
	 * @return 返回影响条数
	 */
	public int uploadFace(User user);

}
