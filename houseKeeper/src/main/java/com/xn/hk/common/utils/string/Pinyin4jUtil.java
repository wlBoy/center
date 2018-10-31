package com.xn.hk.common.utils.string;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 
 * @ClassName: Pinyin4jUtil
 * @Package: com.xn.hk.common.utils
 * @Description: 汉字转拼音的工具类
 * @Author: wanlei
 * @Date: 2018年8月22日 上午9:29:00
 */
public class Pinyin4jUtil {
	private static final Logger logger = LoggerFactory.getLogger(Pinyin4jUtil.class);

	/**
	 * 将汉字转换为全拼，注意:不能区分多音字
	 * 
	 * @param src
	 *            汉字
	 * @return 返回该汉字的拼音，不是汉字返回本身
	 */
	public static String getPinYin(String src) {
		String t4 = "";
		if (StringUtil.isEmpty(src)) {
			return t4;
		}
		char[] t1 = null;
		t1 = src.toCharArray();
		String[] t2 = new String[t1.length];
		// 设置汉字拼音输出的格式
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// 判断能否为汉字字符
				if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);// 将汉字的几种全拼都存到t2数组中
					t4 += t2[0];// 取出该汉字全拼的第一种读音并连接到字符串t4后
				} else {
					// 如果不是汉字字符，间接取出字符并连接到字符串t4后
					t4 += Character.toString(t1[i]);
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			logger.error("{}转换拼音失败，原因为:{}", src, e);
		}
		return t4;
	}

	/**
	 * 提取每个汉字的首字母
	 * 
	 * @param str 原字符串
	 * @return 返回汉字的首字母
	 */
	public static String getPinYinHeadChar(String str) {
		String convert = "";
		if (StringUtil.isEmpty(str)) {
			return convert;
		}
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			// 提取汉字的首字母
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += word;
			}
		}
		return convert;
	}

	/**
	 * 将字符串转换成ASCII码
	 * 
	 * @param cnStr
	 * @return 返回该字符串的ASCII码
	 */
	public static String getCnASCII(String cnStr) {
		StringBuffer strBuf = new StringBuffer();
		if (StringUtil.isEmpty(cnStr)) {
			return strBuf.toString();
		}
		// 将字符串转换成字节序列
		byte[] bGBK = cnStr.getBytes();
		for (int i = 0; i < bGBK.length; i++) {
			// 将每个字符转换成ASCII码
			strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
		}
		return strBuf.toString();
	}
	/**
	 * 测试方法
	 * @param args
	 */
	public static void main(String[] args) {
		String cnStr = "中国";
		System.out.println(getPinYin(cnStr));
		System.out.println(getPinYinHeadChar(cnStr));
		System.out.println(getCnASCII(cnStr));
	}
}