package com.xn.hk.common.utils.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @ClassName: RegexUtil
 * @Package: com.xn.hk.common.utils.string
 * @Description: 正则表达式工具类
 * @Author: wanlei
 * @Date: 2018年11月1日 下午2:14:13
 */
public class RegexUtil {
	private static final String mobile = "^(13|15|18|17|16)[0-9]{9}$";
	private static final String codeAndMobile = "^\\+[0-9]{2}\\-(13|15|18|17|16)[0-9]{9}$";

	/** 整数 */
	private static final String intege = "^-?[1-9]\\d*$";
	/** 正整数 */
	private static final String intege1 = "^[1-9]\\d*$";
	/** 负整数 */
	private static final String intege2 = "^-[1-9]\\d*$";
	/** 数字 */
	private static final String num = "^([+-]?)\\d*\\.?\\d+$";
	/** 正数（正整数 + 0） */
	private static final String num1 = "^[1-9]\\d*|0$";
	/** 负数（负整数 + 0） */
	private static final String num2 = "^-[1-9]\\d*|0$";
	/** 浮点数 */
	private static final String decmal = "^([+-]?)\\d*\\.\\d+$";
	/** 正浮点数 */
	private static final String decmal1 = "^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*$";
	/** 负浮点数 */
	private static final String decmal2 = "^-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*)$";
	/** 浮点数 */
	private static final String decmal3 = "^-?([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0)$";
	/** 非负浮点数（正浮点数 + 0） */
	private static final String decmal4 = "^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0$";
	/** 非正浮点数（负浮点数 + 0） */
	private static final String decmal5 = "^(-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*))|0?.0+|0$";
	/** 邮件 */
	private static final String email = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
	/** 颜色 */
	private static final String color = "^[a-fA-F0-9]{6}$";
	/** url */
	private static final String url = "^http[s]?=\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$";
	/** 仅中文 */
	private static final String chinese = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$";
	/** 仅ACSII字符 */
	private static final String ascii = "^[\\x00-\\xFF]+$";
	/** 邮编 */
	private static final String zipcode = "^\\d{6}$";
	/** ip地址 */
	private static final String ip4 = "^(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)$";
	/** 非空 */
	private static final String notempty = "^\\S+$";
	/** 图片 */
	private static final String picture = "(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$";
	/** 压缩文件 */
	private static final String rar = "(.*)\\.(rar|zip|7zip|tgz)$";
	/** 日期 */
	private static final String date = "^\\d{4}(\\-|\\/|\\.)\\d{1,2}\\1\\d{1,2}$";
	/** QQ号码 */
	private static final String qq = "^[1-9]*[1-9][0-9]*$";
	/** 电话号码的函数(包括验证国内区号;国际区号;分机号) */
	private static final String tel = "^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(\\d{1,}))?$";
	/** 用来用户注册。匹配由数字、26个英文字母或者下划线组成的字符串 */
	private static final String username = "^\\w+$";
	/** 字母 */
	private static final String letter = "^[A-Za-z]+$";
	private static final String letterAndSpace = "^[A-Za-z ]+$";
	/** 大写字母 */
	private static final String letter_u = "^[A-Z]+$";
	/** 6到20位字母和数字的混合体，至少一个大写字母和数字 */
	private static final String isLetterAndNumber = "^(?![0-9]+$)(?![a-zA-Z]+$)(?![a-z0-9]+$)[0-9A-Za-z]{6,20}$";
	/** 小写字母 */
	private static final String letter_l = "^[a-z]+$";
	/** 身份证 */
	private static final String idcard = "^[1-9]([0-9]{14}|[0-9]{17})$";
	/** 判断字符串是否为浮点数 */
	private static final String isFloat = "^[-]?\\d+(\\.\\d+)?$";
	/** 判断字符串是否为正浮点数 */
	private static final String isUFloat = "^\\d+(\\.\\d+)?$";
	/** 判断是否是整数 */
	private static final String isInteger = "^[-]?\\d+$";
	/** 判断是否是正整数 */
	private static final String isUInteger = "^\\d+$";
	/** 判断车辆Vin码 */
	private static final String isCarVin = "^[1234567890WERTYUPASDFGHJKLZXCVBNM]{13}[0-9]{4}$";

	/** 手机号 */
	public static boolean isMobile(String input) {
		return matches(mobile, input);
	}

	public static boolean isCodeAndMobile(String input) {
		return matches(codeAndMobile, input);
	}

