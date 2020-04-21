package com.kl.ar.resource.recv;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import koal.urm.client.constant.OpType;
import koal.urm.client.constant.ResType;
import koal.urm.client.resource.recv.IResRecvRegisterProcess;
import koal.urm.client.util.JsonUtil;
/**
 * 
 * @package:com.kl.ar.resource.recv
 * @Description:资源接收实现类  
 * @author: wanlei
 * @date: 2019年12月21日下午3:12:49
 */
public class ResRecvRegisterProcessImpl implements IResRecvRegisterProcess {
	private Logger logger = Logger.getLogger(ResRecvRegisterProcessImpl.class);
	private ResType resType;

	public ResRecvRegisterProcessImpl(ResType resType) {
		this.resType = resType;
	}

	@Override
	public void beginTrans() throws Exception {
		logger.info("beginTrans");
	}

	@Override
	public void commit() throws Exception {
		logger.info("commit");
	}

	@Override
	public void rollback() throws Exception {
		logger.info("rollback");
	}

	@Override
	public List<Map<String, Object>> process(OpType opType, Map<String, Object> resourceMap) throws Exception {
		logger.info("resType：" + resType.getType() + "；     opType：" + opType + "；      resourceMap："
				+ JsonUtil.bean2Str(resourceMap));
		return null;
	}

}
