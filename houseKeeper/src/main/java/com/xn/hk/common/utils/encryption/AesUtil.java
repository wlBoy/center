package com.xn.hk.common.utils.encryption;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xn.hk.common.constant.Constant;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * @ClassName: AesUtil
 * @Package: com.xn.hk.common.utils.encryption
 * @Description: AES对称加密和解密的工具类
 * @Author: wanlei
 * @Date: 2019年2月25日 下午5:28:25
 */
@SuppressWarnings("restriction")
public class AesUtil {
	private static final Logger logger = LoggerFactory.getLogger(DesUtil.class);
	public static final String AES = "AES";// AES加密算法
	public static final String RULES = "wanlei123456";// AES加密与解密规则

	/**
	 * AES加密 (1.构造密钥生成器 2.根据ecnodeRules规则初始化密钥生成器 3.产生密钥 4.创建和初始化密码器 5.内容加密 6.返回字符串)
	 * 
	 * @param encodeRules
	 *            加密规则
	 * @param content
	 *            加密内容
	 * @return 返回加密之后的字符串
	 */
	public static String AesEncode(String encodeRules, String content) {
		try {
			// 1.构造密钥生成器，指定为AES算法,不区分大小写
			KeyGenerator keygen = KeyGenerator.getInstance(AES);
			// 2.根据encodeRules规则初始化密钥生成器
			// 生成一个128位的随机源,根据传入的字节数组
			keygen.init(128, new SecureRandom(encodeRules.getBytes()));
			// 3.产生原始对称密钥
			SecretKey original_key = keygen.generateKey();
			// 4.获得原始对称密钥的字节数组
			byte[] raw = original_key.getEncoded();
			// 5.根据字节数组生成AES密钥
			SecretKey key = new SecretKeySpec(raw, AES);
			// 6.根据指定算法AES自成密码器
			Cipher cipher = Cipher.getInstance(AES);
			// 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
			cipher.init(Cipher.ENCRYPT_MODE, key);
			// 8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
			byte[] byte_encode = content.getBytes(Constant.UTF8);
			// 9.根据密码器的初始化方式--加密：将数据加密
			byte[] byte_AES = cipher.doFinal(byte_encode);
			// 10.将加密后的数据转换为字符串并返回
			return new String(new BASE64Encoder().encode(byte_AES));
		} catch (Exception e) {
			logger.error("AES加密失败，原因为：{}", e);
			return null;
		}
	}

	/**
	 * AES解密(1.同加密1-4步 2.将加密后的字符串反纺成byte[]数组 3.将加密内容解密)
	 * 
	 * @param encodeRules
	 *            解密规则，应与加密规则一样
	 * @param content
	 *            解密内容
	 * @return 解密之后的字符串，即原字符串
	 */
	public static String AesDecode(String encodeRules, String content) {
		try {
			// 1.构造密钥生成器，指定为AES算法,不区分大小写
			KeyGenerator keygen = KeyGenerator.getInstance(AES);
			// 2.根据ecnodeRules规则初始化密钥生成器
			// 生成一个128位的随机源,根据传入的字节数组
			keygen.init(128, new SecureRandom(encodeRules.getBytes()));
			// 3.产生原始对称密钥
			SecretKey original_key = keygen.generateKey();
			// 4.获得原始对称密钥的字节数组
			byte[] raw = original_key.getEncoded();
			// 5.根据字节数组生成AES密钥
			SecretKey key = new SecretKeySpec(raw, AES);
			// 6.根据指定算法AES自成密码器
			Cipher cipher = Cipher.getInstance(AES);
			// 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
			cipher.init(Cipher.DECRYPT_MODE, key);
			// 8.将加密并编码后的内容解码成字节数组
			byte[] byte_content = new BASE64Decoder().decodeBuffer(content);
			byte[] byte_decode = cipher.doFinal(byte_content);
			return new String(byte_decode, Constant.UTF8);
		} catch (Exception e) {
			logger.error("AES解密失败，原因为：{}", e);
			return null;
		}
	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String content = "测试123OK";// 加密字符串
		String content1 = "ca8Yw/qm6AHV+yoCsfcF6A==";// 解密字符串
		/*
		 * 加密
		 */
		System.out.println("根据输入的规则" + RULES + "加密后的密文是:" + AesEncode(RULES, content));
		/*
		 * 解密
		 */
		System.out.println("根据输入的规则" + RULES + "解密后的明文是:" + AesDecode(RULES, content1));
	}

}