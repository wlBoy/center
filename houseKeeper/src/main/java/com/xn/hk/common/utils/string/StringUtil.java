package com.xn.hk.common.utils.string;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xn.hk.common.constant.Constant;
import com.xn.hk.common.utils.text.StrFormatter;

/**
 * @Title: StringUtil
 * @Package: com.xn.hk.common.utils
 * @Description: 字符串工具类
 * @Author: wanlei
 * @Date: 2018年1月8日 下午5:01:12
 */
public final class StringUtil {
	private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);
	/** 下划线 */
	private static final char SEPARATOR = '_';
	/**
	 * 所有字符和数字
	 */
	private static final String allNumAndChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	/**
	 * 数字字符数组
	 */
	private static final char[] digitArray = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	/**
	 * 16进制数
	 */
	private static final String hexString = "0123456789ABCDEF";

	/**
	 * 随机生成指定长度的纯数字字符串
	 * 
	 * @param length
	 *            指定长度
	 * @return 返回指定长度的纯数字字符串
	 */
	public static String randomDigit(int length) {
		char[] c = new char[length];
		Random random = new Random();
		for (int i = 0; i < length; i++)
			c[i] = digitArray[random.nextInt(digitArray.length)];
		return new String(c);
	}

	/**
	 * 下划线转驼峰命名
	 */
	public static String toUnderScoreCase(String str) {
		if (str == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		// 前置字符是否大写
		boolean preCharIsUpperCase = true;
		// 当前字符是否大写
		boolean curreCharIsUpperCase = true;
		// 下一字符是否大写
		boolean nexteCharIsUpperCase = true;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (i > 0) {
				preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
			} else {
				preCharIsUpperCase = false;
			}

			curreCharIsUpperCase = Character.isUpperCase(c);

			if (i < (str.length() - 1)) {
				nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
			}

			if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase) {
				sb.append(SEPARATOR);
			} else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase) {
				sb.append(SEPARATOR);
			}
			sb.append(Character.toLowerCase(c));
		}
		return sb.toString();
	}

	/**
	 * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。
	 * 例如：HELLO_WORLD->HelloWorld
	 * 
	 * @param name
	 *            转换前的下划线大写方式命名的字符串
	 * @return 转换后的驼峰式命名的字符串
	 */
	public static String convertToCamelCase(String name) {
		StringBuilder result = new StringBuilder();
		// 快速检查
		if (name == null || name.isEmpty()) {
			// 没必要转换
			return "";
		} else if (!name.contains("_")) {
			// 不含下划线，仅将首字母大写
			return name.substring(0, 1).toUpperCase() + name.substring(1);
		}
		// 用下划线将原始字符串分割
		String[] camels = name.split("_");
		for (String camel : camels) {
			// 跳过原始字符串中开头、结尾的下换线或双重下划线
			if (camel.isEmpty()) {
				continue;
			}
			// 首字母大写
			result.append(camel.substring(0, 1).toUpperCase());
			result.append(camel.substring(1).toLowerCase());
		}
		return result.toString();
	}

	/**
	 * 驼峰式命名法 例如：user_name->userName
	 */
	public static String toCamelCase(String s) {
		if (s == null) {
			return null;
		}
		s = s.toLowerCase();
		StringBuilder sb = new StringBuilder(s.length());
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c == SEPARATOR) {
				upperCase = true;
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 去空格
	 */
	public static String trim(String str) {
		return (str == null ? "" : str.trim());
	}

	/**
	 * 格式化文本, {} 表示占位符<br>
	 * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
	 * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
	 * 例：<br>
	 * 通常使用：format("this is {} for {}", "a", "b") -> this is a for b<br>
	 * 转义{}： format("this is \\{} for {}", "a", "b") -> this is \{} for a<br>
	 * 转义\： format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
	 * 
	 * @param template
	 *            文本模板，被替换的部分用 {} 表示
	 * @param params
	 *            参数值
	 * @return 格式化后的文本
	 */
	public static String format(String template, Object... params) {
		if (isEmpty(params) || isEmpty(template)) {
			return template;
		}
		return StrFormatter.format(template, params);
	}

	/**
	 * 是否包含字符串
	 * 
	 * @param str
	 *            验证字符串
	 * @param strs
	 *            字符串组
	 * @return 包含返回true
	 */
	public static boolean inStringIgnoreCase(String str, String... strs) {
		if (str != null && strs != null) {
			for (String s : strs) {
				if (str.equalsIgnoreCase(trim(s))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * * 判断一个对象数组是否为空
	 * 
	 * @param objects
	 *            要判断的对象数组
	 ** @return true：为空 false：非空
	 */
	public static boolean isEmpty(Object[] objects) {
		return isNull(objects) || (objects.length == 0);
	}

	/**
	 * * 判断一个对象是否为空
	 * 
	 * @param object
	 *            Object
	 * @return true：为空 false：非空
	 */
	public static boolean isNull(Object object) {
		return object == null;
	}

	/**
	 * 判断String集合中哪个是重复的
	 * 
	 * @param sourceList
	 * @return 返回重复的String，没有则返回null
	 */
	public static String isDuplicated(List<String> sourceList) {
		String result = null;
		if (sourceList.size() < 2) {
			return result;
		}
		for (String s : sourceList) {
			List<String> tempList = new ArrayList<String>();
			tempList.addAll(sourceList);
			tempList.remove(s);
			if (tempList.contains(s)) {
				return s;
			}
		}
		return result;
	}

	/**
	 * 生成指定长度的随机字符串
	 * 
	 * @param len
	 *            指定长度
	 * @return 返回指定长度的字符串
	 */
	public static String randomString(int len) {
		Random r = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < len; i++) {
			sb.append(allNumAndChar.charAt(r.nextInt(allNumAndChar.length())));
		}
		return sb.toString();
	}

	/**
	 * 用指定的字符替换一个字符串的指定位置，不改变原字符串的长度，例如:将15079077425换成150****7425
	 * 
	 * @param str
	 *            要替换的字符串
	 * @param c
	 *            替换字符
	 * @param start
	 *            起始位置，字符串索引
	 * @param end
	 *            结束位置，字符串索引
	 * @return 返回替换后的字符串
	 */
	public static String replaceStrWithChar(String str, char c, int start, int end) {
		StringBuilder sb = new StringBuilder(str);
		if (end <= start) {
			logger.error("结束索引应该大于开始索引!");
			return sb.toString();
		}
		char[] chars = new char[end - start];
		for (int i = 0; i < chars.length; i++) {
			chars[i] = c;
		}
		// 用替换字符构造新的替换字符串
		String newStr = new String(chars);
		sb.replace(start, end, newStr);
		return sb.toString();
	}

	/**
	 * 将字符串按照指定字符切割为list<String>
	 * 
	 * @param source
	 *            源字符串
	 * @param splitSymbol
	 *            指定字符
	 * @return 返回切割后的list<String>
	 */
	public static List<String> splitStringToList(String source, String splitSymbol) {
		List<String> list = new ArrayList<String>();
		if (!StringUtil.isEmpty(source)) {
			String[] ss = source.trim().split(splitSymbol);
			for (int i = 0; i < ss.length; i++) {
				list.add(ss[i]);
			}
		}
		return list;
	}

	/**
	 * 将list<String>用指定字符连接成新字符串
	 * 
	 * @param sourceList
	 *            原list<String>
	 * @param splitSymbol
	 *            指定字符
	 * @return 返回连接后的新字符串
	 */
	public static String concatListToString(List<String> sourceList, String splitSymbol) {
		StringBuffer sb = new StringBuffer();
		if (sourceList.size() > 0) {
			for (int i = 0; i < sourceList.size() - 1; i++) {
				sb.append(sourceList.get(i));
				sb.append(splitSymbol);
			}
			sb.append(sourceList.get(sourceList.size() - 1));
		}
		return sb.toString();
	}

	/**
	 * 判断一个字符串为空
	 * 
	 * @param value
	 *            字符串
	 * @return 为空返回true，否则返回false
	 */
	public static boolean isEmpty(String value) {
		return value == null || value.trim().equals("");
	}

	/**
	 * 判断一个字符串不为空
	 * 
	 * @param value
	 *            字符串
	 * @return 不为空返回true，否则返回false
	 */
	public static boolean isNotEmpty(String value) {
		return !isEmpty(value);
	}

	/**
	 * 生成提示语,供houseKeeper项目的提示框插件使用
	 * 
	 * @param tip
	 *            提示语
	 * @param isSuccess
	 *            true则代表success，false代表error
	 * @return 返回生成提示语
	 */
	public static String genTipMsg(String tip, boolean isSuccess) {
		String status = null;
		if (isSuccess) {
			status = "success";
		} else {
			status = "error";
		}
		return String.format("<script>$(function(){swal('OMG!', '%s', '%s');});</script>", tip, status);
	}

	/**
	 * 生成随机的UUID字符串
	 * 
	 * @return 返回随机的UUID字符串
	 */
	public static String genUUIDString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 绘画验证码
	 * 
	 * @param output
	 *            ByteArrayOutputStream
	 * @param codeLength
	 *            验证码长度
	 * @return 验证码字符串
	 */
	public static String drawImg(ByteArrayOutputStream output, int codeLength) {
		// 随机产生codeLength个字符
		String code = randomString(codeLength);
		// 设置验证码的宽高
		int width = 100;
		int height = 48;
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		Font font = new Font("Times New Roman", Font.PLAIN, 26);
		// 调用Graphics2D绘画验证码
		Graphics2D g = bi.createGraphics();
		g.setFont(font);
		Color color = new Color(66, 2, 82);
		g.setColor(color);
		g.setBackground(new Color(226, 226, 240));
		g.clearRect(0, 0, width, height);
		FontRenderContext context = g.getFontRenderContext();
		Rectangle2D bounds = font.getStringBounds(code, context);
		double x = (width - bounds.getWidth()) / 2;
		double y = (height - bounds.getHeight()) / 2;
		double ascent = bounds.getY();
		double baseY = y - ascent;
		g.drawString(code, (int) x, (int) baseY);
		g.dispose();
		try {
			ImageIO.write(bi, "jpg", output);
		} catch (IOException e) {
			logger.error("生成验证码失败，原因为:" + e);
		}
		return code;
	}

	/**
	 * 过滤一个字符串的非数字,即得到该字符串中的纯数字
	 * 
	 * @param str
	 *            待过滤字符串
	 * @return 该字符串中的纯数字组合
	 */
	public static String filterUnNumber(String str) {
		if (StringUtil.isEmpty(str))
			return null;
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * 将一个字符串进行URL编码
	 * 
	 * @param str
	 *            字符串
	 * @return 返回URL编码后的字符串
	 */
	public final static String urlEncode(String str) {
		if (isEmpty(str)) {
			return null;
		}
		try {
			return URLEncoder.encode(str, Constant.UTF8);
		} catch (UnsupportedEncodingException e) {
			logger.error("URL编码失败，原因为:" + e);
			return null;
		}
	}

	/**
	 * 将一个字符串进行URL解码
	 * 
	 * @param str
	 *            字符串
	 * @return 返回URL解码后的字符串
	 */
	public final static String urlDecode(String str) {
		if (isEmpty(str)) {
			return null;
		}
		try {
			return URLDecoder.decode(str, Constant.UTF8);
		} catch (UnsupportedEncodingException e) {
			logger.error("URL编码失败，原因为:" + e);
			return null;
		}
	}

	/**
	 * 将字符串编码成16进制数字,适用于所有字符（包括中文）
	 * 
	 * @param str
	 *            字符串（包括中文）
	 * @return 16进制数字字符串
	 */
	public static String encode2Binary(String str) {
		// 根据默认编码获取字节数组
		byte[] bytes = str.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	/**
	 * 将16进制数字解码成字符串,适用于所有字符（包括中文）
	 * 
	 * @param bytes
	 *            16进制数字字符串
	 * @return 字符串（包括中文）
	 */
	public static String decode2String(String bytes) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}

	/**
	 * 解决mysql数据库datetime类型取出时间多个.0的问题，截取前19位即可，格式为:yyyy-MM-dd HH:mm:ss）
	 * 
	 * @param str
	 *            时间字符串
	 * @return 处理后的字符串
	 */
	public static String subMysqlTimeStr(String str) {
		// 解决mysql数据库datetime类型取出时间多个.0的问题，截取前19位即可，格式为:yyyy-MM-dd HH:mm:ss
		if (str.length() < 19 || str.length() > 19) {
			return str;
		} else {
			return str.substring(0, 19);
		}
	}

	public static void main(String[] args) {
		System.out.println(encode2Binary("111中啊法￥多少"));
		System.out.println(decode2String("313131E4B8ADE5958AE6B395EFBFA5E5A49AE5B091"));
		System.out.println(replaceStrWithChar("15079077425", '*', 3, 7));
		System.out.println(genTipMsg("aaa", false));
	}
}
