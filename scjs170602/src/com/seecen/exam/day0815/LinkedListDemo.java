package com.seecen.exam.day0815;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * 测试LinkedList LinkedList(链表)定义一个 “不唯一，有序”的集合,是非连续(链式)存储的内存结构，其父接口为List接口
 * 查询速度慢，但中间位置的增，删速度快
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月15日
 */
public class LinkedListDemo {

	public static void main(String[] args) {
		// 定义泛型String，这里只能存储String类型的数据
		LinkedList<String> heros = new LinkedList<String>();
		heros.addFirst("小法");
		heros.add("石头人");
		heros.add("狐狸");
		heros.add("螳螂");
		heros.addLast("瞎子");
		heros.set(1, "剑圣");// set方法修改值
		// 第一种遍历方法:for一般循环
		for (int i = 0; i < heros.size(); i++) {
			System.out.print(heros.get(i) + " ");
		}
		System.out.println();
		System.out.println("--------------");
		// 删除第一个或最后一个
		heros.removeFirst();
		heros.removeLast();
		// 第二种遍历方法:forEach循环(增强for循环)
		for (Object o : heros) {
			System.out.print(o + " ");
		}
		System.out.println();
		System.out.println("--------------");
		// 获取第一个或最后一个
		System.out.println(heros.getFirst() + " " + heros.getLast());
		System.out.println("--------------");
		LinkedList<Penguin> pens = new LinkedList<Penguin>();
		pens.add(new Penguin("dfsd","男"));
		pens.add(new Penguin("ewrw","女"));
		pens.add(new Penguin("afgh","男"));
		// 第三种遍历方法:用迭代器进行循环
		Iterator<Penguin> penItor = pens.iterator();
		while (penItor.hasNext()) {
			Penguin pen = penItor.next();
			System.out.print(pen.getName() + "-" + pen.getSex() + "  ");
		}
	}

}
