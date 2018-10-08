package com.xn.hk.common.utils.cfg;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xn.hk.common.constant.Constant;

/**
 * 
 * @ClassName: SystemCfg
 * @Package: com.xn.hk.common.utils.cfg
 * @Description:初始化系统配置文件systemCfg.ini
 * @Author: wanlei
 * @Date: 2018年10月8日 下午5:16:25
 */
public class SystemCfg {
	/**
	 * 记录日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(SystemCfg.class);
	private static SystemCfg instance;

	private SystemCfg() {
		super();
	}

	/**
	 * 构造器私有化,单例模式建实例
	 * 
	 * @return
	 */
	public synchronized static SystemCfg getInstance() {
		if (instance == null) {
			instance = new SystemCfg();
		}
		return instance;
	}

	/**
	 * 读取systemCfg.ini中所有的配置信息(支持中文)
	 * 
	 * @return
	 */
	public Properties loadCfg() {
		InputStreamReader in = null;
		Properties prop = new Properties();
		// 使用流加载配置文件
		try {
			in = new InputStreamReader(SystemCfg.class.getClassLoader().getResourceAsStream(Constant.SYSTEM_CFG_INI),
					"UTF-8");
			prop.load(in);
		} catch (UnsupportedEncodingException e) {
			logger.error("初始化系统配置文件失败，原因为:" + e.getMessage());
		} catch (IOException e) {
			logger.error("初始化系统配置文件失败，原因为:" + e.getMessage());
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				logger.error("关闭字符流失败，原因为:" + e.getMessage());
			}
		}
		return prop;
	}

}