	/** 整数 */
	public static boolean isIntege(String input) {
		return matches(intege, input);
	}

	/** 正整数 */
	public static boolean isintege1(String input) {
		return matches(intege1, input);
	}

	/** 负整数 */
	public static boolean isIntege2(String input) {
		return matches(intege2, input);
	}

	/** 数字 */
	public static boolean isNum(String input) {
		return matches(num, input);
	}

	/** 正数（正整数 + 0） */
	public static boolean isNum1(String input) {
		return matches(num1, input);
	}

	/** 负数（负整数 + 0） */
	public static boolean isNum2(String input) {
		return matches(num2, input);
	}

	/** 浮点数 */
	public static boolean isDecmal(String input) {
		return matches(decmal, input);
	}

	/** 正浮点数 */
	public static boolean isDecmal1(String input) {
		return matches(decmal1, input);
	}

	/** 密码复杂度 */
	public static boolean isPassword(String input) {
		return matches(isLetterAndNumber, input);
	}

	/** 负浮点数 */
	public static boolean isDecmal2(String input) {
		return matches(decmal2, input);
	}

	/** 浮点数 */
	public static boolean isDecmal3(String input) {
		return matches(decmal3, input);
	}

	/** 非负浮点数（正浮点数 + 0） */
	public static boolean isDecmal4(String input) {
		return matches(decmal4, input);
	}

	/** 非正浮点数（负浮点数 + 0） */
	public static boolean isDecmal5(String input) {
		return matches(decmal5, input);
	}

	/** 邮件 */
	public static boolean isEmail(String input) {
		return matches(email, input);
	}

	/** 颜色 */
	public static boolean isColor(String input) {
		return matches(color, input);
	}

	/** url */
	public static boolean isUrl(String input) {
		return matches(url, input);
	}

	/** 中文 */
	public static boolean isChinese(String input) {
		return matches(chinese, input);
	}

	/** ascii码 */
	public static boolean isAscii(String input) {
		return matches(ascii, input);
	}

	/** 邮编 */
	public static boolean isZipcode(String input) {
		return matches(zipcode, input);
	}

	/** IP地址 */
	public static boolean isIP4(String input) {
		return matches(ip4, input);
	}

	/** 非空 */
	public static boolean isNotEmpty(String input) {
		return matches(notempty, input);
	}

	/** 图片 */
	public static boolean isPicture(String input) {
		return matches(picture, input);
	}

	/** 压缩文件 */
	public static boolean isRar(String input) {
		return matches(rar, input);
	}

	/** 日期 */
	public static boolean isDate(String input) {
		return matches(date, input);
	}

	/** qq */
	public static boolean isQQ(String input) {
		return matches(qq, input);
	}

	/** 电话号码的函数(包括验证国内区号;国际区号;分机号) */
	public static boolean isTel(String input) {
		return matches(tel, input);
	}

	/** 用来用户注册。匹配由数字、26个英文字母或者下划线组成的字符串 */
	public static boolean isUserName(String input) {
		return matches(username, input);
	}

	/** 字母 */
	public static boolean isLetter(String input) {
		return matches(letter, input);
	}

	public static boolean isLetterAndSpace(String input) {
		return matches(letterAndSpace, input);
	}

	/** 小写字母 */
	public static boolean isLowLetter(String input) {
		return matches(letter_l, input);
	}

	/** 大写字母 */
	public static boolean isUpperLetter(String input) {
		return matches(letter_u, input);
	}

	/** 身份证 */
	public static boolean isIDCard(String input) {
		return matches(idcard, input);
	}

	public static boolean isFloat(String input) {
		return matches(isFloat, input);
	}

	public static boolean isUFloat(String input) {
		return matches(isUFloat, input);
	}

	public static boolean isInteger(String input) {
		return matches(isInteger, input);
	}

	public static boolean isUInteger(String input) {
		return matches(isUInteger, input);
	}

	public static boolean isCarVin(String carVin) {
		return matches(isCarVin, carVin);
	}

	private static boolean matches(String regex, String input) {
		if (StringUtil.isEmpty(input))
			return false;
		if (input.matches(regex))
			return true;
		return false;
	}

