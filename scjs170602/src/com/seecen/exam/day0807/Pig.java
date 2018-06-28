package com.seecen.exam.day0807;

import com.seecen.exam.day0808.work.Pet;
/**
 * 猪实体类,与Pet实体类不在同一包中
 * @scjs170602
 * @author 【万磊】
 * @2017年8月8日
 */
public class Pig {

	public static void main(String[] args) {
		//不在同一包中，只能访问public属性
		Pet pet1 = new Pet();
		pet1.speed = 10.25;
		
		//不在同一包中，只能访问public方法
		Pet pet = new Pet("母猪",26,100.25,10.23);
		pet.move();
	}

}
