package com.xn.hk.common.utils.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: ExceptionUtil
 * @Package: com.xn.hk.common.utils.exception
 * @Description: 异常操作工具类
 * @Author: wanlei
 * @Date: 2018年11月1日 上午11:08:34
 */
public class ExceptionUtil {
	private static Logger logger = LoggerFactory.getLogger(ExceptionUtil.class);

	/**
	 * 获取异常的堆栈信息
	 * 
	 * @param t
	 *            任意异常类
	 * @return 异常的堆栈信息
	 */
	public static String getStackTrace(Throwable t) {
		String result = null;
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		try {
			t.printStackTrace(pw);
			result = sw.toString();
		} catch (Exception e) {
			logger.error("获取异常堆栈信息失败，原因为:{}", e);
		} finally {
			pw.close();
		}
		return result;
	}

}
