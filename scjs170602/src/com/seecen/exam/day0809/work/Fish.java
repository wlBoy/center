package com.seecen.exam.day0809.work;

/**
 * 鱼实体类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月9日
 */
public class Fish extends Animal {
	private int weight;// 体重

	public Fish(){
		super();
	}
	public Fish(String name, int age, int weight) {
		super(name, age);
		this.weight = weight;
	}

	public int getWeight() {
		return weight;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public void show() {
		System.out.println("我是一只" + getWeight() + "斤重的" + getName() + "!");
		System.out.println("今年" + getAge() + "岁了!");

	}

}
