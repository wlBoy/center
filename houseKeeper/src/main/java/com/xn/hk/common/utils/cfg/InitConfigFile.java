package com.xn.hk.common.utils.cfg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xn.hk.common.constant.Constant;

/**
 * 
 * @ClassName: InitConfigFile
 * @Package: com.xn.hk.common.utils.cfg
 * @Description: 初始化配置文件的通用管理类
 * @Author: wanlei
 * @Date: 2018年10月18日 下午4:58:14
 */
public class InitConfigFile {
	/**
	 * 记录日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(InitConfigFile.class);
	private static final String SAVE_CFG_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "main" + File.separator + "resources" + File.separator;
	private static InitConfigFile instance;

	private InitConfigFile() {
		super();
	}

	/**
	 * 构造器私有化,单例模式建实例
	 * 
	 * @return
	 */
	public synchronized static InitConfigFile getInstance() {
		if (instance == null) {
			instance = new InitConfigFile();
		}
		return instance;
	}

	/**
	 * 方式一:返回Properties实体(支持中文)
	 * 
	 * @param cfgFileName
	 *            件名 配置文
	 * @return 返回Properties实体
	 */
	public Properties loadCfg(String cfgFileName) {
		InputStreamReader in = null;
		Properties prop = new OrderedProperties();
		// 使用流加载配置文件
		try {
			in = new InputStreamReader(SystemCfg.class.getClassLoader().getResourceAsStream(cfgFileName),
					Constant.UTF8);
			prop.load(in);
			logger.info("读取{}配置文件成功，共{}项配置", cfgFileName, prop.size());
		} catch (UnsupportedEncodingException e) {
			logger.error("初始化{}配置文件失败，原因为:{}", cfgFileName, e.getMessage());
		} catch (IOException e) {
			logger.error("初始化{}配置文件失败，原因为:{}", cfgFileName, e.getMessage());
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				logger.error("关闭字符输入流失败，原因为:{}", e.getMessage());
			}
		}
		return prop;
	}

	/**
	 * 方式二:返回配置信息map集合(支持中文)
	 * 
	 * @param cfgFileName
	 *            件名 配置文
	 * @return 返回配置信息map集合
	 */
	public Map<String, String> loadCfgMap(String cfgFileName) {
		Map<String, String> cfgMap = new HashMap<String, String>();
		Properties prop = loadCfg(cfgFileName);
		for (Object key : prop.stringPropertyNames()) {
			cfgMap.put(String.valueOf(key), prop.getProperty(String.valueOf(key)));
		}
		return cfgMap;
	}

	/**
	 * 将配置信息map中的配置信息保存至配置文件
	 * 
	 * @param cfgMap
	 *            配置信息map
	 * @return 保存成功返回true，否则返回false
	 */
	public boolean saveCfg(Map<String, Object> cfgMap, String cfgFileName) {
		boolean isSuccess = false;
		if (cfgMap.isEmpty()) {
			logger.error("保存的配置信息map不能为空!");
			return isSuccess;
		}
		// 保存配置信息的配置文件路径
		String saveCfgPath = SAVE_CFG_PATH + cfgFileName;
		Properties prop = loadCfg(cfgFileName);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(new File(saveCfgPath), false);// //true表示追加打开,false每次都是清空再重写
			Set<String> keys = cfgMap.keySet();
			for (String key : keys) {
				prop.setProperty(key, String.valueOf(cfgMap.get(key)));
			}
			// 保存配置文件，第二个参数为备注，可不传
			prop.store(new OutputStreamWriter(out, Constant.UTF8), null);
			isSuccess = true;
			logger.info("保存{}配置文件成功，共改变了{}项配置", cfgFileName, cfgMap.size());
		} catch (FileNotFoundException e) {
			logger.error("路径{}下找不到{}配置文件，原因为:{}", saveCfgPath, cfgFileName, e.getMessage());
		} catch (IOException e) {
			logger.error("保存{}配置文件失败，原因为:{}", cfgFileName, e.getMessage());
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				logger.error("关闭字符输出流失败，原因为:{}", e.getMessage());
			}
		}
		return isSuccess;
	}
}
