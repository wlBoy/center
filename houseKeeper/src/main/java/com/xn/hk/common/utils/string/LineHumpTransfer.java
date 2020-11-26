package com.xn.hk.common.utils.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @ClassName: LineHumpTransfer
 * @Package: com.xn.hk.common.utils.string
 * @Description: 驼峰下划线互转工具类
 * @Author: wanlei
 * @Date: 2020年11月26日 上午11:44:23
 */
public class LineHumpTransfer {
	private static Pattern linePattern = Pattern.compile("_(\\w)");

	private static Pattern humpPattern = Pattern.compile("[A-Z]");

	/**
	 * @description: 下划线转驼峰
	 * @param: str
	 * @return: java.lang.String
	 * @author zhangWei
	 * @date: 2020/11/25 15:25
	 */
	public static String lineToHump(String str) {
		str = str.toLowerCase();
		Matcher matcher = linePattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * @description: 驼峰转下划线
	 * @param: str
	 * @return: java.lang.String
	 * @author zhangWei
	 * @date: 2020/11/25 15:24
	 */
	public static String humpToLine(String str) {
		Matcher matcher = humpPattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String lineToHump = lineToHump("f_parent_no_leader");
		System.out.println(lineToHump);// fParentNoLeader
		System.out.println(humpToLine(lineToHump));// f_parent_no_leader
	}
}
