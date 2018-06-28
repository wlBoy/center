package com.seecen.exam.day0803;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 学员类
 * 
 * @author 万磊
 */
public class Student {
	private String[] stus = new String[5];

	/**
	 * 依次 录入5位学员的信息
	 */
	public void addName() {
		Scanner sc = new Scanner(System.in);
		System.out.println("请依次录入5位学员的姓名:");
		for (int i = 0; i < stus.length; i++) {
			stus[i] = sc.next();
		}
		System.out.println("录入信息完毕!");
		sc.close();
	}

	/**
	 * 学员数组被私有化了，就通过共有方法来获取学员数组
	 * 
	 * @return 学员数组
	 */
	public String[] getStus() {
		return stus;
	}
	/**
	 * 先排序,再显示所有学员的信息
	 */
	public void showAll(){
		Arrays.sort(stus);
		for (int i = 0; i < stus.length; i++) {
			System.out.print(stus[i] + "  ");
		}
	}
}
