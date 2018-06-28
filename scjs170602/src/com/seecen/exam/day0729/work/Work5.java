package com.seecen.exam.day0729.work;

/**
 * 原数组将其逆序在进行筛选
 * @author Administrator
 */
public class Work5 {
	public static void main(String[] args) {
		int[] nums = {1,3,-1,5,-2};
		System.out.print("原数组为:");
		for (int i = 0; i < nums.length; i++) {
			System.out.print(nums[i]+" ");
		}
		System.out.println("");
		System.out.print("逆序并处理后的数组为:");
		for (int i = nums.length - 1; i >=0; i--) {
			if(nums[i]<0){
				nums[i] = 0;
			}
			System.out.print(nums[i]+" ");
		}
	}
}
