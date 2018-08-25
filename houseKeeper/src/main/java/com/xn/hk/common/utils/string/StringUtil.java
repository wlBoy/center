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
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

/**
 * @Title: StringUtil
 * @Package: com.xn.hk.common.utils
 * @Description: 字符串工具类
 * @Author: wanlei
 * @Date: 2018年1月8日 下午5:01:12
 */
public final class StringUtil {
	/**
	 * 数字字符数组
	 */
	private final static char[] digitArray = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	/**
	 * 16进制数
	 */
	private static final String hexString = "0123456789ABCDEF";

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
	 * 生成指定长度的字符串
	 * 
	 * @param len
	 * @return
	 */
	public static String randomString(int len) {
		Random r = new Random();
		StringBuffer sb = new StringBuffer();
		String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		for (int i = 0; i < len; i++) {
			sb.append(s.charAt(r.nextInt(s.length())));
		}
		return sb.toString();
	}

	/**
	 * 判断一个字符串为空
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value) {
		return value == null || value.trim().equals("");
	}

	/**
	 * 生成提示语,供houseKeeper项目的提示框插件使用
	 * 
	 * @param tip
	 *            提示语
	 * @param status
	 *            success 或 error
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
		String code = randomString(4);
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
	 * 过滤一个字符串的非数字,即得到该字符串中的纯数字
	 * 
	 * @param str
	 *            待过滤字符串
	 * @return 该字符串中的纯数字组合
	 */
	public static String filterUnNumber(String str) {
		if (StringUtil.isEmpty(str))
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
	 *            字符串
	 * @return
	 */
	public static boolean isDigit(String str) {
		String regEx = "(\\d+?)";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 判断一个字符串是否为号码
	 * 
	 * @param number
	 *            手机号码字符串
	 * @return
	 */
	public static boolean isMobileNumber(String number) {
		if (StringUtil.isEmpty(number))
			return false;
		if (number.length() > 11)
			number = number.substring(number.length() - 11);
		Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(number);
		return m.matches();
	}

	/**
	 * 将一个字符串进行URL编码
	 * 
	 * @param url
	 *            字符串
	 * @return
	 */
	public final static String urlEncode(String url) {
		if (isEmpty(url)) {
			return "";
		}
		try {
			return URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 将一个字符串进行URL解码
	 * 
	 * @param url
	 *            字符串
	 * @return
	 */
	public final static String urlDecode(String url) {
		if (isEmpty(url)) {
			return "";
		}
		try {
			return URLDecoder.decode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
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

	public static void main(String[] args) {
		System.out.println(encode2Binary("111中啊法￥多少"));
		System.out.println(decode2String("313131E4B8ADE5958AE6B395EFBFA5E5A49AE5B091"));
	}
}
