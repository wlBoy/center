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
	// 取出配置文件中sms编码
	public static final String SMS_CHARACTER_CODING = String
			.valueOf(InitSmsCfg.cfgMap.get(Constant.SMS_CHARACTER_CODING)).toLowerCase();
	private static final String SMS_URL = "http://" + SMS_CHARACTER_CODING + ".sms.webchinese.cn";
	// 中国网建SMS短信通SDK中的key(官方固定的)
	public static final String UID = "Uid";
	public static final String KEY = "Key";
	public static final String SMSMOB = "smsMob";
	public static final String SMSTEXT = "smsText";
	public static final String CONTENT_TYPE = "Content-Type";

	// 存储sms的所有配置
	public static Map<String, Object> cfgMap = new HashMap<String, Object>();
	static {
		Properties prop = new Properties();
		// 使用流加载配置文件
		InputStream in = InitSmsCfg.class.getClassLoader().getResourceAsStream("sms.properties");
		try {
			prop.load(in);
			cfgMap.put(Constant.SMS_URL, SMS_URL);
			cfgMap.put(Constant.SMS_CHARACTER_CODING, prop.getProperty(Constant.SMS_CHARACTER_CODING));
			cfgMap.put(Constant.SMS_MOBILE, prop.getProperty(Constant.SMS_MOBILE));
			cfgMap.put(Constant.SMS_PASSWORD, prop.getProperty(Constant.SMS_PASSWORD));
			cfgMap.put(Constant.SMS_USERNAME, prop.getProperty(Constant.SMS_USERNAME));
			cfgMap.put(Constant.ENABLE_SMS, prop.getProperty(Constant.ENABLE_SMS));
		} catch (IOException e) {
			logger.error("初始化SMS短信配置失败，原因为:" + e.getMessage());
		}
	}
}
