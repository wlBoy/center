package com.seecen.exam.day0803.work;

/**
 * 创建一个计算器类，完成两个数的(+,-,*,/,%)运算
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月3日
 */
public class Calculator {
	double num1;
	double num2;

	/**
	 * 传入操作符进行算术运算
	 * 
	 * @param operator
	 *            操作符
	 * @return 运算结果字符串
	 */
	public String calculate(String operator) {
		if (operator.equals("+")) {
			//匹配加法
			return "加法结果为:" + (num1 + num2);
		} else if (operator.equals("-")) {
			//匹配减法
			if (num1 > num2) {
				return "减法结果为:" + (num1 - num2);
			} else {
				return "减法结果为:" + (num2 - num1);
			}
		} else if (operator.equals("*")) {
			//匹配乘法
			return "乘法结果为:" + num1 * num2;
		} else if (operator.equals("/")) {
			//匹配除法
			if (num2 != 0) {
				return "除法结果为:" + num1 / num2;
			} else {
				return "除法结果为:" + 0;
			}
		} else if (operator.equals("%")) {
			//匹配求余
			return "求余结果为:" + num1 % num2;
		} else {
			return "请输入正确的操作符！";
		}
	}
}
