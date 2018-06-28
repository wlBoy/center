package com.seecen.exam.day0807;


/**
 * 用户业务类
 * @scjs170602
 * @author 【万磊】
 * @2017年8月7日
 */
public class UserBiz {
	/**
	 * 用户登录
	 * @param us 用户对象数组
	 * @param name 用户名
	 * @param pwd 密码
	 * @return 登录成功返回true,否则返回false
	 */
	public boolean login(User[] users,String name,String pwd){
		for (User u : users) {
			if(name.equals(u.getName())&&pwd.equals(u.getPwd())){
				return true;
			}
		}
		return false;
	}
}
