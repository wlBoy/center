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
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
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
 * @ClassName: SnmpManager
 * @Package: com.xn.hk.common.utils.snmp
 * @Description: snmp管理操作类(snmp V2版本)
 * @Author: wanlei
 * @Date: 2018年9月29日 下午4:48:06
 */
public class SnmpManager {
	/**
	 * 记录日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(SnmpManager.class);

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
	public SnmpManager() {
		start();
	}

	private synchronized void start() {
		if (isReady()) {
			return;
		}
		TransportMapping<UdpAddress> transportMapping = null;
		try {
			transportMapping = new DefaultUdpTransportMapping();
			snmp = new Snmp(transportMapping);
			snmp.listen();
		} catch (IOException e) {
			logger.error("snmp初始化失败,原因为:{}", e);
		}
		isReady.getAndSet(true);
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
		Target target = CommunityTargetHelper.createDefaultCommunityTarget(targetAddress);
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
	 * Get values for each oid in the given array
	 * 
	 * @param oids
	 * @return
	 * @throws IOException
	 */
	public List<VariableBinding> getAsList(String targetAddress, String... oids) throws IOException {
		List<VariableBinding> resultList = new LinkedList<VariableBinding>();
		PDU pdu = new PDU();
		pdu.setType(PDU.GET);
		for (String oid : oids) {
			pdu.add(new VariableBinding(new OID(oid)));
		}
		Target target = CommunityTargetHelper.createDefaultCommunityTarget(targetAddress);
		ResponseEvent event = snmp.get(pdu, target);
		if (null != event) {
			PDU response = event.getResponse();
			if (null != response) {
				int length = response.size();
				for (int i = 0; i < length; i++) {
					resultList.add(response.get(i));
				}
			}
			logger.debug(resultList.toString());
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
		PDU pdu = new PDU();
		pdu.setType(PDU.GET);
		for (String oid : Oids) {
			pdu.add(new VariableBinding(new OID(oid)));
		}
		Target target = CommunityTargetHelper.createDefaultCommunityTarget(targetAddress);
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
				resultMap.put(value.getOid().toString(), value.toValueString());
			}
		}
		return resultMap;
	}

	/**
	 * @param targetAddress
	 * @param names
	 *            自定义的扩展oid名称，会被按照规则翻译为OID
	 * @return 以map方式返回，即map的key是参数names的每一个元素
	 * @throws IOException
	 */
	public Map<String, String> getStatusAsMapWithExtendsName(String targetAddress, String... names) throws IOException {
		List<VariableBinding> resultList = new LinkedList<VariableBinding>();
		Map<String, String> resultMap = new HashMap<>();
		PDU pdu = new PDU();
		pdu.setType(PDU.GET);
		for (String name : names) {
			pdu.add(new VariableBinding(new OID(OidUtil.genOutputFullOID(name))));
		}
		Target target = CommunityTargetHelper.createDefaultCommunityTarget(targetAddress);
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
				resultMap.put(OidUtil.reverseOutputFullOIDToName(value.getOid().toDottedString()),
						value.toValueString());
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
		PDU pdu = new PDU();
		pdu.setType(PDU.GET);
		for (EnumOid enumOid : enumOids) {
			pdu.add(new VariableBinding(new OID(enumOid.getOidValue())));
		}
		Target target = CommunityTargetHelper.createDefaultCommunityTarget(targetAddress);
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
	 * 使用UDP的get协议拿到个OID对应的值并封装数据map
	 * 
	 * @param targetAddress
	 * @param enumOids
	 * @return Map<EnumStatusItem, String>
	 * @throws IOException
	 */
	public Map<EnumStatusItem, String> getStatusAsMapWithItems(String targetAddress, EnumStatusItem[] items)
			throws IOException {
		List<VariableBinding> resultList = new LinkedList<VariableBinding>();
		Map<EnumStatusItem, String> resultMap = new HashMap<>();
		PDU pdu = new PDU();
		pdu.setType(PDU.GET);
		for (EnumStatusItem item : items) {
			pdu.add(new VariableBinding(new OID(EnumOid.getEnumOidWithEnumStatusItem(item).getOidValue())));
		}
		Target target = CommunityTargetHelper.createDefaultCommunityTarget(targetAddress);
		ResponseEvent event = snmp.get(pdu, target);// 基于snmp协议向target的地址发送UDP的get协议
		if (null != event) {
			PDU response = event.getResponse();// 各OID的响应数据
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
			logger.error("setInteger失败,原因为:{}", e);
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
			logger.error("setString失败,原因为:{}", e);
			return false;
		}
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

	private boolean set(String targetAddress, String oid, Variable variable) throws IOException {
		VariableBinding binding = new VariableBinding(new OID(oid), variable);
		PDU pdu = new PDU();
		pdu.setType(PDU.SET);
		pdu.add(binding);
		ResponseEvent event = snmp.set(pdu, CommunityTargetHelper.createDefaultCommunityTarget(targetAddress));
		if (event.getResponse().getErrorStatus() != PDU.noError) {
			logger.info("oid [" + oid + "] variable [" + variable.toString() + "] "
					+ event.getResponse().getErrorStatusText());
			return false;
		}
		return true;
	}

}
