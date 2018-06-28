package com.seecen.exam.day0728.work;

import java.util.Scanner;
/**
 * 根据输入数字显示对应的英文星期的名称
 * @author Administrator
 */
public class Work8 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int num = 0;
		while(true){
			System.out.print("请输入一个数字1-7(输入0结束):");
			num = sc.nextInt();
			switch (num) {
			case 1:
				System.out.println("今天是\t Monday");
				break;
			case 2:
				System.out.println("今天是\t Tuesday");
				break;
			case 3:
				System.out.println("今天是\t Wednesday");
				break;
			case 4:
				System.out.println("今天是\t Thurday");
				break;
			case 5:
				System.out.println("今天是\t Friday");
				break;
			case 6:
				System.out.println("今天是\t Saturday");
				break;
			case 7:
				System.out.println("今天是\t Sunday");
				break;
			default:
				break;
			}
			if(num == 0){
				System.out.println("程序结束！");
				break;
			}
		}
		sc.close();
	}
}
