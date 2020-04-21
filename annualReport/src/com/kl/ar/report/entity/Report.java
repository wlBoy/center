package com.kl.ar.report.entity;

import java.util.Date;

/**
 * 
 * @package:com.kl.ar.report.entity
 * @Description: 报告实体类
 * @author: wanlei
 * @date: 2019年12月21日下午3:10:31
 */
public class Report {
	// 数据库表名
	public static String TABLE = "tb_report";
	private String ID;
	private String USER_ID;
	private String RESPONSIBILITIES;
	private String SALE_HISTORY;
	private String NEW_CUSTOMER;
	private String BIG_PROJECT;
	private String DEVELOP_WORK;
	private String DEPLOY_WORK;
	private String TEST_WORK;
	private String ADMIN_WORK;
	private String SECRECT_WORK;
	private String PERSONAL_GOALS;
	private String PROGRESS;
	private String PROBLEM;
	private String TARGET;
	private String PRODUCT_SUGGEST;
	private String PROJECT_SUGGEST;
	private String ADMIN_SUGGEST;
	private String STATUS;
	private String SUGGEST;
	private String TYPE;
	private Date ADD_TIME;
	private Date UPDATE_TIME;
	private String REPORT_YEAR;

	// 用户展示的字段，使用public修饰
	public String userName;
	public String orgName;

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}

	public String getRESPONSIBILITIES() {
		return RESPONSIBILITIES;
	}

	public void setRESPONSIBILITIES(String rESPONSIBILITIES) {
		RESPONSIBILITIES = rESPONSIBILITIES;
	}

	public String getSALE_HISTORY() {
		return SALE_HISTORY;
	}

	public void setSALE_HISTORY(String sALE_HISTORY) {
		SALE_HISTORY = sALE_HISTORY;
	}

	public String getNEW_CUSTOMER() {
		return NEW_CUSTOMER;
	}

	public void setNEW_CUSTOMER(String nEW_CUSTOMER) {
		NEW_CUSTOMER = nEW_CUSTOMER;
	}

	public String getBIG_PROJECT() {
		return BIG_PROJECT;
	}

	public void setBIG_PROJECT(String bIG_PROJECT) {
		BIG_PROJECT = bIG_PROJECT;
	}

	public String getPERSONAL_GOALS() {
		return PERSONAL_GOALS;
	}

	public void setPERSONAL_GOALS(String pERSONAL_GOALS) {
		PERSONAL_GOALS = pERSONAL_GOALS;
	}

	public String getPROGRESS() {
		return PROGRESS;
	}

	public void setPROGRESS(String pROGRESS) {
		PROGRESS = pROGRESS;
	}

	public String getPROBLEM() {
		return PROBLEM;
	}

	public void setPROBLEM(String pROBLEM) {
		PROBLEM = pROBLEM;
	}

	public String getTARGET() {
		return TARGET;
	}

	public void setTARGET(String tARGET) {
		TARGET = tARGET;
	}

	public String getPRODUCT_SUGGEST() {
		return PRODUCT_SUGGEST;
	}

	public void setPRODUCT_SUGGEST(String pRODUCT_SUGGEST) {
		PRODUCT_SUGGEST = pRODUCT_SUGGEST;
	}

	public String getPROJECT_SUGGEST() {
		return PROJECT_SUGGEST;
	}

	public void setPROJECT_SUGGEST(String pROJECT_SUGGEST) {
		PROJECT_SUGGEST = pROJECT_SUGGEST;
	}

	public String getADMIN_SUGGEST() {
		return ADMIN_SUGGEST;
	}

	public void setADMIN_SUGGEST(String aDMIN_SUGGEST) {
		ADMIN_SUGGEST = aDMIN_SUGGEST;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public String getSUGGEST() {
		return SUGGEST;
	}

	public void setSUGGEST(String sUGGEST) {
		SUGGEST = sUGGEST;
	}

	public String getDEVELOP_WORK() {
		return DEVELOP_WORK;
	}

	public void setDEVELOP_WORK(String dEVELOP_WORK) {
		DEVELOP_WORK = dEVELOP_WORK;
	}

	public String getDEPLOY_WORK() {
		return DEPLOY_WORK;
	}

	public void setDEPLOY_WORK(String dEPLOY_WORK) {
		DEPLOY_WORK = dEPLOY_WORK;
	}

	public String getTEST_WORK() {
		return TEST_WORK;
	}

	public void setTEST_WORK(String tEST_WORK) {
		TEST_WORK = tEST_WORK;
	}

	public String getADMIN_WORK() {
		return ADMIN_WORK;
	}

	public void setADMIN_WORK(String aDMIN_WORK) {
		ADMIN_WORK = aDMIN_WORK;
	}

	public String getSECRECT_WORK() {
		return SECRECT_WORK;
	}

	public void setSECRECT_WORK(String sECRECT_WORK) {
		SECRECT_WORK = sECRECT_WORK;
	}

	public String getTYPE() {
		return TYPE;
	}

	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}

	public Date getADD_TIME() {
		return ADD_TIME;
	}

	public void setADD_TIME(Date aDD_TIME) {
		ADD_TIME = aDD_TIME;
	}

	public Date getUPDATE_TIME() {
		return UPDATE_TIME;
	}

	public void setUPDATE_TIME(Date uPDATE_TIME) {
		UPDATE_TIME = uPDATE_TIME;
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

	public String getREPORT_YEAR() {
		return REPORT_YEAR;
	}

	public void setREPORT_YEAR(String rEPORT_YEAR) {
		REPORT_YEAR = rEPORT_YEAR;
	}

}
