package com.seecen.exam.day0726;

import java.util.Scanner;

/**
 * 简易的购物管理系统
 * 
 * @author Administrator
 */
public class ShopManageSystem {
	public static void main(String[] args) {
		System.out.println("欢迎使用我行我素购物管理系统");
		System.out.println("1.登录系统");
		System.out.println("2.退出系统");
		System.out.println("请选择，输入数字：");
		Scanner sc = new Scanner(System.in);
		int index1 = sc.nextInt();
		if (index1 == 1) {
			System.out.println("欢迎你，登录成功！");
		} else {
			System.out.println("你已经退出系统！");
		}
		System.out.println("***************************************");
		System.out.println("欢迎使用我行我素购物管理系统");
		System.out.println("1.客户信息管理");
		System.out.println("2.购物结算");
		System.out.println("3.真情回馈");
		System.out.println("4.注销登录");
		System.out.println("请选择，输入数字：");
		int index2 = sc.nextInt();
		switch (index2) {
		case 1:
			System.out.println("客户信息如下：");
			break;
		case 2:
			System.out.println("购物结算如下：");
			break;
		case 3:
			System.out.println("真情回馈：");
			break;
		default:
			System.out.println("你已经注销登录了！");
			break;
		}
		sc.close();
	}
}
