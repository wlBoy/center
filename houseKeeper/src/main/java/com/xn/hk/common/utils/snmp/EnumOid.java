package com.xn.hk.common.utils.snmp;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @ClassName: EnumOid
 * @Package: com.xn.hk.common.utils.snmp
 * @Description: oid枚举类
 * @Author: wanlei
 * @Date: 2018年9月29日 下午4:45:37
 */
public enum EnumOid {
	
	CPU(EnumStatusItem.CPU, "1.3.6.1.4.1.2021.11.9.0"), // CPU使用率
	TOTALMEMORY(EnumStatusItem.TOTALMEMORY, "1.3.6.1.4.1.2021.4.5.0"), // Total RAM in machine
	NETWORKFLOW(EnumStatusItem.NETWORKFLOW, "1.3.6.1.2.1.2.2.1.10.2"), // 接口收到的总字节数(累积量)
	TOTALMEMORYUSED(EnumStatusItem.TOTALMEMORYUSED, "1.3.6.1.4.1.2021.4.6.0"), // Total RAM used
	TOTALMEMORYFREE(EnumStatusItem.TOTALMEMORYFREE, "1.3.6.1.4.1.2021.4.11.0"), // Total RAM Free = Total RAM in machine
	DISK(EnumStatusItem.DISK, "1.3.6.1.4.1.2021.9.1.9.1"), // 硬盘使用率
	SYSUPTIME(EnumStatusItem.SYSUPTIME, "1.3.6.1.2.1.1.3.0"),// 设备运行时间
	;
	
	private EnumStatusItem item;
	private String oidValue;

	private EnumOid(EnumStatusItem item, String oidValue) {
		this.item = item;
		this.oidValue = oidValue;
	}

	/**
	 * @return the oidKey
	 */
	public EnumStatusItem getEnumStatusItem() {
		return item;
	}

	/**
	 * @return the oidValue
	 */
	public String getOidValue() {
		return oidValue;
	}

	/**
	 * 通过枚举状态项拿到对应的OID枚举
	 * 
	 * @param item
	 * @return
	 */
	public static EnumOid getEnumOidWithEnumStatusItem(EnumStatusItem item) {
		EnumOid[] values = EnumOid.values();
		for (EnumOid value : values) {
			if (value.getEnumStatusItem().equals(item)) {
				return value;
			}
		}
		return null;
	}

	/**
	 * 通过OID值拿到对应的OID枚举
	 * 
	 * @param oidValue
	 * @return
	 */
	public static EnumOid getEnumOidWithOidValue(String oidValue) {
		EnumOid[] values = EnumOid.values();
		for (EnumOid value : values) {
			if (value.getOidValue().equals(oidValue)) {
				return value;
			}
		}
		return null;
	}

	public static List<EnumOid> translate(List<EnumStatusItem> items) {
		List<EnumOid> oids = new ArrayList<>();
		for (EnumStatusItem item : items) {
			oids.add(getEnumOidWithEnumStatusItem(item));
		}
		return oids;
	}

}

/**
 * 常用Oid
服务器负载：
1 minute Load: .1.3.6.1.4.1.2021.10.1.3.1

5 minute Load: .1.3.6.1.4.1.2021.10.1.3.2

15 minute Load: .1.3.6.1.4.1.2021.10.1.3.3

CPU信息：
percentage of user CPU time: .1.3.6.1.4.1.2021.11.9.0

raw user cpu time: .1.3.6.1.4.1.2021.11.50.0

percentages of system CPU time: .1.3.6.1.4.1.2021.11.10.0

raw system cpu time: .1.3.6.1.4.1.2021.11.52.0

percentages of idle CPU time: .1.3.6.1.4.1.2021.11.11.0

raw idle cpu time: .1.3.6.1.4.1.2021.11.53.0

raw nice cpu time: .1.3.6.1.4.1.2021.11.51.0

内存使用：
Total Swap Size: .1.3.6.1.4.1.2021.4.3.0

Available Swap Space: .1.3.6.1.4.1.2021.4.4.0

Total RAM in machine: .1.3.6.1.4.1.2021.4.5.0

Total RAM used: .1.3.6.1.4.1.2021.4.6.0

Total RAM Free: .1.3.6.1.4.1.2021.4.11.0

Total RAM Shared: .1.3.6.1.4.1.2021.4.13.0

Total RAM Buffered: .1.3.6.1.4.1.2021.4.14.0

Total Cached Memory: .1.3.6.1.4.1.2021.4.15.0

硬盘使用：
Path where the disk is mounted: .1.3.6.1.4.1.2021.9.1.2.1

Path of the device for the partition: .1.3.6.1.4.1.2021.9.1.3.1

Total size of the disk/partion (kBytes): .1.3.6.1.4.1.2021.9.1.6.1

Available space on the disk: .1.3.6.1.4.1.2021.9.1.7.1

Used space on the disk: .1.3.6.1.4.1.2021.9.1.8.1

Percentage of space used on disk: .1.3.6.1.4.1.2021.9.1.9.1

Percentage of inodes used on disk: .1.3.6.1.4.1.2021.9.1.10.1

系统运行时间：
.1.3.6.1.2.1.1.3.0
 * */
