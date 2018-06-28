package com.seecen.exam.day0727.work;

import java.util.Scanner;
/**
 * 我行我素购物管理系统-if...else版
 * @author Administrator
 */
public class updateWork1 {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("欢迎使用我行我素购物管理系统");
		System.out.println("1.登录系统");
		System.out.println("2.退出系统");
		System.out.println("请选择，输入数字：");
		int menu1 = sc.nextInt();
		if(menu1 == 1){
			System.out.println("1.客户信息管理");
			System.out.println("2.购物结算");
			System.out.println("3.真情回馈");
			System.out.println("4.注销登录");
			System.out.println("请选择，输入数字：");
			int menu2 = sc.nextInt();
			if(menu2==1){
				System.out.println("购物管理系统>客户信息管理");
				System.out.println("1.添加显示所有客户");
				System.out.println("2.添加客户信息");
				System.out.println("3.修改客户信息");
				System.out.println("4.查询客户信息");
			}else if(menu2==2){
				System.out.println("购物管理系统>购物结算");
				System.out.println("1.购物总金额");
				System.out.println("2.购物清单");
			}else if(menu2==3){
				System.out.println("购物管理系统>真情回馈");
				System.out.println("1.幸运大放送");
				System.out.println("2.幸运抽奖");
				System.out.println("3.生日问候");
			}else if(menu2==4){
				System.out.println("购物管理系统>注销登录");
				System.out.println("你已经注销登录！");
			}
		}else{
			System.out.println("退出系统！");
			System.exit(1);
		}
		sc.close();
	}
}
