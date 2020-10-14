package com.xn.hk.common.utils.encryption.sm;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;

import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.gm.GMObjectIdentifiers;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;

import com.xn.hk.common.constant.Constant;

/**
 * 
 * @ClassName: Sm2SignUtil
 * @Package: com.xn.hk.common.utils.encryption.sm
 * @Description: sm2算法签名验证工具类
 * @Author: wanlei
 * @Date: 2020年10月14日 上午11:02:44
 */
public class Sm2SignUtil {
	/**
	 * 签名
	 */
	public static Signature signature;
	/**
	 * 密钥对
	 */
	public static KeyPair keyPair;

	/**
	 * 初始化sm2签名和创建密钥对
	 */
	static {
		try {
			signature = Signature.getInstance(GMObjectIdentifiers.sm2sign_with_sm3.toString(),
					new BouncyCastleProvider());
			createKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建sm2密钥对
	 */
	public static void createKeyPair() {
		// 获取SM2 椭圆曲线推荐参数
		X9ECParameters ecParameters = GMNamedCurves.getByName("sm2p256v1");
		// 构造EC 算法参数
		ECNamedCurveParameterSpec sm2Spec = new ECNamedCurveParameterSpec(
				// 设置SM2 算法的 OID
				GMObjectIdentifiers.sm2p256v1.toString()
				// 设置曲线方程
				, ecParameters.getCurve()
				// 椭圆曲线G点
				, ecParameters.getG()
				// 大整数N
				, ecParameters.getN());
		try {
			// 创建 密钥对生成器
			KeyPairGenerator gen = KeyPairGenerator.getInstance("EC", new BouncyCastleProvider());
			// 使用SM2的算法区域初始化密钥生成器
			gen.initialize(sm2Spec, new SecureRandom());
			// 获取密钥对
			keyPair = gen.generateKeyPair();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 签名
	 * 
	 * @param privateKey
	 *            签名私钥
	 * @param plainText
	 *            明文
	 * @return
	 */
	public static String sign(PrivateKey privateKey, String plainText) {

		try {
			signature.initSign(privateKey);
			signature.update(plainText.getBytes());
			byte[] bytes = signature.sign();
			return new String(bytes, 0, bytes.length, Constant.ISO_8859_1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 验证签名
	 * 
	 * @param publicKey
	 *            签名公钥
	 * @param signResult
	 *            签名结果
	 * @param plainText
	 *            明文
	 * @return
	 */
	public static boolean verify(PublicKey publicKey, byte[] signResult, String plainText) {
		boolean result = false;
		try {
			signature.initVerify(publicKey);
			signature.update(plainText.getBytes());
			result = signature.verify(signResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String plainText = "123456";
		try {
			String signResult = sign(keyPair.getPrivate(), plainText);
			System.out.println("签名之后的字符串为:" + signResult);
			boolean result = verify(keyPair.getPublic(), signResult.getBytes(Constant.ISO_8859_1), plainText);
			System.out.println("验签结果为:" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}