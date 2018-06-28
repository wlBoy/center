package com.seecen.exam.day0804.work.OnlineSystem;

import java.util.Scanner;

/**
 * VIP业务类 (注册，登录，幸运抽奖，修改密码，注销登录并销户，打印所有会员信息)
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月4日
 */
public class UserBiz {

	/**
	 * 菜单1:用户注册
	 * 
	 * @param vips
	 *            会员对象数组
	 * @param sc
	 *            控制台输入流
	 * @param index
	 *            当前存储位置的索引
	 * @return index 当前存储位置的索引
	 */
	public int register(User[] vips, int index, Scanner sc) {
		String registerContinue = null;
		do {
			System.out.println("请填写个人注册信息:");
			System.out.print("用户名:");
			String name = sc.next();
			System.out.print("密码:");
			String pwd = sc.next();
			// 对用户名进行筛选(不能重复)，筛选完再填入数组
			boolean isNotExit = true;// 先假设不存在重复
			for (int i = 0; i < vips.length; i++) {
				if (vips[i].getName() == null) {
					break;
				}
				if (name.equals(vips[i].getName())) {
					isNotExit = false;
					break;
				}
			}
			if (!isNotExit) {
				System.out.println("你要注册的用户名已经存在，请重新输入!");
			} else {
				// 用户名不允许超过8位
				if (name.length() > 8 || name.length() == 0) {
					System.out.println("用户名的长度必须在1~8位之间，请重新输入！");
				} else {
					// 用户名不重复,可以使用,再生成不重复的随机会员卡号
					int randNum = 0;
					while (true) {
						boolean IdNumIsExist = false;// 假设不存在重复
						randNum = 1000 + (int) (Math.random() * 9000);// 1000-10000
						for (int i = 0; i < vips.length; i++) {
							if (vips[i].getIdNum() == 0) {
								break;
							}
							if (randNum == vips[i].getIdNum()) {
								IdNumIsExist = true;
								break;
							}
						}
						// 如果不存在重复的跳出外层死循环，可以使用该会员号；否则一直产生随机号码直到不重复为止
						if (!IdNumIsExist) {
							break;
						}
					}
					// 将用户名,密码和会员卡号填入相应的数组中并打印注册信息
					vips[index].setName(name);
					vips[index].setPwd(pwd);
					vips[index].setIdNum(randNum);
					System.out.println("注册成功,请记住您的会员卡号,你的注册信息如下:");
					System.out.println("用户名\t密码\t会员卡号");
					System.out.println(name + "\t" + pwd + "\t" + randNum);
				}
				index++;
				// 当index的大小大于数据库的大小，表示已满
				if (index > vips.length) {
					System.out.println("系统用户已满，暂不开放注册!");
				}
			}
			System.out.print("你是否继续注册？(y/n):");
			registerContinue = sc.next();
		} while (registerContinue.equals("y"));
		System.out.println("注册完毕,已退出!");
		return index;
	}

