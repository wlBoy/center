package com.seecen.exam.day0815;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * 测试Map Map定义一个 “Key-value"(键值对)形式的集合接口,其实现类HashMap(键不会排序)和TreeMap(键会排序),键不能重复。
 * HashMap通常比TreeMap快一点,建议多使用HashMap,在需要排序的Map时候才用TreeMap。
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月15日
 */
public class MapDemo {

	public static void main(String[] args) {
		// HashMap不会按键排序
		Map<String, String> countries = new HashMap<String, String>();
		countries.put("jp", "日本");
		countries.put("ch", "中国");
		countries.put("uk", "英国");
		countries.put("fr", "法国");
		countries.remove("jp");

		// 第一种遍历Map的方法，通过get()方法从先通过keySet()方法获取键的set集合，再key键来取value值
		Set<String> keys = countries.keySet();
		// forEach循环(增强for循环)
		for (String key : keys) {
			System.out.print(key + ":" + countries.get(key) + "  ");
		}
		System.out.println();
		// 用迭代器进行循环
		Iterator<String> itor = keys.iterator();
		while (itor.hasNext()) {
			String key = itor.next();
			System.out.print(key + ":" + countries.get(key) + "  ");
		}
		System.out.println();
		System.out.println("--------------------");

		// 第二种遍历Map的方法,通过entrySet()方法获取实体entry的set集合，再通过实体的get()方法得到key和value
		Set<Entry<String, String>> sets = countries.entrySet();
		// forEach循环(增强for循环)
		for (Entry<String, String> entry : sets) {
			System.out.print(entry.getKey() + ":" + entry.getValue() + "  ");
		}
		System.out.println();
		// 用迭代器进行循环
		Iterator<Entry<String, String>> itor2 = sets.iterator();
		while (itor2.hasNext()) {
			Entry<String, String> temp = itor2.next();
			System.out.print(temp.getKey() + ":" + temp.getValue() + "  ");
		}
		System.out.println();
		System.out.println("--------------------");

		// TreeMap会按键排序，如果键是整形，则会从小到大；如果键是String,则按Ascall码值排序
		Map<String, String> countries1 = new TreeMap<String, String>();
		countries1.put("jp", "日本");
		countries1.put("ch", "中国");
		countries1.put("uk", "英国");
		countries1.put("fr", "法国");
		Set<String> keys1 = countries1.keySet();
		Iterator<String> itor1 = keys1.iterator();
		while (itor1.hasNext()) {
			String key = itor1.next();
			System.out.print(key + ":" + countries1.get(key) + "  ");
		}
		System.out.println();
		System.out.print(countries.containsKey("cn") + "  ");
		System.out.println(countries.containsValue("中国"));
		System.out.println("***************练习一**************************");
		// 使用TreeMap<Integer, Student>，先初始化对象，再遍历打印输出对象
		Student[] stus = new Student[4];
		// key是学号,value是完整的该学生的对象
		Map<Integer, Student> stuList = new TreeMap<Integer, Student>();
		for (int i = 0; i < stus.length; i++) {
			// 生成随机数的类
			Random random = new Random();
			// 生成一个1000-9999之间的随机数,nextInt(9000)生成一个0-8999之间的数
			int stuNo = random.nextInt(9000) + 1000;
			stus[i] = new Student("小明" + (i + 1), 18 + i, stuNo);
			// 将4个student对象方法Map中
			stuList.put(stuNo, stus[i]);
		}
		Set<Integer> stuKeys = stuList.keySet();
		Iterator<Integer> ator2 = stuKeys.iterator();
		while (ator2.hasNext()) {
			int key = ator2.next();
			// 值是Student对象类型
			Student stu = stuList.get(key);
			// 调用student对象中的toString方法打印输出
			System.out.println(stu.toString());
		}
		System.out.println("***************练习二**************************");
		Map<String, Penguin> penList = new HashMap<String, Penguin>();
		penList.put("001", new Penguin("fdsaf", "男"));
		penList.put("002", new Penguin("rrtsa", "女"));
		penList.put("003", new Penguin("terte", "女"));
		penList.put("004", new Penguin("dgsdf", "男"));
		Set<String> penKeys = penList.keySet();
		for (String penKey : penKeys) {
			System.out.print(penList.get(penKey).getName() + "-"
					+ penList.get(penKey).getSex() + "  ");
		}
		System.out.println();
		System.out.println(penList.containsKey("005") ? "找到啦" : "没找到");
		System.out.println("-------------------------");
		// 调用自己封装的方法
		MapDemo md = new MapDemo();
		System.out.println(md.findByKey(penList, "002").getName());

	}

	/**
	 * 根据key查找Map<String, Penguin>中的企鹅对象
	 * 
	 * @param penList
	 *            map集合
	 * @param str
	 *            key值
	 * @return 查到返回一个Penguin对象,否则返回null
	 */
	public Penguin findByKey(Map<String, Penguin> penList, String str) {
		Set<String> penKeys = penList.keySet();
		boolean isExit = false;
		for (String key : penKeys) {
			if (str.equals(key)) {
				isExit = true;
				break;
			}
		}
		if (isExit) {
			System.out.println("该企鹅存在!");
			return penList.get(str);
		} else {
			System.out.println("该企鹅不存在!");
			return null;
		}
	}

}
