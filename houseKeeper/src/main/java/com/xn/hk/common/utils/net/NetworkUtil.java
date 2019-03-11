package com.xn.hk.common.utils.net;

import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xn.hk.common.utils.string.RegexUtil;
import com.xn.hk.common.utils.string.StringUtil;

/**
 * 
 * @ClassName: NetworkUtil
 * @Package: com.xn.hk.common.utils.net
 * @Description: netWork工具类
 * @Author: wanlei
 * @Date: 2019年3月11日 上午10:43:02
 */
public class NetworkUtil {
	private static final Logger logger = LoggerFactory.getLogger(NetworkUtil.class);

	/**
	 * 通过命令返回值 获取 mac 地址
	 * 
	 * @param commandRes
	 * @return
	 */
	public static String getMac(String commandRes) {
		String mac = "未知";
		String regexString = "[a-f0-9][a-f0-9]:[a-f0-9][a-f0-9]:[a-f0-9][a-f0-9]:[a-f0-9][a-f0-9]:[a-f0-9][a-f0-9]:[a-f0-9][a-f0-9]";
		Matcher macMatcher = RegexUtil.getMatcher(commandRes, regexString);
		if (macMatcher.find()) {
			mac = macMatcher.group(0);
		}
		return mac;
	}

	/**
	 * 计算子网掩码，如 24 --> 255.255.255.0
	 * 
	 * @param netMask
	 * @return
	 * @throws Exception
	 */
	public static String calculateNetMask(String netMask) {
		String result = null;
		String regex = "^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])(\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])){3}$";
		if (!RegexUtil.isMatched(netMask, regex)) {
			regex = "/[1-9]([0-9]*)$";
			if (!RegexUtil.isMatched(netMask, regex)) {
				logger.error("子网掩码格式错误!");
				return result;
			}
			String valueStr = netMask.substring(1);
			int powValue = Integer.parseInt(valueStr);
			long value = (long) Math.pow(2L, 32L) - (long) Math.pow(2L, 32L - powValue);
			long index1 = (value & 0xff000000) >> 24;
			long index2 = (value & 0xff0000) >> 16;
			long index3 = (value & 0xff00) >> 8;
			long index4 = (value & 0xff);
			result = index1 + "." + index2 + "." + index3 + "." + index4;
		}
		return result;
	}

