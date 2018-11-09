package com.xn.hk.common.utils.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @ClassName: TomcatListener
 * @Package: com.xn.hk.common.utils.listener
 * @Description: tomcat监听器，在tomcat启动或关闭时会调用，需要web.xml中配置
 * @Author: wanlei
 * @Date: 2018年10月18日 下午5:40:45
 */
public class TomcatListener implements ServletContextListener {
	private static final Logger logger = LoggerFactory.getLogger(TomcatListener.class);
	/*
	 * 在web.xml中注册监听
	 *  <listener>  
     *  	<listener-class>com.xn.hk.common.utils.listener.TomcatListener</listener-class> 
     *	</listener> 
	 */
	
	/**
	 * tomcat启动后就会执行该方法
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("tomcat is started!");
		// 调用启动定时任务
		// TimingTask.getInstance().initJob();
	}

	/**
	 * tomcat关闭后就会执行该方法
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("tomcat is destroyed!");
	}

}
