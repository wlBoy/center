package com.seecen.exam.day0726;

import java.util.Scanner;
/**
 * 成绩统计清单
 * @author Administrator
 */
public class ScoreList {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("请输入语文成绩：");
		int chinese=sc.nextInt();
		System.out.println("请输入数学成绩：");
		int math=sc.nextInt();
		System.out.println("请输入Java成绩：");
		int java=sc.nextInt();
		System.out.println("-----------------------");
		System.out.println("你的成绩列表如下：");
		System.out.println("科目\t成绩");
		System.out.println("语文\t" + chinese);
		System.out.println("数学\t" + math);
		System.out.println("Java\t" + java);
		System.out.println("-----------------------");
		int sum = chinese + math + java;
		double averge = sum / 3;
		int max = 0;
		if (chinese >= math){
			max = chinese;
		}else{
			max = math;
		}
		if(max <= java){
			max = java;
		}
		System.out.println("你的最高分是：" + max + "分");
		System.out.println("你的总分是：" + sum + "分");
		System.out.println("你的平均分是：" + averge + "分");
		sc.close();
	}
}
