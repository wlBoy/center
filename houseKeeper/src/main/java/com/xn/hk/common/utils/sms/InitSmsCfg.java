package com.xn.hk.common.utils.sms;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xn.hk.common.constant.Constant;

/**
 * 
 * @ClassName: InitSmsCfg
 * @Package: com.xn.hk.common.utils.sms
 * @Description: 初始化发送短信的配置文件
 * @Author: wanlei
 * @Date: 2018年9月29日 下午8:54:07
 */
public class InitSmsCfg {
	private static final Logger logger = LoggerFactory.getLogger(InitSmsCfg.class);
	// 中国网建SMS短信通SDK中的key(官方固定的)
	public static final String UID = "Uid";
	public static final String KEY = "Key";
	public static final String SMSMOB = "smsMob";
	public static final String SMSTEXT = "smsText";

	// 存储sms的所有配置
	public static Map<String, Object> cfgMap = new HashMap<String, Object>();
	static {
		Properties prop = new Properties();
		// 使用流加载配置文件
		InputStream in = InitSmsCfg.class.getClassLoader().getResourceAsStream("sms.properties");
		try {
			prop.load(in);
			cfgMap.put(Constant.SMS_MOBILE, prop.getProperty(Constant.SMS_MOBILE));
			cfgMap.put(Constant.SMS_PASSWORD, prop.getProperty(Constant.SMS_PASSWORD));
			cfgMap.put(Constant.SMS_URL, prop.getProperty(Constant.SMS_URL));
			cfgMap.put(Constant.SMS_USERNAME, prop.getProperty(Constant.SMS_USERNAME));
			cfgMap.put(Constant.ENABLE_SMS, prop.getProperty(Constant.ENABLE_SMS));
		} catch (IOException e) {
			logger.error("初始化SMS短信配置失败，原因为:" + e.getMessage());
		}
	}
}
