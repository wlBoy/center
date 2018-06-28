package com.seecen.exam.day0808;

import java.util.Scanner;

public class MasterDemo {

	public static void main(String[] args) {
		Master m = new Master();

		Pet pet1 = new Dog("阿黄", 30, 10);
		m.Cure(pet1);
		System.out.println(pet1.getName() + "治疗后的健康值为:" + pet1.getHealthy());
		m.feed(pet1);
		Pet pet2 = new Penguin("Q仔", 40, 20);
		m.Cure(pet2);
		System.out.println(pet2.getName() + "治疗后的健康值为:" + pet2.getHealthy());
		m.feed(pet2);
		System.out.println("----------------");
		Pet pet3 = new Dog("小狗", 60, 30);
		m.play(pet3);
		System.out.println(pet3.getName() + "玩完之后的属性值为:" + "健康值:"
				+ pet3.getHealthy() + ",亲密度:" + pet3.getLover());
		Pet pet4 = new Penguin("Q妹", 70, 10);
		m.play(pet4);
		System.out.println(pet4.getName() + "玩完之后的属性值为:" + "健康值:"
				+ pet4.getHealthy() + ",亲密度:" + pet4.getLover());

		Scanner sc = new Scanner(System.in);
		System.out.println("******欢迎来到宠物店*********");
		System.out.print("请输入要领养的宠物名字:");
		String name = sc.next();
		System.out.print("请选择要领养的宠物类型(1.狗狗  2.企鹅):");
		int num = sc.nextInt();
		if (num == 1) {
			// 狗狗
			Dog dog = new Dog();
			dog.setName(name);
			System.out.print("请输入狗狗的品种(1.聪明的拉布拉多犬 2.酷酷的雪娜瑞):");
			if (sc.nextInt() == 1) {
				dog.setType("聪明的拉布拉多犬");
			} else {
				dog.setType("酷酷的雪娜瑞");
			}
			System.out.print("请输入狗狗的健康值(1-100之间):");
			int health = sc.nextInt();
			dog.setHealthy(health);
			System.out.println("狗狗的自白:");
			dog.show();
		} else {
			// 企鹅
			Penguin pen = new Penguin();
			pen.setName(name);
			System.out.print("请输入企鹅的品种(1.Q仔 2.Q妹):");
			if (sc.nextInt() == 1) {
				pen.setSex(pen.SEX_MEN);
			} else {
				pen.setSex(pen.SEX_FEMAL);
			}
			System.out.print("请输入企鹅的健康值(1-100之间):");
			int health = sc.nextInt();
			pen.setHealthy(health);
			System.out.println("企鹅的自我展示:");
			pen.show();
		}
		sc.close();
	}

}
