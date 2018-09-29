package com.xn.hk.common.utils.snmp;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xn.hk.common.constant.Constant;

/**
 * 
 * @ClassName: MonitorPolicy
 * @Package: com.xn.hk.common.utils.snmp
 * @Description: 读取配置文件实现监听策略
 * @Author: wanlei
 * @Date: 2018年9月29日 下午7:36:21
 */
public class MonitorPolicy {
	private static final Logger logger = LoggerFactory.getLogger(MonitorPolicy.class);
	// 存储snmp监控的所有配置
	public static Map<String, Object> cfgMap = new HashMap<String, Object>();
	static {
		Properties prop = new Properties();
		// 使用流加载配置文件
		InputStream in = MonitorPolicy.class.getClassLoader().getResourceAsStream("snmp.properties");
		try {
			prop.load(in);
			cfgMap.put(Constant.MONITOR_CPU, prop.getProperty(Constant.MONITOR_CPU));
			cfgMap.put(Constant.MONITOR_DISK, prop.getProperty(Constant.MONITOR_DISK));
			cfgMap.put(Constant.MONITOR_MEMORY, prop.getProperty(Constant.MONITOR_MEMORY));
			cfgMap.put(Constant.MONITOR_NETWORK_FLOW, prop.getProperty(Constant.MONITOR_NETWORK_FLOW));
			cfgMap.put(Constant.MONITOR_SERVICE_TIME, prop.getProperty(Constant.MONITOR_SERVICE_TIME));
			cfgMap.put(Constant.MONITOR_IP, prop.getProperty(Constant.MONITOR_IP));
		} catch (IOException e) {
			logger.error("初始化snmp监控策略配置失败，原因为:" + e.getMessage());
		}
	}

	/**
	 * 通过配置文件中的监听策略是否启动各信息的监控
	 * 
	 * @return
	 */
	public static List<EnumStatusItem> getMonitorItems() {
		List<EnumStatusItem> items = new ArrayList<EnumStatusItem>();
		if (Boolean.valueOf(String.valueOf(cfgMap.get(Constant.MONITOR_CPU)))) {// 开启监听CPU
			items.add(EnumStatusItem.CPU);
		}
		if (Boolean.valueOf(String.valueOf(cfgMap.get(Constant.MONITOR_DISK)))) {// 开启监听硬盘
			items.add(EnumStatusItem.DISK);
		}
		if (Boolean.valueOf(String.valueOf(cfgMap.get(Constant.MONITOR_NETWORK_FLOW)))) {// 开启监听网络流量
			items.add(EnumStatusItem.NETWORKFLOW);
		}
		if (Boolean.valueOf(String.valueOf(cfgMap.get(Constant.MONITOR_SERVICE_TIME)))) {// 开启监听设备服务时间
			items.add(EnumStatusItem.SYSUPTIME);
		}
		if (Boolean.valueOf(String.valueOf(cfgMap.get(Constant.MONITOR_MEMORY)))) {// 开启监听内存
			items.add(EnumStatusItem.TOTALMEMORY);
			items.add(EnumStatusItem.TOTALMEMORYFREE);
			items.add(EnumStatusItem.TOTALMEMORYUSED);
		}
		return items;
	}

}
