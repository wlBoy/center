package com.seecen.exam.day0726.work;

/**
 *  给出2个数， num1=10,num2=20. 
	最后打出结果为  num1 = 20   num2 =10
 * @author Administrator
 *
 */
public class Work3 {
	public static void main(String[] args) {
		int num1 = 10;
		int num2 = 20;
		System.out.println("交换前为："+num1 +"-"+ num2);
		int mid;
		mid = num1;
		num1 = num2;
		num2 = mid;
		System.out.println("交换后为："+num1 +"-"+ num2);
	}
}
