package com.xn.hk.common.utils.encryption;

import java.util.zip.CRC32;

import com.xn.hk.common.utils.file.FileUtil;

/**
 * 
 * @ClassName: CrcUtil
 * @Package: com.xn.hk.common.utils.encryption
 * @Description: 计算crc校验码工具类
 * @Author: wanlei
 * @Date: 2019年3月11日 上午10:24:56
 */
public class CrcUtil {

	/**
	 * 计算数据的crc校验码
	 * 
	 * @param oriData
	 *            原始数据
	 * @return 返回数据的crc校验码
	 */
	public static Long genCrc(byte[] oriData) {
		CRC32 gen = new CRC32();
		gen.update(oriData);
		return gen.getValue();
	}

	/**
	 * 计算字符串的crc校验码
	 * 
	 * @param oriStr
	 *            原始字符串
	 * @return 返回字符串的crc校验码
	 */
	public static Long genCrc(String oriStr) {
		CRC32 gen = new CRC32();
		gen.update(oriStr.getBytes());
		return gen.getValue();
	}

	/**
	 * 计算文件的crc校验码
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 返回文件的crc校验码
	 * @throws Exception
	 */
	public static Long genFileCrc(String filePath) {
		byte[] oriData = FileUtil.readFileAsByteArray(filePath);
		return genCrc(oriData);
	}

}
