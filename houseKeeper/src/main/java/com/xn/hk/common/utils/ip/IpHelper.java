package com.xn.hk.common.utils.ip;

/**
 * 
 * @ClassName: IpHelper
 * @Package: com.xn.hk.common.utils.ip
 * @Description: 获取IP地址的工具类
 * @Author: wanlei
 * @Date: 2018年10月15日 下午5:29:48
 */
public class IpHelper {
	// 本地IP线程变量
	private static ThreadLocal<String> ipLocal = new ThreadLocal<String>() {
		/**
		 * 复写父类的初始化方法，默认返回127.0.0.1，防止get方法在set方法之前调用，报空指针异常
		 */
		@Override
		protected String initialValue() {
			return "127.0.0.1";
		}

	};

	/**
	 * 设置IP(在系统启动时，在ManagerLoginFilter类中的初始化访问系统的IP地址)
	 * 
	 * @param ip
	 */
	public static void setIp(String ip) {
		ipLocal.set(ip);
	}

	/**
	 * 获取当前IP
	 * 
	 * @return
	 */
	public static String getIp() {
		return ipLocal.get();
	}
}
