package com.seecen.exam.day0802.work;

/**
 * 创建一个计算器类，完成两个数的+，-，*，/,%运算
 * @author Administrator
 */
public class Calculator {
	double num1;
	double num2;

	public String calculate(String operator) {
		if (operator.equals("+")) {
			return "加法结果为:" + (num1 + num2);
		} else if (operator.equals("-")) {
			if (num1 > num2) {
				return "减法结果为:" + (num1 - num2);
			} else {
				return "减法结果为:" + (num2 - num1);
			}
		} else if (operator.equals("*")) {
			return "乘法结果为:" + num1 * num2;
		} else if (operator.equals("/")) {
			if (num2 != 0) {
				return "除法结果为:" + num1 / num2;
			} else {
				return "除法结果为:" + 0;
			}
		} else if(operator.equals("%")){
			return "求余结果为:" + num1 % num2;
		} else{
			return "请输入正确的操作符！";
		}

	}
}
