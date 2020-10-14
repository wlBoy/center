package com.xn.hk.common.utils.encryption;

import org.bouncycastle.util.encoders.Hex;

import com.xn.hk.common.utils.encryption.sm.Sm3Util;
import com.xn.hk.common.utils.encryption.sm.Sm4KeyUtil;
import com.xn.hk.common.utils.encryption.sm.Sm4Util;

import java.security.MessageDigest;

/**
 * 
 * @ClassName: SMEncryptUtil
 * @Package: com.xn.hk.common.utils.encryption
 * @Description: sm算法加密工具类
 * @Author: wanlei
 * @Date: 2020年8月24日 上午11:58:07
 */
public class SmEncryptUtil {

	/**
	 * PIN 码用SM4 对称加密 对称密钥=SM3(手机号+格尔软件)前16个字节 SM4加密算法 加密算法/分组加密模式/分组填充方式 =
	 * "SM4/ECB/PKCS5Padding"
	 *
	 * @param userName
	 *            用户名
	 * @param pin
	 *            pin码
	 * @return 16进制字符串
	 */
	public static String encryptWithSM4(String userName, String pin) throws Exception {
		String newPIN = null;
		byte[] key = Sm4KeyUtil.generateKey(userName);
		byte[] encryptedPIN = Sm4Util.encrypt_Ecb_Padding(key, pin.getBytes("UTF-8"));
		newPIN = Hex.toHexString(encryptedPIN);
		return newPIN;
	}

	/**
	 * PIN 码用SM4 对称解密 对称密钥=SM3(手机号+格尔软件)前16个字节 SM4加密算法 加密算法/分组加密模式/分组填充方式 =
	 * "SM4/ECB/PKCS5Padding"
	 *
	 * @param userName
	 *            用户名
	 * @param pin
	 *            pin码
	 * @return 16进制字符串
	 */
	public static String decryptWithSM4(String userName, String pin) throws Exception {
		String newPIN = null;
		byte[] key = Sm4KeyUtil.generateKey(userName);
		byte[] encryptedPIN = Sm4Util.decrypt_Ecb_Padding(key, pin.getBytes("UTF-8"));
		newPIN = new String(encryptedPIN, "UTF-8");
		return newPIN;
	}

	/**
	 * PIN 码用SM4 对称解密 对称密钥=SM3(手机号+格尔软件)前16个字节 SM4加密算法 加密算法/分组加密模式/分组填充方式 =
	 * "SM4/ECB/PKCS5Padding"
	 *
	 * @param userName
	 *            用户名
	 * @param pin
	 *            pin码
	 * @return 字符串
	 */
	public static String decryptWithSM4(String userName, byte[] pin) throws Exception {
		String newPIN = null;
		byte[] key = Sm4KeyUtil.generateKey(userName);
		byte[] encryptedPIN = Sm4Util.decrypt_Ecb_Padding(key, pin);
		newPIN = new String(encryptedPIN, "UTF-8");
		return newPIN;
	}

	/**
	 * 对密码进行摘要 先用sha-1 摘要， 再用SM3 摘要
	 *
	 * @param pwd
	 *            密码
	 * @return 16进制字符串
	 */
	public static String digestWithShaAndSM3(String pwd) throws Exception {
		String newPwd = null;
		MessageDigest messageDigest = MessageDigest.getInstance("SHA"); // 此处的sha代表sha1
		byte[] shaDigestedBytes = messageDigest.digest(pwd.getBytes("UTF-8"));
		byte[] sm3DigestedBytes = Sm3Util.hash(shaDigestedBytes);
		newPwd = Hex.toHexString(sm3DigestedBytes);
		return newPwd;
	}
}
