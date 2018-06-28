package com.seecen.exam.day0803;

import java.util.Scanner;

public class ShopSystemDemo {

	public static void main(String[] args) {
		ShopSystem ss = new ShopSystem();
		Scanner sc = new Scanner(System.in);
		ss.printHeader();
		System.out.print("请选择,输入数字:");
		int num = sc.nextInt();
		switch (num) {
		case 1:
			String answer = null;
			do{
				ss.printMainMenu();
				System.out.print("请选择,输入数字(0返回上一级菜单):");
				int menu = sc.nextInt();
				if(menu==1){
					ss.customerManage();
				}
				if(menu==2){
					ss.tureFeedback();
				}
				if(menu==0){
					ss.printHeader();
				}
				System.out.print("是否继续(y/n):");
				answer = sc.next();
			}while(answer.equals("y"));
			break;
		case 2:
			System.out.println("系统退出,谢谢使用！");
			System.exit(1);
			break;
		default:
			System.out.println("请输入正确的选项！");
			break;
		}
		sc.close();
	}
}
