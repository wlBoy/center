package com.seecen.exam.day0728.work;
/**
 * 从100每次递减5输出直至5
 * @author Administrator
 */
public class Work5 {
	public static void main(String[] args) {
		for (int i = 100 ;i >= 5 ; i -= 5) {
			System.out.print(i+"-");
		}
	}
}
