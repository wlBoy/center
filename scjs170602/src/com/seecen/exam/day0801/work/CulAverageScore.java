package com.seecen.exam.day0801.work;

import java.util.Scanner;

/**
 * 输入三个班的学员平均分及成绩为85分以上的学员人数
 * @author 万磊
 */
public class CulAverageScore {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int classCount = 3;
		int[] scores = new int[4];
		int count = 0;//计算85分以上的学员人数
		for (int i = 1; i <= classCount; i++) {
			//每次循环将sum清0
			double sum = 0;
			System.out.println("请输入第" + i +"个班级的成绩");
			for (int j = 0; j < scores.length; j++) {
				System.out.print("第" + (j+1) + "个学员的成绩:");
				scores[i] = sc.nextInt();
				sum += scores[i];
				if(scores[i]>=85){
					count++;
				}
			}
			System.out.println("第"+i+"个班级参赛学员的平均分为:" + sum/scores.length);
			System.out.println();
		}
		System.out.println("成绩为85分以上的学员人数为" + count);
		sc.close();
	}
}
