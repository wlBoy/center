package com.seecen.exam.day0810.work2;

/**
 * 测试动物商店类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月10日
 */
public class StoreDemo {

	public static void main(String[] args) {
		Store s = new Store();
		s.getAnimal("dog").shout();
		s.getAnimal("pig").shout();
		s.getAnimal("cat").shout();
	}

}
