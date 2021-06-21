package com.xn.hk.common.utils.encryption.rsa;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xn.hk.common.utils.string.StringUtil;

/**
 *    注意：
 *　　 1、因为rsa在加解密的时候，有长度限制，所以在加解密的时候应该采用分段式加解密。
 *　　 2、当需要同时使用加解密的时候，必须是先签名后加密，假设是 A 请求 B ，A用A的私钥生成签名，再用B的公钥加密，最后把密文传B，B 收到密文的时候，先用B的私钥进行解密，再用A的公钥验签。
 *　使用场景：
 *　    1、只对数据加密 ， 此时只需要server端生成一对公私钥，把公钥交给client，这种情况主要用在web与服务器的交互，比如传密码之类的。（防泄漏）
*　   2、只对数据签名，此时只需要client端生成一对公私钥，把公钥交给server，这种情况主要用在公司内部与第三方对接，比如，公司有个项目A需要接入微信，支付宝，京东等，即，A 是client，第三方则是server，这样每次调用第三方都需要把数据签名后，发给第三方，然后进行验签。（防篡改）
*　   3、对数据进行签名和加密，此时client和server两方各自生成一对公私钥，相互交换公钥，这种情况主要用在企业之间的数据传输，比如 甲  乙 两个企业要通信。（防篡改和防泄漏）
*/
/**
 * 
 * @ClassName: RsaUtil
 * @Package: com.xn.hk.common.utils.encryption
 * @Description: RSA算法加解密工具类，支持RSA加解密和签名验签的方法
 * @Author: wanlei
 * @Date: 2020年5月14日 下午4:14:28
 */
public class RsaUtil {
	private static final Logger logger = LoggerFactory.getLogger(RsaUtil.class);

	private RsaUtil() {
	}

	// RSA算法
	private static final String RSA = "RSA";
	// RSA默认秘钥长度
	private static final int DEFAULT_KEY_SIZE = 1024;
	// RSA默认签名算法
	private static final String DEFAULT_SIGN_ALGORITHMS = "SHA256withRSA";
	// 公私钥key
	public static final String PUBLIC_KEY = "publicKey";
	public static final String PRIVATE_KEY = "privateKey";

	/**
	 * 随机生成一对默认秘钥长度的RSA密钥对MAP
	 *
	 * @return 返回一对RSA密钥对
	 */
	public static Map<String, String> genKeyPair() {
		return genKeyPair(DEFAULT_KEY_SIZE);
	}

	/**
	 * 随机生成一对RSA密钥对MAP
	 *
	 * @param keySize
	 *            秘钥长度(64的倍数)，一般为:1024或2048
	 * @return 返回一对RSA密钥对
	 */
	public static Map<String, String> genKeyPair(int keySize) {
		Map<String, String> keyMap = new HashMap<>();
		try {
			// KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSA);
			// 初始化密钥对生成器，设置密钥大小
			keyPairGen.initialize(keySize, new SecureRandom());
			// 生成一个密钥对，保存在keyPair中
			KeyPair keyPair = keyPairGen.generateKeyPair();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate(); // 得到私钥
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic(); // 得到公钥
			String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
			String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
			keyMap.put(PUBLIC_KEY, publicKeyString);
			keyMap.put(PRIVATE_KEY, privateKeyString);
		} catch (Exception e) {
			logger.error("gen rsa keyPair failed ", e);
		}
		return keyMap;
	}

	/**
	 * 使用默认秘钥长度进行，RSA公钥加密
	 *
	 * @param str
	 *            加密字符串
	 * @param publicKey
	 *            公钥
	 * @return 密文
	 */
	public static String encrypt(String str, String publicKey) {
		return encrypt(str, publicKey, DEFAULT_KEY_SIZE);
	}

