package com.seecen.exam.day0727.work;

import java.util.Scanner;
/**
 * switch的使用和if..else if等结构的使用
 * @author Administrator
 */
public class Work3 {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("请输入赵本山的成绩：");
		int score = sc.nextInt();
		if(score==100){
			System.out.println("爸爸给他买辆车！");
		}else if(score>=90&&score<100){
			System.out.println("妈妈给他买MP4！");
		}else if(score>=60&&score<90){
			System.out.println("妈妈给他买本参考书！");
		}else{
			System.out.println("什么都不买！");
		}
		System.out.println("------------------------------");
		System.out.println("张三为他的手机设定了自动拨号，请输入数字(1,2,3,4)：");
		int  num = sc.nextInt();
		switch (num) {
		case 1:
			System.out.println("按"+num+"：拨爸爸的号");
			break;
		case 2:
			System.out.println("按"+num+"：拨妈妈的号");
			break;
		case 3:
			System.out.println("按"+num+"：拨爷爷的号");
			break;
		case 4:
			System.out.println("按"+num+"：拨奶奶的号");
			break;
		default:
			System.out.println("请输入正确的选项！");
			break;
		}
		sc.close();
	}
}
