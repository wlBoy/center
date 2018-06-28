package com.seecen.exam.day0802;

import java.util.Scanner;

public class CalScoreDemo {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		CalScore cs = new CalScore();
		System.out.print("请输入Java成绩:");
		cs.javaScore = sc.nextDouble();
		System.out.print("请输入C#成绩:");
		cs.CShapeScore = sc.nextDouble();
		System.out.print("请输入DB成绩:");
		cs.DBScore = sc.nextDouble();

		System.out.println("总成绩是:" + cs.sumScore());
		System.out.println("平均分是:" + cs.avgScore());
		sc.close();
	}

}
