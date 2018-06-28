package com.seecen.exam.day0810;
/**
 * 我的家书,实现家书接口
 * @scjs170602
 * @author 【万磊】
 * @2017年8月10日
 */
public class MyHomeLetter implements IHomeLetter {

	@Override
	public void title() {
		System.out.println("亲爱的爸爸,妈妈:");
	}

	@Override
	public void greet() {
		System.out.println("    您们最近过得好吗?");
	}

	@Override
	public void content() {
		System.out.println("\t我在这里挺好的，您们不用太担心我，在这里我认识了好多朋友!");
		System.out.println("\t我在这里会好好学习,已经学到了Java OPP面向对象编程啦!");
		System.out.println("\t您们二老保重身体,不用担心儿子，我已经长大啦,会照顾好自己!");
	}

	@Override
	public void wish() {
		System.out.println("    此致");
		System.out.println("敬礼");
	}

	@Override
	public void end() {
		System.out.println("\t\t\t\t\t您们最亲爱的儿子");
		System.out.println("\t\t\t\t\t2017年8月10日");
	}
	
	public static void main(String[] args) {
		MyHomeLetter mhl = new MyHomeLetter();
		mhl.title();
		mhl.greet();
		mhl.content();
		mhl.wish();
		mhl.end();
	}
}
