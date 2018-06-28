package com.seecen.exam.day0727.work;

import java.util.Scanner;

/**
 * 优惠换购活动-switch版
 * @author Administrator
 *
 */
public class Work2 {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("请输入消费金额：");
		int price = sc.nextInt();
		int money = 0;
		System.out.println("是否参加优惠换购活动：");
		System.out.println("1:满50元，加2元换购百事可乐1瓶");
		System.out.println("2:满100元，加3元换购500ML可乐1瓶");
		System.out.println("3:满100元，加10元换购5公斤面粉 ");
		System.out.println("4:满200元，加10元可换购1个苏泊尔炒菜锅");
		System.out.println("5:满200元，加20元换购欧莱雅爽肤水一瓶");
		System.out.println("0:不换购");
		System.out.println("请选择：");
		int num = sc.nextInt();
		String tip = null;
		switch (num) {
		case 0:
			money = price;
			tip  = "不换购";
			break;
		case 1:
			if(price>=50){
				money = price + 2;
				tip = "成功换购：百事可乐1瓶";
			}
			break;
		case 2:
			if(price>=100){
				money = price + 10;
				tip = "成功换购：500ML可乐1瓶";
			}
			break;
		case 3:
			if(price>=100){
				money = price + 10;
				tip = "成功换购：5公斤面粉";
			}
			break;
		case 4:
			if(price>=200){
				money = price + 20;
				tip = "成功换购：1个苏泊尔炒菜锅";
			}
			break;
		case 5:
			if(price>=200){
				money = price + 3;
				tip = "成功换购：欧莱雅爽肤水一瓶";
			}
			break;
		default:
			money = price;
			tip  = "不满足换购条件！";
			break;
		}
		System.out.println("本次消费总金额：" + money);
		System.out.println(tip);
		sc.close();
	}
}
