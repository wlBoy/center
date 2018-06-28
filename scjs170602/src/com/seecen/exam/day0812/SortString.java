package com.seecen.exam.day0812;

import java.util.Arrays;

/**
 * 自己编写代码将字符串String s=“11.1,56.1,2.9,34.3,1.03,24.2“按照从小到大排序，排序的结果放入 double
 * []数组中
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月12日
 */
public class SortString {

	public static void main(String[] args) {
		String s = "11.1,56.1,2.9,34.3,1.03,24.2";
		SortString ss = new SortString();
		double[] results = ss.sortString(s);
		System.out.println("排序后的double数组为:");
		for (double d : results) {
			System.out.print(d + " ");
		}
	}

	/**
	 * 字符串排序
	 * 
	 * @param str
	 *            要排序的字符串
	 * @return 排序完返回的数组
	 */
	public double[] sortString(String str) {
		String[] strs = str.split(",");
		double[] newStrs = new double[strs.length];
		for (int i = 0; i < strs.length; i++) {
			// 调用Double.parseDouble方法将字符串转为double
			double strNum = Double.parseDouble(strs[i]);
			// 再讲转换后的double数字放入一个新的double数组中
			newStrs[i] = strNum;
		}
		// 对新的double数组进行排序
		Arrays.sort(newStrs);
		return newStrs;
	}
}
