package com.xn.hk.common.utils.encryption;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xn.hk.common.constant.Constant;

/**
 * 
 * @ClassName: DesUtil
 * @Package: com.xn.hk.common.utils.encryption
 * @Description: des算法加密工具类,支持DESede算法，是可逆算法
 * @Author: wanlei
 * @Date: 2018年10月9日 下午1:49:34
 */
public class DesUtil {
	private static final Logger logger = LoggerFactory.getLogger(DesUtil.class);
	public static final String DESEDE = "DESede";// DESede加密算法
	// keybyte为加密密钥，长度为24字节
	private static final byte[] keyBytes = { 0x51, 0x52, 0x4F, 0x58, (byte) 0x88, 0x10, 0x40, 0x38, 0x28, 0x25, 0x79,
			0x51, (byte) 0xCB, (byte) 0xDD, 0x55, 0x66, 0x77, 0x29, 0x74, (byte) 0x98, 0x30, 0x40, 0x36, (byte) 0xE2 };

	/**
	 * 加密字符串
	 * 
	 * @param src
	 *            被加密的数据缓冲区（源），即待加密字符串
	 * @param algorithm
	 *            加密算法
	 * @return 返回加密之后的字符串
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	private static byte[] encrypt(byte[] src, String algorithm) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		// 生成密钥
		SecretKey deskey = new SecretKeySpec(keyBytes, algorithm);
		// 加密
		Cipher c1 = Cipher.getInstance(algorithm);
		c1.init(Cipher.ENCRYPT_MODE, deskey);
		return c1.doFinal(src);
	}

	/**
	 * 默认使用DESede算法加密，再使用base64加密获取加密后的字符串
	 * 
	 * @param src
	 *            被加密的数据缓冲区（源），即待加密字符串
	 * @return 返回加密之后的字符串
	 */
	public static String encryptAndBase64(String src) {
		return encryptAndBase64(src, DESEDE);
	}

	/**
	 * 使用非对称算法加密，再使用base64加密获取加密后的字符串
	 * 
	 * @param src
	 *            被加密的数据缓冲区（源），即待加密字符串
	 * @param algorithm
	 *            加密算法
	 * @return 返回加密之后的字符串
	 */
	private static String encryptAndBase64(String src, String algorithm) {
		String enc = null;
		if (StringUtils.isEmpty(src)) {
			return enc;
		}
		try {
			enc = new String(Base64Util.encode(DesUtil.encrypt(src.getBytes(Constant.UTF8), algorithm)));
		} catch (Exception e) {
			logger.error("{}算法加密失败，原因为:{}", algorithm, e);
		}
		return enc;
	}

	/**
	 * 解密字符串
	 * 
	 * @param by
	 *            加密后的数据
	 * @param algorithm
	 *            加密算法
	 * @return 解密后的字符串
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	private static byte[] decrypt(byte[] by, String algorithm) throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		// 生成密钥
		SecretKey deskey = new SecretKeySpec(keyBytes, algorithm);
		// 解密
		Cipher c1 = Cipher.getInstance(algorithm);
		c1.init(Cipher.DECRYPT_MODE, deskey);
		return c1.doFinal(by);
	}

	/**
	 * 默认使用base64解码，再使用DESede算法解密获取解密后的字符串
	 * 
	 * @param src
	 *            要解密的字符串
	 * @return 解密后的字符串
	 */
	public static String base64AndDecrypt(String src) {
		return base64AndDecrypt(src, DESEDE);
	}

	/**
	 * 使用base64解码，在使用非对称算法解密获取解密后的字符串
	 * 
	 * @param src
	 *            要解密的字符串
	 * @param algorithm
	 *            对称算法，例如 DES,DESede,Blowfish
	 * @return 解密后的字符串
	 */
	private static String base64AndDecrypt(String src, String algorithm) {
		String dec = null;
		if (StringUtils.isEmpty(src)) {
			return dec;
		}
		try {
			dec = new String(decrypt(Base64Util.decode(src), algorithm));
		} catch (Exception e) {
			logger.error("{}算法解密失败，原因为:{}", algorithm, e);
		}
		return dec;
	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String str = "admin123";
		System.out.println(encryptAndBase64(str));
		System.out.println(base64AndDecrypt("V2h5Sq2H9rS1SLCKaI02MA=="));
	}

}
