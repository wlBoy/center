package com.seecen.exam.day0728;
import java.util.Scanner;
/**
 * 循环的用法:while，do....while,for等
 * @author Administrator
 */
public class TestCycle1 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		/*
			String  answer = "n";
			while(answer.equals("n")){
				System.out.println("上午学习理论，下午继续敲代码");
				System.out.println("是否合格(y/n)？");
				answer = sc.next();
			}
			System.out.println("-------------------------------------");
			double first = 8;
			double dis = 0.25 ;
			int year=2006;
			while(first<=20){
				first += first * dis;
				year++;
				System.out.println(year+"年培训了"+ first +"万人");
			}
		*/
		int num = 0 ,sum = 0;
		while(num <= 100){
			if(num%2==0){
				sum += num;
			}
			num++;
		}
		System.out.println("100以内 的偶数之和为：" + sum);
		System.out.println("-------------------------------------");
		System.out.println("Myshopping管理系统>购物结算");
		System.out.println("**********************************");
		System.out.println("请选择购买商品的编号:");
		System.out.println("1.T恤\t2.网球鞋\t3.网球拍");
		System.out.println("**********************************");
		String isContinue = "y";
		while(isContinue.equals("y")){
			System.out.println("请输入商品的编号:");
			int listNum = sc.nextInt();
			switch (listNum) {
			case 1:
				System.out.println("T恤\t¥200");
				break;
			case 2:
				System.out.println("网球鞋\t¥500");
				break;
			case 3:
				System.out.println("网球拍\t¥100");
				break;
			default:
				System.out.println("请输入正确的编号!");
				break;
			}
			System.out.println("是否继续(y/n)?");
			isContinue = sc.next();
			if(!isContinue.equals("y")){
				System.out.println("购买结束！");
			}
			sc.close();
		}
	}
}
