package com.seecen.exam.day0814.work;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.seecen.exam.day0814.MyRuntimeException;

/**
 * 测试字符串数组类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月14日
 */
public class StringListDemo {
	private static Logger log = Logger.getLogger(StringListDemo.class);

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		StringList sl = new StringList();
		// 数组最小长度,不够会进行扩容处理
		int minSize = 1;
		String[] strs = new String[minSize];
		String answer = null;
		try {
			do {
				sl.printMenu();
				System.out.print("请选择数字:");
				int num = sc.nextInt();
				switch (num) {
				case 1:
					System.out.print("请输入你要添加的字符串:");
					String addInput = sc.next();
					strs = sl.addStringToList(strs, addInput);
					System.out.println("字符串数组的内容如下:");
					sl.showList(strs);
					break;
				case 2:
					System.out.print("请输入你要删除的索引位置:");
					int indexInput = sc.nextInt();
					strs = sl.removeStringInList(strs, indexInput);
					System.out.println("字符串数组的内容如下:");
					sl.showList(strs);
					break;
				case 3:
					System.out.print("请输入你要查找的字符串:");
					String strInput = sc.next();
					int index = sl.findStringByIndex(strs, strInput);
					if (index == -1) {
						System.out.println("您要查找的字符串不存在!");
					} else {
						System.out.println("字符串\"" + strInput + "\"在数组里的第"
								+ (index + 1) + "个位置上!");
					}
					break;
				case 4:
					System.out.println("字符串数组的内容如下:");
					sl.showList(strs);
					break;
				default:
					throw new MyRuntimeException("请输入正确的菜单选项!");
				}
				System.out.println("是否继续(y/n):");
				answer = sc.next();
			} while (answer.equalsIgnoreCase("y"));
		} catch (ArrayIndexOutOfBoundsException e) {
			log.error(e.getMessage());
		} catch (NullPointerException e) {
			log.error(e.getMessage());
		} catch (MyRuntimeException e) {
			log.error(e.getMessage());
		} catch (Exception e) {
			log.error("请输入数字!");
		} finally {
			System.out.println("谢谢使用,正在退出!");
			System.exit(1);
			sc.close();
		}
	}
}
