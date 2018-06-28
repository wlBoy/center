package com.seecen.exam.day0804.work;

import java.util.Scanner;

/**
 * 数组类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月4日
 */
public class Work4Array {
	/**
	 * 在指定的数组，指定的位置上插入指定的数据
	 * 
	 * @param nums
	 *            数组
	 * @param index
	 *            插入的位置
	 * @param data
	 *            插入的数据
	 * @return 返回一个插入新数据的新数组
	 */
	public static int[] insertData(int[] nums, int index, int data) {
		// 将新数组在要插入的位置index之后的内容全部往后移一个位置,从后面开始移
		for (int i = nums.length - 1; i > index; i--) {
			nums[i] = nums[i - 1];
		}
		// 插入数据
		nums[index] = data;
		return nums;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int[] nums = { 25, 36, 89, 56, 75, 0, 0, 0, 0, 0 };
		System.out.println("插入前的数组为:");
		for (int i : nums) {
			System.out.print(i + "  ");
		}
		System.out.println();
		System.out.print("请输入你要插入的位置:");
		int indexNum = sc.nextInt();
		System.out.print("请输入你要插入的数据:");
		int dataNum = sc.nextInt();

		int[] newNums = insertData(nums, indexNum, dataNum);
		System.out.println("插入后的数组为:");
		for (int i : newNums) {
			System.out.print(i + "  ");
		}
		sc.close();
	}
}
