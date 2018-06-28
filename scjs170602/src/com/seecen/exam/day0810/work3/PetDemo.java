package com.seecen.exam.day0810.work3;

/**
 * 测试宠物类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月10日
 */
public class PetDemo {

	public static void main(String[] args) {
		Dog dog = new Dog("欧欧", "阿布拉多犬");
		dog.eat();
		dog.catchingFlyDisc();
		dog.show();

		Penguin penguin = new Penguin("娜娜", "Q妹");
		penguin.eat();
		penguin.swim();
		penguin.show();
	}

}
