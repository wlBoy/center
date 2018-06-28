package com.seecen.exam.day0731;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

/*
 * 可视化日历程序
 * */
public class VisualCalender {
	public static void main(String[] args) {
		System.out.println("请输入日期（按照格式：2010-10-13）：");
		Scanner scanner = new Scanner(System.in);
		// 获得键盘输入流
		String temp = scanner.nextLine();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = format.parse(temp);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			int day = calendar.get(Calendar.DATE);
			// 获取当天的号数
			calendar.set(Calendar.DATE, 1);

			System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
			// 当月的一号是属于星期几
			System.out.println(calendar.getActualMaximum(Calendar.DATE));
			// 当月的最大日期
			int maxDate = calendar.getActualMaximum(Calendar.DATE);

			System.out.println("日\t一\t二\t三\t四\t五\t六");

			for (int i = 1; i < calendar.get(Calendar.DAY_OF_WEEK); i++) {
				System.out.print("\t");
			}
			for (int i = 1; i <= maxDate; i++) {
				if (i == day) {
					System.out.print("*");
				}
				System.out.print(i + "\t");
				int week = calendar.get(Calendar.DAY_OF_WEEK);
				if (week == Calendar.SATURDAY) {
					System.out.print("\n");
				}
				calendar.add(Calendar.DATE, 1);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		scanner.close();
	}
}
