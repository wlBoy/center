package com.xn.hk.system.model;

import java.io.Serializable;

import com.xn.hk.common.utils.string.StringUtil;

/**
 * @Title: Log
 * @Package: com.xn.hk.system.model
 * @Description: 系统管理的记录日志实体类(使用AOP技术实现的，这个记录的不是很规范)
 * @Author: wanlei
 * @Date: 2018年1月23日 下午3:47:38
 */
public class Log implements Serializable {
	private static final long serialVersionUID = -6708031009745280903L;
	/**
	 * 日志ID(UUID)
	 */
	private String logId;
	/**
	 * 用户ID
	 */
	private Integer userId;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 请求IP
	 */
	private String requestIp;
	/**
	 * 请求类名
	 */
	private String requestClass;
	/**
	 * 请求方法名
	 */
	private String requestMethod;
	/**
	 * 创建日期
	 */
	private Integer curday;
	/**
	 * 创建时间
	 */
	private String createTime;
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

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public String getRequestClass() {
		return requestClass;
	}

	public void setRequestClass(String requestClass) {
		this.requestClass = requestClass;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getCreateTime() {
		// 解决mysql数据库datetime类型取出时间多个.0的问题，截取前19位即可，格式为:yyyy-MM-dd HH:mm:ss
		return StringUtil.subMysqlTimeStr(createTime);
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getCurday() {
		return curday;
	}

	public void setCurday(Integer curday) {
		this.curday = curday;
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
		return "Log [logId=" + logId + ", userId=" + userId + ", userName=" + userName + ", requestIp=" + requestIp
				+ ", requestMethod=" + requestMethod + ", curday=" + curday + ", createTime=" + createTime + ", isOk="
				+ isOk + "]";
	}

}
