package com.xn.hk.common.utils.snmp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xn.hk.common.utils.cfg.CfgConstant;
import com.xn.hk.common.utils.cfg.SystemCfg;

/**
 * 
 * @ClassName: SnmpTask
 * @Package: com.xn.hk.common.utils.snmp
 * @Description: snmp监控设备的定时任务
 * @Author: wanlei
 * @Date: 2018年9月29日 下午5:17:40
 */
public class SnmpTask {
	// 被监控设备(目前OID只支持linux,window的OID不一样)的IP地址，这里写死(以虚拟机中ubuntu18为例)，实际情况要从设备表中取出
	private static String snmpAgentAdress = SystemCfg.getInstance().loadCfg().getProperty(CfgConstant.MONITOR_IP);
	private static SnmpManager snmpManager = new SnmpManager();

	/**
	 * 打印出被监控设备的信息,实际情况应将监控信息存入数据库
	 * 
	 * @throws IOException
	 */
	public void moinitor() throws IOException {
		// 读取配置文件加载监听策略项，将list转换为数组
		List<EnumStatusItem> items = getMonitorItems();
		EnumStatusItem[] itemArray = items.toArray(new EnumStatusItem[items.size()]);
		Map<EnumStatusItem, String> resMap = snmpManager.getStatusAsMapWithItems(snmpAgentAdress, itemArray);
		System.out.println("监控设备信息如下:.....");
		for (EnumStatusItem key : resMap.keySet()) {
			System.out.println(key.getDesc() + "-->" + resMap.get(key));
		}
		;
	}

	/**
	 * 通过配置文件中的监听策略是否启动各信息的监控
	 * 
	 * @return
	 */
	private List<EnumStatusItem> getMonitorItems() {
		List<EnumStatusItem> items = new ArrayList<EnumStatusItem>();
		if (Boolean.valueOf(SystemCfg.getInstance().loadCfg().getProperty(CfgConstant.MONITOR_CPU))) {// 开启监听CPU
			items.add(EnumStatusItem.CPU);
		}
		if (Boolean.valueOf(SystemCfg.getInstance().loadCfg().getProperty(CfgConstant.MONITOR_DISK))) {// 开启监听硬盘
			items.add(EnumStatusItem.DISK);
		}
		if (Boolean.valueOf(SystemCfg.getInstance().loadCfg().getProperty(CfgConstant.MONITOR_NETWORK_FLOW))) {// 开启监听网络流量
			items.add(EnumStatusItem.NETWORKFLOW);
		}
		if (Boolean.valueOf(SystemCfg.getInstance().loadCfg().getProperty(CfgConstant.MONITOR_SERVICE_TIME))) {// 开启监听设备服务时间
			items.add(EnumStatusItem.SYSUPTIME);
		}
		if (Boolean.valueOf(SystemCfg.getInstance().loadCfg().getProperty(CfgConstant.MONITOR_MEMORY))) {// 开启监听内存
			items.add(EnumStatusItem.TOTALMEMORY);
			items.add(EnumStatusItem.TOTALMEMORYFREE);
			items.add(EnumStatusItem.TOTALMEMORYUSED);
		}
		return items;
	}

}
