package com.seecen.exam.day0729.work;

import java.util.Scanner;
/**
 * 输入5句话并将其逆序输出
 * @author Administrator
 */
public class Work2 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入5句话");
		String[] Word = new String[5];
		for (int i = 0; i < Word.length; i++) {
			System.out.print("第"+(i+1)+"句话:");
			Word[i] = sc.next();
		}
		System.out.println("逆序输出5句话为:");
		for (int i = Word.length - 1; i >= 0; i--) {
			System.out.println(Word[i]);
		}
		sc.close();
	}
}
