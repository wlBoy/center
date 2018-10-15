package com.xn.hk.system.model;

import java.io.Serializable;

import com.xn.hk.common.utils.log.LogType;

/**
 * 
 * @ClassName: AdminLog
 * @Package: com.xn.hk.system.model
 * @Description: 管理员操作日志实体类(使用LogHelper类实现的，记录信息更全更规范，推荐使用这种方式)
 * @Author: wanlei
 * @Date: 2018年10月12日 下午2:51:41
 */
public class AdminLog implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 日志ID
	 */
	private String logId;
	/**
	 * 用户ID
	 */
	private Integer userId;
	/**
	 * 用户姓名
	 */
	private String userName;
	/**
	 * 日志类型
	 */
	private Integer logType;
	/**
	 * 日志类型描述(枚举类中的描述)
	 */
	private String logTypeDesc;
	/**
	 * 日志记录IP
	 */
	private String logIp;
	/**
	 * 操作名称
	 */
	private String logName;
	/**
	 * 操作内容
	 */
	private String logContent;
	/**
	 * 操作结果
	 */
	private String logResult;
	/**
	 * 创建日期
	 */
	private Integer curday;
	/**
	 * 日志时间
	 */
	private String logTime;
	/**
	 * 日志时间戳
	 */
	private String logTimeStamp;
	/**
	 * 是否有效：0：未删状态(默认)，1：已删状态
	 */
	private Integer isOk;

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public Integer getUserId() {
		return userId;
	}

	public String getLogTypeDesc() {
		// 替换枚举类中的描述语
		logTypeDesc = LogType.getLogType(logType);
		return logTypeDesc;
	}

	public void setLogTypeDesc(String logTypeDesc) {
		this.logTypeDesc = logTypeDesc;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getLogType() {
		return logType;
	}

	public void setLogType(Integer logType) {
		this.logType = logType;
	}

	public String getLogIp() {
		return logIp;
	}

	public void setLogIp(String logIp) {
		this.logIp = logIp;
	}

	public String getLogName() {
		return logName;
	}

	public void setLogName(String logName) {
		this.logName = logName;
	}

	public String getLogContent() {
		return logContent;
	}

	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}

	public String getLogResult() {
		return logResult;
	}

	public void setLogResult(String logResult) {
		this.logResult = logResult;
	}

	public Integer getCurday() {
		return curday;
	}

	public void setCurday(Integer curday) {
		this.curday = curday;
	}

	public String getLogTime() {
		return logTime.substring(0, logTime.length() - 2);
	}

	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}

	public String getLogTimeStamp() {
		return logTimeStamp.substring(0, logTimeStamp.length() - 2);
	}

	public void setLogTimeStamp(String logTimeStamp) {
		this.logTimeStamp = logTimeStamp;
	}

	public Integer getIsOk() {
		return isOk;
	}

	public void setIsOk(Integer isOk) {
		this.isOk = isOk;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "AdminLog [logId=" + logId + ", userId=" + userId + ", userName=" + userName + ", logType=" + logType
				+ ", logIp=" + logIp + ", logName=" + logName + ", logContent=" + logContent + ", logResult="
				+ logResult + ", curday=" + curday + ", logTime=" + logTime + ", logTimeStamp=" + logTimeStamp
				+ ", isOk=" + isOk + "]";
	}

}
