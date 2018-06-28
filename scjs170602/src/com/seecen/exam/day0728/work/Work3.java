package com.seecen.exam.day0728.work;

import java.util.Scanner;
/**
 * 使用do..while循环小小的测试
 * @author Administrator
 *
 */
public class Work3 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String answer = null;
		do{
			System.out.println("上机编写程序!");
			System.out.println("你合格了吗(y/n)？");
			answer = sc.next();
		}while(answer.equals("n"));
		System.out.println("恭喜你通过了测试!");
		sc.close();
	}
}
