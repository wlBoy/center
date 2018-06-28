package com.seecen.exam.day0726.work;

/**
 * 根据天数（46）计算周数和剩余的天数
 * @author Administrator
 *
 */
public class Work1 {
	public static void main(String[] args) {
		int date = 46;
		int week = date/7;
		int day = date%7;
		System.out.println("周数为："+week);
		System.out.println("剩余天数为："+day);
	}
}
