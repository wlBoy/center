package com.xn.hk.common.utils.string;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * @ClassName: SessionIdUtil
 * @Package: com.xn.hk.common.utils.string
 * @Description: sessionId生成器
 * @Author: wanlei
 * @Date: 2020年8月24日 下午12:04:25
 */
public class SessionIdUtil {

	private static Mac mac;

	public SessionIdUtil(String secret) throws InvalidKeyException, NoSuchAlgorithmException {
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
		sha256_HMAC.init(secret_key);
		mac = sha256_HMAC;
	}

	/**
	 * 随机数+时间戳+签名
	 * 
	 * @return
	 */
	public static String createRandom() {
		String id = generateCode();
		String time = getTimeHex();
		String sign = getSign(id, time);
		return id + time + sign;
	}

	private static String getSign(String id, String time) {
		return BytesToHex(mac.doFinal((id + time).getBytes())).substring(0, 15);
	}

	private static String getTimeHex() {
		return intToHex((int) (System.currentTimeMillis() / 1000));
	}

	public static String generateCode() {
		Random random = new Random();
		StringBuffer code = new StringBuffer();
		int size = 1;
		for (int i = 0; i < 8; i++) {
			code.append(random.nextInt(10) * size);
		}

		return code.toString();
	}

	private static String intToHex(int n) {
		StringBuffer s = new StringBuffer();
		String a;
		char[] b = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		while (n != 0) {
			s = s.append(b[n % 16]);
			n = n / 16;
		}
		a = s.reverse().toString();
		return a;
	}

	public static String BytesToHex(byte[] b) {
		String hexs = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			hexs = hexs + hex.toUpperCase();
		}
		return hexs;
	}

}
