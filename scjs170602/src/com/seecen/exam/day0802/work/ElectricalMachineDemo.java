package com.seecen.exam.day0802.work;

import java.util.Scanner;

public class ElectricalMachineDemo {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		ElectricalMachine refrigerator = new ElectricalMachine();
		refrigerator.name = "\"新飞冰箱\"";//使用转义字符\"达到打印"的效果
		refrigerator.price = 3500;
		
		ElectricalMachine colorTV = new ElectricalMachine();
		colorTV.name = "\"34寸彩电\"";
		colorTV.price = 4000;
		
		String answer = null ;
		do{
			System.out.print("请输入你要 猜的电器(0代表冰箱,1代表彩电):");
			int operator = sc.nextInt();
			if(operator==0){
				//猜冰箱
				for (int i = 4; i > 0 ; i--) {
					//调用printMenu方法打印菜单
					System.out.print(refrigerator.printMenu(4));
					refrigerator.guessPrice = sc.nextInt();
					//当用户猜对啦，就退出程序
					if(refrigerator.Guess().equals("猜对啦!")){
						//用户猜对啦，跳出循环
						System.out.println(refrigerator.Guess());
						break;
					}
					//当用户只有最后一次机会时,提示语要变一下
					if(i == 1){
						System.out.println("4次内没有猜对，下次努力吧!");
					}else{
						System.out.println(refrigerator.Guess());
					}
				}
			}else{
				//猜彩电
				for (int i = 4; i > 0 ; i--) {
					//调用printMenu方法打印菜单
					System.out.print(refrigerator.printMenu(4));
					colorTV.guessPrice = sc.nextInt();
					//用户猜对啦，跳出循环
					if(colorTV.Guess().equals("猜对啦!")){
						System.out.println(colorTV.Guess());
						break;
					}
					//当用户只有最后一次机会时,提示语要变一下
					if(i == 1){
						System.out.println("4次内没有猜对，下次努力吧!");
					}else{
						System.out.println(colorTV.Guess());
					}
				}
			}
			
			System.out.print("是否继续猜价格(y/n):");
			answer = sc.next();
		}while(answer.equals("y"));
		sc.close();
	}

}
