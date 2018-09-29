package com.xn.hk.common.utils.snmp;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.xn.hk.common.constant.Constant;

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
	private static String snmpAgentAdress = String.valueOf(MonitorPolicy.cfgMap.get(Constant.MONITOR_IP));
	private static SnmpManager snmpManager = new SnmpManager();

	/**
	 * 打印出被监控设备的信息,实际情况应将监控信息存入数据库
	 * 
	 * @throws IOException
	 */
	public void moinitor() throws IOException {
		// 读取配置文件加载监听策略项，将list转换为数组
		List<EnumStatusItem> items = MonitorPolicy.getMonitorItems();
		EnumStatusItem[] itemArray = items.toArray(new EnumStatusItem[items.size()]);
		Map<EnumStatusItem, String> resMap = snmpManager.getStatusAsMapWithItems(snmpAgentAdress, itemArray);
		System.out.println("监控设备信息如下:.....");
		for (EnumStatusItem key : resMap.keySet()) {
			System.out.println(key.getDesc() + "-->" + resMap.get(key));
		}
		;
	}

}
