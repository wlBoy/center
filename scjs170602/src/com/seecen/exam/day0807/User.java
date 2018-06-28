package com.seecen.exam.day0807;

/**
 * 用户实体类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月7日
 */
public class User {
	private String name;
	private String pwd;

	public User(String name, String pwd) {
		super();
		this.name = name;
		this.pwd = pwd;
	}

	public User() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}
