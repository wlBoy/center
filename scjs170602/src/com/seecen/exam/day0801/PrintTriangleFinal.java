package com.seecen.exam.day0801;

import java.util.Scanner;
/**
 * 打印三角形终极版(实心菱形和空心菱形)
 * @author Administrator
 */
public class PrintTriangleFinal {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("请输入行数：");
		int num = sc.nextInt();
		//外层循环一次代表一行，内层循环显示一行要打印的东西
		for (int i = 1; i <= num; i++) {
			// 输出空格数("/t"表示一个单位空格," "表示一个字符空格)
			for (int j = num; j > i; j--) {
				System.out.print(" ");
			}
			// 输出#
			for (int j = 0; j < (2 * i - 1); j++) {
				System.out.print("#");
			}
			//换行
			System.out.println();
		}
		//逆序输出:只要外层循环从后往前循环,内层循环一样
		for (int i = num; i > 0; i--) {
			// 输出空格数("/t"表示一个单位空格," "表示一个字符空格)
			for (int j = num; j > i; j--) {
				System.out.print(" ");
			}
			// 输出#
			for (int j = 0; j < (2 * i - 1); j++) {
				System.out.print("#");
			}
			//换行
			System.out.println();
		}
		System.out.println("-----------------------");
		System.out.print("请输入行数：");
		int n = sc.nextInt();
		for (int i = 1; i <= n; i++) {
			// 输出空格数("/t"表示一个单位空格," "表示一个字符空格)
			for (int j = n; j > i; j--) {
				System.out.print(" ");
			}
			// 输出#
			for (int j = 0; j < (2 * i - 1); j++) {
				//如果第一个位置或者是2 * i - 2的位置就打印#，其他空位用空格
				if (j == 0 || j == (2 * i - 2)) {
					System.out.print("#");
				} else {
					System.out.print(" ");
				}
			}
			//换行
			System.out.println();
		}
		for (int i = n; i > 0; i--) {
			// 输出空格数("/t"表示一个单位空格," "表示一个字符空格)
			for (int j = n; j > i; j--) {
				System.out.print(" ");
			}
			// 输出#
			for (int j = 0; j < (2 * i - 1); j++) {
				//如果第一个位置或者是2 * i - 2的位置就打印#，其他空位用空格
				if (j == 0 || j == (2 * i - 2)) {
					System.out.print("#");
				} else {
					System.out.print(" ");
				}
			}
			//换行
			System.out.println();
		}
		sc.close();
	}
}
