package com.xn.hk.common.utils.encryption.sm;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

/**
 * 
 * @ClassName: SMBaseUtil
 * @Package: com.xn.hk.common.utils.encryption.sm
 * @Description: sm算法基础工具类
 * @Author: wanlei
 * @Date: 2020年8月24日 上午11:50:23
 */
public class SMBaseUtil {
	private static final double MIN_REQUIRED_VERSION = 1.59;
	static {
		if (null != Security.getProvider(BouncyCastleProvider.PROVIDER_NAME)) {
			double version = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME).getVersion();
			if (version < MIN_REQUIRED_VERSION) {
				Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
				Security.addProvider(new BouncyCastleProvider());
			}
		} else {
			Security.addProvider(new BouncyCastleProvider());
		}
	}
}
