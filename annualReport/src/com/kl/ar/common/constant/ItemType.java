package com.kl.ar.common.constant;

/**
 * 
 * @package:com.kl.ar.common.constant
 * @Description: 年终报告模板对应的各模块枚举类
 * @author: wanlei
 * @date: 2019年12月21日下午2:06:53
 */
public enum ItemType {
	
	RESPONSIBILITIES_TEXT("RESPONSIBILITIES_TEXT", "1.1岗位职责"),
	SALE_HISTORY_TABLE("SALE_HISTORY_TABLE", "1.1.1.1销售回顾 - 表01"),
	NEW_CUSTOMER_TABLE("NEW_CUSTOMER_TABLE","1.1.1.2新客户开拓情况 - 表02"), 
	BIG_PROJECT_TABLE("BIG_PROJECT_TABLE", "1.1.1.3个人前3-5大项目推进情况 - 表03"),
	DEVELOP_WORK_TABLE("DEVELOP_WORK_TABLE", "1.1.2.1技术研发人员工作概况，请描述 - 表04"), 
	DEPLOY_WORK_TABLE("DEPLOY_WORK_TABLE","1.1.2.2实施人员工作概况，请描述 - 表05"), 
	TEST_WORK_TABLE("TEST_WORK_TABLE", "1.1.2.3测试人员工作概况，请描述 - 表06"),
	ADMIN_WORK_TABLE("ADMIN_WORK_TABLE", "1.1.3行政类人员-表07"),
	SECRECY_WORK_TABLE("SECRECY_WORK_TABLE", "2.保密工作情况及总结-表08"),
	PERSONAL_GOALS_TEXT("PERSONAL_GOALS_TEXT", "3.1个人目标的实现程度"),
	PROGRESS_TEXT("PROGRESS_TEXT", "3.2进步和收获"),
	PROBLEM_TEXT("PROBLEM_TEXT", "3.3问题与不足"),
	TARGET_TEXT("TARGET_TEXT", "4.明年目标"),
	PRODUCT_SUGGEST_TABLE("PRODUCT_SUGGEST_TABLE", "5.1 产品建议"),
	PROJECT_SUGGEST_TABLE("PROJECT_SUGGEST_TABLE", "5.2项目建议"),
	ADMIN_SUGGEST_TABLE("ADMIN_SUGGEST_TABLE", "5.3 管理建议");
	/**
	 * id
	 */
	private String id;
	/**
	 * 描述
	 */
	private String desc;

	private ItemType(String id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
