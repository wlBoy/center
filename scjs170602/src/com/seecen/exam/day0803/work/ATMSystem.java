package com.seecen.exam.day0803.work;

import java.util.Scanner;

/**
 * ATMSystem模拟ATM机系统类(共有七个方法,6个菜单选项)
 * @scjs170602
 * @author 【万磊】
 * @2017年8月3日
 */
public class ATMSystem {
	/**
	 * 打印模拟ATM机系统的菜单
	 */
	public void printMenu(){
		System.out.println("*****欢迎进入模拟ATM机系统*****");
		System.out.println("\t1.登录");
		System.out.println("\t2.存款");
		System.out.println("\t3.取款");
		System.out.println("\t4.注销登录");
		System.out.println("\t5.查询余额");
		System.out.println("\t6.退卡");
		System.out.println("****************************");
		System.out.print("请选择菜单：");
	}
	/**
	 * 用户登录
	 * @param pwd 银行卡初始密码
	 * @param sc  控制台输入流
	 * @param isLogin 登录状态
	 * @return 登录状态(是否登录),true表示已登录,false表示未登录
	 */
	public boolean login(String pwd, Scanner sc,boolean isLogin){
		while(true){
			//3次输入错误机会
			int count = 3 ;
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
		return isLogin;
	}
	/**
	 * 用户存款(必须先登录)
	 * @param isLogin 登录状态
	 * @param accountMoney 账户余额
	 * @param sc 控制台输入流
	 * @return 账户余额
	 */
	public long saveMoney(boolean isLogin, long accountMoney, Scanner sc){
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
		return accountMoney;
	}
	/**
	 * 用户取款(必须先登录)
	 * @param isLogin 登录状态
	 * @param accountMoney 账户余额
	 * @param sc 控制台输入流
	 * @return 账户余额
	 */
	public long drawMoney(boolean isLogin, long accountMoney, Scanner sc){
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
		return accountMoney;
	}
	/**
	 * 注销登录
	 * @param sc 控制台输入流
	 * @param isLogin 登录状态
	 * @return 登录状态(是否登录),true表示已登录,false表示未登录
	 */
	public boolean logOff(Scanner sc, boolean isLogin){
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
		return isLogin;
	}
	/**
	 * 查询账户余额
	 * @param accountMoney 账户余额
	 * @param isLogin 登录状态
	 * @return 账户余额
	 */
	public long queryMoney(long accountMoney, boolean isLogin){
		if(isLogin){
			System.out.println("您的账户余额为:" + accountMoney);
		}else{
			System.out.println("你还没登录，先去登录!");
		}
		return accountMoney;
	}
	/**
	 * 手动退卡
	 * @param sc
	 */
	public void exitSystem(Scanner sc){
		System.out.print("您确定退卡(y/n):");
		String isOut = sc.next();
		if(isOut.equals("y")){
			System.out.println("正在退卡,谢谢使用！");
			System.exit(1);
		}
	}
}
