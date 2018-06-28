package com.seecen.exam.day0815.work;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.junit.Test;

/**
 * 测试学生类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月15日
 */
public class StudentDemo {
	@Test
	public void testStudent(){
		Scanner sc = new Scanner(System.in);
		List<Student> stuList = new ArrayList<Student>();
		Map<String, Student> stuMap = new HashMap<String, Student>();
		String answer = null;
		do {
			System.out.println("**********************************");
			System.out.println("\t1.添加学员");
			System.out.println("\t2.查找学员");
			System.out.println("**********************************");
			System.out.print("请选择,输入数字:");
			int num = sc.nextInt();
			if (num == 1) {
				// 添加学员
				System.out.print("请输入学员姓名:");
				String nameInput = sc.next();
				System.out.print("请输入学员年龄:");
				int ageInput = sc.nextInt();
				stuList.add(new Student(nameInput, ageInput));
				stuMap.put(nameInput, new Student(nameInput, ageInput));
				System.out.println("学员列表如下:");
				System.out.println("序号\t姓名\t年龄");
				for (int i = 0; i < stuList.size(); i++) {
					System.out.println((i + 1) + "\t"
							+ stuList.get(i).getName() + "\t"
							+ stuList.get(i).getAge());
				}
			} else if (num == 2) {
				// 查找学员
				StudentDemo sd = new StudentDemo();
				System.out.print("请输入你要查找的学员名字:");
				String findName = sc.next();
				Student stu = sd.findStuByName(stuMap, findName);
				if (stu == null) {
					System.out.println("对不起,您要查找的学员不存在!");
				} else {
					System.out.println("找到啦,该学员信息如下:");
					System.out.println("姓名\t年龄");
					System.out.println(stu.getName() + "\t" + stu.getAge());
				}
			} else {
				System.out.println("请输入正确的菜单选项!");
			}
			System.out.print("是否继续(y/n):");
			answer = sc.next();
		} while (answer.equals("y"));
		System.out.println("谢谢使用!");
		sc.close();
	}
	/*public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		List<Student> stuList = new ArrayList<Student>();
		Map<String, Student> stuMap = new HashMap<String, Student>();
		String answer = null;
		do {
			System.out.println("**********************************");
			System.out.println("\t1.添加学员");
			System.out.println("\t2.查找学员");
			System.out.println("**********************************");
			System.out.print("请选择,输入数字:");
			int num = sc.nextInt();
			if (num == 1) {
				// 添加学员
				System.out.print("请输入学员姓名:");
				String nameInput = sc.next();
				System.out.print("请输入学员年龄:");
				int ageInput = sc.nextInt();
				stuList.add(new Student(nameInput, ageInput));
				stuMap.put(nameInput, new Student(nameInput, ageInput));
				System.out.println("学员列表如下:");
				System.out.println("序号\t姓名\t年龄");
				for (int i = 0; i < stuList.size(); i++) {
					System.out.println((i + 1) + "\t"
							+ stuList.get(i).getName() + "\t"
							+ stuList.get(i).getAge());
				}
			} else if (num == 2) {
				// 查找学员
				StudentDemo sd = new StudentDemo();
				System.out.print("请输入你要查找的学员名字:");
				String findName = sc.next();
				Student stu = sd.findStuByName(stuMap, findName);
				if (stu == null) {
					System.out.println("对不起,您要查找的学员不存在!");
				} else {
					System.out.println("找到啦,该学员信息如下:");
					System.out.println("姓名\t年龄");
					System.out.println(stu.getName() + "\t" + stu.getAge());
				}
			} else {
				System.out.println("请输入正确的菜单选项!");
			}
			System.out.print("是否继续(y/n):");
			answer = sc.next();
		} while (answer.equals("y"));
		System.out.println("谢谢使用!");
		sc.close();
	}*/

	/**
	 * 根据名字查找学员
	 * 
	 * @param stuMap
	 *            Map<String, Student>学员Map集合
	 * @param name
	 *            要查找的名字
	 * @return 查到返回该学员对象,否则返回NUll
	 */
	public Student findStuByName(Map<String, Student> stuMap, String name) {
		Set<String> stuKeys = stuMap.keySet();
		for (String string : stuKeys) {
			if (name.equals(string)) {
				return stuMap.get(name);
			}
		}
		return null;
	}
}
