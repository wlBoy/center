package com.xn.hk.common.utils.otp;

import java.util.concurrent.TimeUnit;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorConfig;
import com.warrenstrange.googleauth.IGoogleAuthenticator;
import com.warrenstrange.googleauth.KeyRepresentation;

public class OtpUtil {
	/**
	 * otp令牌长度
	 */
	private static final int CODE_DIGITS = 6;
	/**
	 * 服务端与客户端运行的时间偏差
	 */
	private static final int WINDOW_SIZE = 3;
	/**
	 * 动态令牌过期时间，单位：s
	 */
	private static final int OTP_EXPIRE_TIME = 30;

	private static OtpUtil instance;
	private static IGoogleAuthenticator googleAuthenticatorInstance;

	private OtpUtil() {
		super();
	}

	/**
	 * 构造器私有化,单例模式建实例，synchronized线程安全
	 * 
	 * @return 实例对象
	 */
	public synchronized static OtpUtil getInstance() {
		if (instance == null) {
			instance = new OtpUtil();
			if (googleAuthenticatorInstance == null) {
				googleAuthenticatorInstance = googleAuthenticatorInstance();
			}
		}
		return instance;
	}

	/**
	 * 谷歌的otp认证器
	 *
	 * @return
	 */
	public static IGoogleAuthenticator googleAuthenticatorInstance() {
		final GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder bldr = new GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder();
		bldr.setCodeDigits(CODE_DIGITS);
		bldr.setTimeStepSizeInMillis(TimeUnit.SECONDS.toMillis(OTP_EXPIRE_TIME));
		bldr.setWindowSize(WINDOW_SIZE);
		bldr.setKeyRepresentation(KeyRepresentation.BASE32);
		return new GoogleAuthenticator(bldr.build());
	}

	/**
	 * otp认证
	 * 
	 * @param otpSecret
	 *            otp秘钥
	 * @param otp
	 *            otp动态令牌数字
	 * @return 返回是否认证成功
	 */
	public boolean otpAuth(String otpSecret, int otp) {
		return googleAuthenticatorInstance.authorize(otpSecret, otp);
	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String otpSecret = "abcdefghijklmnop";
		int otp = 478590;
		System.out.println("otp认证结果为:" + OtpUtil.getInstance().otpAuth(otpSecret, otp));
	}
}
