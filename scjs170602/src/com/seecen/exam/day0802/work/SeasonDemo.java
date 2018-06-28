package com.seecen.exam.day0802.work;

import java.util.Scanner;

public class SeasonDemo {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Season s = new Season();
		String answer = null ;
		do{
			System.out.print("请输入月份:");
			s.month = sc.nextInt();
			
			//调用Season类的方法进行打印季节
			System.out.println(s.printSeason());
			
			System.out.print("是否继续输入月份(y/n):");
			answer = sc.next();
		}while(answer.equals("y"));
		sc.close();
	}
}
