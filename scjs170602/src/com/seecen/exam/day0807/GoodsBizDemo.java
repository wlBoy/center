package com.seecen.exam.day0807;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * 测试商品业务类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月7日
 */
public class GoodsBizDemo {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		GoodsBiz gb = new GoodsBiz();
		UserBiz ub = new UserBiz();
		
		// 声明一个用户对象数组并调用有参的构造方法进行初始化(模拟数据库)
		User[] users = {new User("wanlei","123456"),new User("admin","admin"),new User("xiaole","123"),
				new User("ok","ok")};
		// 声明一个商品对象数组并调用有参的构造方法进行初始化(模拟数据库)
		Goods[] gds = {new Goods(1,"电风扇",124.23),new Goods(2,"洗衣机",4500.0),new Goods(3,"电视机",8800.9),
				new Goods(4,"冰箱",5000.88),new Goods(5,"空调机",4456.0),new Goods(6,"扫地机",1256.56)};
		
		System.out.print("请输入用户名:");
		String name = sc.next();
		System.out.print("请输入密码:");
		String pwd = sc.next();
		boolean isLogin = ub.login(users, name, pwd);
		if (isLogin) {
			System.out.println("欢迎"+name+",登录成功!");
			System.out.println("********欢迎进入商品批发城***********");
			gb.showAll(gds);
			System.out.println("*********************************");
			System.out.print("请输入您要批发的商品编号:");
			int index = sc.nextInt();
			System.out.print("请输入批发的数量:");
			int num = sc.nextInt();
			double sumPrice = gb.buyGoods(gds, index, num);
			DecimalFormat df = new DecimalFormat("#,###.####");
			System.out.println("您需要付款:" + df.format(sumPrice));
		} else {
			System.out.println("登录失败!");
		}
		sc.close();
	}
}
