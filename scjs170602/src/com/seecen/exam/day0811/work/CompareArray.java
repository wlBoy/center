package com.seecen.exam.day0811.work;
/**
 * 给定两个一维字符串数组a和b，要求除去a中包含b中元素的项，得到数组c。说明：a和b中都可能存在为null的元素。
 * 普通版
 * @scjs170602
 * @author 【万磊】
 * @2017年8月11日
 */
public class CompareArray {
	public static void main(String[] args) {
		String a[] = new String[] { "a", "b", null, "c", null, "d", "f", "f" };
		String b[] = new String[] { "a", "c", "d", null };
		String[] c = new String[a.length];
		CompareArray ca = new CompareArray();
		String[] d = ca.compareArrays(a, b, c);
		ca.show(d);
	}
	/**
	 * 展示结果数组
	 * @param strs 结果数组
	 */
	public void show(String[] strs){
		System.out.print("结果为:");
		for (String string : strs) {
			System.out.print(string + " ");
		}
	}
	/**
	 * 除去a数组中包含b中元素的项，返回一个新数组
	 * @param a a数组
	 * @param b b数组
	 * @param c 中间临时数组
	 * @return 新数组
	 */
	public String[] compareArrays(String[] a, String[] b, String[] c) {
		// 不相等的个数和索引
		int index = 0;
		for (int i = 0; i < a.length; i++) {
			boolean isTure = true;// 先假设true代表有不相等
			if (a[i] == null) {
				for (int j = 0; j < b.length; j++) {
					if (b[j] == null) {
						isTure = false;
						break;
					}
				}
			} else {
				for (int j = 0; j < b.length; j++) {
					if (a[i].equals(b[j])) {
						isTure = false;
						break;
					}
				}
			}
			if (isTure) {
				c[index] = a[i];
				index++;
			}
		}
		// 输出结果数组
		String d[] = new String[index];
		for (int i = 0; i < index; i++) {
			d[i] = c[i];
		}
		return d;
	}
}
