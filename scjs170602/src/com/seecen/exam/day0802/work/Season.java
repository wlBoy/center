package com.seecen.exam.day0802.work;

/**
 * 季节类，输入一个月份，打印该月份是什么季节
 * 
 * @author Administrator
 */
public class Season {
	int month;

	public String printSeason() {
		if (month >= 1 && month <= 3) {
			// 1,2,3
			return month + "月，为:春季";
		} else if (month >= 4 && month <= 6) {
			// 4,5,6
			return month + "月，为:夏季";
		} else if (month >= 7 && month <= 9) {
			// 7,8,9
			return month + "月，为:秋季";
		} else {
			// 10,11,12
			return month + "月，为:冬季";
		}

	}
}
