package com.seecen.exam.day0801;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 测试奖客富翁系统(面向对象版) 
 * 小型的简易在线奖客富翁系统(本系统有注册，登录，抽奖，修改密码，注销登录及销户,打印用户信息等功能)
 * 功能描述:该系统必须先注册，再进行登录，必须先登录，再进行抽奖,必须先登录,才可以修改该用户名的密码
 * 必须先登录，才能注销登录，销户后，用户名，密码，会员卡号不存在的！
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月4日
 */
public class OnlineSystemDemo {
	public static void main(String[] args) {
		OnlineSystem os = new OnlineSystem();
		Scanner sc = new Scanner(System.in);
		// 声明三个数组各存放用户名,密码和会员卡号(临时数据库)
		int dbSize = 100;
		String[] names = new String[dbSize];
		String[] pwds = new String[dbSize];
		int[] idNums = new int[dbSize];
		String answer = null;
		// 用户是否登录,默认为未登录状态
		boolean isLogin = false;
		String loginName = "";
		int loginIndex = 0;
		// 进过抽奖环节
		boolean isCome = false;
		// 当前存储位置
		int index = 0;
		do {
			os.printMenu();
			int menuNum = sc.nextInt();
			switch (menuNum) {
			case 1:
				System.out.println("【奖客富翁系统》注册】");
				index = os.register(names, pwds, idNums, index, sc);
				break;
			case 2:
				System.out.println("【奖客富翁系统》登录】");
				ArrayList<Object> loginValue = os.login(names, pwds, index,
						isLogin, loginName, loginIndex, sc);
				isLogin = (boolean) loginValue.get(0);
				loginIndex = (int) loginValue.get(1);
				loginName = (String) loginValue.get(2);
				break;
			case 3:
				System.out.println("【奖客富翁系统》幸运抽奖】");
				isCome = os.luckyDraw(idNums, isLogin, loginName, isCome,
						loginIndex, index, sc);
				break;
			case 4:
				System.out.println("【奖客富翁系统》修改密码】");
				os.updatePwd(names, pwds, isLogin, loginName, loginIndex, sc);
				break;
			case 5:
				System.out.println("【奖客富翁系统》注销登录并销户】");
				isLogin = os.logOff(names, pwds, idNums, isLogin, loginName,
						index, sc);
				break;
			case 6:
				System.out.println("【奖客富翁系统》打印用户信息】");
				os.printAll(names, pwds, idNums, index);
				break;
			default:
				System.out.println("请输入正确的菜单选项!");
				break;
			}
			System.out.print("你是否回到菜单？(y/n):");
			answer = sc.next();
		} while (answer.equals("y"));
		System.out.println("系统退出,谢谢使用!");
		sc.close();
		System.exit(1);
	}
}
