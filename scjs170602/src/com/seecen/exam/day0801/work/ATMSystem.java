package com.seecen.exam.day0801.work;

import java.util.Scanner;

/**
 * 	1. 模拟ATM机完成以下功能：
    1. 输入密码登录，错误3次自动退卡
    2. 存款
    3. 取款
	可以循环是否继续，输入n退卡
 * @author Administrator
 */
public class ATMSystem {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("请初始化你的银行卡密码:");
		String pwd = sc.next();
		int count = 3 ;//3次输入错误机会
		long accountMoney = 0 ;//用户账户余额
		boolean isLogin = false;//初始为未登录状态
		String answer = null; 
		do{
			System.out.println("*****欢迎进入模拟ATM机系统*****");
			System.out.println("\t1.登录");
			System.out.println("\t2.存款");
			System.out.println("\t3.取款");
			System.out.println("\t4.注销登录");
			System.out.println("\t5.查询余额");
			System.out.println("\t6.退卡");
			System.out.println("****************************");
			System.out.print("请选择菜单：");
			int menu = sc.nextInt();
			switch (menu) {
			case 1:
				System.out.println("【欢迎进入模拟ATM机系统>登录】");
				while(true){
					boolean pwdCheck = true ;
					System.out.print("请输入银行卡密码(6位数字):");
					String pwdInput = sc.next();
					//判断密码长度必须为6位
					if (pwdInput.length() != 6) {
						pwdCheck = false ;
					}
					//判断密码必须为数字(0~9)
					for (int i = 0; i <= pwdInput.length()-1; i++) {
						if (!(pwdInput.charAt(i) >= '0' && pwdInput.charAt(i) <= '9')) {
							pwdCheck = false ;
							break;
						}
					}
					if(pwdCheck){
						if(!pwdInput.equals(pwd)){
							System.out.println("您输入的银行卡密码不正确!");
							count--;
							if(count == 0){
								System.out.println("正在退卡,谢谢使用！");
								System.exit(1);
							}else{
								System.out.println("你还有" + count + "次机会！");
							}
						}else{
							System.out.println("登录成功!");
							isLogin = true;
							break;
						}
					}else{
						System.out.println("银行卡密码必须为6位数字!");
					}
				}
				break;
			case 2:
				System.out.println("【欢迎进入模拟ATM机系统>存款】");
				if(isLogin){
					while(true){
						System.out.print("请输入存款金额:");
						int saveMoney = sc.nextInt();
						if(saveMoney % 50 != 0 && saveMoney > 0){
							System.out.println("金额必须是大于0且是50的倍数!");
						}else{
							accountMoney += saveMoney ;
							System.out.println("请稍等,正在存钱!");
							break;
						}
					}
					System.out.println("存钱成功,您的账户余额为:" + accountMoney);
				}else{
					System.out.println("你还没登录，先去登录!");
				}
				break;
			case 3:
				System.out.println("【欢迎进入模拟ATM机系统>取款】");
				if(isLogin){
					if(accountMoney == 0){
						System.out.println("您账户余额为:0,先去存点钱吧！");
					}else{
						while(true){
							System.out.print("请输入取款金额:");
							int drawMoney = sc.nextInt();
							if(drawMoney % 50 != 0 && drawMoney > 0){
								System.out.println("金额必须是大于0且是50的倍数!");
							}else if(drawMoney>accountMoney){
								System.out.println("对不起，您的账户里没有这么多钱!");
							}else{
								accountMoney -= drawMoney;
								System.out.println("请稍等,正在取钱!");
								break;
							}
						}
						System.out.println("取钱成功,您的账户余额为:" + accountMoney);
					}
				}else{
					System.out.println("你还没登录，先去登录!");
				}
				break;
			case 4:
				System.out.println("【欢迎进入模拟ATM机系统>注销登录】");
				if(isLogin){
					System.out.print("你确定注销登录(y/n):");
					String cancelLogin = sc.next();
					if(cancelLogin.equals("y")){
						isLogin = false ;
						System.out.println("注销成功！");
					}else{
						System.out.println("您已经取消注销啦!");
					}
				}else{
					System.out.println("你还没登录，先去登录!");
				}
				break;
			case 5:
				System.out.println("【欢迎进入模拟ATM机系统>查询余额】");
				if(isLogin){
					System.out.println("您的账户余额为:" + accountMoney);
				}else{
					System.out.println("你还没登录，先去登录!");
				}
				break;
			case 6:
				System.out.println("【欢迎进入模拟ATM机系统>注销登录退卡 】");
				System.out.print("您确定退卡(y/n):");
				String isOut = sc.next();
				if(isOut.equals("y")){
					System.out.println("正在退卡,谢谢使用！");
					System.exit(1);
				}
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
