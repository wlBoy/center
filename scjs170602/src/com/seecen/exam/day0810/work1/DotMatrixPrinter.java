package com.seecen.exam.day0810.work1;

/**
 * 针式打印机类,继承抽象父类打印机
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月10日
 */
public class DotMatrixPrinter extends Printer {

	@Override
	public void printer() {
		System.out.println("针式打印机:打印速度慢，效果差，噪音高");
	}

}
