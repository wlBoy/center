package com.xn.hk.common.utils.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.xn.hk.common.utils.cfg.SystemCfg;

/**
 * 
 * @ClassName: SftpUtil
 * @Package: com.xn.hk.common.utils.ftp
 * @Description: sftp服务器上传与下载操作工具类(ftp的默认端口号为21，sftp的默认端口号为22)
 * @Author: wanlei
 * @Date: 2018年11月1日 上午11:12:17
 */
public class SftpUtil {
	private static final Logger logger = LoggerFactory.getLogger(SftpUtil.class);

	/**
	 * 获取sftp服务器连接
	 * 
	 * @param host
	 *            主机IP
	 * @param port
	 *            端口号,sftp默认为22
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return 连接成功返回sftp对象，否则返回null
	 */
	private static Sftp getConnect() {
		// 获取配置文件的ftp服务器连接参数
		String host = SystemCfg.loadCfg().getProperty(SystemCfg.SFTP_HOST);
		int port = Integer.parseInt(SystemCfg.loadCfg().getProperty(SystemCfg.SFTP_PORT));
		String username = SystemCfg.loadCfg().getProperty(SystemCfg.SFTP_USERNAME);
		String password = SystemCfg.loadCfg().getProperty(SystemCfg.SFTP_PASSWORD);
		Sftp s = new Sftp();
		Session session = null;
		Channel channel = null;
		ChannelSftp sftp = null;// sftp操作类
		JSch jsch = new JSch();
		try {
			session = jsch.getSession(username, host, port);
			session.setPassword(password);
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no"); // 不验证 HostKey
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
		} catch (Exception e) {
			if (channel.isConnected()) {
				channel.disconnect();
			}
			if (session.isConnected()) {
				session.disconnect();
			}
			logger.error("连接sftp服务器失败,请检查主机[{}],端口[{}],用户名[{}],密码是否正确,以上信息正确的情况下请检查网络连接是否正常或者请求被防火墙拒绝！,失败原因为：{}", host,
					port, username, e);
		}
		sftp = (ChannelSftp) channel;
		s.setChannel(channel);
		s.setSession(session);
		s.setSftp(sftp);
		logger.info("连接sftp服务器成功!");
		return s;
	}

	/**
	 * 断开sftp服务器连接
	 * 
	 * @param s
	 *            Sftp实体
	 */
	private static void disConnect(Sftp s) {
		if (s == null) {
			return;
		}
		Session session = s.getSession();
		Channel channel = s.getChannel();
		ChannelSftp sftp = s.getSftp();
		if (null != sftp) {
			sftp.disconnect();
			sftp.exit();
			sftp = null;
		}
		if (null != channel) {
			channel.disconnect();
			channel = null;
		}
		if (null != session) {
			session.disconnect();
			session = null;
		}
	}

	/**
	 * 向sftp服务器上传文件
	 * 
	 * @param directory
	 *            上传的目录-相对于SFPT设置的用户访问目录， 为空则在SFTP设置的根目录进行创建文件（除设置了服务器全磁盘访问）
	 * @param uploadFile
	 *            要上传的文件全路径
	 * @return 操作成功返回true，失败返回false
	 */
	public static boolean uploadFile(String directory, String uploadFile) {
		boolean flag = false;
		Sftp s = getConnect();// 建立连接
		ChannelSftp sftp = s.getSftp();// sftp操作类
		try {
			sftp.cd(directory); // 进入目录
			File file = new File(uploadFile);
			if (!file.exists()) {
				logger.error("上传文件{}不存在!", uploadFile);
				return flag;
			}
			InputStream in = new FileInputStream(file);
			sftp.put(in, file.getName());
			flag = true;
			in.close();
		} catch (Exception e) {
			logger.error("sftp上传文件失败,原因为:{}", e);
		} finally {
			disConnect(s);
		}
		return flag;
	}

	/**
	 * 从sftp服务器中下载文件
	 * 
	 * @param directory
	 *            下载目录 根据SFTP设置的根目录来进行传入
	 * @param downloadFile
	 *            下载的文件
	 * @param saveFile
	 *            存在本地的路径
	 * @return 操作成功返回true，失败返回false
	 */
	public static boolean downloadFile(String directory, String downloadFile, String saveFile) {
		boolean flag = false;
		Sftp s = getConnect();// 建立连接
		ChannelSftp sftp = s.getSftp();// sftp操作类
		try {
			sftp.cd(directory); // 进入目录
			File file = new File(saveFile);
			if (!file.exists()) {
				new File(file.getParent()).mkdirs();// 创建目录
			}
			OutputStream out = new FileOutputStream(file);
			sftp.get(downloadFile, out);
			flag = true;
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error("sftp下载文件失败,原因为:{}", e);
		} finally {
			disConnect(s);
		}
		return flag;
	}

