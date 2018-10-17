package com.xn.hk.common.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

	/**
	 * 
	 * 将指定文件或文件夹压缩成ZIP
	 * 
	 * @param srcDir
	 *            压缩文件夹路径
	 * @param out
	 *            压缩文件输出流
	 * @param KeepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 */

	public static void zipFile(String srcDir, OutputStream out, boolean KeepDirStructure) {
		long start = System.currentTimeMillis();
		ZipOutputStream zos = null;
		try {
			zos = new ZipOutputStream(out);
			File sourceFile = new File(srcDir);
			compress(sourceFile, zos, sourceFile.getName(), KeepDirStructure);
			long end = System.currentTimeMillis();
			logger.info("压缩完成，耗时{}ms!", (end - start));
		} catch (Exception e) {
			logger.error("压缩文件失败，原因为:{}", e);
		} finally {
			try {
				if (zos != null) {
					zos.close();
				}
			} catch (IOException e) {
				logger.error("关闭流失败，原因为:{}", e);
			}
		}
	}

	/**
	 * 
	 * 将文件列表压缩成ZIP
	 * 
	 * @param srcFiles
	 *            需要压缩的文件列表
	 * @param out
	 *            压缩文件输出流
	 */

	public static void zipFile(List<File> srcFiles, OutputStream out) {
		long start = System.currentTimeMillis();
		ZipOutputStream zos = null;
		try {
			zos = new ZipOutputStream(out);
			for (File srcFile : srcFiles) {
				byte[] buf = new byte[1024];
				zos.putNextEntry(new ZipEntry(srcFile.getName()));
				int len = 0;
				FileInputStream in = new FileInputStream(srcFile);
				while ((len = in.read(buf)) != -1) {
					zos.write(buf, 0, len);
				}
				zos.closeEntry();
				in.close();
			}
			long end = System.currentTimeMillis();
			logger.info("压缩完成，耗时{}ms!", (end - start));
		} catch (Exception e) {
			logger.error("压缩文件失败，原因为:{}", e);
		} finally {
			try {
				if (zos != null) {
					zos.close();
				}
			} catch (IOException e) {
				logger.error("关闭流失败，原因为:{}", e);
			}
		}
	}

	/**
	 * 
	 * 递归压缩方法
	 * 
	 * @param sourceFile
	 *            源文件
	 * @param zos
	 *            zip输出流
	 * @param name
	 *            压缩后的名称
	 * @param KeepDirStructure
	 *            是否保留原来的目录结构,true:保留目录结构;
	 *            false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @throws Exception
	 * 
	 */

	private static void compress(File sourceFile, ZipOutputStream zos, String name, boolean KeepDirStructure)
			throws Exception {
		byte[] buf = new byte[1024];
		if (sourceFile.isFile()) {
			// 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
			zos.putNextEntry(new ZipEntry(name));
			// copy文件到zip输出流中
			int len = 0;
			FileInputStream in = new FileInputStream(sourceFile);
			while ((len = in.read(buf)) != -1) {
				zos.write(buf, 0, len);
			}
			zos.closeEntry();
			in.close();
		} else {
			File[] listFiles = sourceFile.listFiles();
			if (listFiles == null || listFiles.length == 0) {
				// 需要保留原来的文件结构时,需要对空文件夹进行处理
				if (KeepDirStructure) {
					// 空文件夹的处理
					zos.putNextEntry(new ZipEntry(name + "/"));
					// 没有文件，不需要文件的copy
					zos.closeEntry();
				}
			} else {
				for (File file : listFiles) {
					// 判断是否需要保留原来的文件结构
					if (KeepDirStructure) {
						// 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
						// 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
						compress(file, zos, name + "/" + file.getName(), KeepDirStructure);
					} else {
						compress(file, zos, file.getName(), KeepDirStructure);
					}
				}
			}
		}
	}

	/**
	 * 
	 * 解压zip文件
	 * 
	 * @param srcDirPath
	 *            zip源路径
	 * @param destDirPath
	 *            解压后的目标文件夹,例如:D:\\test\\(D盘下的test文件夹，不存在会自动创建，test后面的\\一定要有)
	 */

	public static void unZipFile(String srcDirPath, String destDirPath) {
		long start = System.currentTimeMillis();
		File srcFile = new File(srcDirPath);
		// 判断源文件是否存在
		if (!srcFile.exists()) {
			logger.error("{}所指文件不存在", srcFile.getPath());
			return;
		}
		// 判断源文件是否为zip格式压缩包
		if (!srcDirPath.endsWith("zip")) {
			logger.error("源文件不是zip类型的文件!");
			return;
		}
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(srcFile);
			Enumeration<?> entries = zipFile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				logger.info("解压{}", entry.getName());
				// 如果是文件夹，就创建个文件夹
				if (entry.isDirectory()) {
					String dirPath = destDirPath + "/" + entry.getName();
					File dir = new File(dirPath);
					dir.mkdirs();
				} else {
					// 如果是文件，就先创建一个文件，然后用io流把内容copy过去
					File targetFile = new File(destDirPath + "/" + entry.getName());
					// 保证这个文件的父文件夹必须要存在
					if (!targetFile.getParentFile().exists()) {
						targetFile.getParentFile().mkdirs();
					}
					targetFile.createNewFile();
					// 将压缩文件内容写入到这个文件中
					InputStream is = zipFile.getInputStream(entry);
					FileOutputStream fos = new FileOutputStream(targetFile);
					int len = 0;
					byte[] buf = new byte[1024];
					while ((len = is.read(buf)) != -1) {
						fos.write(buf, 0, len);
					}
					// 关流顺序，先打开的后关闭
					fos.close();
					is.close();
				}
			}
			long end = System.currentTimeMillis();
			logger.info("解压完成，耗时{}ms!", (end - start));
		} catch (Exception e) {
			logger.error("解压文件失败，原因为:{}", e);
		} finally {
			try {
				if (zipFile != null) {
					zipFile.close();
				}
			} catch (IOException e) {
				logger.error("关闭流失败，原因为:{}", e);
			}
		}
	}

	public static void main(String[] args) throws IOException {

		FileOutputStream fos1 = new FileOutputStream(new File("D:/mytest.zip"));
		zipFile("E:\\develop tools\\drivenFile", fos1, false);
		List<File> fileList = new ArrayList<File>();
		fileList.add(new File("E:\\develop tools\\vmware12\\vmFile\\IE\\IE9-Windows7-x64-chs.exe"));
		fileList.add(new File("E:\\develop tools\\vmware12\\vmFile\\IE\\IE9-Windows7-x86-chs.exe"));
		FileOutputStream fos2 = new FileOutputStream(new File("D:/ie9.zip"));
		zipFile(fileList, fos2);

		unZipFile("E:\\develop tools\\apache-maven-3.5.3-bin.zip", "D:\\test\\");

	}
}