	/**
	 * 转换子网掩码形式 如 255.255.255.0 --> 24
	 * 
	 * @param netMask
	 * @return
	 * @throws Exception
	 */
	public static String netMaskToShortNum(String netMask) {
		String result = null;
		String regex = "^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])(\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])){3}$";
		if (!RegexUtil.isMatched(netMask, regex)) {
			logger.error("子网掩码格式错误!");
			return result;
		} else {
			String[] ss = netMask.split("\\.");
			int x1 = Integer.parseInt(ss[0]);
			int x2 = Integer.parseInt(ss[1]);
			int x3 = Integer.parseInt(ss[2]);
			int x4 = Integer.parseInt(ss[3]);
			String x1Str = Integer.toBinaryString(x1);
			String x2Str = Integer.toBinaryString(x2);
			String x3Str = Integer.toBinaryString(x3);
			String x4Str = Integer.toBinaryString(x4);
			String total = x1Str + x2Str + x3Str + x4Str;
			int count = 0;
			char c = '1';
			char[] chars = total.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				if (c == chars[i]) {
					count++;
				}
			}
			result = count + "";
		}
		return result;
	}

	/**
	 * 判断是否是IPv4地址
	 * 
	 * @param ip
	 * @return
	 */
	public static boolean isIpV4(String ip) {
		List<String> fields = StringUtil.splitStringToList(ip, "\\.");
		if (fields.size() != 4) {
			return false;
		}
		int index0 = Integer.parseInt(fields.get(0).toString());
		int index1 = Integer.parseInt(fields.get(1).toString());
		int index2 = Integer.parseInt(fields.get(2).toString());
		int index3 = Integer.parseInt(fields.get(3).toString());
		// 首位不能为0或大于223
		if (index0 <= 0 || index0 > 223) {
			// 对0.0.0.0进行特殊处理
			if (index0 == 0 && index1 == 0 && index2 == 0 && index3 == 0) {
				return true;
			}
			return false;
		}
		// 首位不能为负数或大于223
		if (!RegexUtil.isMatched(fields.get(1), "^[0-9]+$") || index1 < 0 || index1 > 255) {
			return false;
		}
		// 首位不能为负数或大于223
		if (!RegexUtil.isMatched(fields.get(2), "^[0-9]+$") || index2 < 0 || index2 > 255) {
			return false;
		}
		// 末尾位不能为0或大于255
		if (!RegexUtil.isMatched(fields.get(3), "^[0-9]+$") || index3 <= 0 || index3 > 255) {
			return false;
		}
		return true;
	}

	/**
	 * 判断是否是网段
	 * 
	 * @param network
	 * @return
	 */
	public static boolean isNetwork(String network) {
		List<String> fields = StringUtil.splitStringToList(network, "\\.");
		if (fields.size() != 4) {
			return false;
		}
		for (int i = 0; i < fields.size(); i++) {
			int intVal = Integer.parseInt(fields.get(i));
			if (intVal < 0 || intVal > 255) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断是否是子网掩码
	 * 
	 * @param network
	 * @return
	 */
	public static boolean isNetmask(String netmask) {
		String regex = "^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])(\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])){3}$";
		if (!RegexUtil.isMatched(netmask, regex)) {
			regex = "^[1-9]([0-9]*)$";
			if (!RegexUtil.isMatched(netmask, regex)) {
				logger.error("子网掩码格式错误!");
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查网络和子网掩码是否匹配
	 *
	 * @param network
	 * @param maskString
	 * 			@return @throws Exception @throws
	 */
	public static boolean checkNetwork(String network, String maskString) {
		boolean validate = true;
		Integer mask = Integer.parseInt(netMaskToShortNum(maskString));
		if (mask != null && mask != 0 && network != null && !"".equals(network)) {
			if (mask > 32) {
				validate = true;
				return validate;
			}
			long destLong = ip2long(network);
			if (destLong == 0) {
				validate = false;
				return validate;
			}
			String bin = Long.toBinaryString(destLong);
			String check = bin.substring(mask, bin.length());
			char[] checkArray = check.toCharArray();
			for (char ch : checkArray) {
				if (ch != '0') {
					validate = false;
					return validate;
				}
			}
		}
		return validate;
	}

	public static long ip2long(String ip) {
		String[] items = ip.split("\\.");
		if (items.length != 4) {
			return 0;
		}
		return Long.valueOf(items[0]) << 24 | Long.valueOf(items[1]) << 16 | Long.valueOf(items[2]) << 8
				| Long.valueOf(items[3]);
	}

	/**
	 * 检查指定的ip是否在子网里
	 *
	 * @param ipaddr
	 * @param subnet
	 * @param mask
	 * @return
	 */
	public final static boolean isInSubnet(String ipaddr, String subnet, String mask) {
		int ipaddrInt = parseNumericAddress(ipaddr);
		if (ipaddrInt == 0) {
			return false;
		}
		int subnetInt = parseNumericAddress(subnet);
		if (subnetInt == 0) {
			return false;
		}
		int maskInt = parseNumericAddress(mask);
		if (maskInt == 0) {
			return false;
		}
		if ((ipaddrInt & maskInt) == subnetInt) {
			return true;
		}
		return false;
	}

	/**
	 * 检查指定的地址是否是正确的TCP/IP地址，并且返回integer值
	 *
	 * @param ipaddr
	 * @return
	 */
	public final static int parseNumericAddress(String ipaddr) {
		// Check if the string is valid
		if (ipaddr == null || ipaddr.length() < 7 || ipaddr.length() > 15) {
			return 0;
		}
		// Check the address string, should be n.n.n.n format
		StringTokenizer token = new StringTokenizer(ipaddr, ".");
		if (token.countTokens() != 4) {
			return 0;
		}
		int ipInt = 0;
		while (token.hasMoreTokens()) {
			// Get the current token and convert to an integer value
			String ipNum = token.nextToken();
			try {
				// Validate the current address part
				int ipVal = Integer.valueOf(ipNum).intValue();
				if (ipVal < 0 || ipVal > 255) {
					return 0;
				}
				// Add to the integer address
				ipInt = (ipInt << 8) + ipVal;
			} catch (NumberFormatException ex) {
				return 0;
			}
		}
		// Return the integer address
		return ipInt;
	}

	/**
	 * 将子网掩码数装换为ip，24 -> 255.255.255.0
	 * 
	 * @param length
	 * @return
	 */
	public static String maskNum2Ip(int length) {
		if (length == 0) {
			return "0.0.0.0";
		}
		int mask = -1 << (32 - length);
		int partsNum = 4;
		int bitsOfPart = 8;
		int maskParts[] = new int[partsNum];
		int selector = 0x000000ff;
		for (int i = 0; i < maskParts.length; i++) {
			int pos = maskParts.length - 1 - i;
			maskParts[pos] = (mask >> (i * bitsOfPart)) & selector;
		}
		String result = "";
		result = result + maskParts[0];
		for (int i = 1; i < maskParts.length; i++) {
			result = result + "." + maskParts[i];
		}
		return result;
	}
}
