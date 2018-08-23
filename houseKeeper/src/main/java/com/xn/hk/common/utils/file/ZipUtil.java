package com.xn.hk.common.utils.file;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: ZipUtil
 * @Package: com.xn.hk.common.utils.file
 * @Description: Zip相关函数
 * @Author: wanlei
 * @Date: 2018年8月23日 上午11:18:48
 */
public class ZipUtil {
	private static final Logger logger = LoggerFactory.getLogger(ZipUtil.class);

	public ZipUtil() {
		super();
	}

	/**
	 * 压缩文件
	 * 
	 * @param srcFiles
	 *            : 要被压缩的文件
	 * @param dstFile
	 *            : 压缩后的文件
	 * @param comment
	 *            : 文件注释
	 * @param ifFullPath
	 *            : 是否全路径打包
	 * @param srcFilesPrefix
	 *            : 被压缩文件的前缀
	 */

	public static void zip(String[] srcFiles, String dstFile, String comment, boolean ifFullPath, String srcFilesPrefix)
			throws IOException {
		if (srcFiles.length == 0)
			return;
		File fileDst = new File(dstFile);
		File parentDir = fileDst.getParentFile();
		if (!parentDir.exists()) {
			parentDir.mkdirs();
		}
		if (fileDst.exists())
			fileDst.delete();
		fileDst.createNewFile();

		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(fileDst));
		zos.setMethod(ZipOutputStream.DEFLATED);

		if (comment != null) {
			zos.setComment(comment);
		}

		DataOutputStream dos = new DataOutputStream(zos);

