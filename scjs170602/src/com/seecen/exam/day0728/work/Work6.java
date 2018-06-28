package com.seecen.exam.day0728.work;
/**
 * 1至50中是7的倍数的数值之和
 * @author Administrator
 */
public class Work6 {
	public static void main(String[] args) {
		int sum = 0;
		for (int i = 1; i <= 50; i++) {
			if(i%7==0){
				sum += i;
			}
		}
		System.out.println(" 1-50中是7的倍数的数值之和为:"+ sum);
	}
}
