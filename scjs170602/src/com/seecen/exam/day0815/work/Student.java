package com.seecen.exam.day0815.work;

/**
 * 学生实体类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月15日
 */
public class Student {
	private String name;
	private Integer age;

	public Student() {
		super();
	}

	public Student(String name, Integer age) {
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

	public void setAge(Integer age) {
		this.age = age;
	}

}
