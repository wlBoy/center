package com.seecen.exam.day0808.work;

/**
 * 老虎实体类，父类Pet宠物类，在同一包中
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月8日
 */
public class Tiger extends Pet {

	public Tiger() {
		super();
	}

	public Tiger(String name, int age, double weight, double speed) {
		super(name, age, weight, speed);
	}

	public static void main(String[] args) {
		//子类和父类在同一包中，可以继承所有属性，但不能使用private属性
		Tiger tiger1 = new Tiger();
		tiger1.age = 23;
		tiger1.weight = 56.12;
		tiger1.speed = 78.63;
		
		//子类和父类在同一包中，可以继承所有方法，但不能调用private方法
		Tiger tiger = new Tiger("老虎",36,56.45,64.53);
		tiger.show();
		tiger.look();
		tiger.move();
	}

}
