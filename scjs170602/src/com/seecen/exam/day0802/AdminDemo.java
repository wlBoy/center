package com.seecen.exam.day0802;

/**
 * 创建多个Admin对象，可以使用对象数组遍历实例化
 * @author Administrator
 *
 */
public class AdminDemo {
	public static void main(String[] args) {
			//先申明一个对象数组
			Admin[] admins = new Admin[3];
			for (int i = 0; i < admins.length; i++) {
				//循环将对象数组实例化
				admins[i] = new Admin();
				admins[i].name  = "admin"+ (i+1);
				admins[i].pwd  = "1"+ (i+1);
				admins[i].show();
			}
	}
}
