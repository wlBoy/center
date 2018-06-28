package com.seecen.exam.day0814;

import java.util.Scanner;

import org.apache.log4j.Logger;

/**
 * 测试异常类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月14日
 */
public class TestException {
	// 得到日志对象
	private static Logger log = Logger.getLogger(TestException.class);

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		try {
			System.out.print("请输入被除数:");
			int num1 = sc.nextInt();
			System.out.print("请输入除数:");
			int num2 = sc.nextInt();
			if (num2 == 0) {
				throw new MyRuntimeException("除数不能为0!");
			} else {
				log.info("结果为:" + num1 / num2);
			}
		} catch (MyRuntimeException e) {
			log.warn(e.getMessage());
		} catch (Exception e) {
			log.error("你输入的不是整数！");
		} finally {
			sc.close();
		}
	}

}
