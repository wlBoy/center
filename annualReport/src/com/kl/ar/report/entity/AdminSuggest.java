package com.kl.ar.report.entity;

import java.util.Date;

/**
 * 
 * @package:com.kl.ar.report.entity
 * @Description: 报告中的管理建议模块实体类
 * @author: wanlei
 * @date: 2019年12月21日下午3:07:58
 */
public class AdminSuggest {
	// 数据库表名
	public static String TABLE = "admin_suggest";

	private String ID;
	private String USER_ID;
	private String REPORT_ID;
	private String TYPE;
	/**
	 * 涉及部门与内容
	 */
	private String NAME;
	/**
	 * 制度、措施、执行不全之处
	 */
	private String CONTENT;
	/**
	 * 您的建议
	 */
	private String SUGGEST;

	private Date ADD_TIME;

	// 用于展示的字段
	public String userName;
	public String orgName;
	public Integer reportYear;

	public String getID() {
		return ID;
	}

	public static String getTABLE() {
		return TABLE;
	}

	public static void setTABLE(String tABLE) {
		TABLE = tABLE;
	}

	public String getTYPE() {
		return TYPE;
	}

	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getUSER_ID() {
		return USER_ID;
	}

	public String getREPORT_ID() {
		return REPORT_ID;
	}

	public void setREPORT_ID(String rEPORT_ID) {
		REPORT_ID = rEPORT_ID;
	}

	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getCONTENT() {
		return CONTENT;
	}

	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}

	public String getSUGGEST() {
		return SUGGEST;
	}

	public void setSUGGEST(String sUGGEST) {
		SUGGEST = sUGGEST;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Date getADD_TIME() {
		return ADD_TIME;
	}

	public void setADD_TIME(Date aDD_TIME) {
		ADD_TIME = aDD_TIME;
	}

	public Integer getReportYear() {
		return reportYear;
	}

	public void setReportYear(Integer reportYear) {
		this.reportYear = reportYear;
	}

}
