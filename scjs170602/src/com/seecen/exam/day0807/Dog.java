package com.seecen.exam.day0807;

import com.seecen.exam.day0808.work.Pet;
/**
 * 狗狗实体类，与父类Pet类不在同一包中
 * @scjs170602
 * @author 【万磊】
 * @2017年8月8日
 */
public class Dog extends Pet {

	public Dog() {
		super();
	}

	public Dog(String name, int age, double weight, double speed) {
		super(name, age, weight, speed);
	}

	public static void main(String[] args) {
		// 子类和父类不在同一包中，只能调用protect和public的属性
		Dog dog1 = new Dog();
		dog1.weight = 53.23;
		dog1.speed = 56.26;
		
		// 子类和父类不在同一包中，只能调用protect和public的方法
		Dog dog = new Dog("哈士奇", 20, 55.2, 50.5);
		dog.look();
		dog.move();
	}
}
