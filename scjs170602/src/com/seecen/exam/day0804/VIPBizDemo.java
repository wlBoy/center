package com.seecen.exam.day0804;

import java.util.Scanner;

public class VIPBizDemo {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		VIP[] vips = new VIP[5];
		VIPBiz vb = new VIPBiz();
		//打印所有会员信息
		for (int i = 0; i < vips.length; i++) {
			vips[i] = new VIP();
			System.out.print("输入会员编号:");
			vips[i].num = sc.next();
			System.out.print("输入会员积分:");
			vips[i].score = sc.nextInt();
		}
		vb.showAll(vips);
		//根据会员编号查找会员
		System.out.print("请输入你要查找的会员编号:");
		String findNum = sc.next();
		if(vb.findByNum(vips, findNum) == null){
			System.out.println("您输入的会员编号不存在!");
		}else{
			System.out.println("会员编号为:" +findNum+"的会员，您的积分是:"+vb.findByNum(vips, findNum).score);
		}
		
		sc.close();
	}
}
