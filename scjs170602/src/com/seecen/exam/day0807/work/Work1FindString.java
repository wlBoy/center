package com.seecen.exam.day0807.work;

import java.util.Scanner;

/**
 * 在一串字符串中查找指定字符串位置
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月7日
 */
public class Work1FindString {
	/**
	 * 根据字符串查找指定字符串位置
	 * 
	 * @param str
	 *            字符串
	 * @param findStr
	 *            指定字符串
	 * @return 存放位置索引的数组,最后一个存放出现的次数
	 */
	public int[] findString(String str, String findStr) {
		int index = str.indexOf(findStr);
		// 出现的次数
		int count = 0;
		// 存放位置索引的数组
		int[] results = new int[str.length()];
		while (index != -1) {
			results[count++] = index;
			index = str.indexOf(findStr, index + 1);
		}
		//将出现次数放在数组最后一个位置，方便后面取出遍历
		results[str.length() - 1] = count;
		return results;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Work1FindString fs = new Work1FindString();
		System.out.println("请输入一段字符:");
		String str = sc.next();
		System.out.println("请输入要查找的字符串:");
		String findStr = sc.next();
		int[] results = fs.findString(str, findStr);
		System.out.print(findStr + "出现的位置是:");
		for (int i = 0; i < results[str.length() - 1]; i++) {
			System.out.print(results[i] + " ");
		}
		sc.close();
	}

}
