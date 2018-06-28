package com.seecen.exam.day0810;

/**
 * 打印机抽象类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月10日
 */
public abstract class Printer {
	public abstract void printer(IInkBox inkBox, IPaper paper, String data);
}
