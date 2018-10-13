package com.xn.hk.common.utils.log;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @ClassName: LogType
 * @Package: com.xn.hk.system.model
 * @Description: 日志类型
 * @Author: wanlei
 * @Date: 2018年10月12日 下午2:30:57
 */
public enum LogType {
	USER_LOG(1, "用户日志"), ROLE_LOG(2, "角色日志"), MODULE_LOG(4, "模块日志"), ACCOUNT_LOG(5, "账务日志"), ACCOUNT_TYPE_LOG(6,
			"账务类别日志"), PAPER_LOG(7, "试卷日志"), QUESTION_LOG(8, "题目日志"), QUESTION_TYPE_LOG(9, "题型日志");

	private Integer type;// 日志类型
	private String desc;// 日志描述

	LogType(Integer type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	/**
	 * 通过日志类型拿到对应日志描述
	 * 
	 * @param code
	 *            日志类型
	 * @return 返回日志描述
	 */
	public static String getLogType(Integer code) {
		String desc = null;
		for (LogType type : LogType.values()) {
			if (code.intValue() == type.getType().intValue()) {
				desc = type.getDesc();
				break;
			}
		}
		return desc;
	}

	/**
	 * 拿到所有的日志类型枚举map
	 * 
	 * @return 所有的日志类型枚举map
	 */
	public static Map<Integer, String> getChoiceMap() {
		Map<Integer, String> logTypeMaps = new HashMap<Integer, String>();
		for (LogType item : LogType.values()) {
			logTypeMaps.put(item.getType(), item.getDesc());
		}
		return logTypeMaps;
	}

	public Integer getType() {
		return type;
	}

	public String getDesc() {
		return desc;
	}

}