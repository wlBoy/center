package com.seecen.exam.day0804.work.OnlineSystem;

/**
 * 当前登录的会员实体类(当前登录会员的登录名,当前登录会员在VIP对象数组里的索引,当前登录会员的登录状态)
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月4日
 */
public class LoginedUser {
	// 当前登录会员的登录名
	private String loginName;
	// 当前登录会员在VIP对象数组里的索引
	private int loginIndex;
	// 当前登录会员的登录状态
	private boolean isLogin;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public int getLoginIndex() {
		return loginIndex;
	}

	public void setLoginIndex(int loginIndex) {
		this.loginIndex = loginIndex;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

}
