package com.seecen.exam.day0726.work;

/**
 * 已知圆的半径radius= 1.5，求其面积
 * @author Administrator
 *
 */
public class Work2 {
	public static void main(String[] args) {
		double radius = 1.5;
		double area = Math.PI * Math.pow(radius, 2);
		System.out.println("该圆的面积为：" + area);
	}
}
