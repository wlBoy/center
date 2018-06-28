package com.seecen.exam.day0727;

import java.util.Random;
import java.util.Scanner;
/**
 * 产生随机数的方法
 * @author Administrator
 */
public class RandomNum {
	public static void main(String[] args) {
		/*
		 * 第一种方法产生随机数：
		 *  //Math.random()产生一个0~1之间的随机小数
		 *	double randomNum = Math.random();
		 *	System.out.println((int)(randomNum * 100));
		 * 第二种方法产生随机数：
		 *  //rd.nextInt(bound)，bound表示边界，从0开始
		 *	Random rd = new Random();
		 *	System.out.println(rd.nextInt(100));
		 */
		//随机产生一个0~9之间的数字
		Random rd = new Random();
		int randomNum = rd.nextInt(10);
//		System.out.println(randomNum);
		System.out.println("我行我素购物管理系统>幸运抽奖");
		System.out.println("请输入4位会员号：");
		Scanner sc = new Scanner(System.in);
		int IdNum = sc.nextInt();
		int IdNum2 = IdNum/10%10 ;//百位数字
		if(IdNum2 == randomNum) {
			System.out.println(IdNum+"号客户是幸运客户，获得精美Mp3一个！");
		}else{
			System.out.println("很遗憾，你没中奖！");
		}
		System.out.println("--------------------------------------");
		System.out.println("请输入你是否是会员 ：是（y）,否（其他字符）");
		String isVip = sc.next();
		System.out.println("请输入购物金额：");
		double money = sc.nextDouble();
		double sum;
		if(isVip.equals("y")){
			//是会员
			if(money>=200){
				sum  = money * 0.75;
			}else{
				sum  = money * 0.8;
			}
		}else{
			//不是会员
			if(money>=100){
				sum  = money * 0.9;
			}else{
				sum  = money * 1.0;
			}
		}
		System.out.println("实际支付："+ sum);
		sc.close();
	}
}
