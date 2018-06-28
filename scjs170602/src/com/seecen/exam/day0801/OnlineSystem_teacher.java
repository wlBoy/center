package com.seecen.exam.day0801;

import java.util.Scanner;
/**
 * 小型的简易在线奖客富翁系统(本系统有注册，登录，抽奖，修改密码，注销登录及销户,打印用户信息等功能)
 * 老师版本的
 * @author Administrator
 */
public class OnlineSystem_teacher {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String answer = "y"; // 是否继续操作
		// 数据存储部分
		int size = 5;
		String[] accounts = new String[size];
		String[] passwords = new String[size];
		int[] cards = new int[size];
		boolean[] joinLuck = new boolean[size];// 是否参与过抽奖
		int loginIndex = -1; // 登录账号的位置,-1代表未登录
		do {
			printMenu();	// 打印菜单
			int choose = sc.nextInt();
			if(loginIndex < 0 && (choose == 3 || choose == 4 || choose == 5)) {
				System.out.println("请先登录！");
				continue;
			}
			switch(choose) {
				case 1:
					register(accounts, passwords, cards ,sc);
					break;
				case 2:
					loginIndex = login(accounts, passwords,sc);
					break;
				case 3:
					luckDraw(loginIndex, cards, joinLuck,sc);
					break;
				case 4:
					updatePassword(loginIndex,passwords,sc);
					break;
				case 5:
					loginIndex = logOff(loginIndex, accounts, passwords, cards, joinLuck,sc);
					break;
				case 6:
					printAll(accounts);
					break;
				default:
					break;
			}
			System.out.print("继续吗(y/n):");
			answer = sc.next();
		} while("y".equals(answer));
		System.out.println("\r\n系统退出！");
	}
	

	public static void printAll(String[] accounts) {
		for (String str : accounts) {
			System.out.print((str == null ? "" : str) + "  ");
		}
		System.out.println();
	}


	public static int logOff(int loginIndex, String[] accounts, String[] passwords, int[] cards, boolean[] joinLuck, Scanner sc) {
		for(int i = loginIndex; i < accounts.length - 1; i++) {
			accounts[i] = accounts[i + 1];
			passwords[i] = passwords[i + 1];
			cards[i] = cards[i + 1];
			joinLuck[i] = joinLuck[i + 1];
		}
		accounts[accounts.length - 1] = null;
		passwords[passwords.length - 1] = null;
		cards[cards.length - 1] = 0;
		joinLuck[joinLuck.length - 1] = false;
		System.out.println("注销成功！");
		return -1;
	}


	public static void updatePassword(int loginIndex, String[] passwords, Scanner sc) {
		while(true) {
			System.out.print("输入新密码：");
			String password1 = sc.next();
			if(password1.equals("0")) {
				return;
			}
			System.out.print("再次输入新密码：");
			String password2 = sc.next();
			if(!password1.equals(password2)) {
				System.out.println("两次输入的密码不一致，请重新输出(按0取消)");
			} else {
				passwords[loginIndex] = password1;
				System.out.println("新密码修改成功");
				break;
			}
		}
		
	}

	public static void luckDraw(int loginIndex,int[] cards,boolean[] joinLuck, Scanner sc) {
		System.out.print("输入会员号：");
		int inputCard = sc.nextInt();
		while(inputCard != cards[loginIndex]) {
			System.out.print("输入的会员号不正确，请重新录入（按0退出）：");
			inputCard = sc.nextInt();
			if(inputCard == 0) return;
		}
		if(joinLuck[loginIndex]) {
			System.out.println("你的抽奖次数已耗尽！");
			return;
		}
		// 开始抽奖
		// 生成2个随机的索引
		int index1 = 0;
		int index2 = 0;
		while(index1 == index2) {
			index1 = (int)(Math.random() * cards.length);
			index2 = (int)(Math.random() * cards.length);
		}
		String str = cards[index1] == 0 ? "谢谢参与" : cards[index1] + "";
		str += "   " + (cards[index2] == 0 ? "谢谢参与" : cards[index2] + "");
		System.out.println("今天的幸运会员号为：" + str);
		if(inputCard == cards[index1] || 
				inputCard == cards[index2]) {
			System.out.println("恭喜中奖了");
		} else {
			System.out.println("抱歉，没有中奖");
		}
		joinLuck[loginIndex] = true;	
	}

	public static int login(String[] accounts, String[] passwords, Scanner sc) {
		System.out.print("用户名：");
		String account = sc.next();
		System.out.print("密码：");
		String password = sc.next();
		int code = (int)(Math.random() * 9000) + 1000;
		System.out.print("输入验证码[" + code + "]:" );
		int inputCode = sc.nextInt();
		// 判断验证码是否匹配
		if(code == inputCode) {
			// 验证码对的情况下，遍历用户名和密码是否匹配
			for(int i = 0; i < accounts.length; i++) {
				if(accounts[i] != null && 
						accounts[i].equals(account) && 
						passwords[i].equals(password)) {
					System.out.println("登录成功！");
					// 匹配到就返回对应数组中的位置
					return i;
				} 
			}
			// 代码能够执行到这，代表没有匹配的用户名和密码
			System.out.println("用户名或密码错误！");
		} else {
			System.out.println("验证码错误！");
		}
		return -1;
	}

	public static void register(String[] accounts, String[] passwords, int[] cards, Scanner sc) {
		System.out.print("用户名：");
		String account = sc.next();
		System.out.print("密码：");
		String password = sc.next();
		// 检查是否有重复的用户名
		boolean isExist = false;
		int i = 0;
		for (; i < accounts.length; i++) {
			// 如果当前位置为空，代表可以存储
			if(accounts[i] == null) {
				break;
			} else if(accounts[i].equals(account)) {
				isExist = true; // 已存在
				break;
			}
		}
		// 如果i加到了length的长度，代表满了
		if(i == accounts.length) {
			System.out.println("用户已爆满，暂不开放注册！");
		} else if(isExist) {
			System.out.println("用户名已经存在，请更换！");
		} else {
			accounts[i] = account;
			passwords[i] = password;
			int card = 0;
			// 生成会员卡号
			while(true) {
				card = (int)(Math.random() * 9000) + 1000;
				boolean cardExist = false;
				for(int j = 0; j < i; j++) {
					if(cards[j] == card) {
						cardExist = true;
						/*有重复的可以跳出去了
						重新生成一个随机数再来检查*/
						break;
					}
				}
				/*如果为false，代表没有重复的，
				这个卡号可以用，故结束循环*/
				if(!cardExist) break;
			}
			cards[i] = card;
			System.out.println("注册成功！您的会员信息为：");
			System.out.println(account + "\t" + password + "\t" + card);
		}
	}

	public static void printMenu() {
		System.out.println("*****欢迎进入奖客富翁系统*****");
		System.out.println("\t1.注册");
		System.out.println("\t2.登录");
		System.out.println("\t3.抽奖");
		System.out.println("\t4.修改密码");
		System.out.println("\t5.注销并销户");
		System.out.println("\t6.打印用户信息");
		System.out.println("****************************");
		System.out.print("请选择菜单：");
	}
}