	/**
	 * RSA公钥加密
	 *
	 * @param str
	 *            加密字符串
	 * @param publicKey
	 *            公钥
	 * @param keySize
	 *            秘钥长度
	 * @return 密文
	 */
	public static String encrypt(String str, String publicKey, int keySize) {
		if (StringUtil.isEmpty(str)) {
			return null;
		}
		// 根据秘钥长度计算RSA最大加密明文大小
		int maxEncryptBlock = keySize / 8 - 11;
		if (str.length() < maxEncryptBlock) {
			return encryptAll(str, publicKey);
		} else {
			return encryptWithSection(str, publicKey, maxEncryptBlock);
		}
	}

	/**
	 * RSA公钥加密
	 *
	 * @param str
	 *            加密字符串
	 * @param publicKey
	 *            公钥
	 * @return 密文
	 */
	private static String encryptAll(String str, String publicKey) {
		try {
			// base64编码的公钥
			byte[] decoded = Base64.decodeBase64(publicKey);
			RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(RSA)
					.generatePublic(new X509EncodedKeySpec(decoded));
			// RSA加密
			Cipher cipher = Cipher.getInstance(RSA);
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			return Base64.encodeBase64String(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
		} catch (Exception e) {
			logger.error("rsa encrypt failed ", e);
		}
		return null;
	}

	/**
	 * RSA公钥加密(支持分段加密)
	 *
	 * @param str
	 *            加密字符串
	 * @param publicKey
	 *            公钥
	 * @param maxEncryptBlock
	 *            RSA最大加密明文大小
	 * @return 密文
	 */
	private static String encryptWithSection(String str, String publicKey, int maxEncryptBlock) {
		try {
			// base64编码的公钥
			byte[] decoded = Base64.decodeBase64(publicKey);
			RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(RSA)
					.generatePublic(new X509EncodedKeySpec(decoded));
			// RSA加密
			Cipher cipher = Cipher.getInstance(RSA);
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			byte[] data = str.getBytes(StandardCharsets.UTF_8);
			int inputLen = data.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > maxEncryptBlock) {
					cache = cipher.doFinal(data, offSet, maxEncryptBlock);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * maxEncryptBlock;
			}
			byte[] encryptedData = out.toByteArray();
			out.close();
			return Base64.encodeBase64String(encryptedData);
		} catch (Exception e) {
			logger.error("rsa encrypt failed ", e);
		}
		return null;
	}

	/**
	 * 使用默认秘钥长度进行RSA私钥解密
	 *
	 * @param str
	 *            加密字符串
	 * @param privateKey
	 *            私钥
	 * @return
	 */
	public static String decrypt(String str, String privateKey) {
		return decrypt(str, privateKey, DEFAULT_KEY_SIZE);
	}

	/**
	 * RSA私钥解密
	 *
	 * @param str
	 *            加密字符串
	 * @param privateKey
	 *            私钥
	 * @param keySize
	 *            秘钥长度
	 * @return
	 */
	public static String decrypt(String str, String privateKey, int keySize) {
		if (StringUtil.isEmpty(str)) {
			return null;
		}
		// 根据秘钥长度计算RSA最大解密密文大小
		int maxDecryptBlock = keySize / 8;
		if (str.length() < maxDecryptBlock) {
			return decryptAll(str, privateKey);
		} else {
			return decryptWithSection(str, privateKey, maxDecryptBlock);
		}
	}

	/**
	 * RSA私钥解密
	 *
	 * @param str
	 *            加密字符串
	 * @param privateKey
	 *            私钥
	 * @return 铭文
	 * @throws Exception
	 *             解密过程中的异常信息
	 */
	private static String decryptAll(String str, String privateKey) {
		try {
			// 64位解码加密后的字符串
			byte[] inputByte = Base64.decodeBase64(str.getBytes(StandardCharsets.UTF_8));
			// base64编码的私钥
			byte[] decoded = Base64.decodeBase64(privateKey);
			RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(RSA)
					.generatePrivate(new PKCS8EncodedKeySpec(decoded));
			// RSA解密
			Cipher cipher = Cipher.getInstance(RSA);
			cipher.init(Cipher.DECRYPT_MODE, priKey);
			return new String(cipher.doFinal(inputByte));
		} catch (Exception e) {
			logger.error("rsa decrypt failed ", e);
		}
		return null;
	}

