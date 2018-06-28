package com.seecen.exam.day0812;

/**
 * 计算字符串“23743298”奇数位的和，偶数位的和
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月12日
 */
public class CalStringSum {

	public static void main(String[] args) {
		String str = "23743298";
		int oddSum = 0;
		int evenSum = 0;
		for (int i = 0; i < str.length(); i++) {
			// 将字符先变成字符串,再调用Integer.parseInt()方法将字符串转为int
			int stringNum = Integer.parseInt(str.charAt(i) + "");
			if ((i + 1) % 2 == 0) {
				//偶数位
				evenSum += stringNum;
			} else {
				//奇数位
				oddSum += stringNum;
			}
		}
		System.out.println(str + "字符串偶数位的和为:" + evenSum);
		System.out.println(str + "字符串奇数位的和为:" + oddSum);
	}

}
