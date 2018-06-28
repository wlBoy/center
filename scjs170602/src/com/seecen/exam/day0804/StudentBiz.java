package com.seecen.exam.day0804;

public class StudentBiz {
	/**
	 * 计算学生的平均高度
	 * 
	 * @param stus
	 *            student对象数组
	 * @return 平均高度
	 */
	public float getAvgHeight(Student[] stus) {
		float sum = 0;
		//正常身高学生人数(为0不计数)
		int count = 0;
		for (int i = 0 ; i < stus.length; i++) {
			if (stus[i].height != 0) {
				sum += stus[i].height;
				count++;
			}
		}
		return sum / count;
	}
}
