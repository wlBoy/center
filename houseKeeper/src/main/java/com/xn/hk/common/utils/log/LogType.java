package com.xn.hk.common.utils.log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	USER_LOG(1, "用户日志"), ROLE_LOG(2, "角色日志"), MODULE_LOG(3, "模块日志"), ACCOUNT_LOG(4, "账务日志"), ACCOUNT_TYPE_LOG(5,
			"账务类别日志"), PAPER_LOG(6, "试卷日志"), QUESTION_LOG(7, "题目日志"), QUESTION_TYPE_LOG(8,
					"题型日志"), FILE_LOG(9, "文件日志"), DATA_DIC_LOG(10, "数据字典日志"), DATA_DIC_TERM_LOG(11, "数据字典项日志");

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

	/**
	 * 拿到所有的日志类型枚举List
	 * 
	 * @return 所有的日志类型枚举List
	 */
	public static List<LogType> getChoiceList() {
		List<LogType> logTypeList = new ArrayList<LogType>();
		for (LogType item : LogType.values()) {
			logTypeList.add(item);
		}
		return logTypeList;
	}

	public Integer getType() {
		return type;
	}

	public String getDesc() {
		return desc;
	}

}