	/**
	 * 删除文件
	 * 
	 * @param directory
	 *            要删除文件所在目录
	 * @param deleteFile
	 *            要删除的文件
	 * @return 操作成功返回true，失败返回false
	 */
	public static boolean deleteFile(String directory, String deleteFile) {
		boolean flag = false;
		Sftp s = getConnect();// 建立连接
		ChannelSftp sftp = s.getSftp();// sftp操作类
		try {
			sftp.cd(directory); // 进入的目录应该是要删除的目录的上一级
			sftp.rm(deleteFile);// 删除目录
			flag = true;
		} catch (Exception e) {
			logger.error("sftp删除文件失败,原因为:{}", e);
		} finally {
			disConnect(s);
		}
		return flag;
	}

	/**
	 * 列出目录下的文件
	 * 
	 * @param directory
	 *            要列出的目录
	 * @return list 文件名列表
	 */
	@SuppressWarnings("rawtypes")
	public static List<String> listFiles(String directory) {
		Sftp s = getConnect();// 建立连接
		ChannelSftp sftp = s.getSftp();// sftp操作类
		Vector fileList = null;
		List<String> fileNameList = new ArrayList<String>();
		try {
			// 返回目录下所有文件名称
			fileList = sftp.ls(directory);
			Iterator it = fileList.iterator();
			while (it.hasNext()) {
				String fileName = ((LsEntry) it.next()).getFilename();
				if (".".equals(fileName) || "..".equals(fileName)) {
					continue;
				}
				fileNameList.add(fileName);
			}
		} catch (SftpException e) {
			logger.error("sftp列出文件失败,原因为:{}", e);
		} finally {
			disConnect(s);
		}
		return fileNameList;
	}

	/**
	 * 删除目录下所有文件
	 * 
	 * @param directory
	 *            要删除文件所在目录
	 * @return 操作成功返回true，失败返回false
	 */
	public static boolean deleteAllFile(String directory) {
		boolean flag = false;
		Sftp s = getConnect();// 建立连接
		ChannelSftp sftp = s.getSftp();// sftp操作类
		try {
			List<String> files = listFiles(directory);// 返回目录下所有文件名称
			sftp.cd(directory); // 进入目录
			for (String deleteFile : files) {
				sftp.rm(deleteFile);// 循环一次删除目录下的文件
			}
			flag = true;
		} catch (Exception e) {
			logger.error("sftp删除所有文件失败,原因为:{}", e);
		} finally {
			disConnect(s);
		}
		return flag;
	}

	/**
	 * 删除目录 (删除的目录必须为空)
	 * 
	 * @param deleteDir
	 *            要删除的目录
	 * @return 操作成功返回true，失败返回false
	 */
	public static boolean deleteDir(String deleteDir) {
		boolean flag = false;
		Sftp s = getConnect();// 建立连接
		ChannelSftp sftp = s.getSftp();// sftp操作类
		try {
			sftp.rmdir(deleteDir);
			flag = true;
		} catch (Exception e) {
			logger.error("sftp删除目录失败,原因为:{}", e);
		} finally {
			disConnect(s);
		}
		return flag;
	}

	/**
	 * 创建目录
	 * 
	 * @param directory
	 *            要创建的目录 位置
	 * @param dir
	 *            要创建的目录
	 * @return 操作成功返回true，失败返回false
	 */
	public static boolean creatDir(String directory, String dir) {
		boolean flag = false;
		Sftp s = getConnect();// 建立连接
		ChannelSftp sftp = s.getSftp();// sftp操作类
		try {
			sftp.cd(directory);
			sftp.mkdir(dir);
			flag = true;
		} catch (Exception e) {
			logger.error("sftp创建目录失败,原因为:{}", e);
		} finally {
			disConnect(s);
		}
		return flag;
	}

	/**
	 * 更改文件名
	 * 
	 * @param directory
	 *            文件所在目录
	 * @param oldFileNm
	 *            原文件名
	 * @param newFileNm
	 *            新文件名
	 * @return 操作成功返回true，失败返回false
	 */
	public static boolean renameFile(String directory, String oldFileNm, String newFileNm) {
		boolean flag = false;
		Sftp s = getConnect();// 建立连接
		ChannelSftp sftp = s.getSftp();// sftp操作类
		try {
			sftp.cd(directory);
			sftp.rename(oldFileNm, newFileNm);
			flag = true;
		} catch (Exception e) {
			logger.error("sftp更改文件名失败,原因为:{}", e);
		} finally {
			disConnect(s);
		}
		return flag;
	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(downloadFile("/usr/local/logdTest", "data.txt", "D:\\data.txt"));
		System.out.println(uploadFile("/usr/local", "D:\\picture\\project\\6.jpg"));
		System.out.println(creatDir("/usr/local", "ftp"));
		System.out.println(deleteDir("/usr/local/ftp"));
		System.out.println(deleteAllFile("/usr/local/logs"));
		System.out.println(renameFile("/usr/local/logdTest", "dataFtp.txt", "data.txt"));
		System.out.println(deleteFile("/usr/local/logdTest", "KIAMP.log"));
		List<String> fileNameList = listFiles("/usr/local/logdTest");
		for (String str : fileNameList) {
			System.out.println(str);
		}

	}
}
