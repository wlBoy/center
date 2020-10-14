package com.xn.hk.common.utils.encryption.sm;

import java.io.UnsupportedEncodingException;

/**
 * 
 * @ClassName: SM4KeyUtil
 * @Package: com.xn.hk.common.utils.encryption.sm
 * @Description: sm4Key工具类
 * @Author: wanlei
 * @Date: 2020年8月24日 上午11:56:53
 */
public class Sm4KeyUtil {
	/**
	 * 生成对称密钥=SM3(手机号+格尔软件)前16个字节
	 * 
	 * @param phoneNumber
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] generateKey(String phoneNumber) throws UnsupportedEncodingException {
		if (null == phoneNumber || phoneNumber.length() == 0) {
			throw new IllegalArgumentException();
		}
		String source = phoneNumber + "格尔软件";
		byte[] basekey = Sm3Util.hash(source.getBytes("UTF-8"));
		byte[] key = new byte[16];
		System.arraycopy(basekey, 0, key, 0, 16);
		return key;
	}
}
