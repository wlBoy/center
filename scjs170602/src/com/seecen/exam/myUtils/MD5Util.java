package com.seecen.exam.myUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5瀹搞儱鍙跨猾锟�
 * @author gong.zhiwei
 *
 */
public class MD5Util {
	private MessageDigest md;
	private static MD5Util md5;

	private MD5Util() {
		try {
			md = MessageDigest.getInstance("md5");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("濞屸剝婀佹潻娆戭潚缁犳纭�");
		}
	}

	// 娴溠呮晸娑擄拷娑撶嫻D5鐎圭偘绶�
	public static MD5Util getInstance() {
		if (null != md5)
			return md5;
		else {
			makeInstance();
			return md5;
		}
	}

	// 娣囨繆鐦夐崥灞肩閺冨爼妫块崣顏呮箒娑擄拷娑擃亞鍤庣粙瀣躬娴ｈ法鏁D5閸旂姴鐦�
	private static synchronized void makeInstance() {
		if (null == md5)
			md5 = new MD5Util();
	}

	public String createMD5(String pass) {
		md.update(pass.getBytes());
		byte[] b = md.digest();
		return byteToHexString(b);
	}

	private String byteToHexString(byte[] b) {
		StringBuffer sb = new StringBuffer();
		String temp = "";
		for (int i = 0; i < b.length; i++) {
			temp = Integer.toHexString(b[i] & 0Xff);
			if (temp.length() == 1)
				temp = "0" + temp;
			sb.append(temp);
		}
		return sb.toString();
	}
	public static String encode(String password) {
		return MD5Util.getInstance().createMD5(password);
	}
	public static void main(String[] args) {
		System.out.println(MD5Util.encode("sc1234"));
		
	}
}
