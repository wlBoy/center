package com.seecen.exam.day0811.work;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定两个一维字符串数组a和b，要求除去a中包含b中元素的项，得到数组c。说明：a和b中都可能存在为null的元素。
 * ArrayList版本的
 * @scjs170602
 * @author 【万磊】
 * @2017年8月11日
 */
public class Work2 {

	public static void main(String[] args) {
		String[] a = new String[] { "a", "b", "c", null, null, "d", "f", "e",
				"f" };
		String[] b = new String[] { "a", "c", "d" };
		Work2 w = new Work2();
		w.getArray(a, b);
	}

	/**
	 * 除去a中包含b中元素的项，得到数组c
	 * 
	 * @param a
	 *            数组a
	 * @param b
	 *            数组b
	 */
	public void getArray(String[] a, String[] b) {
		List<String> list = new ArrayList<String>();
		// 先把数组a中的元素都放在链表list中
		for (int i = 0; i < a.length; i++) {
			list.add(a[i]);
		}
		// 去除数组b中的元素与链表list中元素相同的部分
		for (int i = 0; i < b.length; i++) {
			if (b[i] == null) {
				// 当b数组的元素等于Null的时候，遍历a数组,将a数组里面的null移出
				for (int j = 0; j < list.size(); j++) {
					if (a[j] == null) {
						list.remove(a[j]);
					}
				}
			} else {
				// 当b数组里的元素不等于Null的时候，遍历a数组,将a数组里面的等于b[i]移出
				for (int j = 0; j < list.size(); j++) {
					if (b[i].equals(a[j])) {
						list.remove(a[j]);
					}
				}
			}
		}
		showArray(list);
	}

	/**
	 * 展示结果数组的方法
	 * 
	 * @param arr
	 */
	public void showArray(List<String> c) {
		System.out.println("数组c中的元素展示如下：");
		for (String string : c) {
			System.out.print(string + " ");
		}
	}
}
