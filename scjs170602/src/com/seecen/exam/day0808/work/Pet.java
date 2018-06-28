package com.seecen.exam.day0808.work;

/**
 * 宠物实体类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月8日
 */
public class Pet {
	// 名字(private)，年龄(defalut)，体重(protected)，速度(public)
	private String name;
	int age;
	protected double weight;
	public double speed;

	public Pet() {
		super();
	}

	public Pet(String name, int age, double weight, double speed) {
		super();
		this.name = name;
		this.age = age;
		this.weight = weight;
		this.speed = speed;
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

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	@SuppressWarnings("unused")
	private void say() {
		System.out.println(getName() + "sayHello!");
	}

	void show() {
		System.out.println(getName()+"的年龄是:" + getAge());
	}

	protected void look() {
		System.out.println(getName()+"的体重是:" + getWeight());
	}

	public void move() {
		System.out.println(getName()+"的速度是:" + getSpeed());
	}
}
