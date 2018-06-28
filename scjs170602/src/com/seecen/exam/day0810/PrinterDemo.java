package com.seecen.exam.day0810;

import java.util.Scanner;

/**
 * 测试打印机
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月10日
 */
public class PrinterDemo {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Printer first = new FirstPrinter();
		IInkBox colorBox = new ColorInkBox();
		IInkBox blackBox = new BlackInkBox();
		IPaper a4Paper = new A4Paper();
		IPaper b5Paper = new B5Paper();
		System.out.print("请输入你要打印的字符串:");
		String data = sc.next();
		System.out.println("******打印结果如下*******");
		first.printer(colorBox, b5Paper, data);
		first.printer(blackBox, a4Paper, data);
		sc.close();
	}

}
