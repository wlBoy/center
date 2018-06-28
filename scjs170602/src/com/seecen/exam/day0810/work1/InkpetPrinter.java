package com.seecen.exam.day0810.work1;

/**
 * 喷墨打印机类,继承抽象父类打印机
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月10日
 */
public class InkpetPrinter extends Printer {

	@Override
	public void printer() {
		System.out.println("喷墨打印机:打印效果介于针式和激光打印机之间");
	}

}
