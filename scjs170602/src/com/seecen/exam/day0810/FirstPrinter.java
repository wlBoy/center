package com.seecen.exam.day0810;

/**
 * 第一台打印机,继承了抽象父类打印机
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月10日
 */
public class FirstPrinter extends Printer {

	@Override
	public void printer(IInkBox inkBox, IPaper paper, String data) {
		System.out.println("第一台打印机采用了 " + inkBox.getColor() + " 的墨盒," + paper.getSize() + "纸打印了【"
				+ data + "】!");
	}

}
