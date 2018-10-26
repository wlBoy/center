package com.xn.hk.common.utils.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 
 * @ClassName: MyAuthenticator
 * @Package: com.xn.hk.common.utils.email
 * @Description: 邮箱授权验证的实体类
 * @Author: wanlei
 * @Date: 2018年9月12日 上午11:43:22
 */
public class MyAuthenticator extends Authenticator {
	/**
	 * 邮箱用户名
	 */
	private String username;
	/**
	 * 邮箱密码
	 */
	private String password;

	public MyAuthenticator() {
		super();
	}

	public MyAuthenticator(String username, String password) {
		this.username = username;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(this.username, this.password);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
