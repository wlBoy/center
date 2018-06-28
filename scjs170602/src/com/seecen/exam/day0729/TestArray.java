package com.seecen.exam.day0729;

import java.util.Arrays;
import java.util.Scanner;
/**
 * 数组的介绍及使用
 * @author Administrator
 */
public class TestArray {
	public static void main(String[] args) {
		 /*
		   	1)第一种创建数组的方法
			int[] nums = new int[3];
			nums[0] = 1 ;
			nums[1] = 2 ;
			nums[2] = 3 ;
			2)第二中创建数组的方法，需要和数组的申明一起使用，否则报错，50后面加逗号不影响数组长度
			int[] nums2 = {10,20,30,40,50};
			3)第三种创建数组的方法，中括号里面不能加长度
			int[] nums3 = new int[]{10,20,30,40,50,60};
			nums3 = new int[]{1,2,3,4,5};//可重新给该数组赋值
			1)输出5位同学的成绩并计算总分和平均分
			Scanner sc = new Scanner(System.in);
			int[] scores = new int[5];
			double sum = 0;
			for (int i = 0; i < scores.length; i++) {
				System.out.print("请输入第"+(i+1)+"同学的成绩:");
				scores[i] = sc.nextInt();
				sum += scores[i];
			}
			System.out.println("总分为:"+ sum);
			System.out.println("平均分为:" + sum / scores.length);
			2)输入一个数字，在数组中查找，找到返回其索引值，否则没有找到
			Scanner sc = new Scanner(System.in);
			int[] nums = { 8, 4, 2, 1, 23, 344, 12 };
			System.out.println("请输入你要查找的数字:");
			int num = sc.nextInt();
			boolean flag = true;
			for (int i = 0; i < nums.length; i++) {
				if (num == nums[i]) {
					flag = false;
					System.out.print("数字" + num + "在该数组的第" + (i + 1) + "个位置");
					break;
				}
			}
			// 当flag不为true时，就说明找到了数字，反之
			if (flag) {
				System.out.println("对不起，没有找到该数字，请重新输入查找!");
			}
			3)输入消费金额，放入数组中，并将其遍历打印出来
			Scanner sc = new Scanner(System.in);
			double[] prices = new double[5];
			for (int i = 0; i < prices.length; i++) {
				System.out.print("请输入第" + (i + 1) + "次消费的金额:");
				// 将每次消费的金额放入数组中
				prices[i] = sc.nextDouble();
			}
			System.out.println("序号\t金额(元)");
			// 遍历数组取出各金额
			for (int i = 0; i < prices.length; i++) {
				System.out.println((i + 1) + "\t" + prices[i]);
			}
			4)给定一个字符数组，将其进行升序及降序并输出
			char[] charNums = {'a','c','u','b','e','p','f','z'};
			System.out.print("原字符序列:");
			for (int i = 0; i < charNums.length; i++) {
				System.out.print(charNums[i]+" ");
			}
			//数组排序，可以借用Arrays工具类中的sort方法进行排序
			Arrays.sort(charNums);
			System.out.println("");
			System.out.print("升序排序后:");
			for (int i = 0; i < charNums.length; i++) {
				System.out.print(charNums[i]+" ");
			}
			System.out.println("");
			System.out.print("降序排序后:");
			for (int i = charNums.length - 1; i >=0; i--) {
				System.out.print(charNums[i]+" ");
			}
			5)输入n家店的价格，将其中最低价格和最高价格的打印出来
			Scanner sc = new Scanner(System.in);
			System.out.print("请输入店的家数:");
			int num = sc.nextInt();
			int[] prices = new int[num];
			for (int i = 0; i < prices.length; i++) {
				System.out.print("第"+(i+1)+"家店的价格:");
				prices[i] = sc.nextInt();
			}
			Arrays.sort(prices);//升序排序
			System.out.println("最低价格是:" + prices[0]);
			System.out.println("最高价格是:" + prices[prices.length-1]);
		 */
		Scanner sc = new Scanner(System.in);
		System.out.print("请输入店的家数:");
		int num = sc.nextInt();
		int[] prices = new int[num];
		for (int i = 0; i < prices.length; i++) {
			System.out.print("第"+(i+1)+"家店的价格:");
			prices[i] = sc.nextInt();
		}
		Arrays.sort(prices);//升序排序
		System.out.println("最低价格是:" + prices[0]);
		System.out.println("最高价格是:" + prices[prices.length-1]);
		sc.close();
	}
}
