package com.seecen.exam.day0728.work;

import java.util.Scanner;
/**
 * 小型在线的商品购买结算系统
 * @author Administrator
 *
 */
public class Work0 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String str1 = "T恤";
		String str2 = "网球鞋";
		String str3 = "网球拍";
		int str1price = 245;
		int str2price = 570;
		int str3price = 100;
		double dis = 0.8 ;
		System.out.println("**********************************");
		System.out.println("请选择购买商品的编号:");
		System.out.println("1."+str1+"\t2."+str2+"\t3."+str3);
		System.out.println("**********************************");
		String isContinue = "y";
		int sum1 = 0,sum2 = 0,sum3 = 0;
		while(isContinue.equals("y")){
			System.out.println("请输入商品的编号:");
			int listNum = sc.nextInt();
			System.out.println("请输入购买的数量:");
			int count = sc.nextInt();
			switch (listNum) {
			case 1:
				sum1 = count * str1price ;
				System.out.println(str1+" ¥ "+str1price+"\t数量 "+  count + "\t合计 ¥" + sum1);
				break;
			case 2:
				sum2 = count * str2price ;
				System.out.println(str2+" ¥ "+str2price+"\t数量 "+  count + "\t合计 ¥" + sum2);
				break;
			case 3:
				sum3 = count * str3price ;
				System.out.println(str3+" ¥ "+str3price+"\t数量 "+  count + "\t合计 ¥" + sum3);
				break;
			default:
				System.out.println("请输入正确的编号!");
				break;
			}
			//统计所有商品的总价
			int pay = sum1 + sum2 + sum3;
			System.out.println("是否继续(y/n)?");
			isContinue = sc.next();
			if(!isContinue.equals("y")){
				System.out.println("购买结束！");
				System.out.println("折扣 :"+ dis);
				System.out.println("应付金额:" + pay*dis);
				System.out.print("实付金额:");
				int realPay = sc.nextInt();
				System.out.println("找钱:"+(realPay-pay*dis));
			}
			sc.close();
		}
	}
}
