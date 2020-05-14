package com.xn.hk.common.utils.encryption;

import java.io.ByteArrayOutputStream;
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
 * @Description: RSA算法加解密工具类
 * @Author: wanlei
 * @Date: 2020年5月14日 下午4:14:28
 */
public class RsaUtil {
	// RSA算法
	private static final String RSA = "RSA";
	private static final String UTF_8 = "UTF-8";
	// RSA最大加密明文大小
	private static final int MAX_ENCRYPT_BLOCK = 117;
	// RSA最大解密密文大小
	private static final int MAX_DECRYPT_BLOCK = 128;
	// 公私钥
	private static final String PUBLIC_KEY = "publicKey";
	private static final String PRIVATE_KEY = "privateKey";
	// RSA签名算法
	private static final String SIGN_ALGORITHMS = "SHA512withRSA";

	/**
	 * 随机生成一对RSA密钥对MAP
	 * 
	 * @return 返回一对RSA密钥对:0表示公钥，1表示私钥
	 */
	public static Map<String, String> genKeyPair() {
		Map<String, String> keyMap = new HashMap<String, String>();
		try {
			// KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSA);
			// 初始化密钥对生成器，密钥大小为96-1024位
			keyPairGen.initialize(1024, new SecureRandom());
			// 生成一个密钥对，保存在keyPair中
			KeyPair keyPair = keyPairGen.generateKeyPair();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate(); // 得到私钥
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic(); // 得到公钥
			String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
			String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
			keyMap.put(PUBLIC_KEY, publicKeyString);
			keyMap.put(PRIVATE_KEY, privateKeyString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return keyMap;
	}

	/**
	 * RSA公钥加密
	 * 
	 * @param str       加密字符串
	 * @param publicKey 公钥
	 * @return 密文
	 */
	public static String encrypt(String str, String publicKey) {
		if (StringUtil.isEmpty(str)) {
			return null;
		}
		if (str.length() < MAX_ENCRYPT_BLOCK) {
			return encryptAll(str, publicKey);
		} else {
			return encryptWithSection(str, publicKey);
		}
	}

	/**
	 * RSA公钥加密(加密字符串长度小于117)
	 * 
	 * @param str       加密字符串
	 * @param publicKey 公钥
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
			return Base64.encodeBase64String(cipher.doFinal(str.getBytes(UTF_8)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * RSA公钥加密(支持分段加密),加密字符串长度大于117
	 * 
	 * @param str       加密字符串
	 * @param publicKey 公钥
	 * @return 密文
	 */
	private static String encryptWithSection(String str, String publicKey) {
		try {
			// base64编码的公钥
			byte[] decoded = Base64.decodeBase64(publicKey);
			RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(RSA)
					.generatePublic(new X509EncodedKeySpec(decoded));
			// RSA加密
			Cipher cipher = Cipher.getInstance(RSA);
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			byte[] data = str.getBytes(UTF_8);
			int inputLen = data.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			byte[] encryptedData = out.toByteArray();
			out.close();
			return Base64.encodeBase64String(encryptedData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * RSA私钥解密
	 * 
	 * @param str        加密字符串
	 * @param privateKey 私钥
	 * @return 铭文
	 * @throws Exception 解密过程中的异常信息
	 */
	public static String decrypt(String str, String privateKey) {
		if (StringUtil.isEmpty(str)) {
			return null;
		}
		if (str.length() < MAX_DECRYPT_BLOCK) {
			return decryptAll(str, privateKey);
		} else {
			return decryptWithSection(str, privateKey);
		}
	}

	/**
	 * RSA私钥解密(解密字符串长度小于128)
	 * 
	 * @param str        加密字符串
	 * @param privateKey 私钥
	 * @return 铭文
	 * @throws Exception 解密过程中的异常信息
	 */
	private static String decryptAll(String str, String privateKey) {
		try {
			// 64位解码加密后的字符串
			byte[] inputByte = Base64.decodeBase64(str.getBytes(UTF_8));
			// base64编码的私钥
			byte[] decoded = Base64.decodeBase64(privateKey);
			RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(RSA)
					.generatePrivate(new PKCS8EncodedKeySpec(decoded));
			// RSA解密
			Cipher cipher = Cipher.getInstance(RSA);
			cipher.init(Cipher.DECRYPT_MODE, priKey);
			return new String(cipher.doFinal(inputByte));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * RSA私钥解密(支持分段解密)，解密字符串长度大于128
	 * 
	 * @param str        加密字符串
	 * @param privateKey 私钥
	 * @return 铭文
	 * @throws Exception 解密过程中的异常信息
	 */
	private static String decryptWithSection(String str, String privateKey) {
		try {
			// 64位解码加密后的字符串
			byte[] data = Base64.decodeBase64(str.getBytes(UTF_8));
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
				if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
					cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}
			byte[] decryptedData = out.toByteArray();
			out.close();
			return new String(decryptedData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/******************** 以上是RSA加解密方法，以下是RSA签名验签方法 ********************/
	/**
	 * RSA私钥签名
	 * 
	 * @param content    待签名数据
	 * @param privateKey RSA私钥
	 * @return 签名值
	 */
	public static String sign(String content, String privateKey) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
			KeyFactory keyf = KeyFactory.getInstance(RSA);
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);
			Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
			signature.initSign(priKey);
			signature.update(content.getBytes(UTF_8));
			byte[] signed = signature.sign();
			return Base64.encodeBase64String(signed);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * RSA公钥验签
	 * 
	 * @param content   待签名数据
	 * @param sign      签名值
	 * @param publicKey 分配给开发商公钥
	 * @return 布尔值
	 */
	public static boolean checkSign(String content, String sign, String publicKey) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(RSA);
			byte[] encodedKey = Base64.decodeBase64(publicKey);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
			Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
			signature.initVerify(pubKey);
			signature.update(content.getBytes(UTF_8));
			boolean bverify = signature.verify(Base64.decodeBase64(sign));
			return bverify;
		} catch (Exception e) {
			e.printStackTrace();
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
		// 1.随机生成一对公钥和私钥
		Map<String, String> keyMap = genKeyPair();
		String publicKey = keyMap.get(PUBLIC_KEY);
		String privateKey = keyMap.get(PRIVATE_KEY);
		System.out.println("随机生成的公钥为:" + publicKey);
		System.out.println("随机生成的私钥为:" + privateKey);
		System.out.println("######################");
		// 2.公钥加密字符串
		String messageEn = encrypt(message, publicKey);
		System.out.println("公钥加密后的字符串为:" + messageEn);
		// 3.私钥解密字符串
		String messageDe = decrypt(messageEn, privateKey);
		System.out.println("私钥解密后的字符串为:" + messageDe);
		System.out.println("######################");
		// 4.私钥签名字符串
		String signStr = sign(message, privateKey);
		System.out.println("私钥签名后的字符串为:" + signStr);
		// 5.公钥验签
		boolean isValid = checkSign(message, signStr, publicKey);
		System.out.println("公钥验签结果为:" + isValid);

	}

}
