package com.seecen.exam.day0803.work;

import java.util.Scanner;

/**
 * 测试计算器
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月3日
 */
public class CalculatorDemo {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Calculator c = new Calculator();
		
		String answer = null;
		System.out.println("**欢迎来到小型计算器系统**");
		do {
			System.out.print("请输入第一个操作数:");
			c.num1 = sc.nextDouble();
			System.out.print("请输入第二个操作数:");
			c.num2 = sc.nextDouble();
			System.out.print("请输入操作符(+,-,*,/,%):");
			String operator = sc.next();

			// 调用Calculator类的方法进行运算,将operator操作符传入
			System.out.println(c.calculate(operator));

			System.out.print("是否继续运算(y/n):");
			answer = sc.next();
		} while (answer.equals("y"));

		sc.close();
	}
}
