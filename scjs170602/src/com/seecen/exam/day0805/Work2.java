package com.seecen.exam.day0805;

import java.util.Scanner;

/**
 * 输入一个四位整数，输出它的各位数字之和
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月5日
 */
public class Work2 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("请输入第一个四位整数:");
		int num = sc.nextInt();
		int sum = 0;
		sum += num % 10 + num / 10 % 10 + num / 100 % 10 + num / 1000 % 10;
		System.out.println(num + "各位数字之和为:" + sum);
		sc.close();
	}

}
