package com.xn.hk.common.utils.sms;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xn.hk.common.constant.Constant;
import com.xn.hk.common.utils.string.StringUtil;

/**
 * 
 * @ClassName: SendSmsUtil
 * @Package: com.xn.hk.common.utils.sms
 * @Description: 调用第三方SDK(中国网建SMS短信通)发送短信，短信条数需要付费购买
 * @Author: wanlei
 * @Date: 2018年9月29日 下午8:41:07
 */
public class SendSmsUtil {
	/**
	 * 记录日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(SendSmsUtil.class);
	private static final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset="
			+ InitSmsCfg.SMS_CHARACTER_CODING;
	private static SendSmsUtil instance;

	private SendSmsUtil() {
	}

	/**
	 * 构造器私有化，单例模式实例化
	 * 
	 * @return
	 */
	public static synchronized SendSmsUtil getInstance() {
		if (instance == null) {
			instance = new SendSmsUtil();
		}
		return instance;
	}

	/**
	 * 调用中国网建SMS短信通的SDK发送短信
	 * 
	 */
	public void sendSms() {
		if (!Boolean.valueOf(String.valueOf(InitSmsCfg.cfgMap.get(Constant.ENABLE_SMS)))) {
			return;
		}
		String code = StringUtil.randomDigit(6);// 随机生成6位验证码数字
		String smsText = "您正在注册本站会员,本次验证码为:" + code + ",有效时间为5分钟!";
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(String.valueOf(InitSmsCfg.cfgMap.get(Constant.SMS_URL)));
		post.addRequestHeader(InitSmsCfg.CONTENT_TYPE, CONTENT_TYPE);// 在头文件中设置转码
		NameValuePair[] data = {
				new NameValuePair(InitSmsCfg.UID, String.valueOf(InitSmsCfg.cfgMap.get(Constant.SMS_USERNAME))), // 登录的用户名
				new NameValuePair(InitSmsCfg.KEY, String.valueOf(InitSmsCfg.cfgMap.get(Constant.SMS_PASSWORD))), // 接口安全密钥
				new NameValuePair(InitSmsCfg.SMSMOB, String.valueOf(InitSmsCfg.cfgMap.get(Constant.SMS_MOBILE))), // 接受验证码的手机号
				new NameValuePair(InitSmsCfg.SMSTEXT, smsText) };// 发送短信内容
		post.setRequestBody(data);
		try {
			client.executeMethod(post);// 执行POST请求
			Header[] headers = post.getResponseHeaders();
			int statusCode = post.getStatusCode();
			if (HttpStatus.SC_OK == statusCode) {
				for (Header h : headers) {
					logger.info("响应头信息如下:" + h.toString());
				}
				String result = new String(post.getResponseBodyAsString().getBytes(InitSmsCfg.SMS_CHARACTER_CODING));
				logger.info(result + "条短信发送成功!");
			} else {
				logger.error("返回状态码异常，状态码为:" + statusCode);
			}
		} catch (Exception e) {
			logger.info("短信发送失败,原因为:" + e.getMessage());
		} finally {
			post.releaseConnection();
		}
	}

	public static void main(String[] args) {
		// 调用该方法发送验证码短信
		SendSmsUtil.getInstance().sendSms();
	}
}
