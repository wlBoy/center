package com.xn.hk.common.aspectj;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.xn.hk.common.aspectj.annotation.Log;
import com.xn.hk.common.aspectj.enums.BusinessStatus;
import com.xn.hk.common.constant.Constant;
import com.xn.hk.common.model.OperLog;
import com.xn.hk.common.utils.ip.IpUtil;
import com.xn.hk.common.utils.string.StringUtil;
import com.xn.hk.common.utils.web.ServletUtil;
import com.xn.hk.system.model.User;

/**
 * 操作日志记录处理
 * 
 * @author ruoyi
 */
@Aspect
@Component
public class LogAspect
{
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    // 配置织入点
    @Pointcut("@annotation(com.xn.hk.common.aspectj.annotation.Log)")
    public void logPointCut()
    {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult,HttpServletRequest request)
    {
        handleLog(joinPoint, null, jsonResult,request);
    }

    /**
     * 拦截异常操作
     * 
     * @param joinPoint 切点
     * @param e 异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e,HttpServletRequest request)
    {
        handleLog(joinPoint, e, null,request);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult,HttpServletRequest request)
    {
        try
        {
            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null)
            {
                return;
            }

            // 获取当前的用户
            // 从session中拿出用户信息
    		User currentUser = (User) request.getSession().getAttribute(Constant.SESSION_USER);

            // *========数据库日志=========*//
            OperLog operLog = new OperLog();
            operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            // 请求的地址
            String ip = IpUtil.getIpAddr(request);
            operLog.setOperIp(ip);
            // 返回参数
            operLog.setJsonResult(JSONObject.toJSONString(jsonResult));

            operLog.setOperUrl(ServletUtil.getRequest().getRequestURI());
            if (currentUser != null)
            {
                operLog.setOperName(currentUser.getUserName());
            }

            if (e != null)
            {
                operLog.setStatus(BusinessStatus.FAIL.ordinal());
                operLog.setErrorMsg(StringUtil.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            operLog.setRequestMethod(ServletUtil.getRequest().getMethod());
            // 处理设置注解上的参数
            getControllerMethodDescription(controllerLog, operLog);
            // 保存数据库
            //AsyncManager.me().execute(AsyncFactory.recordOper(operLog));
        }
        catch (Exception exp)
        {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     * 
     * @param log 日志
     * @param operLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(Log log, OperLog operLog) throws Exception
    {
        // 设置action动作
        operLog.setBusinessType(log.businessType().ordinal());
        // 设置标题
        operLog.setTitle(log.title());
        // 设置操作人类别
        operLog.setOperatorType(log.operatorType().ordinal());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData())
        {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(operLog);
        }
    }

    /**
     * 获取请求的参数，放到log中
     * 
     * @param operLog
     * @param request
     */
    private void setRequestValue(OperLog operLog)
    {
        Map<String, String[]> map = ServletUtil.getRequest().getParameterMap();
        String params = JSONObject.toJSONString(map);
        operLog.setOperParam(StringUtil.substring(params, 0, 2000));
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception
    {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null)
        {
            return method.getAnnotation(Log.class);
        }
        return null;
    }
}
