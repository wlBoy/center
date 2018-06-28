package com.seecen.exam.day0814;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.log4j.Logger;
/**
 * 测试自定义异常
 * @scjs170602
 * @author 【万磊】
 * @2017年8月14日
 */
public class TestMyException {
	private static Logger log = Logger.getLogger(TestMyException.class);

	@SuppressWarnings("all")
	public static void main(String[] args) {
		//建立log4j日志对象
		TestMyException te = new TestMyException();
//		te.test1("sffs");
		File file = new File("D:/a.txt");
		try {
			te.test2(file);
			InputStream in = new FileInputStream(file);
		} catch (Exception e) {
			//将异常信息写到日志文件中
			log.error(e);
		} finally{
			System.out.println("end");
		}
		System.out.println("------------------------------");
		Scanner sc = new Scanner(System.in);
		try {
			System.out.print("请输入课程代号(1~3之间的数字):");
			int num = sc.nextInt();
			switch (num) {
			case 1:
				System.out.println("C#编程");
				break;
			case 2:
				System.out.println("Java编程");
				break;
			case 3:
				System.out.println("MySQL编程");
				break;
			default:
				throw new InputMismatchException("你输入的数字不匹配!");
			}
		} catch (Exception e) {
			log.error(e);
		} finally{
			System.out.println("欢迎提出意见!");
		}
		sc.close();
	}

	public void test1(String str) {
		if (str == "") {
			throw new NullPointerException("你输入的字符串为空!");
		} else if (!str.equals("str")) {
			throw new MyRuntimeException("你输入的字符串不相等！");
		} else {
			System.out.println("fsdsfs");
		}
	}

	public void test2(File file) throws Exception {
		if (!file.exists()) {
			throw new FileNotFoundException("文件未找到异常!");
		} else {
			if (!file.isFile()) {
				throw new MyCheckException("不是文件异常！");
			} else {
				System.out.println("sfdfs");
			}
		}
	}
}
