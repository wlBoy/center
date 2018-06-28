package com.seecen.exam.day0810.work2;

/**
 * 动物商店类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月10日
 */
public class Store {
	/**
	 * 用户购买动物
	 * 
	 * @param type
	 *            用户选择的类型
	 * @return 动物类型
	 */
	public IAnimal getAnimal(String type) {
		if (type.equalsIgnoreCase("dog")) {
			return new Dog();
		} else if (type.equalsIgnoreCase("pig")) {
			return new Pig();
		} else {
			return new Cat();
		}
	}
}
