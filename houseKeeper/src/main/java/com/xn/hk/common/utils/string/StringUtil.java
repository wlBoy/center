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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xn.hk.common.constant.Constant;

/**
 * @Title: StringUtil
 * @Package: com.xn.hk.common.utils
 * @Description: 字符串工具类
 * @Author: wanlei
 * @Date: 2018年1月8日 下午5:01:12
 */
public final class StringUtil {
	private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);
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
