package com.seecen.exam.day0808.work;
/**
 * 企鹅实体类，不继承Pet实体类，但是放在同一包中
 * @scjs170602
 * @author 【万磊】
 * @2017年8月8日
 */
public class Penguin {

	public static void main(String[] args) {
		//在同一包中，能访问public方法,protect方法,默认friendly属性
		Pet pet1 = new Pet();
		pet1.age = 25;
		pet1.weight = 32.23;
		pet1.speed = 32.12;
		
		
		//在同一包中，能访问public方法,protect方法,默认friendly方法
		Pet pet = new Pet("Q仔",15,10.25,32.23);
		pet.show();
		pet.look();
		pet.move();
	}

}
