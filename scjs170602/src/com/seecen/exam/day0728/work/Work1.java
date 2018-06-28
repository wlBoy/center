package com.seecen.exam.day0728.work;

import java.util.Scanner;
/**
 * Myshopping管理系统-选择菜单的判断
 * @author Administrator
 */
public class Work1 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("欢迎使用MyShopping管理系统");
		System.out.println("**********************************");
		System.out.println("\t1.客户信息管理");
		System.out.println("\t2.购物结算");
		System.out.println("\t3.真情回馈");
		System.out.println("\t4.注销登录");
		System.out.println("**********************************");
		System.out.println("请选择，输入数字:");
		int num = sc.nextInt();
		while(num>=5||num==0){
			if(num>=5||num==0){
				System.out.println("输入错误，请重新输入数字:");
				num = sc.nextInt();
			}else{
				switch (num) {
				case 1:
					System.out.println("执行客户信息管理");
					break;
				case 2:
					System.out.println("执行购物结算");
					break;
				case 3:
					System.out.println("执行真情回馈");
					break;
				case 4:
					System.out.println("执行注销登录");
					break;
				default:
					break;
				}
			}
		}
		switch (num) {
		case 1:
			System.out.println("执行客户信息管理");
			break;
		case 2:
			System.out.println("执行购物结算");
			break;
		case 3:
			System.out.println("执行真情回馈");
			break;
		case 4:
			System.out.println("执行注销登录");
			break;
		default:
			break;
		}
		System.out.println("程序结束");
		System.exit(1);
		sc.close();
	}
}
