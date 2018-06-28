package com.seecen.exam.day0804.work.OnlineSystem;

/**
 * 会员实体类(会员名,密码,会员卡号)
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月4日
 */
public class User {
	private String name;
	private String pwd;
	private int idNum;

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

	public int getIdNum() {
		return idNum;
	}

	public void setIdNum(int idNum) {
		this.idNum = idNum;
	}
}
