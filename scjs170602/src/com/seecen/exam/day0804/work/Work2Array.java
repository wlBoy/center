package com.seecen.exam.day0804.work;

import java.util.Scanner;

/**
 * 数组类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月4日
 */
public class Work2Array {
	// 用来存放比较完的最大值和最小值的结果数组，声明为static为类变量，共享使用
	static int[] results = new int[2];

	/**
	 * 查找一个数组中的最大值与最小值
	 * 
	 * @param nums
	 *            数组
	 * @return 最大值和最小值存放的结果数组(0位置为最大值,1位置为最小值)
	 */
	public static int[] findMinAndMax(int[] nums) {
		// 先将最大值和最小值设为数组第一个数值，循环从数组的第二个数值开始
		int min = nums[0];
		int max = nums[0];
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] > max) {
				max = nums[i];
			}
			if (nums[i] < min) {
				min = nums[i];
			}
		}
		results[0] = max;
		results[1] = min;
		return results;
	}

	/**
	 * 测试主方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int[] nums = new int[10];
		for (int i = 0; i < nums.length; i++) {
			System.out.print("请输入第" + (i + 1) + "个数字:");
			nums[i] = sc.nextInt();
		}
		System.out.println("该数组的最大值为:" + findMinAndMax(nums)[0]);
		System.out.println("该数组的最小值为:" + findMinAndMax(nums)[1]);
		sc.close();
	}
}
