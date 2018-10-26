package com.xn.hk.common.utils.snmp;

/**
 * 
 * @ClassName: OidUtil
 * @Package: com.xn.hk.common.utils.snmp
 * @Description:Oid工具类
 * @Author: wanlei
 * @Date: 2018年9月29日 下午4:44:38
 */
public class OidUtil {

	public static final String NET_SNMP_EXTEND_BASE = "1.3.6.1.4.1.8072.1.3.2.";
	public static final String NET_SNMP_EXTEND_nsExtendCacheTime_BASE = NET_SNMP_EXTEND_BASE + "2.1.5.";
	public static final String NET_SNMP_EXTEND_nsExtendOutputFull_BASE = NET_SNMP_EXTEND_BASE + "3.1.2.";

	private static String toOIDSeq(String str) {
		StringBuilder sb = new StringBuilder();
		int length = str.length();
		for (int i = 0; i < length; i++) {
			sb.append("." + (int) str.charAt(i));
		}
		return sb.toString();
	}

	public static String genCacheTimeOID(String name) {
		return NET_SNMP_EXTEND_nsExtendCacheTime_BASE + name.length() + toOIDSeq(name);
	}

	public static String genOutputFullOID(String name) {
		return NET_SNMP_EXTEND_nsExtendOutputFull_BASE + name.length() + toOIDSeq(name);
	}

	public static String reverseOutputFullOIDToName(String oid) {
		String subOid = oid.substring(NET_SNMP_EXTEND_nsExtendOutputFull_BASE.length());
		subOid = subOid.substring(subOid.indexOf(".") + 1);// 去掉name.length
		String[] subOidArray = subOid.split("\\.");
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < subOidArray.length; i++) {
			res.append((char) Integer.parseInt(subOidArray[i]));
		}
		return res.toString();
	}
}
