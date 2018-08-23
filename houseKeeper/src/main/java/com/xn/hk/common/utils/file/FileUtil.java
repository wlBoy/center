package com.xn.hk.common.utils.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.commons.io.output.FileWriterWithEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	public static byte[] readFile2Byte(File file) throws IOException {
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
	public static boolean append(String path, StringBuffer content, String encoding) {
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

	/**
	 * 构造输入的两个路径片断为组合路径
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static final String join(String s1, String s2) {
		File f = new File(s1, s2);
		return f.getPath();
	}

	public static final String join(String[] ss) {
		if (ss == null)
			return null;
		if (ss.length == 1)
			return ss[0];

		String lastPath = ss[0];
		for (int i = 1; i < ss.length; i++) {
			File f = new File(lastPath, ss[i]);
			lastPath = f.getPath();
		}
		return lastPath;
	}

	/**
	 * 以byteArray的形式读文件的全部内容
	 * 
	 * @param path
	 * @return
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public static final byte[] readFileAsByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fi = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		try {
			fi.read(data);
		} finally {
			fi.close();
		}
		return data;
	}

	/**
	 * 以字符串方式返回文件，编码用系统缺省
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static final String readFileAsString(String path) throws IOException {
		return new String(readFileAsByteArray(path));
	}

	/**
	 * 以字符串方式返回文件，编码为charsetName
	 * 
	 * @param path
	 * @param charsetName
	 * @return
	 * @throws Exception
	 */
	public static final String readFileAsString(String path, String charsetName) throws IOException {
		return new String(readFileAsByteArray(path), charsetName);
	}

	/**
	 * 读取文件（前rowCount），
	 * 
	 * @param file
	 * @param rowCount
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String readFileLimitRows(File file, int rowCount, String transcoding) throws Exception {

		StringBuilder sb = new StringBuilder(1024);
		int count = 0;
		InputStreamReader read = null;
		BufferedReader reader = null;
		try {
			read = new InputStreamReader(new FileInputStream(file));
			reader = new BufferedReader(read);
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
				count++;
				if (count == rowCount)
					break;
			}

		} catch (Exception e) {
			throw e;
		} finally {
			if (read != null)
				read.close();
			if (reader != null)
				reader.close();
		}

		// return sb.toString();
		return transcoding == null ? sb.toString() : new String(sb.toString().getBytes(transcoding), "UTF-8");
	}

	/**
	 * 以byteArray的形式写入文件
	 * 
	 * @param path
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static final void writeByteArrayToFile(String path, byte[] data) throws Exception {
		File file = new File(path);
		FileOutputStream fo = new FileOutputStream(file);
		try {
			fo.write(data);
		} finally {
			fo.close();
		}
	}

	/**
	 * 将内容写入文件，会将原来的覆盖
	 * 
	 * @param path
	 * @param content
	 * @throws Exception
	 */
	public static void writeFileContent(String path, String content) throws Exception {
		File file = new File(path);
		if (!file.exists()) {
			if (file.createNewFile()) {
				logger.debug("创建文件成功!");
			} else {
				throw new Exception("创建文件" + path + "失败!");
			}
		}

		BufferedWriter output = new BufferedWriter(new FileWriter(file));
		output.write(content);
		output.close();
	}

	/**
	 * 将内容写入文件，会将原来的覆盖
	 * 
	 * @param path
	 * @param content
	 * @param charsetName
	 *            编码
	 * @throws Exception
	 */
	public static void writeFileContent(String path, String content, String charsetName) throws Exception {
		File file = new File(path);
		if (!file.exists()) {
			if (file.createNewFile()) {
				logger.debug("创建文件成功!");
			} else {
				throw new Exception("创建文件" + path + "失败!");
			}
		}

		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), charsetName);
		writer.write(content);
		writer.close();
	}

	/**
	 * 
	 * @param path
	 * @param content
	 * @param isAppended
	 *            是否新一行写入不覆盖
	 */
	public static void writeFileContent(String path, String content, boolean isAppended, String charsetName)
			throws Exception {
		if (isAppended) {
			File file = new File(path);
			if (!file.exists()) {
				if (file.createNewFile()) {
					logger.debug("创建文件成功!");
				} else {
					throw new Exception("创建文件" + path + "失败!");
				}
			}

			Writer osWriter = null;
			if (charsetName == null) {
				osWriter = new FileWriter(file, isAppended);
			} else {
				osWriter = new FileWriterWithEncoding(file, charsetName, isAppended);
			}
			BufferedWriter output = new BufferedWriter(osWriter);
			output.newLine();
			output.write(content);
			output.close();
		} else {
			FileUtil.writeFileContent(path, content);
		}
	}

	public static void writeFileContent(String path, String content, boolean isAppended) throws Exception {
		writeFileContent(path, content, isAppended, null);
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
		if (delContent.equals("")) {
			throw new Exception("删除指定内容" + path + "不能为空!");
		}

		File file = new File(path);
		if (!file.exists()) {
			throw new Exception("文件" + path + "不存在!");
		}

		List<String> lineList = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String s = null;
		while ((s = reader.readLine()) != null) {
			lineList.add(s);
		}

		lineList.remove(delContent);

		StringBuffer sb = new StringBuffer();
		if (lineList.size() > 0) {
			for (int i = 0; i < lineList.size() - 1; i++) {
				sb.append(lineList.get(i));
				sb.append("\r\n");
			}
			sb.append(lineList.get(lineList.size() - 1));
		} else {
			sb.append("");
		}
		FileUtil.writeFileContent(path, sb.toString());

		reader.close();
	}

	public static boolean hasContent(String path, String content) throws Exception {

		boolean result = false;

		File file = new File(path);
		if (!file.exists()) {
			throw new Exception("文件" + path + "不存在!");
		}

		String fileContent = readFile(file);

		if (fileContent.indexOf(content) != -1) {
			result = true;
		}

		return result;
	}

	/**
	 * 
	 * 读文件(完全读入)
	 * 
	 * @param file
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String readFile(File file) throws FileNotFoundException, IOException {
		BufferedReader br = null;
		StringBuffer buffer = new StringBuffer(64);
		InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
		br = new BufferedReader(isr);
		int s;
		while ((s = br.read()) != -1) {
			buffer.append((char) s);
		}
		br.close();
		isr.close();
		return buffer.toString();
	}

	/**
	 * GAD的一个方法，判断pidFile是否存在
	 * 
	 * @param pid
	 * @param pidFile
	 * @return
	 * @throws Exception
	 */
	public static boolean isAlive(int pid, String pidFile) throws Exception {

		int content = -1;
		File file = new File(pidFile);
		if (pid == 0) {
			if (file.exists()) {
				String fileContent = readFile(file).trim();
				content = Integer.parseInt(fileContent.toString());
			} else {
				return false;
			}
		}

		return FileUtil.isExists("/proc/" + content + "/stat");
	}

	public static boolean isExists(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}

	public static boolean fileListExist(List<File> list) throws Exception {

		boolean result = true;

		for (File file : list) {
			if (!file.exists())
				return false;
		}

		return result;
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
	public static List<String> readFileToLineList(String path) throws IOException {
		File file = new File(path);
		if (!file.exists() || file.isDirectory()) {
			throw new FileNotFoundException();
		}
		List<String> lineList = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String temp = null;
		while ((temp = br.readLine()) != null) {
			lineList.add(temp);
		}
		br.close();
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

	/**
	 * 移动文件
	 * 
	 * @param oriPath
	 *            源文件
	 * @param destPath
	 *            目标文件
	 * @return true 移动文件成功 false 移动文件失败
	 * @throws Exception
	 */
	public static boolean moveFile(String oriPath, String destPath) throws Exception {
		// 检查文件是否存在
		File oriFile = new File(oriPath);
		if (!oriFile.exists() || oriFile.isDirectory()) {
			throw new FileNotFoundException();
		}
		// 检查目标文件是否存在
		File destFile = new File(destPath);
		if (destFile.exists() || destFile.isDirectory()) {
			throw new Exception("目标文件已存在或者是文件夹:" + destPath);
		}
		// 移动
		return oriFile.renameTo(destFile);
	}
}