	/**
	 * RSA私钥解密(支持分段解密)
	 *
	 * @param str
	 *            加密字符串
	 * @param privateKey
	 *            私钥
	 * @param maxDecryptBlock
	 *            RSA最大解密密文大小
	 * @return
	 * @throws Exception
	 *             解密过程中的异常信息
	 */
	private static String decryptWithSection(String str, String privateKey, int maxDecryptBlock) {
		try {
			// 64位解码加密后的字符串
			byte[] data = Base64.decodeBase64(str.getBytes(StandardCharsets.UTF_8));
			// base64编码的私钥
			byte[] decoded = Base64.decodeBase64(privateKey);
			RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(RSA)
					.generatePrivate(new PKCS8EncodedKeySpec(decoded));
			// RSA解密
			Cipher cipher = Cipher.getInstance(RSA);
			cipher.init(Cipher.DECRYPT_MODE, priKey);
			int inputLen = data.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段解密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > maxDecryptBlock) {
					cache = cipher.doFinal(data, offSet, maxDecryptBlock);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * maxDecryptBlock;
			}
			byte[] decryptedData = out.toByteArray();
			out.close();
			return new String(decryptedData);
		} catch (Exception e) {
			logger.error("rsa decrypt failed ", e);
		}
		return null;
	}

	// *************************************上面为RSA加解密方法，下面为RSA签名验签方法**************************************

	/**
	 * 使用默认签名算法进行RSA私钥签名
	 *
	 * @param content
	 *            待签名数据
	 * @param privateKey
	 *            RSA私钥
	 * @return 签名值
	 */
	public static String sign(String content, String privateKey) {
		return sign(content, privateKey, DEFAULT_SIGN_ALGORITHMS);
	}

	/**
	 * RSA私钥签名
	 *
	 * @param content
	 *            待签名数据
	 * @param privateKey
	 *            RSA私钥
	 * @return 签名值
	 */
	public static String sign(String content, String privateKey, String signAlg) {
		return sign(content.getBytes(StandardCharsets.UTF_8), privateKey.getBytes(), signAlg);
	}

	/**
	 * 使用默认签名算法进行RSA私钥签名
	 *
	 * @param content
	 *            待签名数据
	 * @param privateKey
	 *            RSA私钥
	 * @return 签名值
	 */
	public static String sign(byte[] content, byte[] privateKey) {
		return sign(content, privateKey, DEFAULT_SIGN_ALGORITHMS);
	}

	/**
	 * RSA私钥签名
	 *
	 * @param content
	 *            待签名数据
	 * @param privateKey
	 *            RSA私钥
	 * @param signAlg
	 *            签名算法
	 * @return 签名值
	 */
	public static String sign(byte[] content, byte[] privateKey, String signAlg) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
			KeyFactory keyf = KeyFactory.getInstance(RSA);
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);
			Signature signature = Signature.getInstance(signAlg);
			signature.initSign(priKey);
			signature.update(content);
			byte[] signed = signature.sign();
			return Base64.encodeBase64String(signed);
		} catch (Exception e) {
			logger.error("rsa sign failed ", e);
		}
		return null;
	}

	/**
	 * 使用默认签名算法进行RSA公钥验签
	 *
	 * @param content
	 *            待签名数据
	 * @param sign
	 *            签名值
	 * @param publicKey
	 *            分配给开发商公钥
	 * @return 布尔值
	 */
	public static boolean verifySign(String content, String sign, String publicKey) {
		return verifySign(content, sign, publicKey, DEFAULT_SIGN_ALGORITHMS);
	}

	/**
	 * RSA公钥验签
	 *
	 * @param content
	 *            待签名数据
	 * @param sign
	 *            签名值
	 * @param publicKey
	 *            分配给开发商公钥
	 * @param signAlg
	 *            签名算法
	 * @return 布尔值
	 */
	public static boolean verifySign(String content, String sign, String publicKey, String signAlg) {
		return verifySign(content.getBytes(StandardCharsets.UTF_8), sign.getBytes(), publicKey.getBytes(), signAlg);
	}

	/**
	 * 使用默认签名算法进行RSA公钥验签
	 *
	 * @param content
	 *            待签名数据
	 * @param sign
	 *            签名值
	 * @param publicKey
	 *            分配给开发商公钥
	 * @return 布尔值
	 */
	public static boolean verifySign(byte[] content, byte[] sign, byte[] publicKey) {
		return verifySign(content, sign, publicKey, DEFAULT_SIGN_ALGORITHMS);
	}

	/**
	 * RSA公钥验签
	 *
	 * @param content
	 *            待签名数据
	 * @param sign
	 *            签名值
	 * @param publicKey
	 *            分配给开发商公钥
	 * @param signAlg
	 *            签名算法
	 * @return 布尔值
	 */
	public static boolean verifySign(byte[] content, byte[] sign, byte[] publicKey, String signAlg) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(RSA);
			byte[] encodedKey = Base64.decodeBase64(publicKey);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
			Signature signature = Signature.getInstance(signAlg);
			signature.initVerify(pubKey);
			signature.update(content);
			return signature.verify(Base64.decodeBase64(sign));
		} catch (Exception e) {
			logger.error("rsa verify sign failed ", e);
		}
		return false;
	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) {
		// 字符串原文
		String message = "{\"code\":0,\"data\":{\"createTime\":\"2020-05-14 22:46:38\",\"isOk\":0,\"logContent\":\"[用户ID=1237678678,用户姓名=张三,角色名称=管理员,创建时间=2020-05-14 22:46:38,更新时间=2020-05-14 22:46:38]\",\"remark\":\"\",\"rememberMe\":\"\",\"role\":{\"createTime\":\"2020-05-14 22:46:38\",\"isOk\":0,\"logContent\":\"[角色ID=1234871,角色姓名=管理员,创建时间=2020-05-14 22:46:38,更新时间=2020-05-14 22:46:38]\",\"moduleName\":\"\",\"modules\":[],\"remark\":\"\",\"roleId\":1234871,\"roleName\":\"管理员\",\"updateTime\":\"2020-05-14 22:46:38\"},\"updateTime\":\"2020-05-14 22:46:38\",\"userFace\":\"\",\"userId\":1237678678,\"userName\":\"张三\",\"userPwd\":\"zhangsan12345678\",\"userState\":0,\"verifyCode\":\"\"},\"msg\":\"请求成功\"}";
		// String message = "123456";
		// rsa秘钥长度
		int keySize = 2048;
		// rsa签名算法
		String sigAlg = "SHA256withRSA";
		// 1.随机生成一对公钥和私钥
		Map<String, String> keyMap = RsaUtil.genKeyPair(keySize);
		String publicKey = keyMap.get(RsaUtil.PUBLIC_KEY);
		String privateKey = keyMap.get(RsaUtil.PRIVATE_KEY);
		System.out.println("随机生成的公钥为:" + publicKey);
		System.out.println("随机生成的私钥为:" + privateKey);
		System.out.println("######################");
		// 2.公钥加密字符串
		String messageEn = RsaUtil.encrypt(message, publicKey, keySize);
		System.out.println("公钥加密后的字符串为:" + messageEn);
		// 3.私钥解密字符串
		String messageDe = RsaUtil.decrypt(messageEn, privateKey, keySize);
		System.out.println("私钥解密后的字符串为:" + messageDe);
		System.out.println("######################");
		// 4.私钥签名字符串
		String signStr = RsaUtil.sign(message, privateKey, sigAlg);
		System.out.println("私钥签名后的字符串为:" + signStr);
		// 5.公钥验签
		boolean isValid = RsaUtil.verifySign(message, signStr, publicKey, sigAlg);
		System.out.println("公钥验签结果为:" + isValid);

	}

}
