package com.seecen.exam.day0815;
/**
 * 学生实体类
 * @scjs170602
 * @author 【万磊】
 * @2017年8月15日
 */
public class Student {
	private String name;
	private int age;
	private int stuNO;

	@Override
	public String toString() {
		return "Student [name=" + name + ", age=" + age + ", stuNO=" + stuNO
				+ "]";
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

	public int getStuNO() {
		return stuNO;
	}

	public void setStuNO(int stuNO) {
		this.stuNO = stuNO;
	}

	public Student() {

	}

	public Student(String name, int age, int stuNO) {
		super();
		this.name = name;
		this.age = age;
		this.stuNO = stuNO;
	}
}
