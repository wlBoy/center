package com.xn.hk.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
/**
 * 
 * @Title: FileUtil
 * @Package: com.xn.hk.common.utils
 * @Description: 文件操作工具类
 * @Company: 杭州讯牛 
 * @Author: wanlei
 * @Date: 2018年1月8日 下午4:43:49
 */
public class FileUtil {

	/**
	 * 删除文件
	 * 
	 * @param path
	 * @return
	 */
	public static boolean delete(String path) {
		boolean flag = true;
		File file = new File(path);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				flag = flag && delete(f.getPath());
			}
			return flag;
		} else {
			return file.delete();
		}
	}

	/**
	 * 读取指定文件的内容
	 * 
	 * @param file
	 *            文件对象
	 * @return 字节流
	 * @throws IOException
	 * @throws IOException
	 */
	public static byte[] readFile(File file) throws IOException {
		FileInputStream fis = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			fis = new FileInputStream(file);
			byte[] b = new byte[1024];
			int length = fis.read(b);
			while (length != -1) {
				baos.write(b, 0, length);
				length = fis.read(b);
			}
			return baos.toByteArray();
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (fis != null)
					fis.close();
				fis = null;
				if (baos != null)
					baos.close();
				baos = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 写文件
	 * 
	 * @param path
	 * @param b
	 */
	public static void write(String path, byte[] b) {
		File file = new File(path);
		if (!file.exists()) {// 如果文件不存在,进行创建
			new File(file.getParent()).mkdirs();
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			fos.write(b, 0, b.length);
			fos.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			path = null;
			try {
				if (fos != null) {
					fos.close();
					fos = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 写文件
	 * 
	 * @param path
	 * @param b
	 */
	public static void write(String destFilePath, InputStream is) {
		File file = new File(destFilePath);
		if (!file.exists()) {// 如果文件不存在,进行创建
			new File(file.getParent()).mkdirs();
		}
		FileOutputStream fos = null;
		byte[] b = new byte[10240];
		int length = -1;
		try {
			fos = new FileOutputStream(file);
			while ((length = is.read(b)) != -1) {
				fos.write(b, 0, length);
				fos.flush();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
					fos = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 写文件
	 * 
	 * @param path
	 * @param b
	 * @throws IOException
	 */
	public static boolean append(String path, byte[] b) {
		File file = new File(path);
		if (!file.exists()) {// 如果文件不存在,进行创建
			new File(file.getParent()).mkdirs();
		}
		FileOutputStream fos = null;
		boolean flag = false;
		try {
			fos = new FileOutputStream(file, true);
			fos.write(b, 0, b.length);
			fos.flush();
			flag = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				fos = null;
			}
		}
		return flag;
	}

	/**
	 * 写文件
	 * 
	 * @param path
	 * @param b
	 * @throws IOException
	 */
	public static boolean append(String path, StringBuffer content,
			String encoding) {
		File file = new File(path);
		if (!file.exists()) {// 如果文件不存在,进行创建
			new File(file.getParent()).mkdirs();
		}
		OutputStreamWriter osw = null;
		boolean flag = false;
		try {
			FileOutputStream fos = new FileOutputStream(file, true);
			osw = new OutputStreamWriter(fos, encoding);
			osw.write(content.toString());
			osw.flush();
			flag = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (osw != null) {
				try {
					osw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				osw = null;
			}
		}
		return flag;
	}

	/**
	 * 默认按照获取当前时间的的资费串形式
	 * 
	 * @return
	 */
	public static String getFileNameByDataType() {
		return getFileNameByDataType("yyyyMMhhddmmss");
	}

	/**
	 * @param dateStyle
	 *            日期格式化的形式
	 * @return
	 */
	public static String getFileNameByDataType(String dateStyle) {
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateStyle);
		return simpleDateFormat.format(date);
	}

	/**
	 * @return UUID格式的文件名称
	 */
	public static String getFileNameByUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * @param length
	 *            返回字符串的长度
	 * @return
	 */
	public static String getFileNameByUUID(int length) {
		return getFileNameByUUID().substring(0, 0 + length);
	}

	/**
	 * 获取文件大小
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static int getFileSize(String path) throws IOException {
		File f = new File(path);
		if (!f.exists()) {
			return 0;
		}

		FileInputStream fis = null;
		try {
			fis = new FileInputStream(f);
			return fis.available();
		} catch (IOException e) {
			throw e;
		} finally {
			if (fis != null)
				fis.close();
		}
	}
}
