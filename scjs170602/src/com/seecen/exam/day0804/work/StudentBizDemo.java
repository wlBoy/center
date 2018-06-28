package com.seecen.exam.day0804.work;

import java.util.Scanner;

public class StudentBizDemo {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// 申明student对象数组
		Student[] stus = new Student[10];
		//遍历先给学生对象数组初始化
		for (int i = 0; i < stus.length; i++) {
			// 先将对象实例化放到数组里面，只有先将对象new出来，才能对其属性进行操作
			stus[i] = new Student();
			System.out.print("请输入第" + (i + 1) + "名同学的姓名:");
			stus[i].setName(sc.next());
			System.out.print("请输入第" + (i + 1) + "名同学的身高(cm):");
			stus[i].setHeight(sc.nextFloat());
		}
		StudentBiz sb = new StudentBiz();
		//查找最大身高
		int index1 = sb.findMaxHeight(stus);
		System.out.println("该班第" + (index1 + 1)+ "个的学生身高最高,为" + stus[index1].getHeight());
		//根据姓名查找
		System.out.print("请输入你要查找的姓名:");
		String findName = sc.next();
		int index2 = sb.findByName(stus, findName);
		if(index2==-1){
			System.out.println("您输入的姓名不存在!");
		}else{
			System.out.println("您输入的姓名:"+findName+",找到啦,其位置在数组的第" + (index2+1) + "个位置上!");
		}
		sc.close();
	}

}
