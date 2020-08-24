package com.xn.hk.common.utils.encryption.sm;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;

import org.bouncycastle.crypto.DerivationFunction;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.ShortenedDigest;
import org.bouncycastle.crypto.generators.KDF1BytesGenerator;
import org.bouncycastle.crypto.params.ISO18033KDFParameters;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;

/**
 * 
 * @ClassName: Sm2Util
 * @Package: com.xn.hk.common.utils.encryption
 * @Description: SM2的非对称加解密工具类，椭圆曲线方程为：y^2=x^3+ax+b 使用Fp-256
 * @Author: wanlei
 * @Date: 2020年5月14日 下午4:14:28
 */
@SuppressWarnings("deprecation")
public class Sm2Util {
	/** 素数p */
	private static final BigInteger p = new BigInteger(
			"FFFFFFFE" + "FFFFFFFF" + "FFFFFFFF" + "FFFFFFFF" + "FFFFFFFF" + "00000000" + "FFFFFFFF" + "FFFFFFFF", 16);
	/** 系数a */
	private static final BigInteger a = new BigInteger(
			"FFFFFFFE" + "FFFFFFFF" + "FFFFFFFF" + "FFFFFFFF" + "FFFFFFFF" + "00000000" + "FFFFFFFF" + "FFFFFFFC", 16);
	/** 系数b */
	private static final BigInteger b = new BigInteger(
			"28E9FA9E" + "9D9F5E34" + "4D5A9E4B" + "CF6509A7" + "F39789F5" + "15AB8F92" + "DDBCBD41" + "4D940E93", 16);
	/** 坐标x */
	private static final BigInteger xg = new BigInteger(
			"32C4AE2C" + "1F198119" + "5F990446" + "6A39C994" + "8FE30BBF" + "F2660BE1" + "715A4589" + "334C74C7", 16);
	/** 坐标y */
	private static final BigInteger yg = new BigInteger(
			"BC3736A2" + "F4F6779C" + "59BDCEE3" + "6B692153" + "D0A9877C" + "C62A4740" + "02DF32E5" + "2139F0A0", 16);
	/** 基点G, G=(xg,yg),其介记为n */
	private static final BigInteger n = new BigInteger(
			"FFFFFFFE" + "FFFFFFFF" + "FFFFFFFF" + "FFFFFFFF" + "7203DF6B" + "21C6052B" + "53BBF409" + "39D54123", 16);

	private static SecureRandom random = new SecureRandom();
	private static ECCurve.Fp curve = new ECCurve.Fp(p, a, b);
	private static ECPoint G = curve.createPoint(xg, yg);

	/**
	 * 获得公私钥对
	 * 
	 * @return
	 */
	public static Sm2KeyPair generateKeyPair() {
		BigInteger d = random(n.subtract(new BigInteger("1")));
		Sm2KeyPair keyPair = new Sm2KeyPair(G.multiply(d).normalize(), d);
		if (checkPublicKey(keyPair.getPublicKey())) {
			return keyPair;
		} else {
			return null;
		}
	}

	/**
	 * 产生一个随机BigInteger数
	 * 
	 * @param max
	 * @return
	 */
	private static BigInteger random(BigInteger max) {
		BigInteger r = new BigInteger(256, random);
		while (r.compareTo(max) >= 0) {
			r = new BigInteger(128, random);
		}
		return r;
	}

