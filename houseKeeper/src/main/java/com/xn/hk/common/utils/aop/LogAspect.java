package com.xn.hk.common.utils.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xn.hk.common.constant.Constant;
import com.xn.hk.common.utils.string.StringUtil;
import com.xn.hk.system.dao.LogDao;
import com.xn.hk.system.model.Log;
import com.xn.hk.system.model.User;

/**
 * 
 * @Title: LogAspect
 * @Package: com.xn.hk.common.utils
 * @Description: 记录系统日志的AOP切面类
 * @Author: wanlei
 * @Date: 2018年1月23日 下午4:04:48
 */
@Component("LogAspect")
public class LogAspect {
	@Autowired
	private LogDao ld;

	/**
	 * 记录日志
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	public Object doLog(ProceedingJoinPoint pjp) throws Throwable {
		Object value = null;
		HttpServletRequest request = null;
		Log log = new Log();
		value = pjp.proceed();
		// 获取请求request
		request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		// 获取当前session中的用户
		User user = (User) request.getSession().getAttribute(Constant.SESSION_USER_KEY);
		if (user == null) {
			return value;
		}
		try {
			log.setLogId(StringUtil.genUUIDString());
			log.setUserId(user.getUserId());
			// 获取请求类名
			log.setRequestClass(pjp.getTarget().getClass().getSimpleName());
			// 获取请求方法名
			log.setRequestMethod(pjp.getSignature().getName());
			log.setRequestIp(request.getRemoteAddr());
		} catch (Throwable e) {
			log.setLogId(StringUtil.genUUIDString());
			log.setUserId(user.getUserId());
			// 获取请求类名
			log.setRequestClass(pjp.getTarget().getClass().getSimpleName());
			// 获取请求方法名
			log.setRequestMethod(pjp.getSignature().getName());
			log.setRequestIp(request.getRemoteAddr());
		}
		// 添加日志至数据库
		ld.insert(log);
		return value;
	}
}
