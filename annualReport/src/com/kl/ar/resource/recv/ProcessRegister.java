package com.kl.ar.resource.recv;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import koal.urm.client.constant.ResType;
import koal.urm.client.resource.recv.ResRecvRegister;

/**
 * 
 * @package:com.kl.ar.resource.recv
 * @Description: tomcat启动时注册资源接受处理器
 * @author: wanlei
 * @date: 2019年12月21日下午3:11:46
 */
public class ProcessRegister implements ServletContextListener {

	public static String test = null;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ResRecvRegister.registerResRecvRegisterProcess(ResType.RES_TYPE_USER,
				new ResRecvRegisterProcessImpl(ResType.RES_TYPE_USER));
		ResRecvRegister.registerResRecvRegisterProcess(ResType.RES_TYPE_ORG,
				new ResRecvRegisterProcessImpl(ResType.RES_TYPE_ORG));
		ResRecvRegister.registerResRecvRegisterProcess(ResType.RES_TYPE_APP,
				new ResRecvRegisterProcessImpl(ResType.RES_TYPE_APP));
		ResRecvRegister.registerResRecvRegisterProcess(ResType.RES_TYPE_CERT,
				new ResRecvRegisterProcessImpl(ResType.RES_TYPE_CERT));
		ResRecvRegister.registerResRecvRegisterProcess(ResType.RES_TYPE_ACCOUNT,
				new ResRecvRegisterProcessImpl(ResType.RES_TYPE_ACCOUNT));
		ResRecvRegister.registerResRecvRegisterProcess(ResType.RES_TYPE_GROUP,
				new ResRecvRegisterProcessImpl(ResType.RES_TYPE_GROUP));
		ResRecvRegister.registerResRecvRegisterProcess(ResType.RES_TYPE_ROLE,
				new ResRecvRegisterProcessImpl(ResType.RES_TYPE_ROLE));
		ResRecvRegister.registerResRecvRegisterProcess(ResType.RES_TYPE_RES,
				new ResRecvRegisterProcessImpl(ResType.RES_TYPE_RES));
		ResRecvRegister.registerResRecvRegisterProcess(ResType.RES_TYPE_RIGHTS,
				new ResRecvRegisterProcessImpl(ResType.RES_TYPE_RIGHTS));
		ResRecvRegister.registerResRecvRegisterProcess(ResType.RES_TYPE_GROUP_ROLE,
				new ResRecvRegisterProcessImpl(ResType.RES_TYPE_GROUP_ROLE));
		ResRecvRegister.registerResRecvRegisterProcess(ResType.RES_TYPE_GROUP_USER,
				new ResRecvRegisterProcessImpl(ResType.RES_TYPE_GROUP_USER));
		ResRecvRegister.registerResRecvRegisterProcess(ResType.RES_TYPE_ROLE_RES,
				new ResRecvRegisterProcessImpl(ResType.RES_TYPE_ROLE_RES));
		ResRecvRegister.registerResRecvRegisterProcess(ResType.RES_TYPE_USER_ROLE,
				new ResRecvRegisterProcessImpl(ResType.RES_TYPE_USER_ROLE));
		ResRecvRegister.registerResRecvRegisterProcess(ResType.RES_TYPE_LOG,
				new ResRecvRegisterProcessImpl(ResType.RES_TYPE_LOG));
	}
}