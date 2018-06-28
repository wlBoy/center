package com.seecen.exam.day0731;

import java.util.Scanner;
/**
 * 小型的简易在线奖客富翁系统(本系统有注册，登录，抽奖等功能)
 * 该系统必须先注册，再进行登录，再进行抽奖
 * @author Administrator
 */
public class OnlineSystem {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// 声明三个数组各存放用户名,密码和会员卡号(临时数据库)
		int dbSize = 100;// 临时数据库的大小
		String[] names = new String[dbSize];
		String[] pwds = new String[dbSize];
		int[] IdNums = new int[dbSize];
		String answer = null;
		// 检查用户是否登录,默认为未登录状态
		boolean isLogin = false;
		String loginName = "";
		int loginIndex = 0;
		// 当前存储位置
		int index = 0;
		do {
			System.out.println("******欢迎进入奖客富翁系统******");
			System.out.println("\t1.注册");
			System.out.println("\t2.登录");
			System.out.println("\t3.抽奖");
			System.out.println("***************************");
			System.out.print("请选择菜单:");
			int menuNum = sc.nextInt();
			menu: switch (menuNum) {
			case 1:// 注册模块
				String registerContinue = null;
				do {
					System.out.println("【奖客富翁系统》注册】");
					System.out.println("请填写个人注册信息:");
					System.out.print("用户名:");
					String name = sc.next();
					System.out.print("密码:");
					String pwd = sc.next();
					// 对用户名进行筛选(不能重复)，筛选完再填入数组
					boolean isNotExit = true;// 先假设不存在重复
					for (int i = 0; i < dbSize; i++) {
						if (names[i] == null) {
							break;
						}
						if (name.equals(names[i])) {
							isNotExit = false;
							break;
						}
					}
					if (!isNotExit) {
						System.out.println("你要注册的用户名已经存在，请重新输入!");
					} else {
						// 用户名不重复,可以使用,再生成不重复的随机会员卡号
						int randNum = 0;
						while (true) {
							boolean isExist = false;// 假设不存在重复
							randNum = 1000 + (int) (Math.random() * 9000);// 1000-10000
							for (int i = 0; i < dbSize; i++) {
								if (IdNums[i] == 0) {
									break;
								}
								if (randNum == IdNums[i]) {
									isExist = true;
									break;
								}
							}
							if (!isExist) {
								break;
							}
						}
						// 将用户名,密码和会员卡号填入相应的数组中并打印注册信息
						names[index] = name;
						pwds[index] = pwd;
						IdNums[index] = randNum;
						System.out.println("注册成功,请记住您的会员卡号,你的注册信息如下:");
						System.out.println("用户名\t密码\t会员卡号");
						System.out.println(name + "\t" + pwd + "\t" + randNum);
						index++;
						// 当index的大小大于数据库的大小，表示已满
						if (index > dbSize) {
							System.out.println("系统用户已满，暂不开放注册!");
						}
					}
					System.out.print("你是否继续注册？(y/n):");
					registerContinue = sc.next();
				} while (registerContinue.equals("y"));
				System.out.println("注册完毕,已退出!");
				break;
			case 2:// 登录模块
				String loginContinue = null;
				int count = 3;// 3次输入错误机会
				do {
					System.out.println("【奖客富翁系统》登录】");
					System.out.print("请输入用户名:");
					String nameInput = sc.next();
					System.out.print("请输入密码:");
					String pwdInput = sc.next();
					// 假设不存在该用户名和该密码
					boolean isExit1 = false;
					boolean isExit2 = false;
					for (int i = 0; i < index; i++) {
						if (nameInput.equals(names[i])) {
							// 找到了该用户名
							isExit1 = true;
							if (pwdInput.equals(pwds[i])) {
								// 找到了该密码
								loginIndex = i;
								isExit2 = true;
								break;
							}
						}
					}
					if (!isExit1) {
						System.out.println("你输入的用户名不存在 !");
						count--;
						if (count == 0) {
							System.out.println("你已经3次输入错误，请重新进入系统！");
							break;
						} else {
							System.out.println("你还有" + count + "次机会，请重新输入");
						}
					} else if (!isExit2) {
						System.out.println("你输入的密码错误,不匹配 ");
						count--;
						if (count == 0) {
							System.out.println("你已经3次输入错误，请重新进入系统！");
							break;
						} else {
							System.out.println("你还有" + count + "次机会，请重新输入");
						}
					} else {
						System.out.println("欢迎" + nameInput + ",你已经登录啦!");
						isLogin = true;
						loginName = nameInput;
						break;
					}
					System.out.print("你是否继续登录？(y/n):");
					loginContinue = sc.next();
				} while (loginContinue.equals("y"));
				break;
			case 3:// 抽奖模块
				System.out.println("【奖客富翁系统》抽奖】");
				// 这里使用全局变量isLogin和loginName来判断是否登录和已登录的用户名
				if (isLogin) {
					System.out.println("欢迎来到抽奖环节,你已经登录！！" + loginName
							+ ",您可以进行抽奖啦");
					while (true) {
						System.out.print("请输入您的会员号:");
						int idNumInput = sc.nextInt();
						if (idNumInput == 0) {
							System.out.println("请输入正确的四位会员号!");
							break menu;
						}
						//loginIndex表示已登录用户名在数组中的索引,用它来取已登录用户名的会员卡号
						if (idNumInput != IdNums[loginIndex]) {
							System.out.println("你输入的会员号与用户名不匹配,请重新输入!");
						} else {
							System.out.println("会员号匹配成功,您开始抽奖啦!");
							// 产生两个随机会员索引,如果两个随机索引相等，就继续随机产生，知道不相等
							int r1 = 0, r2 = 0;
							//当注册人数为1人时，不能进行抽奖，因为幸运卡号有两个
							if(index == 1){
								System.out.println("注册人数太少，不能进行抽奖!");
								break menu;
							}
							while (r1 == r2) {
								r1 = (int) (Math.random() * index);
								r2 = (int) (Math.random() * index);
							}
							System.out.println("本日的随机幸运数字为:" + IdNums[r1] + "\t" + IdNums[r2]);
							boolean isChecked = false;// 没中奖
							for (int i = 0; i < IdNums.length; i++) {
								if (idNumInput == IdNums[r1] || idNumInput == IdNums[r2]) {
									isChecked = true;
									break;
								}
							}
							if (isChecked) {
								System.out.println("恭喜会员号为:" + idNumInput + "的会员，你中奖啦!");
							} else {
								System.out.println("抱歉，你的会员号" + idNumInput + "不是本日的幸运会员!");
							}
							break;
						}
					}
				} else {
					System.out.println("您还没有登录,请先登录！");
				}
				break;
			default:
				System.out.println("输入错误,请选择正确的菜单选项!");
				break;
			}
			System.out.print("你是否继续操作系统？(y/n):");
			answer = sc.next();
		} while (answer.equals("y"));
		System.out.println("系统退出,谢谢使用!");
		sc.close();
		System.exit(1);
	}
}