	/**
	 * 菜单2:用户登录的方法
	 * 
	 * @param vips
	 *            会员对象数组
	 * @param index
	 *            当前存储位置的索引
	 * @param lv
	 *            当前登录会员
	 * @param sc
	 *            控制台输入流
	 * @return 当前登录会员
	 */
	public LoginedUser login(User[] vips, int index, LoginedUser lv, Scanner sc) {
		String loginContinue = null;
		int count = 3;// 3次输入错误机会
		do {
			System.out.print("请输入用户名:");
			String nameInput = sc.next();
			System.out.print("请输入密码:");
			String pwdInput = sc.next();
			// 假设不存在该用户名和该密码
			boolean isExit1 = false;
			boolean isExit2 = false;
			for (int i = 0; i < index; i++) {
				if (nameInput.equals(vips[i].getName())) {
					// 找到了该用户名
					isExit1 = true;
					if (pwdInput.equals(vips[i].getPwd())) {
						// 找到了该密码
						lv.setLoginIndex(i);
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
				while (true) {
					int codeNum = 1000 + (int) (Math.random() * 9000);// 1000-10000
					System.out.println("验证码是:" + codeNum);
					System.out.print("请输入验证码:");
					int codeNumInput = sc.nextInt();
					if (codeNumInput != codeNum) {
						System.out.println("验证码不一致，请重新输入！");
					} else {
						// 验证码验证,当验证码通过时,才允许登录！break出死循环
						System.out.println("欢迎" + nameInput + ",你已经登录啦!");
						lv.setLogin(true);
						lv.setLoginName(nameInput);
						break;
					}
				}
				// 登录成功后,break跳出继续登录的循环，回到菜单界面
				break;
			}
			System.out.print("你是否继续登录？(y/n):");
			loginContinue = sc.next();
		} while (loginContinue.equals("y"));
		return lv;
	}

	/**
	 * 菜单3:幸运抽奖的方法
	 * 
	 * @param lv
	 *            当前登录会员
	 * @param vips
	 *            会员对象数组
	 * @param sc
	 *            控制台输入流
	 * @param isCome
	 *            当前登录用户是否进入过抽奖环节
	 * @param index
	 *            当前存储位置的索引
	 * @return isCome(boolean) 当前登录用户是否进入过抽奖环节
	 */
	public boolean luckyDraw(User[] vips, LoginedUser lv, boolean isCome,
			int index, Scanner sc) {
		if (lv.isLogin()) {
			System.out.println("欢迎来到抽奖环节,你已经登录！！" + lv.getLoginName()
					+ ",您可以进行抽奖啦");
			while (true) {
				System.out.print("请输入您的会员号:");
				int idNumInput = sc.nextInt();
				if (idNumInput == 0) {
					System.out.println("请输入正确的四位会员号!");
					break;
				}
				// loginIndex表示已登录用户名在数组中的索引,用它来取已登录用户名的会员卡号
				if (idNumInput != vips[lv.getLoginIndex()].getIdNum()) {
					System.out.println("你输入的会员号与用户名不匹配,请重新输入!");
				} else {
					if (!isCome) {
						System.out.println("会员号匹配成功," + lv.getLoginName()
								+ "开始抽奖啦!");
						isCome = true;
						// 产生两个随机会员索引,如果两个随机索引相等，就继续随机产生，知道不相等
						int r1 = 0, r2 = 0;
						// 当注册人数为1人时，不能进行抽奖，因为幸运卡号有两个
						if (index == 1) {
							System.out.println("注册人数太少，不能进行抽奖!");
							break;
						}
						while (r1 == r2) {
							r1 = (int) (Math.random() * index);
							r2 = (int) (Math.random() * index);
						}
						System.out.println("本日的随机幸运数字为:" + vips[r1].getIdNum()
								+ "\t" + vips[r2].getIdNum());
						boolean isChecked = false;// 没中奖
						for (int i = 0; i < vips.length; i++) {
							if (idNumInput == vips[r1].getIdNum()
									|| idNumInput == vips[r2].getIdNum()) {
								isChecked = true;
								break;
							}
						}
						if (isChecked) {
							System.out.println("恭喜会员号为:" + idNumInput
									+ "的会员，你中奖啦!");
						} else {
							System.out.println("抱歉，你的会员号" + idNumInput
									+ "不是本日的幸运会员!");
						}
						break;
					} else {
						System.out.println("每个用户只能抽一次,您已经抽过一次啦！");
						break;
					}
				}
			}
		} else {
			System.out.println("您还没有登录,请先登录！");
		}
		return isCome;
	}

	/**
	 * 菜单4:修改密码的方法
	 * 
	 * @param lv
	 *            当前登录会员
	 * @param sc
	 *            控制台输入流
	 * @param vips
	 *            会员对象数组
	 */
	public void updatePwd(User[] vips, LoginedUser lv, Scanner sc) {
		if (lv.isLogin()) {
			String updateContinue = null;
			System.out.println("欢迎" + lv.getLoginName() + "来到修改密码模块!");
			do {
				System.out.print("请输入原密码:");
				String oldPwd = sc.next();
				if (oldPwd.equals(vips[lv.getLoginIndex()].getPwd())) {
					System.out.println("您输入的原密码匹配成功!");
					System.out.print("请输入新密码:");
					String newPwd = sc.next();
					System.out.print("请确认一遍新密码:");
					String confirmNewPwd = sc.next();
					if (confirmNewPwd.equals(newPwd)) {
						vips[lv.getLoginIndex()].setPwd(newPwd);
						System.out.println("修改密码成功,可以去登录啦！");
						break;
					} else {
						System.out.println("您输入的两次密码不一致，请重新确认！");
					}
				} else {
					System.out.println("您输入的原密码错误,请重新输入!");
				}
				System.out.print("你是否继续修改密码？(y/n):");
				updateContinue = sc.next();
			} while (updateContinue.equalsIgnoreCase("y"));
		} else {
			System.out.println("您还没有登录,不能进行修改密码操作!");
		}
	}

	/**
	 * 菜单5:注销登录及销户的方法
	 * 
	 * @param lv
	 *            当前登录会员
	 * @param sc
	 *            控制台输入流
	 * @param vips
	 *            会员对象数组
	 * @param index
	 *            当前存储位置的索引
	 * @return 当前登录会员
	 */
	public LoginedUser logOff(User[] vips, LoginedUser lv, int index, Scanner sc) {
		System.out.println("请输入你是注销登录还是进行销户操作(0代表注销登录,1代表销户),请谨慎选择:");
		int operate = sc.nextInt();
		if (operate == 0) {
			// 注销登录
			System.out.println("【奖客富翁系统》注销并销户》注销登录】");
			if (lv.isLogin()) {
				System.out.println("尊敬的" + lv.getLoginName() + "会员,您现在处于登录状态!");
				while (true) {
					System.out.print("你确定要进行注销登录操作(y/n):");
					String cancelLogin = sc.next();
					if (cancelLogin.equalsIgnoreCase("y")) {
						System.out.println("注销成功，您现在是未登录状态！");
						lv.setLogin(false);
						break;
					} else {
						System.out.println("您已经取消注销，还是登录状态！");
					}
				}
			} else {
				System.out.println("还没有用户处于登录状态,不能注销登录,请先登录！");
			}
		}
		if (operate == 1) {
			// 销户
			System.out.println("【奖客富翁系统》注销并销户》销户】");
			System.out.print("你确定要进行销户操作(销户后你将要重新注册)(y/n):");
			String isCancel = sc.next();
			if (isCancel.equalsIgnoreCase("y")) {
				System.out.print("请输入你要销户的用户名是:");
				String deleteName = sc.next();
				// 假设用户名不存在
				boolean deleteNameIsExit = false;
				int deleteNameIndex = 0;
				for (int j = 0; j < vips.length; j++) {
					if (deleteName.equalsIgnoreCase(vips[j].getName())) {
						deleteNameIsExit = true;
						deleteNameIndex = j;
						break;
					}
				}
				if (deleteNameIsExit) {
					// 用户存在，再进行销户操作，将所有数据清空，存储位置索引-1
					vips[deleteNameIndex].setName(null);
					vips[deleteNameIndex].setPwd(null);
					vips[deleteNameIndex].setIdNum(0);
					if (deleteName.equals(lv.getLoginName())) {
						lv.setLogin(false);
					}
					System.out.println("销户成功，返回菜单操作！");
				} else {
					System.out.println("你要销户的用户名不存在,请确认！");
				}
			} else {
				System.out.println("您已经取消销户操作了,你的账户还在！");
			}
		}
		return lv;
	}

	/**
	 * 菜单6:打印所有会员信息的方法
	 * 
	 * @param vips
	 *            会员对象数组
	 * @param index
	 *            当前存储位置的索引
	 */
	public void printAll(User[] vips, int index) {
		System.out.println("所有存在用户的信息如下:");
		System.out.println("用户名\t密码\t会员卡号");
		for (int i = 0; i < index; i++) {
			System.out.println(vips[i].getName() + "\t" + vips[i].getPwd()
					+ "\t" + vips[i].getIdNum());
		}
	}
}