	/**
	 * 验证Email
	 * 
	 * @param email
	 *            email地址，格式：zhangsan@zuidaima.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkEmail(String email) {
		String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
		return Pattern.matches(regex, email);
	}

	/**
	 * 验证身份证号码
	 * 
	 * @param idCard
	 *            居民身份证号码15位或18位，最后一位可能是数字或字母
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkIdCard(String idCard) {
		String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
		return Pattern.matches(regex, idCard);
	}

	/**
	 * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
	 * 
	 * @param mobile
	 *            移动、联通、电信运营商的号码段
	 *            <p>
	 *            移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
	 *            、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）
	 *            </p>
	 *            <p>
	 *            联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）
	 *            </p>
	 *            <p>
	 *            电信的号段：133、153、180（未启用）、189
	 *            </p>
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkMobile(String mobile) {
		String regex = "(\\+\\d+)?1[34578]\\d{9}$";
		return Pattern.matches(regex, mobile);
	}

	/**
	 * 验证固定电话号码
	 * 
	 * @param phone
	 *            电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
	 *            <p>
	 *            <b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9 的一位或多位数字，
	 *            数字之后是空格分隔的国家（地区）代码。
	 *            </p>
	 *            <p>
	 *            <b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
	 *            对不使用地区或城市代码的国家（地区），则省略该组件。
	 *            </p>
	 *            <p>
	 *            <b>电话号码：</b>这包含从 0 到 9 的一个或多个数字
	 *            </p>
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkPhone(String phone) {
		String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
		return Pattern.matches(regex, phone);
	}

	/**
	 * 验证整数（正整数和负整数）
	 * 
	 * @param digit
	 *            一位或多位0-9之间的整数
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkDigit(String digit) {
		String regex = "\\-?[1-9]\\d+";
		return Pattern.matches(regex, digit);
	}

	/**
	 * 验证整数和浮点数（正负整数和正负浮点数）
	 * 
	 * @param decimals
	 *            一位或多位0-9之间的浮点数，如：1.23，233.30
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkDecimals(String decimals) {
		String regex = "\\-?[1-9]\\d+(\\.\\d+)?";
		return Pattern.matches(regex, decimals);
	}

	/**
	 * 验证空白字符
	 * 
	 * @param blankSpace
	 *            空白字符，包括：空格、\t、\n、\r、\f、\x0B
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkBlankSpace(String blankSpace) {
		String regex = "\\s+";
		return Pattern.matches(regex, blankSpace);
	}

	/**
	 * 验证中文
	 * 
	 * @param chinese
	 *            中文字符
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkChinese(String chinese) {
		String regex = "^[\u4E00-\u9FA5]+$";
		return Pattern.matches(regex, chinese);
	}

	/**
	 * 验证日期（年月日）
	 * 
	 * @param birthday
	 *            日期，格式：1992-09-03，或1992.09.03
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkBirthday(String birthday) {
		String regex = "[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}";
		return Pattern.matches(regex, birthday);
	}

	/**
	 * 验证URL地址
	 * 
	 * @param url
	 *            格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或
	 *            http://www.csdn.net:80
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkURL(String url) {
		String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
		return Pattern.matches(regex, url);
	}

	/**
	 * <pre>
	 * 获取网址 URL 的一级域
	 * </pre>
	 * 
	 * @param url
	 * @return
	 */
	public static String getDomain(String url) {
		Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)",
				Pattern.CASE_INSENSITIVE);
		// 获取完整的域名
		// Pattern p=Pattern.compile("[^//]*?\\.(com|cn|net|org|biz|info|cc|tv)",
		// Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(url);
		matcher.find();
		return matcher.group();
	}

	/**
	 * 匹配中国邮政编码
	 * 
	 * @param postcode
	 *            邮政编码
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkPostcode(String postcode) {
		String regex = "[1-9]\\d{5}";
		return Pattern.matches(regex, postcode);
	}

	/**
	 * 匹配IP地址(简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
	 * 
	 * @param ipAddress
	 *            IPv4标准地址
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkIpAddress(String ipAddress) {
		String regex = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";
		return Pattern.matches(regex, ipAddress);
	}

	public static void main(String[] args) {
	/*	System.out.println(isInteger("1"));
		System.out.println(isInteger("0"));
		System.out.println(isInteger("-1"));
		System.out.println(isInteger("1.0"));
		System.out.println("--------------------");
		System.out.println(isUInteger("-1"));
		System.out.println(isUInteger("0"));
		System.out.println(isUInteger("10"));
		System.out.println(isUInteger("1.3"));
		System.out.println(isLetterAndSpace("tai  wan"));*/
		System.out.println(isPassword("fdasfsd2432"));

	}
}
