package com.xn.hk.common.utils.sms;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xn.hk.common.utils.cfg.SystemCfg;
import com.xn.hk.common.utils.encryption.HashUtil;
import com.xn.hk.common.utils.string.RegexUtil;
import com.xn.hk.common.utils.string.StringUtil;

/**
 * 
 * @ClassName: SmsUtil
 * @Package: com.xn.hk.common.utils.sms
 * @Description: 调用第三方SDK(中国网建SMS短信通)发送短信，短信条数需要付费购买
 * @Author: wanlei
 * @Date: 2018年9月29日 下午8:41:07
 */
public class SmsUtil {
	/**
	 * 记录日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(SmsUtil.class);
	private static final String GBK_URL = "http://gbk.api.smschinese.cn";
	private static final String UTF8_URL = "http://utf8.api.smschinese.cn";
	// 中国网建SMS短信通SDK中的key(官方固定的)
	private static final String UID = "Uid";
	private static final String KEY = "Key";
	private static final String KEY_MD5 = "KeyMD5";
	private static final String SMSMOB = "smsMob";
	private static final String SMSTEXT = "smsText";
	private static final String CONTENT_TYPE_KEY = "Content-Type";
	// 取出配置文件中sms编码
	private static final String SMS_CHARACTER_CODING = SystemCfg.loadCfgMap()
			.get(SystemCfg.SMS_CHARACTER_CODING).toLowerCase();
	private static final String CONTENT_TYPE_VALUE = "application/x-www-form-urlencoded;charset="
			+ SMS_CHARACTER_CODING;
	private static SmsUtil instance;

	private SmsUtil() {
	}

	/**
	 * 构造器私有化，单例模式实例化
	 * 
	 * @return
	 */
	public static synchronized SmsUtil getInstance() {
		if (instance == null) {
			instance = new SmsUtil();
		}
		return instance;
	}

	/**
	 * 调用中国网建SMS短信通的SDK发送短信
	 * 
	 * @param smsText
	 *            发送短信的内容
	 */
	public void sendSms(String smsText) {
		String smsUrl = "";
		if (!Boolean.valueOf(SystemCfg.loadCfgMap().get(SystemCfg.ENABLE_SMS))) {
			logger.error("请启用发送sms短信功能!");
			return;
		}
		String smsMobile = SystemCfg.loadCfgMap().get(SystemCfg.SMS_MOBILE);
		if (RegexUtil.checkMobile(smsMobile)) {
			logger.error("{}手机号码不合法!", smsMobile);
			return;
		}
		// 根据配置文件中的SMS编码判断smsUrl发送地址
		if ("gbk".equalsIgnoreCase(SMS_CHARACTER_CODING)) {
			smsUrl = GBK_URL;
		} else {
			smsUrl = UTF8_URL;
		}
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(smsUrl);
		post.addRequestHeader(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);// 在头文件中设置转码
		NameValuePair[] data = {
				new NameValuePair(UID, SystemCfg.loadCfgMap().get(SystemCfg.SMS_USERNAME)), // 登录的用户名
				//new NameValuePair(KEY, SystemCfg.loadCfgMap().get(SystemCfg.SMS_PASSWORD)), // 接口安全密钥
				new NameValuePair(KEY_MD5, HashUtil.encryptStr(SystemCfg.loadCfgMap().get(SystemCfg.SMS_PASSWORD)).toUpperCase()), //接口秘钥32位MD5加密，大写
				new NameValuePair(SMSMOB, smsMobile), // 接受验证码的手机号（多个手机号请用半角逗号隔开，如：13888888886,13888888887等，一次最多对100个手机发送） 
				new NameValuePair(SMSTEXT, smsText) };// 发送短信内容，支持长短信，最多400字，普通短信70个字/条含签名，长短信64字/条计费
		post.setRequestBody(data);
		try {
			client.executeMethod(post);// 执行POST请求
			Header[] headers = post.getResponseHeaders();
			int statusCode = post.getStatusCode();
			if (HttpStatus.SC_OK == statusCode) {
				for (Header h : headers) {
					logger.info("响应头信息如下:" + h.toString());
				}
				String result = new String(post.getResponseBodyAsString().getBytes(SMS_CHARACTER_CODING));
				if (Integer.parseInt(result) > 0) {
					logger.info(result + "条短信发送成功!");
				} else {
					logger.error("发送短信失败，接口返回值为:" + result + ",其描述为:" + SmsResult.getResultDesc(result));
				}
			} else {
				logger.error("返回http请求状态码异常，状态码为:" + statusCode);
			}
		} catch (Exception e) {
			logger.info("短信发送失败,原因为:" + e.getMessage());
		} finally {
			post.releaseConnection();
		}
	}

	public static void main(String[] args) {
		String code = StringUtil.randomDigit(6);// 随机生成6位验证码数字
		String smsText = "您正在注册本站会员,本次验证码为:" + code + ",有效时间为5分钟!";
		// 调用该方法发送验证码短信
		SmsUtil.getInstance().sendSms(smsText);
	}
}
