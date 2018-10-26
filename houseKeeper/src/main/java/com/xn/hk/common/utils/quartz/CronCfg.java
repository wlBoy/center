package com.xn.hk.common.utils.quartz;

import java.util.Map;
import java.util.Properties;

import com.xn.hk.common.utils.cfg.InitConfigFile;

/**
 * 
 * @ClassName: CronCfg
 * @Package: com.xn.hk.common.utils.quartz
 * @Description: 定时任务的配置文件
 * @Author: wanlei
 * @Date: 2018年10月18日 下午5:23:01
 */
public class CronCfg {
	/**
	 * 系统配置文件名(原始的)
	 */
	public final static String CRON_CFG = "cron.properties";
	/**
	 * test定时任务时间配置
	 */
	public final static String TEST_TASK = "testTask";
	/**
	 * snmp定时任务时间配置
	 */
	public final static String snmp_TASK = "snmpTask";

	/**
	 * 方式一:返回Properties实体,读取cron.properties中所有的配置信息(支持中文)
	 * 
	 * @return 返回Properties实体
	 */
	public static Properties loadCfg() {
		return InitConfigFile.getInstance().loadCfg(CRON_CFG);
	}

	/**
	 * 方式二:返回配置信息map集合,读取cron.properties中所有的配置信息(支持中文)
	 * 
	 * @return 返回配置信息map集合
	 */
	public static Map<String, String> loadCfgMap() {
		return InitConfigFile.getInstance().loadCfgMap(CRON_CFG);
	}

	/**
	 * main测试方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("-----测试读取配置文件方法-----");
		Map<String, String> cfgMap = loadCfgMap();
		for (String key : cfgMap.keySet()) {
			System.out.println(key + "=" + cfgMap.get(key));
		}
	}
}
