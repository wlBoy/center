package com.seecen.exam.day0809.work;

/**
 * 鸟实体类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月9日
 */
public class Bird extends Animal {
	private String color;// 颜色

	public Bird(){
		super();
	}

	public Bird(String name, int age, String color) {
		super(name, age);
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public void show() {
		System.out.println("我是一只" + getColor() + "色的" + getName() + "!");
		System.out.println("今年" + getAge() + "岁了!");
	}

}
