package com.seecen.exam.day0803;

import java.util.Scanner;
public class StudentManageSystemDemo {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		StudentManageSystem stu = new StudentManageSystem();

		String isContinue = null;
		do {
			stu.printMenu();
			System.out.print("请选择,输入数字:");
			int menu = sc.nextInt();
			switch (menu) {
			case 1:
				/**
				 * 添加学员
				 */
				System.out.println("【学员管理系统>添加学员】");
				String answer = null;
				do {
					System.out.print("请输入学员的名字:");
					String nameInput = sc.next();
					if (stu.add(nameInput)) {
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
				/**
				 * 删除学员
				 */
				System.out.println("【学员管理系统>删除学员】");
				while (true) {
					System.out.print("请输入你要删除的学员姓名:");
					String delName = sc.next();
					if (stu.deleteName(delName)) {
						System.out.println("找到并删除成功!");
						System.out.println("所有学员的信息如下:");
						stu.showAll();
						break;
					} else {
						System.out.println("您要删除的学员不存在!");
					}
				}
				break;
			case 3:
				/**
				 * 修改学员
				 */
				System.out.println("【学员管理系统>修改学员】");
				while (true) {
					System.out.print("请输入你要修改的学员姓名:");
					String oldName = sc.next();
					System.out.print("请输入新的学员姓名:");
					String newName = sc.next();
					if (stu.updateName(oldName, newName)) {
						// 找到了并修改成功
						System.out.println("找到并修改成功!");
						System.out.println("所有学员的信息如下:");
						stu.showAll();
						break;
					} else {
						System.out.println("您要修改的学员不存在!");
					}
				}
				break;
			case 4:
				/**
				 * 全局查找学员
				 */
				System.out.println("【学员管理系统>全局查找学员】");
				while (true) {
					System.out.print("请输入要查找的学员姓名:");
					String findName = sc.next();
					if (stu.findByName(findName) == -1) {
						System.out.println("你输入的名字没找到!");
					} else {
						System.out.println("找到啦,姓名为:" + findName + "的,在数组的第"
								+ (stu.findByName(findName) + 1) + "个位置上!");
						break;
					}
				}
				break;
			case 5:
				/**
				 * 索引查找学员
				 */
				System.out.println("【学员管理系统>索引查找学员】");
				while (true) {
					System.out.print("请输入起始位置:");
					int startNum = sc.nextInt();
					System.out.print("请输入末尾位置:");
					int endNum = sc.nextInt();
					System.out.print("请输入该区间要查找的学员姓名:");
					String findName = sc.next();
					if (stu.findByIndex(startNum, endNum, findName) == -1) {
						System.out.println("你输入的名字没找到!");
					} else {
						System.out.println("找到啦,姓名为:" + findName + "的,在数组的第"
								+ (stu.findByName(findName) + 1) + "个位置上!");
						break;
					}
				}
				break;
			case 6:
				/**
				 * 显示所有学员
				 */
				System.out.println("【学员管理系统>显示所有学员】");
				System.out.println("所有学员的信息如下:");
				stu.showAll();
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
