package com.xn.hk.common.utils.snmp;

import org.snmp4j.CommunityTarget;
import org.snmp4j.Target;
import org.snmp4j.UserTarget;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.SecurityLevel;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OctetString;

/**
 * 
 * @ClassName: CommunityTargetHelper
 * @Package: com.xn.hk.common.utils.snmp
 * @Description: 创建snmp中的CommunityTarget工具类
 * @Author: wanlei
 * @Date: 2018年9月29日 下午4:46:23
 */
final class CommunityTargetHelper {

	/**
	 * snmp V2
	 */
	protected static CommunityTarget createDefaultCommunityTarget(String targetAddress) {
		Address address = GenericAddress.parse(targetAddress + "/" + "161");
		CommunityTarget target = new CommunityTarget();
		target.setCommunity(new OctetString("public"));
		target.setAddress(address);
		target.setRetries(2);
		target.setTimeout(1500);
		target.setVersion(SnmpConstants.version2c);
		return target;
	}

	/**
	 * snmp V3
	 */
	protected static Target createDefaultCommunityTargetAsV3(String targetAddress) {
		Address address = GenericAddress.parse(targetAddress + "/" + "161");
		UserTarget target = new UserTarget();
		target.setSecurityName(new OctetString("dky"));
		target.setSecurityLevel(SecurityLevel.AUTH_PRIV);
		target.setAddress(address);
		target.setRetries(2);
		target.setTimeout(1500);
		target.setVersion(SnmpConstants.version3);
		return target;
	}

}
