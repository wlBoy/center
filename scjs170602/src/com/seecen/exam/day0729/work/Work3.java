package com.seecen.exam.day0729.work;

/**
 * 一个数组18,25,7,36,13,2,89,63,找出最小数和所在位置
 * @author Administrator
 */
public class Work3 {
	public static void main(String[] args) {
		int[] nums = {18,25,7,36,13,2,89,63};
		int min = nums[0];
		int i = 1,j = 0;
		for (; i < nums.length; i++) {
			if(min>=nums[i]){
				min = nums[i];
				j = i;
			}
		}
		System.out.println("最小数为: " + min +",其所在位置为:第" + (j+1) + "个");
	}
}
