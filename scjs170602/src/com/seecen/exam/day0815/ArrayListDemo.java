package com.seecen.exam.day0815;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 测试ArrayList ArrayList定义一个 “不唯一，有序”的集合，是连续存储的内存结构，其父接口为List接口
 * 查找速度快，但中间位置的增，删速度慢
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月15日
 */
public class ArrayListDemo {

	public static void main(String[] args) {
		Penguin pen1 = new Penguin("fdsf", "男");
		Penguin pen2 = new Penguin("aghg", "女");
		Penguin pen3 = new Penguin("errr", "女");
		Penguin pen4 = new Penguin("zbcb", "男");
		List<Penguin> pens = new ArrayList<Penguin>();
		pens.add(pen1);
		pens.add(pen2);
		pens.add(pen3);
		pens.add(pen4);
		System.out.println("大小为:" + pens.size());
		// 第一种遍历方法:for一般循环
		for (int i = 0; i < pens.size(); i++) {
			System.out.print(pens.get(i).getName() + "-" + pens.get(i).getSex()
					+ "  ");
		}
		System.out.println();
		pens.remove(1);
		// 第二种遍历方法:forEach循环(增强for循环)
		for (Penguin penguin : pens) {
			System.out.print(penguin.getName() + "-" + penguin.getSex() + "  ");
		}
		System.out.println();
		pens.set(0, pen4);
		// 第三种遍历方法:用迭代器进行循环
		Iterator<Penguin> itor = pens.iterator();
		while (itor.hasNext()) {
			Penguin pen = itor.next();
			System.out.print(pen.getName() + "-" + pen.getSex() + "  ");
		}
		System.out.println();
		System.out.println("是否包含" + pen1.getName() + ":" + pens.contains(pen1));
	}

}
