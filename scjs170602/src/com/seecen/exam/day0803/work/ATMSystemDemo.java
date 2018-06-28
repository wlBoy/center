package com.seecen.exam.day0803.work;

import java.util.Scanner;
/**
 * 测试ATMSystem系统
 * 模拟ATM机完成以下功能： 1. 输入密码登录，错误3次自动退卡 2. 存款 3. 取款 (可以循环是否继续，输入n退卡)4.注销登录 5.查询余额 6.手动退卡
 * @scjs170602
 * @author 【万磊】
 * @2017年8月3日
 */
public class ATMSystemDemo {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ATMSystem as = new ATMSystem();
		
		//初始化银行卡密码
		System.out.print("请初始化你的银行卡密码:");
		String pwd = sc.next();
		//用户账户余额
		long accountMoney = 0 ;
		//初始为未登录状态
		boolean isLogin = false;
		
		String answer = null; 
		do{
			as.printMenu();
			int menu = sc.nextInt();
			switch (menu) {
			case 1:
				System.out.println("【欢迎进入模拟ATM机系统>登录】");
				isLogin = as.login(pwd , sc , isLogin);
				break;
			case 2:
				System.out.println("【欢迎进入模拟ATM机系统>存款】");
				accountMoney = as.saveMoney(isLogin,accountMoney,sc);
				break;
			case 3:
				System.out.println("【欢迎进入模拟ATM机系统>取款】");
				accountMoney = as.drawMoney(isLogin,accountMoney,sc);
				break;
			case 4:
				System.out.println("【欢迎进入模拟ATM机系统>注销登录】");
				isLogin = as.logOff(sc , isLogin);
				break;
			case 5:
				System.out.println("【欢迎进入模拟ATM机系统>查询余额】");
				accountMoney = as.queryMoney(accountMoney,isLogin);
				break;
			case 6:
				System.out.println("【欢迎进入模拟ATM机系统>退卡 】");
				as.exitSystem(sc);
				break;
			default:
				System.out.println("请选择正确的菜单选项!");
				break;
			}
			System.out.print("是否继续(y/n):");
			answer = sc.next();
		}while("y".equals(answer));
		System.out.println("正在退卡,谢谢使用！");
		System.exit(1);
		sc.close();
	}
}