		for (int i = 0; i < srcFiles.length; i++) {
			String entryPath = srcFiles[i];
			File fileEntry;
			if (srcFilesPrefix != null) {
				fileEntry = new File(srcFilesPrefix + entryPath);
			} else {
				fileEntry = new File(entryPath);
			}

			if (ifFullPath) {
				zos.putNextEntry(new ZipEntry(entryPath));
			} else {
				zos.putNextEntry(new ZipEntry(fileEntry.getName()));
			}

			FileInputStream fis = new FileInputStream(fileEntry);

			byte[] buff = new byte[8192];
			int len = 0;

			while (true) {
				len = fis.read(buff);
				if (len == -1 || len == 0)
					break;

				dos.write(buff, 0, len);
			}

			zos.closeEntry();
			fis.close();
		}
		dos.close();
		zos.close();
	}

	/**
	 * 压缩文件
	 * 
	 * @param srcFiles
	 *            : 要被压缩的文件
	 * @param dstFile
	 *            : 压缩后的文件
	 * @param comment
	 *            : 文件注释
	 */
	public static void zip(String[] srcFiles, String dstFile, String comment) throws IOException {
		zip(srcFiles, dstFile, comment, false, null);
	}

	/**
	 * 压缩文件
	 * 
	 * @param srcFiles
	 *            : 要被压缩的文件
	 * @param dstFile
	 *            : 压缩后的文件
	 * 
	 */
	public static void zip(String[] srcFiles, String dstFile) throws IOException {
		zip(srcFiles, dstFile, null);
	}

	/**
	 * 解压缩文件
	 * 
	 * @param srcFile
	 *            : 压缩文件名
	 * @param dstDir
	 *            : 要解压到的目录
	 * @param entryName
	 *            : 要解压哪个文件，如果为null则解压所有文件。
	 * @param retainPath
	 *            : 是否保留文件路径信息，true-保留，false-不保留。
	 */
	public static void unzip(String srcFile, String dstDir, String entryName, boolean retainPath) throws Exception {

		ZipFile zipFile = null;

		try {
			zipFile = new ZipFile(srcFile);
			Enumeration<? extends ZipEntry> entryEnu = zipFile.entries();
			while (entryEnu.hasMoreElements()) {
				File fileItem = null;
				ZipEntry entry = (ZipEntry) entryEnu.nextElement();

				String name = entry.getName();
				if (entryName != null && !entryName.equalsIgnoreCase(name))
					continue;
				if (dstDir != null) {
					if (!retainPath) {
						File f = new File(name);
						name = f.getName();
					} else {
						if (System.getProperty("os.name").indexOf("Windows") != -1) {

							if (name.matches("^[a-zA-Z]:.*")) {
								// 如果是WINDOWS系统，将文件名中的驱动器符号去掉
								name = name.substring(name.indexOf('\\') + 1);
							}
						}
					}
					fileItem = new File(dstDir + File.separator + name);
				} else {
					fileItem = new File(name);
				}

				File parentDir = fileItem.getParentFile();
				if (!parentDir.exists()) {
					parentDir.mkdirs();
				}
				if (fileItem.exists())
					fileItem.delete();
				fileItem.createNewFile();
				FileOutputStream fos = null;
				InputStream is = null;

				try {
					fos = new FileOutputStream(fileItem);
					is = zipFile.getInputStream(entry);

					byte[] buff = new byte[8192];
					int len = 0;
					while ((len = is.read(buff)) != -1) {
						fos.write(buff, 0, len);
					}
				} finally {
					try {
						fos.flush();
						fos.close();
						is.close();
					} catch (Exception e) {
					}
				}
			}
		} catch (Exception e) {
			throw new Exception("解压缩失败：" + e.getMessage());

		} finally {
			try {
				zipFile.close();
			} catch (Exception e) {
			}
		}

	}

	/**
	 * 将文件解压缩到指定的目录，保持被压缩文件的路径信息。
	 */
	public static void unzip(String zipFile, String dstDir) throws Exception {
		unzip(zipFile, dstDir, null, true);
	}

	/**
	 * 解压缩文件，覆盖被压缩的文件。
	 */
	public static void unzip(String zipFile) throws Exception {
		unzip(zipFile, null, null, true);
	}

	/**
	 * 获取所有的文件目录
	 * 
	 * @param srcFiles
	 * @return
	 */
	private static ArrayList<File> getTotalFiles(File[] srcFiles) {

		ArrayList<File> files = new ArrayList<File>();
		for (int i = 0; i < srcFiles.length; i++) {
			File file = srcFiles[i];
			if (!file.exists()) {
				logger.debug("warnning:the file you want to backup is missing=" + file.getAbsolutePath());
				continue;
			}
			if (file.isDirectory()) {
				// 递归调用
				List<File> dir = getTotalFiles(file.listFiles());
				for (int j = 0; j < dir.size(); j++) {
					File dirfile = dir.get(j);
					files.add(dirfile);
				}
			} else {
				files.add(file);
			}
		}

		return files;
	}

	/**
	 * 获取所有的文件目录，忽略掉srcIgnFiles
	 * 
	 * @param srcFiles
	 * @param srcIgnFiles
	 * @return
	 */
	public static File[] toFiles(File[] srcFiles, File[] srcIgnFiles) {
		ArrayList<File> files = getTotalFiles(srcFiles);
		ArrayList<File> ignFiles = new ArrayList<File>();
		if (srcIgnFiles != null) {
			ignFiles = getTotalFiles(srcIgnFiles);
		}

		ArrayList<File> temp = new ArrayList<File>();
		temp.addAll(files);

		for (File file : files) {
			if (ignFiles.contains(file)) {
				temp.remove(file);
			}
		}

		File[] a = new File[temp.size()];
		return (File[]) temp.toArray(a);
	}

	public static File[] toFiles(String[] srcFiles, String[] ignFiles) {
		File[] a = new File[srcFiles.length];

		for (int i = 0; i < srcFiles.length; i++) {
			a[i] = new File(srcFiles[i]);
		}
		File[] b = null;
		if (ignFiles != null) {
			b = new File[ignFiles.length];
			for (int i = 0; i < ignFiles.length; i++) {
				b[i] = new File(ignFiles[i]);
			}
		}

		return toFiles(a, b);
	}

	public static String[] getTotoalPathes(String[] srcFiles, String[] ignFiles) {

		ArrayList<String> pathes = new ArrayList<String>();
		File[] files = toFiles(srcFiles, ignFiles);

		for (File file : files) {
			pathes.add(file.getAbsolutePath());
		}

		String[] a = new String[pathes.size()];
		return (String[]) pathes.toArray(a);
	}

}