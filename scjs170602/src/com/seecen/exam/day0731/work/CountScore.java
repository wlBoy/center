package com.seecen.exam.day0731.work;

import java.util.Scanner;

/**
 * 计算3个班，每个班4个学员的平均分
 * 
 * @author Administrator
 */
public class CountScore {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int[] scores = new int[4];
		double sum = 0;
		for (int j = 0; j < 3; j++) {
			System.out.println("----请录入第" + (j + 1) + "个班的成绩----");
			for (int i = 0; i < scores.length; i++) {
				System.out.print("请输入第" + (i + 1) + "个学员的成绩:");
				scores[i] = sc.nextInt();
				sum += scores[i];
			}
			System.out.println("第" + (j + 1) + "个班的平均分为:"
					+ (sum / scores.length));
		}
		sc.close();
	}
}
