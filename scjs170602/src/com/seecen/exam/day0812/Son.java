package com.seecen.exam.day0812;

/**
 * 儿子实体类,继承人实体类,实现父亲接口和母亲接口
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月12日
 */
public class Son extends Person implements IEat, IPlay {

	public Son() {
		super();
	}

	public Son(String name, int age) {
		super(name, age);
	}

	public void swim() {
		System.out.println("我会游泳!");
	}

	@Override
	public void eat() {
		System.out.println(getName() + "在吃饭!");
	}

	@Override
	public void play() {
		System.out.println(getName() + "在玩!");

	}

	@Override
	public void show() {
		System.out.println("我是儿子,我的名字是:" + getName() + ",我的年龄是:" + getAge()
				+ ",我的性别是:" + Person.SEX_MEN);

	}

}
