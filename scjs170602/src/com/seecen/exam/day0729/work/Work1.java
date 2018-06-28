package com.seecen.exam.day0729.work;

import java.util.Scanner;
/**
 * 将原有积分进行备份，然后赠送每位会员500积分，并输出
 * @author Administrator
 */
public class Work1 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入5位会员的积分");
		int[] oldPrices = new int[5];
		int[] newPrices = new int[5];
		for (int i = 0; i < oldPrices.length; i++) {
			System.out.print("第"+(i+1)+"位会员的积分:");
			oldPrices[i] = sc.nextInt();
			newPrices[i] = oldPrices[i] + 500 ;
		}
		System.out.println("序号\t历史积分\t新积分");
		for (int i = 0; i < oldPrices.length; i++) {
			System.out.println((i+1)+"\t"+oldPrices[i]+"\t"+newPrices[i]);
		}
		sc.close();
	}
}
