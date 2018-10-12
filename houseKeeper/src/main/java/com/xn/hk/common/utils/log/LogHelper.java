package com.xn.hk.common.utils.log;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.xn.hk.common.constant.Constant;
import com.xn.hk.common.utils.filter.ManagerLoginFilter;
import com.xn.hk.common.utils.string.DateFormatUtil;
import com.xn.hk.common.utils.string.StringUtil;
import com.xn.hk.system.model.AdminLog;
import com.xn.hk.system.model.User;
import com.xn.hk.system.service.AdminLogService;

/**
 * 
 * @ClassName: LogHelper
 * @Package: com.xn.hk.common.utils.log
 * @Description: 记录日志的工具类
 * @Author: wanlei
 * @Date: 2018年10月12日 下午3:13:52
 */
public class LogHelper {
	/**
	 * 记录日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(LogHelper.class);
	private static LogHelper instance = null;

	private LogHelper() {

	}

	public static synchronized LogHelper getInstance() {
		if (instance == null) {
			instance = new LogHelper();
		}
		return instance;
	}

	/**
	 * 注入日志服务类
	 */
	@Autowired
	private AdminLogService adminLogService;

	/**
	 * 保存日志信息
	 * 
	 * @param session
	 *            session
	 * @param logName
	 *            日志操作名称
	 * @param logResult
	 *            操作结果
	 * @param logType
	 *            日志类型
	 * @param Object
	 *            日志记录对象
	 */
	public void saveLog(HttpSession session, String logName, boolean logResult, Integer logType, Object obj) {
		String logContent = logName + "[" + JSONObject.toJSONString(obj) + "]";// 拼接日志内容
		// 从session中拿出用户信息
		User user = (User) session.getAttribute(Constant.SESSION_USER_KEY);
		AdminLog adminLog = new AdminLog();
		adminLog.setLogId(StringUtil.genUUIDString());
		adminLog.setUserId(user.getUserId());
		adminLog.setUserName(user.getUserName());
		adminLog.setLogType(logType);
		adminLog.setLogContent(logContent);
		adminLog.setLogTime(DateFormatUtil.formatDate());
		adminLog.setCurday(DateFormatUtil.getNumberDay());
		adminLog.setLogResult(logResult == true ? "成功" : "失败");
		adminLog.setLogName(logName);
		adminLog.setLogIp(ManagerLoginFilter.IP);
		adminLog.setIsOk(0);
		int result = adminLogService.insert(adminLog);
		if (result == Constant.ZERO_VALUE) {
			logger.error("插入日志失败，日志信息为:" + adminLog.toString());
		} else {
			logger.error("插入日志成功，日志信息为:" + adminLog.toString());
		}
	}

}
