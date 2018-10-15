package com.xn.hk.common.utils.log;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xn.hk.account.model.Account;
import com.xn.hk.account.model.AccountType;
import com.xn.hk.common.constant.Constant;
import com.xn.hk.common.dao.BaseDao;
import com.xn.hk.common.utils.ip.IpHelper;
import com.xn.hk.common.utils.string.DateFormatUtil;
import com.xn.hk.common.utils.string.StringUtil;
import com.xn.hk.exam.model.Paper;
import com.xn.hk.exam.model.Question;
import com.xn.hk.exam.model.QuestionType;
import com.xn.hk.system.model.AdminLog;
import com.xn.hk.system.model.Module;
import com.xn.hk.system.model.Role;
import com.xn.hk.system.model.User;

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

	/**
	 * 单例模式
	 * 
	 * @return
	 */
	public static synchronized LogHelper getInstance() {
		if (instance == null) {
			instance = new LogHelper();
		}
		return instance;
	}

	/**
	 * 保存日志信息
	 * 
	 * @param adminLogDao
	 *            日志dao层
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
	public void saveLog(BaseDao<AdminLog> adminLogDao, HttpSession session, String logName, boolean logResult,
			Integer logType, Object obj) {
		String logContent = logName + getLogContent(obj);// 拼接日志内容
		AdminLog adminLog = new AdminLog();
		// 从session中拿出用户信息
		User user = (User) session.getAttribute(Constant.SESSION_USER);
		// 当用户session为空时，此时为登录或注销操作，此时从obj中取用户信息
		if (user == null) {
			user = (User) obj;
		}
		adminLog.setUserId(user.getUserId());
		adminLog.setUserName(user.getUserName());
		adminLog.setLogId(StringUtil.genUUIDString());
		adminLog.setLogType(logType);
		adminLog.setLogContent(logContent);
		adminLog.setLogTime(DateFormatUtil.formatDateTime());
		adminLog.setCurday(DateFormatUtil.getNumberDay());
		adminLog.setLogResult(logResult == true ? "成功" : "失败");
		adminLog.setLogName(logName);
		adminLog.setLogTimeStamp(DateFormatUtil.formatDateTime());
		adminLog.setLogIp(IpHelper.getIp());
		adminLog.setIsOk(Constant.ZERO_VALUE);
		int result = adminLogDao.insert(adminLog);
		if (result == Constant.ZERO_VALUE) {
			logger.error("插入日志失败，日志信息为:" + adminLog.toString());
		} else {
			logger.error("插入日志成功，日志信息为:" + adminLog.toString());
		}
	}

	/**
	 * 通过传进来的obj对象动态生成日志内容[key1=value1,key2=value2]
	 * 
	 * @param obj
	 *            obj对象
	 * @return 日志内容
	 */
	private String getLogContent(Object obj) {
		if (obj instanceof User) {
			User user = (User) obj;
			return user.getLogContent();
		} else if (obj instanceof Role) {
			Role role = (Role) obj;
			return role.getLogContent();
		} else if (obj instanceof Module) {
			Module module = (Module) obj;
			return module.getLogContent();
		} else if (obj instanceof Account) {
			Account account = (Account) obj;
			return account.getLogContent();
		} else if (obj instanceof AccountType) {
			AccountType accountType = (AccountType) obj;
			return accountType.getLogContent();
		} else if (obj instanceof QuestionType) {
			QuestionType questionType = (QuestionType) obj;
			return questionType.getLogContent();
		} else if (obj instanceof Question) {
			Question question = (Question) obj;
			return question.getLogContent();
		} else if (obj instanceof Paper) {
			Paper paper = (Paper) obj;
			return paper.getLogContent();
		} else {
			logger.error("不支持改对象模型!");
			return "";
		}
	}

}
