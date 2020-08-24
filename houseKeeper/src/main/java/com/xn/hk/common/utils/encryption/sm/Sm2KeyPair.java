package com.xn.hk.common.utils.encryption.sm;

import java.math.BigInteger;
import org.bouncycastle.math.ec.ECPoint;

/**
 * 
 * @ClassName: Sm2KeyPair
 * @Package: com.xn.hk.common.utils.encryption
 * @Description: SM2公私钥实体类
 * @Author: wanlei
 * @Date: 2020年5月14日 下午4:14:28
 */
public class Sm2KeyPair {
	/**
	 * sm2公钥
	 */
	private ECPoint publicKey;
	/**
	 * sm2私钥
	 */
	private BigInteger privateKey;

	public Sm2KeyPair(ECPoint publicKey, BigInteger privateKey) {
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}

	public ECPoint getPublicKey() {
		return publicKey;
	}

	public BigInteger getPrivateKey() {
		return privateKey;
	}

	@Override
	public String toString() {
		return "Sm2KeyPair [publicKey=" + publicKey + ", privateKey=" + privateKey + "]";
	}

}