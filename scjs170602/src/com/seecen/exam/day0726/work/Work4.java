package com.seecen.exam.day0726.work;

import java.util.Scanner;

/**
 *  给出一个华氏温度， 算出摄氏温度
	公式：℃ = (οF - 32) / 1.8
 * @author Administrator
 *
 */
public class Work4 {
	public static void main(String[] args) {
		System.out.println("请输入一个华氏温度：");
		Scanner sc = new Scanner(System.in);
		double oF = sc.nextDouble();
		double tem = (oF-32)/1.8;
		System.out.println("计算后的摄氏温度为：" + tem);
		sc.close();
	}
}
