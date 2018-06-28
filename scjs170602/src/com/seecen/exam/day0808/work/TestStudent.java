package com.seecen.exam.day0808.work;

import java.util.Scanner;

/**
 * 测试student实体类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月8日
 */
public class TestStudent {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Student s = new Student();
		System.out.print("请录入学生名字:");
		String nameInput = sc.next();
		s.setName(nameInput);
		System.out.print("请录入学生年龄:");
		int ageInput = sc.nextInt();
		s.setAge(ageInput);
		System.out.print("请录入学生性别:");
		String sexInput = sc.next();
		s.setSex(sexInput);
		s.setPro(Student.MAJOR_COMPUTER);
		System.out.println("输入的信息如下:");
		s.show();
		System.out.println("---------------------------------------------");
		Student s1 = new Student("张三", 20, "男", Student.MAJOR_ENGINEER);
		Student s2 = new Student("小翠", 18, "女", Student.MAJOR_SALES);
		System.out.println("测试show方法重载的信息如下:");
		s1.show(3);
		s2.show(true);
		sc.close();
	}

}
