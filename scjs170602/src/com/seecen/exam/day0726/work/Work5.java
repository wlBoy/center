package com.seecen.exam.day0726.work;

import java.util.Scanner;

/**
 *  用户输入存款额和存款年限
	2.25%//存期1年利率
	2.7/100;//存期2年利率
	3.24/100;//存期3年利率
	3.6/100;//存期5年利率
 * @author Administrator
 *
 */
public class Work5 {
	public static void main(String[] args) {
		System.out.println("请输入存款额：");
		Scanner sc = new Scanner(System.in);
		double money = sc.nextDouble();
		System.out.println("请输入存款年限(1,2,3,5)：");
		int year = sc.nextInt();
		switch (year) {
		case 1:
			money += money * (2.25/100);
			break;
		case 2:
			money += money * (2.7/100);
			break;
		case 3:
			money += money * (3.24/100);
			break;
		case 5:
			money += money * (3.6/100);
			break;
		default :
			System.out.println("请输入正确的年限!");
			break;
		}
		System.out.println("存款"+year+"年后的总金额为：" + money);
		sc.close();
	}
}