	/**
	 * 公钥加密
	 * 
	 * @param input     待加密消息
	 * @param publicKey 公钥
	 * @return byte[] 加密后的字节数组
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] encrypt(String input, ECPoint publicKey) throws UnsupportedEncodingException {
		byte[] inputBuffer = input.getBytes();
		hexString(inputBuffer);
		/* 1 产生随机数k，k属于[1, n-1] */
		BigInteger k = random(n);
		hexString(k.toByteArray());
		/* 2 计算椭圆曲线点C1 = [k]G = (x1, y1) */
		ECPoint C1 = G.multiply(k);
		byte[] C1Buffer = C1.getEncoded(false);
		hexString(C1Buffer);
		// 3 计算椭圆曲线点 S = [h]Pb * curve没有指定余因子，h为空
		// BigInteger h = curve.getCofactor(); System.out.print("h: ");
		// printHexString(h.toByteArray()); if (publicKey != null) { ECPoint
		// result = publicKey.multiply(h); if (!result.isInfinity()) {
		// System.out.println("pass"); } else {
		// System.err.println("计算椭圆曲线点 S = [h]Pb失败"); return null; } }
		/* 4 计算 [k]PB = (x2, y2) */
		ECPoint kpb = publicKey.multiply(k).normalize();
		/* 5 计算 t = KDF(x2||y2, klen) */
		byte[] kpbBytes = kpb.getEncoded(false);
		DerivationFunction kdf = new KDF1BytesGenerator(new ShortenedDigest(new SHA256Digest(), 20));
		byte[] t = new byte[inputBuffer.length];
		kdf.init(new ISO18033KDFParameters(kpbBytes));
		kdf.generateBytes(t, 0, t.length);
		/* 6 计算C2=M^t */
		byte[] C2 = new byte[inputBuffer.length];
		for (int i = 0; i < inputBuffer.length; i++) {
			C2[i] = (byte) (inputBuffer[i] ^ t[i]);
		}
		/* 7 计算C3 = Hash(x2 || M || y2) */
		byte[] C3 = calculateHash(kpb.getXCoord().toBigInteger(), inputBuffer, kpb.getYCoord().toBigInteger());
		/* 8 输出密文 C=C1 || C2 || C3 */
		byte[] encryptResult = new byte[C1Buffer.length + C2.length + C3.length];
		System.arraycopy(C1Buffer, 0, encryptResult, 0, C1Buffer.length);
		System.arraycopy(C2, 0, encryptResult, C1Buffer.length, C2.length);
		System.arraycopy(C3, 0, encryptResult, C1Buffer.length + C2.length, C3.length);
		System.out.println("公钥加密之后的字符串为:" + hexString(encryptResult));
		return encryptResult;
	}

	/**
	 * 私钥解密
	 * 
	 * @param encryptStr
	 * @param privateKey
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String decrypt(byte[] encryptData, BigInteger privateKey) throws UnsupportedEncodingException {
		byte[] C1Byte = new byte[65];
		// byte[] encryptData = encryptStr.getBytes();
		System.arraycopy(encryptData, 0, C1Byte, 0, C1Byte.length);
		ECPoint C1 = curve.decodePoint(C1Byte).normalize();
		/* 计算[dB]C1 = (x2, y2) */
		ECPoint dBC1 = C1.multiply(privateKey).normalize();
		/* 计算t = KDF(x2 || y2, klen) */
		byte[] dBC1Bytes = dBC1.getEncoded(false);
		DerivationFunction kdf = new KDF1BytesGenerator(new ShortenedDigest(new SHA256Digest(), 20));
		int klen = encryptData.length - 65 - 20;
		byte[] t = new byte[klen];
		kdf.init(new ISO18033KDFParameters(dBC1Bytes));
		kdf.generateBytes(t, 0, t.length);
		/* 5 计算M'=C2^t */
		byte[] M = new byte[klen];
		for (int i = 0; i < M.length; i++) {
			M[i] = (byte) (encryptData[C1Byte.length + i] ^ t[i]);
		}
		/* 6 计算 u = Hash(x2 || M' || y2) 判断 u == C3是否成立 */
		byte[] C3 = new byte[20];
		System.arraycopy(encryptData, encryptData.length - 20, C3, 0, 20);
		byte[] u = calculateHash(dBC1.getXCoord().toBigInteger(), M, dBC1.getYCoord().toBigInteger());
		if (Arrays.equals(u, C3)) {
			return new String(M);
		} else {
			System.out.println("u:" + hexString(u));
			System.out.println("C3:" + hexString(C3));
			System.out.println("SM2解密验证失败");
			return null;
		}
	}

	/**
	 * 计算hash值
	 * 
	 * @param x2
	 * @param M
	 * @param y2
	 * @return
	 */
	private static byte[] calculateHash(BigInteger x2, byte[] M, BigInteger y2) {
		ShortenedDigest digest = new ShortenedDigest(new SHA256Digest(), 20);
		byte[] buf = x2.toByteArray();
		digest.update(buf, 0, buf.length);
		digest.update(M, 0, M.length);
		buf = y2.toByteArray();
		digest.update(buf, 0, buf.length);
		buf = new byte[20];
		digest.doFinal(buf, 0);
		return buf;
	}

	/**
	 * 比较是否在范围之内
	 * 
	 * @param param
	 * @param min
	 * @param max
	 * @return
	 */
	private static boolean between(BigInteger param, BigInteger min, BigInteger max) {
		if (param.compareTo(min) >= 0 && param.compareTo(max) < 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 二进制转16进制字符串
	 * 
	 * @param b
	 * @return
	 */
	private static String hexString(byte[] b) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				builder.append('0' + hex);
				hex = '0' + hex;
			}
			builder.append(hex);
		}
		return builder.toString();
	}

	/**
	 * 公钥校验
	 * 
	 * @param publicKey 公钥
	 * @return boolean true或false
	 */
	private static boolean checkPublicKey(ECPoint publicKey) {
		if (!publicKey.isInfinity()) {
			BigInteger x = publicKey.getXCoord().toBigInteger();
			BigInteger y = publicKey.getYCoord().toBigInteger();
			if (between(x, new BigInteger("0"), p) && between(y, new BigInteger("0"), p)) {
				BigInteger xResult = x.pow(3).add(a.multiply(x)).add(b).mod(p);
				BigInteger yResult = y.pow(2).mod(p);
				if (yResult.equals(xResult) && publicKey.multiply(n).isInfinity()) {
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 * @throws UnsupportedEncodingException
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		// 字符串原文
		String message = "{\"code\":0,\"data\":{\"createTime\":\"2020-05-14 22:46:38\",\"isOk\":0,\"logContent\":\"[用户ID=1237678678,用户姓名=张三,角色名称=管理员,创建时间=2020-05-14 22:46:38,更新时间=2020-05-14 22:46:38]\",\"remark\":\"\",\"rememberMe\":\"\",\"role\":{\"createTime\":\"2020-05-14 22:46:38\",\"isOk\":0,\"logContent\":\"[角色ID=1234871,角色姓名=管理员,创建时间=2020-05-14 22:46:38,更新时间=2020-05-14 22:46:38]\",\"moduleName\":\"\",\"modules\":[],\"remark\":\"\",\"roleId\":1234871,\"roleName\":\"管理员\",\"updateTime\":\"2020-05-14 22:46:38\"},\"updateTime\":\"2020-05-14 22:46:38\",\"userFace\":\"\",\"userId\":1237678678,\"userName\":\"张三\",\"userPwd\":\"zhangsan12345678\",\"userState\":0,\"verifyCode\":\"\"},\"msg\":\"请求成功\"}";
		// String message = "12345678";
		// 1.随机生成一对公钥和私钥
		Sm2KeyPair keyPair = generateKeyPair();
		ECPoint publicKey = keyPair.getPublicKey();
		BigInteger privateKey = keyPair.getPrivateKey();
		System.out.println("随机生成的公钥为:" + publicKey);
		System.out.println("随机生成的私钥为:" + privateKey);
		System.out.println("######################");
		// 2.公钥加密字符串
		byte[] data = encrypt(message, publicKey);
		System.out.println("公钥加密后的字节数组为:" + Arrays.toString(data));
		// 3.私钥解密字符串
		String result = decrypt(data, privateKey);
		System.out.println("私钥解密后的字符串为:" + result);
	}

}