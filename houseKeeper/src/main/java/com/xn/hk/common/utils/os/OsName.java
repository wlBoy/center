
package com.xn.hk.common.utils.os;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 
 * @ClassName: OsName
 * @Package: com.xn.hk.common.utils.os
 * @Description: 判断操作系统及其发行版本的帮助类
 * @Author: wanlei
 * @Date: 2019年3月11日 上午10:51:58
 */
public enum OsName {
	Windows, // Windows
	Ubuntu, // Ubuntu
	Kylin, // 中标麒麟
	Debian, // Debian
	Centos, // CentOS
	Linux, // 其他Linux
	;

	private static OsName os = null; // 预存，加速

	/**
	 * 获取当前操作系统的枚举类
	 * 
	 * @return
	 * @throws Exception
	 */
	public static OsName getOS() throws Exception {
		if (os != null) {
			return os;
		}
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.indexOf("linux") >= 0) {
			String distInfo = "";
			try {
				String[] command = { "/bin/sh", "-c", "cat /etc/issue" };
				distInfo = ProcUtil.exec(command).toLowerCase();
			} catch (Exception e) {
				// 尝试获取操作系统版本信息
				e.printStackTrace();
			}
			if (distInfo.indexOf("ubuntu") >= 0) {
				os = Ubuntu;
			} else if (distInfo.indexOf("kylin") >= 0) {
				os = Kylin;
			} else if (distInfo.indexOf("debian") >= 0) {
				os = Debian;
			} else if (distInfo.indexOf("centos") >= 0) {
				os = Centos;
			} else {
				os = Linux;
			}
		} else if (osName.startsWith("win")) {
			os = Windows;
		}
		if (os != null) {
			return os;
		} else {
			throw new Exception("不支持的操作系统");
		}
	}

	/**
	 * 判断主机的端口号是否占用
	 * 
	 * @param host
	 *            目标主机IP
	 * @param port
	 *            目标主机端口号
	 * @return
	 * @throws IOException
	 */
	public static boolean isPortUsing(String host, int port) throws IOException {
		boolean flag = false;
		try {
			InetAddress theAddress = InetAddress.getByName(host);
			Socket socket = new Socket(theAddress, port);
			flag = true;
			socket.close();
		} catch (IOException e) {
			throw e;
		}
		return flag;
	}

	/**
	 * 是否在给定的数组范围内
	 */
	public static boolean containsOs(OsName... osNames) throws Exception {
		OsName name = OsName.getOS();
		return ArrayUtils.contains(osNames, name);
	}

}
