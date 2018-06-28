package com.seecen.exam.day0810.work1;

/**
 * 激光打印机类,继承抽象父类打印机
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月10日
 */
public class LaserPrinter extends Printer {

	@Override
	public void printer() {
		System.out.println("激光打印机:打印速度快，噪音小，效果好");
	}

}
