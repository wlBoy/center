package com.seecen.exam.day0804.work;

/**
 * 学生业务类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月4日
 */
public class StudentBiz {
	/**
	 * 查找学生对象数组里最大身高的同学
	 * 
	 * @param stus
	 *            学生对象数组
	 * @return 找到了就返回最大身高同学在数组的索引
	 */
	public int findMaxHeight(Student[] stus) {
		// 先将最大值设为第一个学生的身高
		float max = stus[0].getHeight();
		int index = 0;
		//循环应从第二个学生开始
		for (int i = 1; i < stus.length; i++) {
			if (stus[i].getHeight() > max) {
				max = stus[i].getHeight();
				//返回最高身高同学的索引
				index = i;
			}
		}
		return index;
	}

	/**
	 * 根据学生名查找
	 * 
	 * @param stus
	 *            学生对象数组
	 * @param name
	 *            学生名
	 * @return 找到返回学生所在的索引值，否则返回-1
	 */
	public int findByName(Student[] stus, String name) {
		for (int i = 0; i < stus.length; i++) {
			if (name.equals(stus[i].getName())) {
				return i;
			}
		}
		return -1;
	}
}
