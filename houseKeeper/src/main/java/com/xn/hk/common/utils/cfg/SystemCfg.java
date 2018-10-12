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
 * @ClassName: SystemCfg
 * @Package: com.xn.hk.common.utils.cfg
 * @Description:初始化系统配置文件systemCfg.ini的操作类
 * @Author: wanlei
 * @Date: 2018年10月8日 下午5:16:25
 */
public class SystemCfg {
	/**
	 * 记录日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(SystemCfg.class);
	private static final String INI_FILE_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "main" + File.separator + "resources" + File.separator + CfgConstant.REALTIME_SYSTEM_CFG_INI;
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
	 * 方式一:返回Properties实体,读取systemCfg.ini中所有的配置信息(支持中文)
	 * 
	 * @return 返回Properties实体
	 */
	public Properties loadCfg() {
		InputStreamReader in = null;
		Properties prop = new OrderedProperties();
		// 使用流加载配置文件
		try {
			in = new InputStreamReader(
					SystemCfg.class.getClassLoader().getResourceAsStream(CfgConstant.REALTIME_SYSTEM_CFG_INI), Constant.UTF8);
			prop.load(in);
			logger.info("读取系统配置文件成功，共{}项配置", prop.size());
		} catch (UnsupportedEncodingException e) {
			logger.error("初始化系统配置文件失败，原因为:{}", e.getMessage());
		} catch (IOException e) {
			logger.error("初始化系统配置文件失败，原因为:{}", e.getMessage());
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
	 * 方式二:返回配置信息map集合,读取systemCfg.ini中所有的配置信息(支持中文)
	 * 
	 * @return 返回配置信息map集合
	 */
	public Map<String, String> loadCfgMap() {
		Map<String, String> cfgMap = new HashMap<String, String>();
		Properties prop = loadCfg();
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
	 */
	public void saveCfg(Map<String, Object> cfgMap) {
		if (cfgMap.isEmpty()) {
			logger.error("保存的配置信息map不能为空!");
			return;
		}
		Properties prop = loadCfg();
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(new File(INI_FILE_PATH), false);// //true表示追加打开,false每次都是清空再重写
			Set<String> keys = cfgMap.keySet();
			for (String key : keys) {
				prop.setProperty(key, String.valueOf(cfgMap.get(key)));
			}
			// 保存系统配置文件，第二个参数为备注，可不传
			prop.store(new OutputStreamWriter(out, Constant.UTF8), null);
			logger.info("保存系统配置文件成功，共改变了{}项配置", cfgMap.size());
		} catch (FileNotFoundException e) {
			logger.error("路径{}下找不到系统配置文件，原因为:{}", INI_FILE_PATH, e.getMessage());
		} catch (IOException e) {
			logger.error("保存系统配置文件失败，原因为:{}", e.getMessage());
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				logger.error("关闭字符输出流失败，原因为:{}", e.getMessage());
			}
		}

	}

	public static void main(String[] args) throws IOException {
		System.out.println("-----测试保存配置文件方法-----");
		Map<String, Object> cfgMap = new HashMap<String, Object>();
		cfgMap.put(CfgConstant.KEY_STORE, "测试位置");
		cfgMap.put(CfgConstant.LDAP_URL, "10.0.41.11");
		cfgMap.put(CfgConstant.AD_PASSWORD, "test123.");
		SystemCfg.getInstance().saveCfg(cfgMap);
		System.out.println("-----测试读取配置文件方法-----");
		Map<String, String> cfgMap1 = SystemCfg.getInstance().loadCfgMap();
		for (String key : cfgMap1.keySet()) {
			System.out.println(key + "=" + cfgMap1.get(key));
		}
	}
}
