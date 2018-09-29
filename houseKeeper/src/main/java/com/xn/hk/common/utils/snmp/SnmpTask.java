package com.xn.hk.common.utils.snmp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 
 * @ClassName: SnmpTask
 * @Package: com.xn.hk.common.utils.snmp
 * @Description: snmp监控设备的定时任务
 * @Author: wanlei
 * @Date: 2018年9月29日 下午5:17:40
 */
public class SnmpTask {
	private static String snmpAgentAdress = "192.168.229.130";// 被监控设备的IP地址，这里写死(以虚拟机中ubuntu18为例)，实际情况要从设备表中取出
	private static SnmpManager snmpManager = new SnmpManager();
	private static List<EnumStatusItem> items = new ArrayList<EnumStatusItem>();// 初始化要监听的字段
	static {
		items.add(EnumStatusItem.CPU);
		items.add(EnumStatusItem.DISK);
		items.add(EnumStatusItem.NETWORKFLOW);
		items.add(EnumStatusItem.SYSUPTIME);
		items.add(EnumStatusItem.TOTALMEMORY);
		items.add(EnumStatusItem.TOTALMEMORYFREE);
		items.add(EnumStatusItem.TOTALMEMORYUSED);
	}

	/**
	 * 打印出被监控设备的信息
	 * 
	 * @throws IOException
	 */
	public void moinitor() throws IOException {
		System.out.println("进入设备监控方法.....");
		Map<EnumStatusItem, String> resMap = snmpManager.getStatusAsMapWithItems(snmpAgentAdress, getItems());
		for (EnumStatusItem key : resMap.keySet()) {
			System.out.println(key.getName() + "-" + resMap.get(key));
		}
		;
	}

	/*
	 * 将要监听的字段list集合转换为数组
	 */
	private EnumStatusItem[] getItems() {
		return items.toArray(new EnumStatusItem[items.size()]);
	}
}
