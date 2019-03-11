package com.xn.hk.common.utils.log;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xn.hk.account.model.Account;
import com.xn.hk.account.model.AccountType;
import com.xn.hk.common.constant.Constant;
import com.xn.hk.common.constant.EnabledEnum;
import com.xn.hk.common.constant.StatusEnum;
import com.xn.hk.common.dao.BaseDao;
import com.xn.hk.common.utils.date.DateUtil;
import com.xn.hk.common.utils.net.IpHelper;
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
	private static final String LOG_CONTENT_METHOD = "getLogContent";
	private static LogHelper instance = null;

	private LogHelper() {
		super();
	}

	/**
	 * 懒汉式单例模式，线程安全
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
		String logContent = logName + getLogContentByReflect(obj);// 拼接日志内容
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
		adminLog.setLogTime(DateUtil.formatDateTime());
		adminLog.setCurday(DateUtil.getNumberDay());
		adminLog.setLogResult(logResult == true ? "成功" : "失败");
		adminLog.setLogName(logName);
		adminLog.setLogTimeStamp(DateUtil.formatDateTime());
		adminLog.setLogIp(IpHelper.getIp());
		adminLog.setIsOk(Constant.ZERO_VALUE);
		int result = adminLogDao.insert(adminLog);
		if (result == Constant.ZERO_VALUE) {
			logger.error("插入日志失败，日志信息为:{}", adminLog.toString());
		} else {
			logger.error("插入日志成功，日志信息为:{}", adminLog.toString());
		}
	}

	/**
	 * 通过传进来的obj对象动态生成日志内容[key1=value1,key2=value2] 这种方法太麻烦了
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
			logger.error("不支持该对象模型:{}!", obj);
			return "";
		}
	}

	/**
	 * 通过反射实现获取实体类中的日志内容 推荐使用这种方法
	 * 
	 * @param obj
	 *            实体对象
	 * @return 日志内容
	 */
	@SuppressWarnings("all")
	private String getLogContentByReflect(Object obj) {
		String logContent = null;
		Class clazz = obj.getClass();
		Method[] methods = clazz.getDeclaredMethods();
		try {
			Method logMethod = clazz.getMethod(LOG_CONTENT_METHOD);
			if (logMethod != null && !StringUtil.isEmpty(logMethod.getName())) {
				logMethod.setAccessible(true);
				logContent = String.valueOf(logMethod.invoke(obj));
			} else {
				logger.error("{}类中,没有找到getLogContent方法!", clazz.getName());
			}
		} catch (Exception e) {
			logger.error("{}类中,使用反射调用getLogContent方法失败,原因为:{}", clazz.getName(), e);
		}
		return logContent;
	}

	public static void main(String[] args) {
		User user = new User();
		user.setUserId(21);
		user.setUserName("测试");
		user.setRemark("测试记录日志");
		user.setUserState(StatusEnum.ACTIVE.getCode());
		user.setIsOk(EnabledEnum.ENABLED.getCode());
		user.getRole().setRoleName("测试角色");
		user.setCreateTime(DateUtil.formatDateTime(new Date()));
		user.setUpdateTime(DateUtil.formatDateTime(new Date()));
		System.out.println("日志内容为:" + LogHelper.getInstance().getLogContent(user));
		System.out.println("日志内容为:" + LogHelper.getInstance().getLogContentByReflect(user));
	}
}
