package com.seecen.exam.day0803;

import java.util.Scanner;

/**
 * 给一组商品和他们的价格，随机选中一个商品进行竞猜(一共4次机会),猜错有相应的提示，猜对中奖
 * @author Administrator
 */
public class Guess {
	String goods;
	int price;

	/**
	 * 初始化数据的方法
	 */
	public void init() {
		String[] goodses = { "创维电视", "小米MI6", "王牌冰箱", "34寸大彩电", "神舟笔记本", "洗衣机" };
		int[] prices = { 3500, 3000, 5000, 4500, 6800, 3800 };
		// 随机生成索引，达到随机竞猜商品的效果
		int index = (int)(Math.random() * goodses.length);
		goods = goodses[index];
		price = prices[index];
	}

	/**
	 * 竞猜方法
	 * @return 猜对返回true，猜错返回false
	 */
	public boolean guessPrice() {
		Scanner sc = new Scanner(System.in);
		System.out.print("请猜测" + goods + "的价格:");
		for (int i = 3; i >= 0; i--) {
			int moneyInput = sc.nextInt();
			//当用户最后一次没猜中的话，直接跳出循环
			if (i == 0 && moneyInput != price) {
				break;
			}
			if (moneyInput > price) {
				System.out.print("再小点,请继续:");
			} else if (moneyInput < price) {
				System.out.print("再大点,请继续:");
			} else {
				sc.close();
				return true;
			}
		}
		sc.close();
		return false;
	}
}
