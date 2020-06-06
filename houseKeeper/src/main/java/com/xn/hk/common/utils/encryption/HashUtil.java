package com.xn.hk.common.utils.encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xn.hk.common.utils.cfg.SystemCfg;

/**
 * 
 * @Title: HashUtil
 * @Package: com.xn.hk.common.utils
 * @Description: hash算法加密工具类，支持MD5, SHA-1, SHA-256, SHA-512加密算法,是不可逆的加密算法
 * @Author: wanlei
 * @Date: 2018年1月8日 下午4:44:15
 */
public class HashUtil {
	private static final Logger logger = LoggerFactory.getLogger(HashUtil.class);
	private static final char[] HEXES = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };
	public static final String MD5 = "MD5";// MD5算法加密
	public static final String SHA1 = "SHA-1";// SHA-1算法加密
	public static final String SHA256 = "SHA-256";// SHA-256算法加密
	public static final String SHA512 = "SHA-512";// SHA-512算法加密

	/**
	 * 根据MD5加密任意长度的数据, 返回固定长度的十六进制小写哈希值
	 *
	 * @param str 需要加密的字符串
	 */
	public static String encryptStr(String str) {
		return encryptStr(str, MD5);
	}

	/**
	 * 根据指定的算法加密任意长度的数据, 返回固定长度的十六进制小写哈希值
	 *
	 * @param str       需要加密的字符串
	 * @param algorithm 加密算法, 例如: MD5, SHA-1, SHA-256, SHA-512 等
	 * @throws NoSuchAlgorithmException
	 */
	public static String encryptStr(String str, String algorithm) {
		String result = null;
		if (StringUtils.isEmpty(str)) {
			return result;
		}
		try {
			// 1. 根据算法名称获实现了算法的加密实例
			MessageDigest digest = MessageDigest.getInstance(algorithm);
			// 2. 加密数据, 计算数据的哈希值
			byte[] cipher = digest.digest(str.getBytes());
			// 3. 将结果转换为十六进制小写
			result = bytes2Hex(cipher);
		} catch (NoSuchAlgorithmException e) {
			logger.error("{}加密字符串失败，原因为:{}", algorithm, e);
		}
		return result;
	}

	/**
	 * 根据MD5加密文件数据, 返回固定长度的十六进制小写哈希值
	 *
	 * @param file 需要加密的文件
	 */
	public static String encryptFile(File file) {
		return encryptFile(file, MD5);
	}

	/**
	 * 根据指定的算法加密文件数据, 返回固定长度的十六进制小写哈希值
	 *
	 * @param file      需要加密的文件
	 * @param algorithm 加密算法, 例如: MD5, SHA-1, SHA-256, SHA-512 等
	 */
	public static String encryptFile(File file, String algorithm) {
		String result = null;
		if (!file.exists()) {
			logger.info("{}加密文件失败，{}文件不存在!", algorithm, file.getName());
			return result;
		}
		InputStream in = null;
		try {
			// 1. 根据算法名称获实现了算法的加密实例
			MessageDigest digest = MessageDigest.getInstance(algorithm);
			in = new FileInputStream(file);
			byte[] buf = new byte[1024];
			int len = -1;
			while ((len = in.read(buf)) != -1) {
				// 2. 文件数据通常比较大, 使用 update() 方法逐步添加
				digest.update(buf, 0, len);
			}
			// 3. 计算数据的哈希值, 添加完数据后 digest() 方法只能被调用一次
			byte[] cipher = digest.digest();
			// 4. 将结果转换为十六进制小写
			result = bytes2Hex(cipher);
		} catch (Exception e) {
			logger.error("{}加密文件失败，原因为:{}", algorithm, e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					logger.error("关闭流失败，原因为:{}", e);
				}
			}
		}
		return result;
	}

	/**
	 * 将结果转换为十六进制小写
	 * 
	 * @param bytes
	 * @return
	 */
	private static String bytes2Hex(byte[] bytes) {
		if (bytes == null || bytes.length == 0) {
			return null;
		}
		StringBuilder sb = new StringBuilder();

		for (byte b : bytes) {
			sb.append(HEXES[(b >> 4) & 0x0F]);
			sb.append(HEXES[b & 0x0F]);
		}
		return sb.toString();
	}

	/**
	 * 根据应用秘钥获取签名结果
	 * 
	 * @param appPwd        应用秘钥
	 * @param originData    签名原文
	 * @param signAlgorithm 签名算法
	 * @return 返回签名之后的结果
	 */
	public static String getSignResult(String appPwd, String originData, String signAlgorithm) {
		// 1.将拼接的参数进行ASCII排序
		String[] paramArray = new String[] {};
		if (StringUtils.isNotBlank(originData)) {
			String[] queryArray = originData.split("&");
			paramArray = (String[]) ArrayUtils.addAll(queryArray, paramArray);
		}
		Arrays.sort(paramArray);
		// 2.拼接待签名字符串，规则为=>应用秘钥:ASCII排序后的数组:应用秘钥
		StringBuffer buffer = new StringBuffer();
		buffer.append(appPwd);
		buffer.append(":");
		for (int i = 0; i < paramArray.length; i++) {
			buffer.append(paramArray[i]);
			buffer.append("&");
		}
		buffer.deleteCharAt(buffer.length() - 1);
		buffer.append(":");
		buffer.append(appPwd);
		System.out.println("签名原文为:【" + buffer.toString() + "】");
		// 3.对签名原文进行指定算法签名，返回结果
		return encryptStr(buffer.toString(), signAlgorithm);
	}

	/**
	 * 测试main方法
	 * 
	 * @param args
	 * @throws NoSuchAlgorithmException
	 */
	public static void main(String args[]) {
		System.out.println("加密之后的密码hash值为：" + encryptStr("admin" + SystemCfg.USER_PWD_KEY));
		System.out.println("加密之后的文件hash值为：" + encryptFile(new File("E:\\induction training\\公司内部资源.txt")));
		String originData = String.format("appKey=%s&ts=%s&signMethod=%s&once=%s", "1500288560477836",
				System.currentTimeMillis(), "SHA-256", "123456");
		String appPwd = "abcdefg";
		String signResult = getSignResult(appPwd, originData, "SHA-256");
		System.out.println("签名结果为:" + signResult);
	}
}
