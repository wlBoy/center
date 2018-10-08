package com.xn.hk.common.utils.ldap;

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
 * @ClassName: InitLdapCfg
 * @Package: com.xn.hk.common.utils.ldap
 * @Description: 初始化ldap配置
 * @Author: wanlei
 * @Date: 2018年9月29日 下午3:38:45
 */
public class InitLdapCfg {
	private static final Logger logger = LoggerFactory.getLogger(InitLdapCfg.class);
	// 存储LDAP的所有配置
	public static Map<String, Object> cfgMap = new HashMap<String, Object>();
	static {
		Properties prop = new Properties();
		// 使用流加载配置文件
		InputStream in = LdapOpt.class.getClassLoader().getResourceAsStream(Constant.SYSTEM_CFG_INI);
		try {
			prop.load(in);
			cfgMap.put(Constant.SECURITY_PRINCIPAL_KEY, prop.getProperty(Constant.SECURITY_PRINCIPAL_KEY));
			cfgMap.put(Constant.AD_PASSWORD_KEY, prop.getProperty(Constant.AD_PASSWORD_KEY));
			cfgMap.put(Constant.BASE_DN_KEY, prop.getProperty(Constant.BASE_DN_KEY));
			cfgMap.put(Constant.LDAP_URL_KEY, prop.getProperty(Constant.LDAP_URL_KEY));
			cfgMap.put(Constant.KEY_STORE_KEY, prop.getProperty(Constant.KEY_STORE_KEY));
			cfgMap.put(Constant.SSL_PASSWORD_KEY, prop.getProperty(Constant.SSL_PASSWORD_KEY));
			cfgMap.put(Constant.START_AUTO_PUSH_KEY, prop.getProperty(Constant.START_AUTO_PUSH_KEY));
			cfgMap.put(Constant.USE_SSL_KEY, prop.getProperty(Constant.USE_SSL_KEY));
			cfgMap.put(Constant.PUSH_PASSWORD_KEY, prop.getProperty(Constant.PUSH_PASSWORD_KEY));
			cfgMap.put(Constant.PASSWORD_CANT_CHANGE_KEY, prop.getProperty(Constant.PASSWORD_CANT_CHANGE_KEY));
		} catch (IOException e) {
			logger.error("初始化LDAP配置失败，原因为:" + e.getMessage());
		}
	}
}
