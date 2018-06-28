package com.seecen.exam.day0803;
/**
 * 
 * @author 万磊
 */
public class ShopSystem {
	
	public void printHeader() {
		System.out.println("欢迎来到我行我素购物管理系统");
		System.out.println("\t1.登录系统");
		System.out.println("\t2.退出");
		System.out.println("----------------------------");
	}

	public void printMainMenu() {
		System.out.println("----------------------------");
		System.out.println("欢迎来到我行我素购物管理系统的主菜单");
		System.out.println("\t1.客户信息管理");
		System.out.println("\t2.真情回馈");
		System.out.println("----------------------------");
	}

	public void customerManage() {
		System.out.println("----------------------------");
		System.out.println("欢迎我行我素购物管理系统>客户信息管理");
		System.out.println("\t1.添加客户信息");
		System.out.println("\t2.修改客户信息");
		System.out.println("\t3.删除客户信息");
		System.out.println("----------------------------");
	}

	public void tureFeedback() {
		System.out.println("----------------------------");
		System.out.println("欢迎我行我素购物管理系统>真情回馈");
		System.out.println("\t1.幸运大放送");
		System.out.println("\t2.幸运抽奖");
		System.out.println("----------------------------");
	}
}
