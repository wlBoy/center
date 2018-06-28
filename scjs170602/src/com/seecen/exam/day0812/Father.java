package com.seecen.exam.day0812;

/**
 * 父亲实体类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月12日
 */
public class Father extends Person {

	public Father() {
		super();
	}

	public Father(String name, int age) {
		super(name, age);
	}

	@Override
	public void show() {
		System.out.println("我是爸爸,我的名字是:" + getName() + ",我的年龄是:" + getAge()
				+ ",我的性别是:" + Person.SEX_MEN);
	}

}
