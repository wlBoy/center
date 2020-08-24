package com.xn.hk.common.utils.encryption;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xn.hk.common.constant.Constant;
import com.xn.hk.common.exception.sign.SignException;
import com.xn.hk.common.utils.cfg.SystemCfg;
import com.xn.hk.common.utils.string.StringUtil;

/**
 * 
 * @ClassName: InterfaceUtil
 * @Package: com.xn.hk.common.utils.encryption
 * @Description: 接口安全工具类
 * @Author: wanlei
 * @Date: 2019年2月25日 下午5:28:25
 */
public class InterfaceSignUtil {
	private static final Logger logger = LoggerFactory.getLogger(InterfaceSignUtil.class);

	/**
	 * 接口签名保护，更安全
	 * 
	 * @param request
	 * @throws Exception
	 */
	public static void protect(HttpServletRequest request) throws Exception {
		// 1.判断是否开启接口签名验证功能
		String enableIntefaceSign = SystemCfg.loadCfgMap().get(SystemCfg.ENABLE_INTERFACE_SIGN);
		if (!Boolean.valueOf(enableIntefaceSign)) {
			return;
		}
		// 2.从请求头中获取各必填参数并读取相关配置
		String ts = getHeaderValueByRequest(request, Constant.HEADER_PARAM_TIME_STAMP);
		String once = getHeaderValueByRequest(request, Constant.HEADER_PARAM_ONCE);
		String appKey = getHeaderValueByRequest(request, Constant.HEADER_PARAM_APP_KEY);
		String signMethod = getHeaderValueByRequest(request, Constant.HEADER_PARAM_SIGN_METHOD);
		String signData = getHeaderValueByRequest(request, Constant.HEADER_PARAM_SIGN_DATA);

		String enableCheckSignTime = SystemCfg.loadCfgMap().get(SystemCfg.ENABLE_CHECK_SIGN_TIME);
		String signValidTime = SystemCfg.loadCfgMap().get(SystemCfg.SIGN_VALID_TIME);
		String signAlgorithm = SystemCfg.loadCfgMap().get(SystemCfg.SIGN_ALGORITHM);
		String appInfoList = SystemCfg.loadCfgMap().get(SystemCfg.APP_INFO_LIST);

		// 3.校验签名算法
		if (!signMethod.equalsIgnoreCase(signAlgorithm)) {
			throw new SignException(String.format("不支持该签名算法 - [%s] ", signMethod));
		}

		// 4.从缓存当查询应用信息，此处使用从配置文件中读取应用KEY和应用编码对应关系
		Map<String, String> appInfoMap = getAppFromCfg(appInfoList);
		if (appInfoMap.containsKey(appKey)) {
			throw new SignException(String.format("未查询到应用编码 - [%s] ", appKey));
		}
		// 5.获取应用密钥
		String appPwd = String.valueOf(appInfoMap.get(appKey));
		if (StringUtil.isEmpty(appPwd)) {
			throw new SignException(String.format("未查询到应用密钥 - [%s] ", appKey));
		}
		// 6.构建原文
		String originData = String.format("appKey=%s&once=%s&signMethod=%s&ts=%s", appKey, once, signMethod, ts);
		logger.info("接口签名安全保护-签名原文为:[{}],签名算法为:[{}]", originData, signMethod);
		// 7.验证签名
		String signResult = HashUtil.getSignResult(appPwd, originData, signMethod);
		boolean isValid = signResult.equals(signData);
		logger.info("接口签名安全保护-参数中的签名结果为:[{}],服务端的签名结果为:[{}],比对结果为:[{}]", signData, signResult, isValid);
		if (!isValid) {
			throw new SignException("签名结果验证失败");
		}
		// 8.判断是否验证签名有效期
		if (Boolean.valueOf(enableCheckSignTime)) {
			long currentTime = System.currentTimeMillis() / 1000;
			long signTime = Long.parseLong(ts) / 1000;
			if ((currentTime - signTime) > Long.parseLong(signValidTime)) {
				throw new SignException("签名结果已过期");
			}
		}
	};

	/**
	 * 将应用编码和应用KEY的对应关系解析成应用MAP
	 * 
	 * @param appInfoList
	 * @return
	 */
	private static Map<String, String> getAppFromCfg(String appInfoList) {
		Map<String, String> appMap = new HashMap<String, String>();
		if (StringUtil.isEmpty(appInfoList)) {
			return null;
		}
		for (String appInfo : appInfoList.split("&")) {
			if (StringUtil.isEmpty(appInfo)) {
				continue;
			}
			appMap.put(appInfo.split(":")[0], appInfo.split(":")[1]);
		}
		return appMap;
	}

	/**
	 * 获取头信息中的值
	 *
	 * @param request
	 * @param paramName
	 * @return
	 * @throws Exception
	 */
	private static String getHeaderValueByRequest(final HttpServletRequest request, String paramName) throws Exception {
		String header = request.getHeader(paramName);
		if (StringUtil.isEmpty(header)) {
			throw new SignException(String.format("请求头中 [%s] 参数为空", paramName));
		}
		return header;
	}
}
