package com.xn.hk.common.utils.file;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xn.hk.common.constant.Constant;
import com.xn.hk.common.utils.string.StringUtil;

/**
 * 
 * @Title: FileUtil
 * @Package: com.xn.hk.common.utils
 * @Description: 文件操作工具类
 * @Author: wanlei
 * @Date: 2018年1月8日 下午4:43:49
 */
public class FileUtil {
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * 删除指定路径的文件或递归删除文件夹中的所有文件
	 * 
	 * @param path
	 *            指定路径的文件
	 * @return 删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String path) {
		boolean flag = true;
		File file = new File(path);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				flag = flag && deleteFile(f.getPath());
			}
			logger.info("该目录下的所有文件已删除，共{}个文件!", files.length);
			return flag;
		} else {
			return file.delete();
		}
	}

	/**
	 * 读取指定文件的内容输出为字符串
	 * 
	 * @param str
	 *            文件路径
	 * @return 输出字符串
	 */
	public static String readFile2String(String path) {
		return readFile2String(path, Constant.UTF8);
	}

	/**
	 * 以byteArray的形式读文件的全部内容
	 *
	 * @param path
	 * @return
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public static final byte[] readFileAsByteArray(String path) {
		File file = new File(path);
		if (!isExist(path)) {
			logger.error("{}路径下，文件不存在", path);
			return null;
		}
		int count;
		FileInputStream fi = null;
		byte[] data = new byte[(int) file.length()];
		try {
			fi = new FileInputStream(file);
			do {
				count = fi.read(data);
			} while (count > 0);
		} catch (Exception e) {
			logger.error("读取文件{}失败,，原因为:{}", file.getName(), e);
		} finally {
			try {
				fi.close();
			} catch (IOException e) {
				logger.error("关闭文件流失败，原因为:{}", e);
			}
		}
		return data;
	}

	/**
	 * 读取指定文件的内容输出为字符串
	 * 
	 * @param str
	 *            文件路径
	 * @param charset
	 *            指定字符集
	 * @return 输出字符串
	 */
	public static String readFile2String(String path, String charset) {
		String result = null;
		File file = new File(path);
		if (!isExist(path)) {
			logger.error("{}路径下，文件不存在", path);
			return result;
		}
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
			result = new String(baos.toByteArray(), charset);
		} catch (IOException e) {
			logger.info("读取文件失败，原因为:{}", e);
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if (baos != null) {
					baos.close();
				}
			} catch (IOException e) {
				logger.info("关闭流失败，原因为:{}", e);
			}
		}
		return result;
	}

	/**
	 * 将指定字符串写入文件中
	 * 
	 * @param path
	 *            文件路径，文件不存在则创建新文件写入
	 * @param str
	 *            要写入的字符串
	 * @return 写入成功返回true，反之返回false
	 */
	public static boolean writeString2File(String path, String str) {
		return writeString2File(path, str, false);
	}

	/**
	 * 将指定字符串写入文件中
	 * 
	 * @param path
	 *            文件路径，文件不存在则创建新文件写入
	 * @param str
	 *            要写入的字符串
	 * @param isAppend
	 *            是否追加在文件末尾，true表示追加，false表示覆盖
	 * @return 写入成功返回true，反之返回false
	 */
	public static boolean writeString2File(String path, String str, boolean isAppend) {
		boolean flag = false;
		if (StringUtil.isEmpty(str)) {
			logger.info("要写入的字符串不能为空!");
			return flag;
		}
		File file = new File(path);
		if (!isExist(path)) {
			// 如果路径不存在,创建好它的父级目录结构
			new File(file.getParent()).mkdirs();
		}
		byte[] b = str.getBytes();
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file, isAppend);
			fos.write(b, 0, b.length);
			fos.flush();
			flag = true;
		} catch (FileNotFoundException e) {
			logger.info("文件未找到，原因为:{}", e);
		} catch (IOException e) {
			logger.info("读取文件失败，原因为:{}", e);
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				logger.info("关闭流失败，原因为:{}", e);
			}
		}
		return flag;
	}

	/**
	 * 获取指定文件的文件大小
	 * 
	 * @param path
	 *            文件路径
	 * @return 文件大小,单位字节
	 */
	public static int getFileSize(String path) {
		int size = 0;
		File f = new File(path);
		if (!isExist(path)) {
			logger.error("{}路径下，文件不存在", path);
			return size;
		}
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(f);
			size = fis.available();
		} catch (IOException e) {
			logger.info("读取文件失败，原因为:{}", e);
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				logger.info("关闭流失败，原因为:{}", e);
			}
		}
		return size;
	}

	/**
	 * 构造输入的两个路径片断为组合路径
	 * 
	 * @param s1
	 *            路径片断1
	 * @param s2
	 *            路径片断2
	 * @return 文件的全路径
	 */
	public static String join(String s1, String s2) {
		File f = new File(s1, s2);
		return f.getPath();
	}

	/**
	 * 查找指定文件中是否包含指定内容
	 * 
	 * @param path
	 *            文件路径
	 * @param content
	 *            指定内容
	 * @return 包含返回true，否则返回false
	 */
	public static boolean hasContent(String path, String content) {
		boolean result = false;
		String fileContent = readFile2String(path);
		if (fileContent.indexOf(content) != -1) {
			result = true;
		}
		return result;
	}

	/**
	 * 通过文件名获取文件后缀,注意要用lastIndexOf查找.，因为文件名中可能含有.
	 * 
	 * @param fileName
	 *            文件名
	 * @return 文件后缀名
	 */
	public static String getFileSuffix(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	/**
	 * 通过文件名获取文件名(不包含后缀)
	 * 
	 * @param fileName
	 *            文件名
	 * @return 文件名(不包含后缀)
	 */
	public static String getFileBaseName(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf("."));
	}

	/**
	 * 指定路径下的文件是否存在
	 * 
	 * @param filePath
	 *            指定文件路径
	 * @return 存在返回true，否则返回false
	 */
	public static boolean isExist(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}

	/**
	 * 移动文件
	 * 
	 * @param oriPath
	 *            源文件
	 * @param destPath
	 *            目标文件
	 * @return true 移动文件成功 false 移动文件失败
	 */
	public static boolean moveFile(String oriPath, String destPath) {
		// 检查文件是否存在
		File oriFile = new File(oriPath);
		if (!oriFile.exists() || oriFile.isDirectory()) {
			logger.info("源文件不存在或者是文件夹!");
			return false;
		}
		// 检查目标文件是否存在
		File destFile = new File(destPath);
		if (destFile.exists() || destFile.isDirectory()) {
			logger.info("目标文件已存在或者是文件夹!");
			return false;
		}
		// 移动
		return oriFile.renameTo(destFile);
	}

	/**
	 * 获取系统所有的key并将写到文件中，默认注释为空，为System.getProperty方法使用
	 * 
	 * @param path
	 *            文件路径
	 * @return 写入成功返回true，失败返回false
	 */
	public static boolean getSystemKeyToFile(String path) {
		return getSystemKeyToFile(path, null);
	}

	/**
	 * 获取系统所有的key并将写到文件中，为System.getProperty方法使用
	 * 
	 * @param path
	 *            文件路径
	 * @param comment
	 *            文件注释
	 * @return 写入成功返回true，失败返回false
	 */
	public static boolean getSystemKeyToFile(String path, String comment) {
		boolean flag = false;
		File sysFile = new File(path);
		if (!sysFile.exists()) {
			// 如果路径不存在,创建好它的父级目录结构
			new File(sysFile.getParent()).mkdirs();
		}
		// 获取系统所有的key值
		Properties sysPro = System.getProperties();
		try {
			sysPro.store(new FileOutputStream(sysFile), comment);
			flag = true;
		} catch (IOException e) {
			logger.error("获取系统所有的key并将写到文件失败，原因为:" + e);
		}
		return flag;
	}

	/**
	 * 获取指定文件的格式化文件大小
	 * 
	 * @param filepath
	 *            文件路径
	 * @param unit
	 *            单位:BYTE,KB,MB,GB,TB,PB,EB,ZB,YB等
	 * @return 换算单位后的文件大小
	 */
	public static double formatFileSize(String filepath, String unit) {
		// 获取该文件大小，单位为:字节
		double size = getFileSize(filepath) * 1.0;
		double fileSize = 0;
		if (UnitEnum.BYTE_UNIT.getCode().equalsIgnoreCase(unit)) {
			fileSize = size;
		} else if (UnitEnum.KB_UNIT.getCode().equalsIgnoreCase(unit)) {
			fileSize = size / 1024;
		} else if (UnitEnum.MB_UNIT.getCode().equalsIgnoreCase(unit)) {
			fileSize = size / (1024 * 1024);
		} else if (UnitEnum.GB_UNIT.getCode().equalsIgnoreCase(unit)) {
			fileSize = size / (1024 * 1024 * 1024);
		} else if (UnitEnum.TB_UNIT.getCode().equalsIgnoreCase(unit)) {
			fileSize = size / (1024 * 1024 * 1024 * 1024);
		} else if (UnitEnum.PB_UNIT.getCode().equalsIgnoreCase(unit)) {
			fileSize = size / (1024 * 1024 * 1024 * 1024 * 1024);
		} else if (UnitEnum.EB_UNIT.getCode().equalsIgnoreCase(unit)) {
			fileSize = size / (1024 * 1024 * 1024 * 1024 * 1024 * 1024);
		} else if (UnitEnum.ZB_UNIT.getCode().equalsIgnoreCase(unit)) {
			fileSize = size / (1024 * 1024 * 1024 * 1024 * 1024 * 1024 * 1024);
		} else if (UnitEnum.YB_UNIT.getCode().equalsIgnoreCase(unit)) {
			fileSize = size / (1024 * 1024 * 1024 * 1024 * 1024 * 1024 * 1024 * 1024);
		} else {
			logger.error("不支持此{}单位转换!", unit);
		}
		return fileSize;
	}

	/**
	 * 传入字节数动态计算该字节转换后的单位字符串
	 * 
	 * @param size
	 * @return
	 */
	public static String getUnitSize(long size) {
		// 如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
		if (size < 1024) {
			return String.valueOf(size) + "B";
		} else {
			size = size / 1024;
		}
		// 如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
		// 因为还没有到达要使用另一个单位的时候
		// 接下去以此类推
		if (size < 1024) {
			return String.valueOf(size) + "KB";
		} else {
			size = size / 1024;
		}
		if (size < 1024) {
			// 因为如果以MB为单位的话，要保留最后1位小数，
			// 因此，把此数乘以100之后再取余
			size = size * 100;
			return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + "MB";
		} else {
			// 否则如果要以GB为单位的，先除于1024再作同样的处理
			size = size * 100 / 1024;
			return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + "GB";
		}
	}

	/**
	 * 删除文件中 某行为指定内容 的行
	 *
	 * @param path
	 * @param delContent
	 *            需要删除的行的内容
	 * @throws Exception
	 */
	public static void delFileLine(String path, String delContent) throws Exception {
		if (StringUtil.isEmpty(delContent)) {
			logger.error("删除的内容不能为空!");
			return;
		}
		File file = new File(path);
		if (!file.exists()) {
			logger.error("要删除内容的文件不存在!");
			return;
		}
		List<String> lineList = new ArrayList<String>();
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String s;
			while ((s = reader.readLine()) != null) {
				lineList.add(s);
			}
			lineList.remove(delContent);
			StringBuilder sb = new StringBuilder();
			if (!lineList.isEmpty()) {
				for (int i = 0; i < lineList.size() - 1; i++) {
					sb.append(lineList.get(i));
					sb.append("\r\n");
				}
				sb.append(lineList.get(lineList.size() - 1));
			}
			FileUtil.writeString2File(path, sb.toString());
		}
	}

	/**
	 * 递归的读取一个文件夹下面的所有文件，包含文件夹下面的文件夹
	 *
	 * @param pathDirectory
	 * @param fileList
	 */
	public static void readDirectoryFile(String pathDirectory, List<String> fileList) {
		File directory = new File(pathDirectory);
		if (directory.isFile()) {
			fileList.add(pathDirectory);
		} else {
			File[] files = directory.listFiles();
			if (files != null) {
				for (File temp : files) {
					readDirectoryFile(temp.getAbsolutePath(), fileList);
				}
			}
		}
	}

	/**
	 * 读文件转成行内容的集合
	 *
	 * @param path
	 * @return 行内容的集合
	 * @throws IOException
	 */
	public static List<String> readFileToLineList(String path) {
		File file = new File(path);
		if (!file.exists() || file.isDirectory()) {
			logger.error("文件不存在或者该文件为目录!");
			return null;
		}
		List<String> lineList = new ArrayList<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String temp = null;
			while ((temp = br.readLine()) != null) {
				lineList.add(temp);
			}
		} catch (Exception e) {
			logger.error("读取文件失败，原因为:{}", e);
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				logger.error("关闭流失败，原因为:{}", e);
			}
		}
		return lineList;
	}

	/**
	 * 分割路径
	 *
	 * @param filePath
	 * @return
	 */
	public static String[] splitFilepath(String filePath) {
		return filePath.split(Pattern.quote(File.separator));
	}

	public static void main(String[] args) throws IOException {
		// System.out.println(deleteFile("D:/test/aa"));
		// System.out.println(readFile2String("D:/test/aa/aa.txt"));
		// System.out.println(writeString2File("D:/test/bb.txt", "这是测试写文件", true));
		// System.out.println(getFileSize("E:\\develop
		// tools\\apache-maven-3.5.3-bin.zip"));
		// System.out.println(join("E:/develop tools", "/apache-maven-3.5.3-bin.zip"));
		// System.out.println(moveFile("D:/test/aa/aa.txt", "D:/test/aa/b/aa.txt"));
		// System.out.println(getSystemKeyToFile("D:/test/aa.txt"));
		// System.out.println(getFileSuffix("aa.txt"));
		System.out.println(formatFileSize("E:\\develop tools\\apache-maven-3.5.3-bin.zip", UnitEnum.MB_UNIT.getCode()));
		System.out.println(getFileBaseName("aa.txt"));
	}
}
