package com.seecen.exam.day0804;

import java.util.Scanner;

public class StudentBizDemo {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// 申明student对象数组，并不是实例化了5个student对象，仅仅是数组而已
		Student[] stus = new Student[5];
		for (int i = 0; i < stus.length; i++) {
			System.out.print("请输入第" + (i + 1) + "名同学的身高(cm):");
			// 先将对象实例化放到数组里面，只有先将对象new出来，才能对其属性进行操作
			stus[i] = new Student();
			stus[i].height = sc.nextFloat();
		}
		StudentBiz sb = new StudentBiz();
		System.out.println("这5名学生的平均身高为:" + sb.getAvgHeight(stus) + "cm");
		sc.close();
	}

}
