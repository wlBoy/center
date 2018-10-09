package com.xn.hk.common.utils.encryption;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;

/**
 * des加密工具
 * 
 * @author yzf
 *
 */
public class DesUtil {
	// 定义 加密算法,可用 DES,DESede,Blowfish
	private static final String Algorithm = "DESede";
	// keybyte为加密密钥，长度为24字节
	private static final byte[] keyBytes = { 0x51, 0x52, 0x4F, 0x58, (byte) 0x88, 0x10, 0x40, 0x38, 0x28, 0x25, 0x79,
			0x51, (byte) 0xCB, (byte) 0xDD, 0x55, 0x66, 0x77, 0x29, 0x74, (byte) 0x98, 0x30, 0x40, 0x36, (byte) 0xE2 };

	/**
	 * 加密字符串
	 * 
	 * @param src
	 *            被加密的数据缓冲区（源），即待加密字符串
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static byte[] encrypt(byte[] src) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		// 生成密钥
		SecretKey deskey = new SecretKeySpec(keyBytes, Algorithm);
		// 加密
		Cipher c1 = Cipher.getInstance(Algorithm);
		c1.init(Cipher.ENCRYPT_MODE, deskey);
		return c1.doFinal(src);
	}

	/**
	 * 加密字符串，并对加密结果进行base64编码后，以字符串返回
	 * 
	 * @param src
	 *            被加密的数据缓冲区（源），即待加密字符串
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws UnsupportedEncodingException
	 */
	public static String encryptAndBase64(String src) throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		if (StringUtils.isEmpty(src)) {
			return null;
		}
		String enc = new String(Base64Util.encode(DesUtil.encrypt(src.getBytes("utf-8"))));
		return enc;
	}

	/**
	 * 解密
	 * 
	 * @param by
	 *            加密后的数据
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static byte[] decrypt(byte[] by) throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		// 生成密钥
		SecretKey deskey = new SecretKeySpec(keyBytes, Algorithm);
		// 解密
		Cipher c1 = Cipher.getInstance(Algorithm);
		c1.init(Cipher.DECRYPT_MODE, deskey);
		return c1.doFinal(by);
	}

	/**
	 * 解密
	 * 
	 * @param src
	 *            为加密后，并经base64编码，转为字符串的数据
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws UnsupportedEncodingException
	 */
	public static String base64AndDecrypt(String src) throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		if (StringUtils.isEmpty(src)) {
			return null;
		}
		String dec = new String(decrypt(Base64Util.decode(src)));
		return dec;
	}

	public static void main(String[] args) {
		String str = "admin123";
		try {
			System.out.println(new String(encrypt(str.getBytes())));
			System.out.println(new String(decrypt(encrypt(str.getBytes()))));
			System.out.println(encryptAndBase64(str));
			System.out.println(base64AndDecrypt("V2h5Sq2H9rS1SLCKaI02MA=="));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
