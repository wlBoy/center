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
	 * 测试main方法
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		String s = new String("admin");
		System.out.println("MD5后：" + MD5(s));
	}
}
