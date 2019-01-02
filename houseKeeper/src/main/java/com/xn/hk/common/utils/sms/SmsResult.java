package com.xn.hk.common.utils.sms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 
 * @ClassName: SmsResult
 * @Package: com.xn.hk.common.utils.sms
 * @Description: 中国网建SMS短信通接口返回值的枚举类(官方定义的接口返回枚举类)
 * @Author: wanlei
 * @Date: 2019年1月2日 下午5:06:32
 */
public enum SmsResult {
	NO_USER("-1", "没有该用户账户"), ERROR_KEY("-2", "接口密钥不正确"), ERROR_MD5_KEY("-21", "MD5接口密钥加密不正确"), SHORT_SMS_COUNT("-3",
			"短信数量不足"), DISABLED_USER("-11", "该用户被禁用"), EXIST_ILLEGAL_CHARACTER("-14", "短信内容出现非法字符"), ILLEGAL_SMS_MOBIL(
					"-4", "手机号格式不正确"), SMS_MOBIL_EMPTY("-41", "手机号码为空"), SMS_CONTENT_EMPTY("-42",
							"短信内容为空"), ILLEGAL_SMS_SIGN("-51", "短信签名格式不正确"), SMS_SIGN_TOO_LONGER("-52",
									"短信签名太长"), IP_LIMIT("-6", "IP限制"), SMS_COUNT("大于0", "短信条数");
	private String code;// 状态码
	private String desc;// 状态描述

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private SmsResult(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	/**
	 * 通过状态码拿到对应状态描述
	 * 
	 * @param code
	 *            状态码
	 * @return 返回状态描述
	 */
	public static String getResultDesc(String code) {
		String desc = null;
		for (SmsResult result : SmsResult.values()) {
			if (code.equals(result.getCode())) {
				desc = result.getDesc();
				break;
			}
		}
		return desc;
	}

	/**
	 * 拿到所有的状态码枚举map
	 * 
	 * @return 所有的状态码枚举map
	 */
	public static Map<String, String> getChoiceMap() {
		Map<String, String> resultMaps = new HashMap<String, String>();
		for (SmsResult item : SmsResult.values()) {
			resultMaps.put(item.getCode(), item.getDesc());
		}
		return resultMaps;
	}

	/**
	 * 拿到所有的状态码枚举List
	 * 
	 * @return 所有的状态码枚举List
	 */
	public static List<SmsResult> getChoiceList() {
		List<SmsResult> resultList = new ArrayList<SmsResult>();
		for (SmsResult item : SmsResult.values()) {
			resultList.add(item);
		}
		return resultList;
	}
}
