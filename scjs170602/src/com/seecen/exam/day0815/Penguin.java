package com.seecen.exam.day0815;

/**
 * 企鹅实体类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月15日
 */
public class Penguin {
	private String name;
	private String sex;

	public Penguin() {

	}

	public Penguin(String name, String sex) {
		super();
		this.name = name;
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}
