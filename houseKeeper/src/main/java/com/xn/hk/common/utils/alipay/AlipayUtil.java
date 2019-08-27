package com.xn.hk.common.utils.alipay;

import java.io.FileWriter;
import java.io.IOException;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.xn.hk.common.utils.cfg.SystemCfg;
import com.xn.hk.common.utils.date.DateUtil;
import com.xn.hk.common.utils.string.StringUtil;

/**
 * 
 * @ClassName: AlipayUtil
 * @Package: com.xn.hk.common.utils.alipay
 * @Description: 支付宝支付工具类
 * @Author: wanlei
 * @Date: 2019年8月27日 下午4:04:41
 */
public class AlipayUtil {
	private static final String FORMAT = "json";

	/**
	 * 支付接口
	 * 
	 * @param alipayBean
	 * @return
	 * @throws AlipayApiException
	 */
	public static String pay(AlipayBean alipayBean) throws AlipayApiException {
		// 1、获得初始化的AlipayClient
		String serverUrl = SystemCfg.loadCfg().getProperty(SystemCfg.ALIPAY_GATEWAY_URL);// 支付宝网关
		String appId = SystemCfg.loadCfg().getProperty(SystemCfg.ALIPAY_APP_ID);// 应用ID
		String privateKey = SystemCfg.loadCfg().getProperty(SystemCfg.ALIPAY_PRIVATE_KEY);// 商户私钥
		String charset = SystemCfg.loadCfg().getProperty(SystemCfg.ALIPAY_CHARSET);// 字符编码格式
		String alipayPublicKey = SystemCfg.loadCfg().getProperty(SystemCfg.ALIPAY_PUBLIC_KEY);// 支付宝公钥
		String signType = SystemCfg.loadCfg().getProperty(SystemCfg.ALIPAY_SIGN_TYPE);// 签名方式
		String returnUrl = SystemCfg.loadCfg().getProperty(SystemCfg.ALIPAY_RETURN_URL);// 页面跳转同步通知页面路径
		String notifyUrl = SystemCfg.loadCfg().getProperty(SystemCfg.ALIPAY_NOTIFY_URL);// 服务器异步通知页面路径
		AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId, privateKey, FORMAT, charset,
				alipayPublicKey, signType);
		// 2、设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		// 页面跳转同步通知页面路径
		alipayRequest.setReturnUrl(returnUrl);
		// 服务器异步通知页面路径
		alipayRequest.setNotifyUrl(notifyUrl);
		// 封装参数
		alipayRequest.setBizContent(JSON.toJSONString(alipayBean));
		// 3、请求支付宝进行付款，并获取支付结果
		String result = alipayClient.pageExecute(alipayRequest).getBody();
		// 返回付款信息
		return result;
	}

	/**
	 * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
	 * 
	 * @param sWord
	 *            要写入日志里的文本内容
	 */
	public static void logResult(String sWord) {
		String logPath = SystemCfg.loadCfg().getProperty(SystemCfg.ALIPAY_LOG_PATH);// 日志路径
		FileWriter writer = null;
		try {
			writer = new FileWriter(logPath + "alipay_log_" + DateUtil.formatDate() + ".log");
			writer.write(sWord);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 * @throws AlipayApiException
	 */
	public static void main(String[] args) throws AlipayApiException {
		String outTradeNo = StringUtil.genUUIDString();
		String subject = "手机";
		String totalAmount = "0.1";
		String body = "手机描述";
		AlipayBean alipayBean = new AlipayBean();
		alipayBean.setOut_trade_no(outTradeNo);
		alipayBean.setSubject(subject);
		alipayBean.setTotal_amount(totalAmount);
		alipayBean.setBody(body);
		System.out.println(pay(alipayBean));
	}
}
