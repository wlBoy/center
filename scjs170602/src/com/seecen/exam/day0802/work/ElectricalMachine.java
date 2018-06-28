package com.seecen.exam.day0802.work;
/**
 * 根据用户输入的价格,和电器真实的价格进行比较,猜错给出相应的提示,(只有四次机会),猜对结束
 * @author Administrator
 */
public class ElectricalMachine {
	String name;
	int price;
	int guessPrice;

	public String Guess() {
		if (guessPrice > price) {
			return "再小点!";
		} else if (guessPrice < price) {
			return "再大点!";
		} else {
			return "猜对啦!";
		}
	}

	public String printMenu(int count) {
		if (count == 4) {
			return "请猜测" + name + "的价格:";
		} else {
			return "再猜一次吧:";
		}
	}
}
