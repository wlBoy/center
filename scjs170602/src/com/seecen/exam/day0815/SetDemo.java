package com.seecen.exam.day0815;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * 测试Set集合接口 Set定义一个 “唯一，无序(插入顺序)”的集合接口，其父类为Collection，其实现类HashSet和TreeSet
 * 1、TreeSet 是二差树实现的,Treeset中的数据是自动排好序的，不允许放入null值。 2、HashSet
 * 是哈希表实现的,HashSet中的数据是无序的，可以放入null，但只能放入一个null，两者中的值都不能重复，就如数据库中唯一约束。
 * 3、HashSet要求放入的对象必须实现HashCode()方法，放入的对象，是以hashcode码作为标识的，而具有相同内容的
 * String对象，hashcode是一样，所以放入的内容不能重复。但是同一个类的对象可以放入不同的实例 。
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月15日
 */
public class SetDemo {

	public static void main(String[] args) {
		// HashSet用法
		Set<Integer> set1 = new HashSet<Integer>();
		set1.add(6);
		set1.add(3);
		set1.add(4);
		set1.add(2);
		// 用迭代器进行循环
		Iterator<Integer> itor1 = set1.iterator();
		while (itor1.hasNext()) {
			System.out.print(itor1.next() + " ");
		}
		System.out.println();
		System.out.println("该set集合的哈希码值:" + set1.hashCode());
		System.out.println("该set集合的size : " + set1.size());
		System.out.println("--------------------");
		// TreeSet用法
		Set<Integer> set2 = new TreeSet<Integer>();
		set2.add(6);
		set2.add(3);
		set2.add(4);
		set2.add(1);
		// forEach循环(增强for循环)
		for (Integer integer : set2) {
			System.out.print(integer + " ");
		}
		System.out.println();
		System.out.println("该set集合的哈希码值:" + set2.hashCode());
		System.out.println("该set集合的size : " + set2.size());
	}

}
