package com.seecen.exam.day0811.work;

/**
 * 编程实现输出1+2-3+4+5-6……-99+100的结果(1684)
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月11日
 */
public class Work1 {
	public static void main(String[] args) {
		int sum = 0;
		int j = 0;
		for (int i = 1; i <= 100; i++) {
//			当是3的倍数是,将j赋值为-i,否则j赋值为i
			if (i % 3 == 0) {
				j = -i;
			} else {
				j = i;
			}
			sum += j;
		}
		System.out.println("结果为:" + sum);
	}

}
