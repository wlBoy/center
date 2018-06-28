package com.seecen.exam.day0810.work1;

/**
 * 测试打印机
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月10日
 */
public class PrinterDemo {

	public static void main(String[] args) {
		Printer[] printers = { new LaserPrinter(), new DotMatrixPrinter(),
				new InkpetPrinter() };
		printerAll(printers);
	}

	public static void printerAll(Printer[] printers) {
		for (int i = 0; i < printers.length; i++) {
			printers[i].printer();
		}
	}
}
