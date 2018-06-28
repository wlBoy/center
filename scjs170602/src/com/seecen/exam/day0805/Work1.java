package com.seecen.exam.day0805;

import java.util.Scanner;

/**
 * 输入两个整数,输出他们和他们+,-,*,/之后的结果
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月5日
 */
public class Work1 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("请输入第一个整数:");
		int num1 = sc.nextInt();
		System.out.print("请输入第二个整数:");
		int num2 = sc.nextInt();
		System.out.println("num1 = " + num1 + " , num2 = " + num2);
		System.out.println("num1 + num2 =" + (num1 + num2));
		System.out.println("num1 - num2 =" + (num1 - num2));
		System.out.println("num1 * num2 =" + (num1 * num2));
		System.out.println("num1 / num2 =" + (num1 / num2));
		sc.close();
	}

}
