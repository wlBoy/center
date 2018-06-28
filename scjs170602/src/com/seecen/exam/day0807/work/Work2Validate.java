package com.seecen.exam.day0807.work;

import java.util.Scanner;

/**
 * 验证会员生日和密码
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月7日
 */
public class Work2Validate {
	/**
	 * 验证密码
	 * 
	 * @param pwd
	 *            密码
	 * @return 符合条件返回true，否则返回false
	 */
	public boolean checkPwd(String pwd) {
		//密码长度为6~10位
		if (pwd.length() < 6 || pwd.length() > 10) {
			return false;
		}
		return true;
	}

	/**
	 * 验证生日
	 * 
	 * @param birth
	 *            生日
	 * @return 符合条件返回true，否则返回false
	 */
	public boolean checkBirth(String birth) {
		String[] strs = birth.split("/");
		String preStr = strs[0];
		String nextStr = strs[1];
		// 前后面长度必须为2
		if (preStr.length() != 2 || nextStr.length() != 2) {
			return false;
		}
		// 前后面必须为纯数字(0~9)
		for (int i = 0; i < preStr.length(); i++) {
			if (!(preStr.charAt(i) >= '0' && preStr.charAt(i) <= '9')) {
				return false;
			}
			if (!(nextStr.charAt(i) >= '0' && nextStr.charAt(i) <= '9')) {
				return false;
			}
		}
		// 转换为数字，判断其范围
		int preNum = Integer.parseInt(preStr);
		int nextNum = Integer.parseInt(nextStr);
		if (!(preNum >= 1 && preNum <= 12)) {
			return false;
		}
		if (!(nextNum >= 1 && nextNum <= 31)) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Work2Validate wv = new Work2Validate();
		while (true) {
			boolean istrue1 = false;
			boolean istrue2 = false;

			System.out.print("请输入会员生日<月/日>:");
			String birthInput = sc.next();
			if (wv.checkBirth(birthInput)) {
				System.out.println("生日格式正确,该会员的生日是:" + birthInput);
				istrue1 = true;
			} else {
				System.out.println("您输入的生日格式错误!");
			}

			System.out.print("请输入会员密码<6~10位>:");
			String pwdInput = sc.next();
			if (wv.checkPwd(pwdInput)) {
				System.out.println("密码格式正确,密码是:" + pwdInput);
				istrue2 = true;
			} else {
				System.out.println("您输入的密码错误!");
			}
			// 当生日和密码同时通过才跳出循环
			if (istrue1 && istrue2)
				break;
		}
		sc.close();
	}

}
