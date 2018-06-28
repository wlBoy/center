package com.seecen.exam.day0804.work.OnlineSystem;

import java.util.Scanner;

/**
 * 小型的简易在线奖客富翁系统(面向对象版) (本系统有注册，登录，抽奖，修改密码，注销登录及销户,打印用户信息等功能)
 * 功能描述:该系统必须先注册，再进行登录，必须先登录，再进行抽奖,必须先登录,才可以修改该用户名的密码
 * 必须先登录，才能注销登录，销户后，用户名，密码，会员卡号不存在的！
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月4日
 */
public class OnlineSystemOOP {
	public static void main(String[] args) {
		double a = 89.5;
		int b = (int)a + 10;
		System.out.println(b);
		Scanner sc = new Scanner(System.in);
		// 声明一个会员对象数组
		User[] vips = new User[10];
		// 给会员对象数组添加VIP对象，初始化实例
		for (int i = 0; i < vips.length; i++) {
			vips[i] = new User();
		}
		//创建一个VIP业务实例对象
		UserBiz vb = new UserBiz();

		// 初始化当前登录会员实体类,初始状态为未登录状态
		LoginedUser lv = new LoginedUser();
		lv.setLogin(false);
		lv.setLoginName("");
		lv.setLoginIndex(0);

		// 进过抽奖环节
		boolean isCome = false;
		// 当前存储位置
		int index = 0;

		String answer = null;
		do {
			System.out.println("******欢迎进入奖客富翁系统******");
			System.out.println("\t1.用户注册");
			System.out.println("\t2.用户登录");
			System.out.println("\t3.幸运抽奖");
			System.out.println("\t4.修改密码");
			System.out.println("\t5.注销登录并销户");
			System.out.println("\t6.打印用户信息");
			System.out.println("***************************");
			System.out.print("请选择菜单(数字):");
			int menuNum = sc.nextInt();
			switch (menuNum) {
			case 1:
				System.out.println("【奖客富翁系统》注册】");
				index = vb.register(vips, index, sc);
				break;
			case 2:
				System.out.println("【奖客富翁系统》登录】");
				lv = vb.login(vips, index, lv, sc);
				break;
			case 3:
				System.out.println("【奖客富翁系统》幸运抽奖】");
				isCome = vb.luckyDraw(vips, lv, isCome, index, sc);
				break;
			case 4:
				System.out.println("【奖客富翁系统》修改密码】");
				vb.updatePwd(vips, lv, sc);
				break;
			case 5:
				System.out.println("【奖客富翁系统》注销登录并销户】");
				lv = vb.logOff(vips, lv, index, sc);
				break;
			case 6:
				System.out.println("【奖客富翁系统》打印用户信息】");
				vb.printAll(vips, index);
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
