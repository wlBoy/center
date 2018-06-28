package com.seecen.exam.day0731.work;

import java.util.Scanner;

/**
 * 打印三角形(个数跟行*2加1走)
 * 
 * @author Administrator
 */
public class PrintTriangle2 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入你要打印三角形的行数:");
		int line = sc.nextInt();
		for (int i = 1; i <= line; i++) {
			for (int j = 1; j <= (2 * i - 1); j++) {
				System.out.print("*");
			}
			System.out.println("");
		}
		System.out.println("----------------------");
		for (int i = line; i > 0; i--) {
			for (int j = 1; j <= (2 * i - 1); j++) {
				System.out.print("*");
			}
			System.out.println("");
		}
		sc.close();
	}

}
