package com.seecen.exam.day0802.work;

import java.util.Scanner;

public class ComputerDemo {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Computer c = new Computer();
		System.out.print("请输入电脑名称:");
		c.name = sc.next();
		System.out.print("请输入电脑尺寸:");
		c.size = sc.nextDouble();
		System.out.print("请输入电脑价格:");
		c.price = sc.nextDouble();
		System.out.println("您的电脑信息如下:");
		System.out.println(c.toString());
		sc.close();
	}

}
