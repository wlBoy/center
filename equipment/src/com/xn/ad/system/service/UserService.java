package com.xn.ad.system.service;

import com.xn.ad.common.service.BaseService;
import com.xn.ad.system.model.User;
/**
 * 
 * @ClassName: UserService 
 * @PackageName: com.xn.ad.system.service
 * @Description: 用户管理的service接口层
 * @author wanlei
 * @date 2018年5月11日 下午4:50:48
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

}
