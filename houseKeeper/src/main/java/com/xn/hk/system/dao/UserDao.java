package com.xn.hk.system.dao;

import org.apache.ibatis.annotations.Param;

import com.xn.hk.common.dao.BaseDao;
import com.xn.hk.system.model.User;

/**
 * 
 * @Title: UserDao
 * @Package: com.xn.ad.system.dao
 * @Description: 处理账户的dao数据访问层
 * @Company: 杭州讯牛
 * @Author: wanlei
 * @Date: 2017-11-28 下午03:31:47
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

	/**
	 * 切换用户状态
	 * 
	 * @param status
	 *            用户状态 0或1
	 * @param userId
	 *            用户ID
	 * @return
	 */
	public int changeState(@Param(value = "status") Integer status, @Param(value = "userId") Integer userId);

	/**
	 * 上传用户头像
	 * 
	 * @param user
	 *            用户实体
	 * @return 返回影响条数
	 */
	public int uploadFace(User user);

}
