package com.seecen.exam.day0729.work;

import java.util.Scanner;
/**
 * 输入10个数并对其进行筛选
 * @author Administrator
 */
public class Work4 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入10个数");
		int[] nums = new int[10];
		int[] counts = new int[]{0,0,0,0};
		for (int i = 0; i < nums.length; i++) {
			System.out.print("请输入第"+(i+1)+"个数:");
			nums[i] = sc.nextInt();
			if(nums[i] == 1)	counts[0]++;
			else if(nums[i] == 2)	counts[1]++;
			else if(nums[i] == 3)	counts[2]++;
			else	counts[3]++;
		}
		System.out.println("数字 1 的个数为:" + counts[0]);
		System.out.println("数字 2 的个数为:" + counts[1]);
		System.out.println("数字 3 的个数为:" + counts[2]);
		System.out.println("非法数字的个数为:" + counts[3]);
		sc.close();
	}
}
