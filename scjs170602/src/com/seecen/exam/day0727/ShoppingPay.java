package com.seecen.exam.day0727;

import java.util.Scanner;
/**
 * 打印购物小票并计算积分
 * @author Administrator
 */
public class ShoppingPay {
	public static void main(String[] args) {
		System.out.println("***********消费单************");
		System.out.println("购买物品\t单价\t个数\t金额");
		int num1 = 2;
		int num2 = num1 * 245;
		System.out.println("T恤\t¥245\t"+num1+"\t¥"+num2);
		int num3 = 1;
		int num4 = num3 * 570;
		System.out.println("网球鞋\t¥570\t"+num3+"\t¥"+num4);
		int num5 = 1;
		int num6 = num5 * 320;
		System.out.println("网球拍\t¥320\t"+num5+"\t¥"+num6);
		int discount = 8;
		double sum = (num2+num4+num6)*8/10;
		int realPrice =(int)Math.floor(sum);
		double less = sum - realPrice;
		System.out.println("折扣：\t"+discount+"折");
		System.out.println("消费总金额：\t"+ sum);
		System.out.println("实际缴费：\t" + realPrice);
		System.out.println("找钱：\t" + less);
		System.out.println("本次购物获得的积分是：\t33");
		System.out.println("-------------------------------");
		System.out.println("请输入4位会员卡号：");
		Scanner sc = new Scanner(System.in);
		int IdNum = sc.nextInt();
		int numSum = (IdNum%10)+(IdNum/10%10)+(IdNum/100%10)+(IdNum/1000%10);
		System.out.println("会员卡号"+IdNum+"各位之和：" + numSum);
		System.out.println("-------------------------------");
		System.out.println("我行我素购物管理系统》客户信息管理》添加客户信息");
		System.out.println("请输入会员号<4位整数>：");
		int Idnum2 = sc.nextInt();
		System.out.println("请输入会员生日(月/日<用 两位数表示>)：");
		String birth = sc.next();
		int index = birth.indexOf("/");
		String preStr = birth.substring(0, index);
		String nextStr = birth.substring(index);
		System.out.println("请输入积分：");
		int score = sc.nextInt();
		if (Idnum2 > 1000 && Idnum2 < 9999) {
			System.out.println("你输入的会员号合法！");
		}else{
			System.out.println("你输入的会员号不合法！");
		}
		if(preStr.length() == 2 && nextStr.length() == 2){
			System.out.println("你输入的生日合法！"); 
		}else{
			System.out.println("你输入的生日不合法！");
		}
		System.out.println("已录入的会员信息是：");
		System.out.println(Idnum2+"\t"+birth+"\t"+score);
		sc.close();
	}
}
