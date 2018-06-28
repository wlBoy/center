package com.seecen.exam.day0814.work;

/**
 * 字符串数组类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月14日
 */
public class StringList {
	int index = 0;// 字符串的个数

	/**
	 * 向数组中添加字符串
	 * 
	 * @param strs
	 *            字符串数组
	 * @param str
	 *            要添加的字符串
	 * @return 新的字符串数组
	 */
	public String[] addStringToList(String[] strs, String str) {
		if (index == strs.length - 1) {
			// 数组扩容2倍并拷贝数据
			String[] newStrs = new String[strs.length * 2];
			System.arraycopy(strs, 0, newStrs, 0, strs.length);
			newStrs[index++] = str;
			return newStrs;
		} else {
			// 添加字符串到数据
			strs[index++] = str;
			return strs;
		}
	}

	/**
	 * 从字符串数组中移除字符串
	 * 
	 * @param strs
	 *            字符串数组
	 * @param index
	 *            要移除的索引
	 * @return 移除后的新数组
	 * @throws ArrayIndexOutOfBoundsException
	 *             当索引越界时发生的数组越界异常
	 */
	public String[] removeStringInList(String[] strs, int index)
			throws ArrayIndexOutOfBoundsException {
		if (index < 0 || index >= getListSize(strs)) {
			throw new ArrayIndexOutOfBoundsException("你要删除的索引不存在！");
		} else {
			// 将索引所在的位置内容赋值为null
			for (int i = 0; i < strs.length; i++) {
				strs[index] = null;
			}
			// 将从index以后的内容全部往前移一个位置
			for (int i = index; i < strs.length - 1; i++) {
				strs[i] = strs[i + 1];
			}
			return strs;
		}
	}
	/**
	 * 从字符串数组中移除字符串
	 * @param strs
	 * @param str
	 * @return
	 */
	public String[] removeStringInList(String[] strs, String str) {
		if (str == null) {
			throw new NullPointerException("您要删除的字符串不能为空!");
		} else {
			for (int i = 0; i < getListSize(strs); i++) {
				if (str.equals(strs[i])) {
					strs[i] = null;
				}
				for (int j = i; j < strs.length - 1; j++) {
					strs[j] = strs[j + 1];
				}
			}
			return strs;
		}
	}

	/**
	 * 根据字符串查找
	 * 
	 * @param strs
	 *            字符串数组
	 * @param str
	 *            要查找的字符串
	 * @return 查到了返回其索引,否则返回-1
	 * @throws NullPointerException
	 *             要查找的字符串为空时抛出空指针异常
	 */
	public int findStringByIndex(String[] strs, String str)
			throws NullPointerException {
		if (str == null) {
			throw new NullPointerException("查找的字符串不能为空!");
		} else {
			for (int i = 0; i < strs.length; i++) {
				if (str.equals(strs[i])) {
					return i;
				}
			}
			return -1;
		}
	}

	/**
	 * 返回数组内容的大小(字符串的个数)
	 * 
	 * @param strs
	 *            字符串数组
	 * @return 字符串的个数的个数
	 */
	public int getListSize(String[] strs) {
		int count = 0;
		for (int i = 0; i < strs.length; i++) {
			if (strs[i] != null) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 根据索引查找字符串数组的内容
	 * 
	 * @param strs
	 *            字符串数组
	 * @param index
	 *            索引
	 * @return 查到返回该索引的内容,否则抛出数组越界异常
	 * @throws ArrayIndexOutOfBoundsException
	 *             当索引越界时发生的数组越界异常
	 */
	public String getByIndex(String[] strs, int index)
			throws ArrayIndexOutOfBoundsException {
		if (index < 0 || index >= getListSize(strs)) {
			throw new ArrayIndexOutOfBoundsException("你查找的索引数组越界！");
		} else {
			return strs[index];
		}
	}

	/**
	 * 显示数组里的所有内容
	 * 
	 * @param strs
	 *            字符串数组
	 */
	public void showList(String[] strs) {
		for (int i = 0; i < getListSize(strs); i++) {
			System.out.print(strs[i] + " ");
		}
		System.out.println();
	}

	/**
	 * 打印菜单
	 */
	public void printMenu() {
		System.out.println("*****欢迎来到字符串数组的操作界面*****");
		System.out.println("\t1.添加字符串");
		System.out.println("\t2.删除字符串");
		System.out.println("\t3.查找字符串");
		System.out.println("\t4.显示所有字符串");
		System.out.println("*******************************");
	}
}
