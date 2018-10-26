package com.xn.hk.common.utils.snmp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.PDU;
import org.snmp4j.ScopedPDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.MPv3;
import org.snmp4j.security.AuthSHA;
import org.snmp4j.security.PrivAES128;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.security.UsmUser;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TreeEvent;
import org.snmp4j.util.TreeUtils;

/**
 * 
 * @ClassName: SnmpManagerV3
 * @Package: com.xn.hk.common.utils.snmp
 * @Description: snmp管理操作类(snmp V2版本)
 * @Author: wanlei
 * @Date: 2018年9月29日 下午4:50:26
 */
public class SnmpManagerV3 {
	/**
	 * 记录日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(SnmpManagerV3.class);

	private Snmp snmp;

	private AtomicBoolean isReady = new AtomicBoolean(false);

	/**
	 * @return the isReady
	 */
	public boolean isReady() {
		return isReady.get();
	}

	/**
	 * 
	 * @param addrStr
	 *            the agent address
	 * @param port
	 *            the agent port
	 * @param community
	 *            the community name for access
	 * @throws IOException
	 */
	public SnmpManagerV3() {
		start();
	}

	private synchronized void start() {
		if (isReady()) {
			return;
		}
		TransportMapping<UdpAddress> transportMapping = null;
		try {
			transportMapping = new DefaultUdpTransportMapping();
		} catch (IOException e) {
			throw new RuntimeException("snmp初始化失败", e);
		}
		snmp = new Snmp(transportMapping);
		USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(MPv3.createLocalEngineID()), 0);
		SecurityModels.getInstance().addSecurityModel(usm);
		try {
			snmp.listen();
		} catch (IOException e) {
			throw new RuntimeException("snmp初始化失败", e);
		}
		snmp.getUSM().addUser(new OctetString("dky"), createDkyUsmUser());
		isReady.getAndSet(true);
	}

	private UsmUser createDkyUsmUser() {
		UsmUser user = new UsmUser(new OctetString("dky"), AuthSHA.ID, new OctetString("11111111"), PrivAES128.ID,
				new OctetString("11111111"));
		return user;
	}

	/**
	 * Close the snmp manager
	 * 
	 * @throws IOException
	 */
	public synchronized void close() throws IOException {
		snmp.close();
		isReady.getAndSet(false);
	}

	/**
	 * Walk MIB tree with the given oid as the root
	 * 
	 * @param oid
	 * @return
	 * @throws IOException
	 */
	public List<VariableBinding> walkAsList(String oid, String targetAddress) throws IOException {
		TreeUtils treeUtils = new TreeUtils(snmp, new DefaultPDUFactory());
		Target target = CommunityTargetHelper.createDefaultCommunityTargetAsV3(targetAddress);
		List<TreeEvent> events = treeUtils.getSubtree(target, new OID(oid));
		LinkedList<VariableBinding> resultList = new LinkedList<VariableBinding>();
		for (TreeEvent event : events) {
			if (event != null) {
				if (event.isError()) {
					logger.error("oid [" + oid + "] " + event.getErrorMessage());
				}
				VariableBinding[] varBindings = event.getVariableBindings();
				if (varBindings != null && varBindings.length > 0) {
					for (VariableBinding vb : varBindings) {
						resultList.add(vb);
					}
				}
			}
		}
		return resultList;
	}

	/**
	 * @param targetAddress
	 * @param Oids
	 *            提供状态点的oid，例如1.2.3.2.1.1
	 * @return 返回每个状态点oid对应的value值，以map方式返回，即map的key是参数Oids的每一个元素
	 * @throws IOException
	 */
	public Map<String, String> getStatusAsMapWithOids(String targetAddress, List<String> Oids) throws IOException {
		List<VariableBinding> resultList = new LinkedList<VariableBinding>();
		Map<String, String> resultMap = new HashMap<>();

		ScopedPDU pdu = new ScopedPDU();
		pdu.setType(PDU.GET);
		for (String oid : Oids) {
			pdu.add(new VariableBinding(new OID(oid)));
		}
		Target target = CommunityTargetHelper.createDefaultCommunityTargetAsV3(targetAddress);
		ResponseEvent event = snmp.send(pdu, target);
		if (null != event) {
			PDU response = event.getResponse();
			if (null != response) {
				int length = response.size();
				for (int i = 0; i < length; i++) {
					resultList.add(response.get(i));
				}
			} else {
				logger.error("TimeOut...");
			}
			for (Iterator<VariableBinding> iterator = resultList.iterator(); iterator.hasNext();) {
				VariableBinding value = iterator.next();
				resultMap.put(value.getOid().toString(), value.toValueString());
			}
		}
		return resultMap;
	}

	/**
	 * @param targetAddress
	 * @param enumOids
	 * @return Map<EnumStatusItem, String>
	 * @throws IOException
	 */
	public Map<EnumOid, String> getStatusAsMap(String targetAddress, List<EnumOid> enumOids) throws IOException {
		List<VariableBinding> resultList = new LinkedList<VariableBinding>();
		Map<EnumOid, String> resultMap = new HashMap<>();
		PDU pdu = new ScopedPDU();
		pdu.setType(PDU.GET);
		for (EnumOid enumOid : enumOids) {
			pdu.add(new VariableBinding(new OID(enumOid.getOidValue())));
		}
		Target target = CommunityTargetHelper.createDefaultCommunityTargetAsV3(targetAddress);
		ResponseEvent event = snmp.get(pdu, target);
		if (null != event) {
			PDU response = event.getResponse();
			if (null != response) {
				int length = response.size();
				for (int i = 0; i < length; i++) {
					resultList.add(response.get(i));
				}
			}
			for (Iterator<VariableBinding> iterator = resultList.iterator(); iterator.hasNext();) {
				VariableBinding value = iterator.next();
				EnumOid temp = EnumOid.getEnumOidWithOidValue(value.getOid().toDottedString());
				if (temp == null) {
					logger.error("-------can't find EnumOid with:" + value.getOid().toDottedString());
					continue;
				}
				resultMap.put(temp, value.toValueString());
			}
		}
		return resultMap;
	}

	/**
	 * @param targetAddress
	 * @param enumOids
	 * @return Map<EnumStatusItem, String>
	 * @throws IOException
	 */
	public Map<EnumStatusItem, String> getStatusAsMapWithItems(String targetAddress, EnumStatusItem[] items)
			throws IOException {
		List<VariableBinding> resultList = new LinkedList<VariableBinding>();
		Map<EnumStatusItem, String> resultMap = new HashMap<>();
		PDU pdu = new ScopedPDU();
		pdu.setType(PDU.GET);
		for (EnumStatusItem item : items) {
			pdu.add(new VariableBinding(new OID(EnumOid.getEnumOidWithEnumStatusItem(item).getOidValue())));
		}
		Target target = CommunityTargetHelper.createDefaultCommunityTargetAsV3(targetAddress);
		ResponseEvent event = snmp.get(pdu, target);
		if (null != event) {
			PDU response = event.getResponse();
			if (null != response) {
				int length = response.size();
				for (int i = 0; i < length; i++) {
					resultList.add(response.get(i));
				}
			}
			for (Iterator<VariableBinding> iterator = resultList.iterator(); iterator.hasNext();) {
				VariableBinding value = iterator.next();
				EnumOid temp = EnumOid.getEnumOidWithOidValue(value.getOid().toDottedString());
				if (temp == null) {
					logger.error("-------can't find EnumOid with:" + value.getOid().toDottedString());
					continue;
				}
				resultMap.put(temp.getEnumStatusItem(), value.toValueString());
			}
		}
		return resultMap;
	}

	/**
	 * Set integer value for the given oid
	 * 
	 * @param oid
	 * @param value
	 * @return
	 */
	public boolean setInteger(String targetAddress, String oid, int value) {
		try {
			return set(targetAddress, oid, new Integer32(value));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Set string value for the given oid
	 * 
	 * @param oid
	 * @param value
	 * @return
	 */
	public boolean setString(String targetAddress, String oid, String value) {
		try {
			return set(targetAddress, oid, new OctetString(value));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean set(String targetAddress, String oid, Variable variable) throws IOException {
		VariableBinding binding = new VariableBinding(new OID(oid), variable);
		PDU pdu = new ScopedPDU();
		pdu.setType(PDU.SET);
		pdu.add(binding);
		ResponseEvent event = snmp.set(pdu, CommunityTargetHelper.createDefaultCommunityTargetAsV3(targetAddress));
		if (event.getResponse().getErrorStatus() != PDU.noError) {
			logger.info("oid [" + oid + "] variable [" + variable.toString() + "] "
					+ event.getResponse().getErrorStatusText());
			return false;
		}
		return true;
	}

}
