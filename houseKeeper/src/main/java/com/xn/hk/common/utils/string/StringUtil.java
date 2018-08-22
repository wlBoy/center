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
import java.net.URLEncoder;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

/**
 * 
 * @Title: StringUtil
 * @Package: com.xn.hk.common.utils
 * @Description: 字符串工具类
 * @Author: wanlei
 * @Date: 2018年1月8日 下午5:01:12
 */
public final class StringUtil {
	/**
	 * 字符数组
	 */
	private final static char[] charArray = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	/**
	 * 数字字符数组
	 */
	private final static char[] digitArray = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	/**
	 * 随机生成指定长度的纯数字字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String randomDigit(int length) {
		char[] c = new char[length];
		Random random = new Random();
		for (int i = 0; i < length; i++)
			c[i] = digitArray[random.nextInt(digitArray.length)];
		return new String(c);
	}

	/**
	 * 随机生成指定长度的字符串(数字字符混合)
	 * 
	 * @param length
	 * @return
	 */
	public static String randomString(int length) {
		char[] c = new char[length];
		Random random = new Random();
		for (int i = 0; i < length; i++)
			c[i] = charArray[random.nextInt(charArray.length)];
		return new String(c);
	}

	/**
	 * 判断一个字符串为空
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNullValue(String value) {
		return value == null || value.trim().equals("");
	}

	/**
	 * 生成提示语
	 * 
	 * @param tip
	 * @param status
	 * @return
	 */
	public static String genTipMsg(String tip, String status) {
		return "<script>$(function(){swal('OMG!', '" + tip + "', '" + status + "');});</script>";
	}

	/**
	 * 生成随机的UUID字符串
	 * 
	 * @return
	 */
	public static String genUUIDString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 绘画验证码
	 * 
	 * @param output
	 *            ByteArrayOutputStream
	 * @return 验证码字符串
	 */
	public static String drawImg(ByteArrayOutputStream output) {
		// 随机产生4个字符
		String code = genRandomString(4);
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
			e.printStackTrace();
		}
		return code;
	}

	/**
	 * 生成指定长度的字符串
	 * 
	 * @param len
	 * @return
	 */
	public static String genRandomString(int len) {
		Random r = new Random();
		StringBuffer sb = new StringBuffer();
		String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		for (int i = 0; i < len; i++) {
			sb.append(s.charAt(r.nextInt(s.length())));
		}
		return sb.toString();
	}

	/**
	 * 向一个字符串最左边添加任意字符组成新固定长度的字符串
	 * 
	 * @param input
	 * @param num
	 *            新固定长度的字符串
	 * @param chr
	 * @return
	 */
	public static final String leftAppendStr(String input, int num, char chr) {
		int size = num - input.length();
		char[] c = new char[size];
		for (int i = 0; i < size; i++) {
			c[i] = chr;
		}
		return String.valueOf(c).concat(input);
	}

	/**
	 * 向一个字符串最左边添加'0'组成新固定长度的字符串
	 * 
	 * @param input
	 * @param num
	 *            新固定长度的字符串
	 * @param chr
	 * @return
	 */
	public static final String leftAppendStr(String input, int num) {
		return leftAppendStr(input, num, '0');
	}

	/**
	 * 过滤一个字符串的非数字,即得到该字符串中的纯数字
	 * 
	 * @param str
	 * @return
	 */
	public static String filterUnNumber(String str) {
		if (StringUtil.isNullValue(str))
			return "";
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * 判断一个字符串是否为纯数字字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDigit(String str) {
		String regEx = "(\\d+?)";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 判断一个字符串是否为电话号码
	 * 
	 * @param number
	 * @return
	 */
	public static boolean isMobileNumber(String number) {
		if (StringUtil.isNullValue(number))
			return false;
		if (number.length() > 11)
			number = number.substring(number.length() - 11);
		Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(number);
		return m.matches();
	}

	/**
	 * 判断一个字符串是否为有效电话号码
	 * 
	 * @param number
	 * @return
	 */
	public final static boolean isValidMobileNumber(String number) {
		if (StringUtil.isNullValue(number))
			return false;
		if (number.endsWith("00000000") || number.endsWith("13800138000"))
			return false;
		return StringUtil.isMobileNumber(number);
	}

	/**
	 * url编码
	 * 
	 * @param url
	 * @return
	 */
	public final static String urlEncode(String url) {
		if (isNullValue(url)) {
			return "";
		}

		try {
			return URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String getXMLValue(String srcXML, String element) {
		String begElement = "<" + element + ">";
		String endElement = "</" + element + ">";
		int begPos = srcXML.indexOf(begElement);
		int endPos = srcXML.indexOf(endElement);
		if (begPos != -1 && endPos != -1) {
			begPos += begElement.length();
			return srcXML.substring(begPos, endPos);
		} else {
			return "";
		}
	}

	private static String hexString = "0123456789ABCDEF";

	/*
	 * 将字符串编码成16进制数字,适用于所有字符（包括中文）
	 */
	public static String encode(String str) {
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

	/*
	 * 将16进制数字解码成字符串,适用于所有字符（包括中文）
	 */
	public static String decode(String bytes) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}

	public static void main(String[] args) {
		
	}
}
