package com.xn.hk.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @Title: StringUtil
 * @Package: com.xn.hk.common.utils
 * @Description: 字符串工具类
 * @Company: 杭州讯牛 
 * @Author: wanlei
 * @Date: 2018年1月8日 下午5:01:12
 */
public final class StringUtil {
	/**
	 * 字符数组
	 */
	private final static char[] charArray = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	/**
	 * 数字字符数组
	 */
	private final static char[] digitArray = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	/**
	 * 随机生成指定长度的纯数字字符串
	 * @param length
	 * @return
	 */
	public static String randomDigit(int length) {
		char[] c = new char[length];
		Random random = new Random();
		for (int i = 0; i < length; i++)
			c[i] = digitArray[random.nextInt(digitArray.length)];
		return new String(c);
	}
	/**
	 * 随机生成指定长度的字符串(数字字符混合)
	 * @param length
	 * @return
	 */
	public static String randomString(int length) {
		char[] c = new char[length];
		Random random = new Random();
		for (int i = 0; i < length; i++)
			c[i] = charArray[random.nextInt(charArray.length)];
		return new String(c);
	}

	/**
	 * 判断一个字符串为空
	 * @param value
	 * @return
	 */
	public static boolean isNullValue(String value) {
		return value == null || value.trim().equals("");
	}
	/**
	 * 向一个字符串最左边添加任意字符组成新固定长度的字符串
	 * @param input
	 * @param num 新固定长度的字符串
	 * @param chr
	 * @return
	 */
	public static final String leftAppendStr(String input, int num, char chr) {
		int size = num - input.length();
		char[] c = new char[size];
		for (int i = 0; i < size; i++) {
			c[i] = chr;
		}
		return String.valueOf(c).concat(input);
	}

	/**
	 * 向一个字符串最左边添加'0'组成新固定长度的字符串
	 * @param input
	 * @param num 新固定长度的字符串
	 * @param chr
	 * @return
	 */
	public static final String leftAppendStr(String input, int num) {
		return leftAppendStr(input, num, '0');
	}
	/**
	 * 过滤一个字符串的非数字,即得到该字符串中的纯数字
	 * @param str
	 * @return
	 */
	public static String filterUnNumber(String str) {
		if (StringUtil.isNullValue(str))
			return "";
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * 判断一个字符串是否为纯数字字符串
	 * @param str
	 * @return
	 */
	public static boolean isDigit(String str) {
		String regEx = "(\\d+?)";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.matches();
	}
	
	/**
	 * 判断一个字符串是否为电话号码
	 * @param number
	 * @return
	 */
	public static boolean isMobileNumber(String number){
		if(StringUtil.isNullValue(number))
			return false;
		if(number.length() > 11)
			number = number.substring(number.length() - 11);
		Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(number);
		return m.matches();
	}
	
	/**
	 * 判断一个字符串是否为有效电话号码
	 * @param number
	 * @return
	 */
	public final static boolean isValidMobileNumber(String number){
		if(StringUtil.isNullValue(number))
			return false;
		if(number.endsWith("00000000") || number.endsWith("13800138000"))
			return false;
		return StringUtil.isMobileNumber(number);
	}
	
	/**
	 * url编码
	 * @param url
	 * @return
	 */
	public final static String urlEncode(String url) {
		if (isNullValue(url)) {
			return "";
		}

		try {
			return URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static String getXMLValue(String srcXML, String element) {
		String begElement = "<" + element + ">";
		String endElement = "</" + element + ">";
		int begPos = srcXML.indexOf(begElement);
		int endPos = srcXML.indexOf(endElement);
		if (begPos != -1 && endPos != -1) {
			begPos += begElement.length();
			return srcXML.substring(begPos, endPos);
		} else {
			return "";
		}
	}
	
	private static String hexString = "0123456789ABCDEF";

	/*
	 * 将字符串编码成16进制数字,适用于所有字符（包括中文）
	 */
	public static String encode(String str) {
		// 根据默认编码获取字节数组
		byte[] bytes = str.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	/*
	 * 将16进制数字解码成字符串,适用于所有字符（包括中文）
	 */
	public static String decode(String bytes) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}
	
	
	public static void main(String[] args){
		/*String params = "amt=1.00&app_id=5&cm=M2040066&display=4&order_no=20150115084600999&product_id=1410001&pType=1&body=000RES";
		String[] array = params.split("&");
		Arrays.sort(array);
		for(String str : array){
			System.out.println(str);
		}
		
		System.out.println(Base64.encode("460018208401562".getBytes()));
		
		final ScheduledThreadPoolExecutor s = new ScheduledThreadPoolExecutor(2);
		s.schedule(new Runnable(){
			@Override
			public void run() {
				System.out.println("T1-->" + System.currentTimeMillis());	
				System.out.println("queue size->" + s.getQueue().size());
			}
			
		}, 3, TimeUnit.SECONDS);
		
		System.out.println("queue size->" + s.getQueue().size());
		
		s.schedule(new Runnable(){
			@Override
			public void run() {
				System.out.println("T2-->" + System.currentTimeMillis());	
				System.out.println("queue size->" + s.getQueue().size());				
			}
			
		}, 4, TimeUnit.SECONDS);
		
		System.out.println("queue size->" + s.getQueue().size());
		
		s.schedule(new Runnable(){
			@Override
			public void run() {
				System.out.println("T3-->" + System.currentTimeMillis());	
				System.out.println("queue size->" + s.getQueue().size());
			}
			
		}, 5, TimeUnit.SECONDS);
		
		System.out.println("queue size->" + s.getQueue().size());
		
		s.shutdown();*/
		
		
//		String str = "eyumobawxc";
//		//1234567890
//		String mobile = "15811258783";
//		StringBuffer sb = new StringBuffer();
//		for(char c : mobile.toCharArray()){
//			sb.append((str.charAt((Integer.parseInt(String.valueOf(c)) + 9) % 10)));
//		}
//		System.out.println(sb.toString());
//		
//		String number = "";
//		for (char c : sb.toString().toCharArray()) {
//			int i = (str.indexOf(String.valueOf(c)) + 1) % 10;
//			number = number.concat(String.valueOf(i));
//		}
//		System.out.println(Arrays.toString("a,b".split(",")));
//		
////		String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><request><userId>230178723</userId><contentId>634316012394</contentId><consumeCode>006032691002</consumeCode><cpid>772343</cpid><hRet>0</hRet><status>1800</status><versionId>21014</versionId><cpparam>1027105418435467</cpparam><packageID /></request>";
////		String cpParam = StringUtil.filterNullValue(StringUtil.findIgnoreCaseFromString(str, "<cpParam>(.*?)</cpParam>"));
////		String pid =  cpParam.length() > 4 ? cpParam.substring(0, 4) : cpParam;
////		System.out.println(cpParam+ "\n" + pid);
	}
}
