package com.seecen.exam.day0812;

/**
 * 人实体类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月12日
 */
public abstract class Person {
	/**
	 * 性别男
	 */
	public static final String SEX_MEN = "男";
	/**
	 * 性别女
	 */
	public static final String SEX_WOMEN = "女";
	
	private String name;//姓名
	private int age;//年龄

	public Person(){
		super();
	}

	public Person(String name, int age) {
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
	/**
	 * 自白的抽象方法
	 */
	public abstract void show();
}
