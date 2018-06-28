package com.seecen.exam.day0809.work;

/**
 * 动物实体类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月9日
 */
public abstract class Animal {
	private String name;// 名字
	private int age;// 年龄

	public Animal() {
		super();
	}

	public Animal(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public abstract void show();
}
