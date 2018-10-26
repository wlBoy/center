package com.xn.hk.common.utils.snmp;

/**
 * 
 * @ClassName: EnumStatusItem
 * @Package: com.xn.hk.common.utils.snmp
 * @Description: 监控项枚举类
 * @Author: wanlei
 * @Date: 2018年9月29日 下午4:42:40
 */
public enum EnumStatusItem {

	CPU("cpu", "CPU使用率"), DISK("disk", "硬盘使用率"), NETWORKFLOW("workflow", "总网络流量"), SYSUPTIME("systemRuntime",
			"设备运行时间"), TOTALMEMORY("total_RAM",
					"总内存"), TOTALMEMORYUSED("total_RAM_used", "已使用内存"), TOTALMEMORYFREE("total_RAM_free", "未使用内存");

	private String name;// 名称
	private String desc;// 描述语

	private EnumStatusItem(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * 根据名称拿到该枚举项
	 * 
	 * @param name
	 * @return
	 */
	public static EnumStatusItem getStatusItemWithName(String name) {
		EnumStatusItem[] values = EnumStatusItem.values();
		for (EnumStatusItem value : values) {
			if (value.name.equals(name)) {
				return value;
			}
		}
		return null;
	}

}
