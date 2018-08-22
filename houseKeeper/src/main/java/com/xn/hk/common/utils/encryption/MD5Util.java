package com.xn.hk.common.utils.encryption;

import java.security.MessageDigest;

/**
 * 
 * @Title: MD5Util
 * @Package: com.xn.hk.common.utils
 * @Description: MD5操作工具类
 * @Author: wanlei
 * @Date: 2018年1月8日 下午4:44:15
 */
public class MD5Util {
	/**
	 * MD5加密
	 * 
	 * @param str
	 *            加密前的字符串
	 * @return 加密后的字符串
	 */
	public final static String MD5(String str) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] btInput = str.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char dist[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				dist[k++] = hexDigits[byte0 >>> 4 & 0xf];
				dist[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(dist);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 可逆的加密算法(加密)
	 * 
	 * @param str
	 *            加密前的字符串
	 * @return 加密后的字符串
	 */
	public static String KL(String str) {
		char[] a = str.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;
	}

	/**
	 * 可逆的加密算法(加密后解密)
	 * 
	 * @param str
	 *            加密前的字符串
	 * @return 加密后的字符串
	 */
	public static String JM(String str) {
		char[] a = str.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String k = new String(a);
		return k;
	}

	/**
	 * 测试main方法
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		String s = new String("admin");
		System.out.println("原始：" + s);
		System.out.println("MD5后：" + MD5(s));
		System.out.println("MD5后再加密：" + KL(MD5(s)));
		System.out.println("解密为MD5后的：" + JM(KL(MD5(s))));
	}
}
