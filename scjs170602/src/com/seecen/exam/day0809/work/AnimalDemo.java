package com.seecen.exam.day0809.work;
/**
 * 测试动物实体类
 * @scjs170602
 * @author 【万磊】
 * @2017年8月9日
 */
public class AnimalDemo {
	/**
	 * 根据关键字instanceof来判断一个父类类型的句柄是否为子类对象，是返回true，否则返回false
	 * @param animal 父类Animal句柄
	 */
	public static void judgeType(Animal animal){
		if(animal instanceof Fish){
			Fish fish = (Fish) animal;
			fish.setWeight(6);
			fish.setAge(3);
			fish.setName("鲨鱼");
		}
		if(animal instanceof Bird){
			Bird bird = (Bird) animal;
			bird.setColor("黑");
			bird.setAge(8);
			bird.setName("猫头鹰");
		}
	}
	
	public static void main(String[] args) {
		Animal bird = new Bird("小鸟",4,"红");
		bird.show();
		
		Animal fish = new Fish("金鱼",2,5);
		fish.show();
		
		System.out.println("--------------");
		
		Animal b = new Bird();
		judgeType(b);
		b.show();

		Animal f = new Fish();
		judgeType(f);
		f.show();
	}
	
}
