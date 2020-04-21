package com.kl.ar.common.cfg;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kl.ar.common.constant.Constant;

/**
 * 
 * @package:com.kl.ar.common.cfg
 * @Description:读取config.properties配置文件的配置
 * @author: wanlei
 * @date: 2019年12月21日下午2:01:55
 */

public class ConfigUtil {
	/**
	 * 记录日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(ConfigUtil.class);
	private static ConfigUtil instance;

	private ConfigUtil() {
		super();
	}

	/**
	 * 构造器私有化,单例模式建实例，synchronized线程安全
	 * 
	 * @return 实例对象
	 */
	public synchronized static ConfigUtil getInstance() {
		if (instance == null) {
			instance = new ConfigUtil();
		}
		return instance;
	}

	/**
	 * 加载配置文件
	 * 
	 * @return
	 */
	private Properties loadCfg() {
		InputStreamReader in = null;
		Properties prop = new Properties();
		// 使用流加载配置文件
		try {
			in = new InputStreamReader(ConfigUtil.class.getClassLoader().getResourceAsStream(Constant.CONFIG_PROPERTIES),
					Constant.UTF8);
			prop.load(in);
		} catch (Exception e) {
			logger.error("初始化配置文件失败，原因为:{}", e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				logger.error("关闭字符输入流失败，原因为:{}", e);
			}
		}
		return prop;
	}

	/**
	 * 获取所有配置的map集合
	 * 
	 * @return
	 */
	public Map<String, String> loadCfgMap() {
		Map<String, String> cfgMap = new HashMap<String, String>();
		Properties prop = loadCfg();
		for (Object key : prop.stringPropertyNames()) {
			cfgMap.put(String.valueOf(key), prop.getProperty(String.valueOf(key)));
		}
		return cfgMap;
	}

}
