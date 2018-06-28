package com.seecen.exam.day0807;

import java.util.Scanner;

public class RegisterBizDemo {

	public static void main(String[] args) {
		/*
			Scanner sc = new Scanner(System.in);
			System.out.print("请输入一个字符串:");
			String str = sc.next();
			System.out.print("请输入你要查找字符:");
			String findStr = sc.next();
			第一种:
			char[] chars = str.toCharArray();
			int count = 0;
			//遍历字符数组，与要查找的字符比较
			for (int i = 0; i < chars.length; i++) {
				if(findStr.charAt(0)==chars[i]){
					count++;
				}
			}
			System.out.println("\""+str+"\""+"中包含"+ count+"个"+"\""+findStr+"\"");
			第二种:
			int count = 0;
			int index = str.indexOf(findStr);
			//当找不到下一个时跳出循环
			while(index!=-1){
				count++;
				index = str.indexOf(findStr,index+1);
			}
			System.out.println("\""+str+"\""+"中包含"+ count+"个"+"\""+findStr+"\"");
			sc.close();
			System.out.println(------------------------);
			StringBuilder sbd = new StringBuilder("a");
			sbd.append("b");
			sbd.append("c");
			System.out.println(sbd);
			System.out.println(------------------------);
			Scanner sc = new Scanner(System.in);
			System.out.print("请输入一串数字:");
			String nums = sc.next();
			StringBuffer str = new StringBuffer(nums);
			for (int i = str.length() - 3; i > 0; i = i - 3) {
				str.insert(i, ",");
			}
			System.out.println(str);
		 */
		Scanner sc = new Scanner(System.in);
		// 创建数据对象
		Register register = new Register();
		// 创建业务处理对象
		RegisterBiz rb = new RegisterBiz();
		System.out.print("请输入身份证：");
		register.setIdCard(sc.next());
		System.out.print("请输入手机号：");
		register.setPhone(sc.next());
		System.out.print("请输入座机号：");
		register.setTel(sc.next());
		
		int result = rb.checkInput(register);
		if(result == 1) {
			System.out.println("可以注册！");
		} else if(result == 2) {
			System.out.println("身份证后必须是16位或18位");
		} else if(result == 3) {
			System.out.println("手机号码必须是11位");
		} else {
			System.out.println("座机号码区号必须是4位，电话号码必须是8位！");
		}
		sc.close();
	}

}
