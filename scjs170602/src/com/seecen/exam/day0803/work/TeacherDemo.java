package com.seecen.exam.day0803.work;

import java.util.Scanner;
/**
 * 测试老师管理系统类
 * @scjs170602
 * @author 【万磊】
 * @2017年8月3日
 */
public class TeacherDemo {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Teacher tea = new Teacher();

		String isContinue = null;
		do {
			tea.printMenu();
			System.out.print("请选择,输入数字:");
			int menu = sc.nextInt();
			switch (menu) {
			case 1:
				 //添加老师
				System.out.println("【老师管理系统>添加老师】");
				String answer = null;
				do {
					System.out.print("请输入老师的名字:");
					String nameInput = sc.next();
					if (tea.add(nameInput)) {
						System.out.println("添加成功！");
					} else {
						System.out.println("人数已满,添加失败！");
						break;
					}
					System.out.print("是否继续输入(y/n):");
					answer = sc.next();
				} while (answer.equals("y"));
				break;
			case 2:
				 //删除老师
				System.out.println("【老师管理系统>删除老师】");
				while (true) {
					System.out.print("请输入你要删除的老师姓名:");
					String delName = sc.next();
					if (tea.deleteName(delName)) {
						System.out.println("找到并删除成功!");
						System.out.println("所有老师的信息如下:");
						tea.showAll();
						break;
					} else {
						System.out.println("您要删除的老师不存在!");
					}
				}
				break;
			case 3:
				 //修改老师
				System.out.println("【老师管理系统>修改老师】");
				while (true) {
					System.out.print("请输入你要修改的老师姓名:");
					String oldName = sc.next();
					System.out.print("请输入新的老师姓名:");
					String newName = sc.next();
					if (tea.updateName(oldName, newName)) {
						// 找到了并修改成功
						System.out.println("找到并修改成功!");
						System.out.println("所有老师的信息如下:");
						tea.showAll();
						break;
					} else {
						System.out.println("您要修改的老师不存在!");
					}
				}
				break;
			case 4:
				 //全局查找老师
				System.out.println("【老师管理系统>全局查找老师】");
				while (true) {
					System.out.print("请输入要查找的老师姓名:");
					String findName = sc.next();
					if (tea.findByName(findName) == -1) {
						System.out.println("你输入的名字没找到!");
					} else {
						System.out.println("找到啦,姓名为:" + findName + "的,在数组的第"
								+ (tea.findByName(findName) + 1) + "个位置上!");
						break;
					}
				}
				break;
			case 5:
				 //索引查找老师
				System.out.println("【老师管理系统>索引查找老师】");
				while (true) {
					System.out.print("请输入起始位置:");
					int startNum = sc.nextInt();
					System.out.print("请输入末尾位置:");
					int endNum = sc.nextInt();
					System.out.print("请输入该区间要查找的老师姓名:");
					String findName = sc.next();
					if (tea.findByIndex(startNum, endNum, findName) == -1) {
						System.out.println("你输入的名字没找到!");
					} else {
						System.out.println("找到啦,姓名为:" + findName + "的,在数组的第"
								+ (tea.findByName(findName) + 1) + "个位置上!");
						break;
					}
				}
				break;
			case 6:
				 //显示所有老师信息
				System.out.println("【老师管理系统>显示所有老师】");
				System.out.println("所有老师的信息如下:");
				tea.showAll();
				break;
			default:
				System.out.println("请输入正确的菜单选项!");
				break;
			}
			System.out.print("是否继续回到菜单(y/n):");
			isContinue = sc.next();
		} while (isContinue.equals("y"));
		System.out.println("系统退出,谢谢使用！");
		System.exit(1);
		sc.close();
	}
}
