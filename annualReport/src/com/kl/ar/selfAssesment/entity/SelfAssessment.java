package com.kl.ar.selfAssesment.entity;

import java.util.Date;

/**
 * 
 * @package:com.kl.ar.selfAssesment.entity
 * @Description: 我的自评实体类
 * @author: wanlei
 * @date: 2019年12月21日下午3:14:56
 */
public class SelfAssessment {
	// 数据库表名
	public static String TABLE = "tb_self_assessment";
	private String SELF_ASSESSMENT_ID;
	private String USER_ID;
	private Integer PM;
	private Integer IC;
	private Integer SV;
	private Integer RP;
	private Integer SD;
	private Integer TS;
	private Integer SI;
	private Integer CW;
	private Integer DEP_PM;
	private Integer DEP_IC;
	private Integer DEP_SV;
	private Integer DEP_RP;
	private Integer DEP_SD;
	private Integer DEP_TS;
	private Integer DEP_SI;
	private Integer DEP_CW;
	private String EXCELLENT_EXAMPLES;
	private String SELF_COMMENT;
	private String LEADER_COMMENT;
	private String DEPARTMENT_COMMENT;
	private Date ADD_TIME;
	private Date UPDATE_TIME;
	private Integer STATUS;
	private Double SELF_SUM;
	private Double DEP_SUM;
	private String REVIEW_BY;
	private Integer REVIEW_RESULT;
	private String REVIEW_LEVEL;
	private String ASSESSMENT_YEAR;

	// �û�չʾ���ֶΣ�ʹ��public����
	public String userName;
	public String orgName;

	public String getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(String USER_ID) {
		this.USER_ID = USER_ID;
	}

	public Integer getPM() {
		return PM;
	}

	public void setPM(Integer PM) {
		this.PM = PM;
	}

	public Integer getIC() {
		return IC;
	}

	public void setIC(Integer IC) {
		this.IC = IC;
	}

	public Integer getSV() {
		return SV;
	}

	public void setSV(Integer SV) {
		this.SV = SV;
	}

	public Integer getRP() {
		return RP;
	}

	public void setRP(Integer RP) {
		this.RP = RP;
	}

	public Integer getSD() {
		return SD;
	}

	public void setSD(Integer SD) {
		this.SD = SD;
	}

	public Integer getTS() {
		return TS;
	}

	public void setTS(Integer TS) {
		this.TS = TS;
	}

	public Integer getSI() {
		return SI;
	}

	public void setSI(Integer SI) {
		this.SI = SI;
	}

	public Integer getCW() {
		return CW;
	}

	public void setCW(Integer CW) {
		this.CW = CW;
	}

	public String getEXCELLENT_EXAMPLES() {
		return EXCELLENT_EXAMPLES;
	}

	public void setEXCELLENT_EXAMPLES(String EXCELLENT_EXAMPLES) {
		this.EXCELLENT_EXAMPLES = EXCELLENT_EXAMPLES;
	}

	public String getSELF_COMMENT() {
		return SELF_COMMENT;
	}

	public void setSELF_COMMENT(String SELF_COMMENT) {
		this.SELF_COMMENT = SELF_COMMENT;
	}

	public String getLEADER_COMMENT() {
		return LEADER_COMMENT;
	}

	public void setLEADER_COMMENT(String LEADER_COMMENT) {
		this.LEADER_COMMENT = LEADER_COMMENT;
	}

	public String getDEPARTMENT_COMMENT() {
		return DEPARTMENT_COMMENT;
	}

	public void setDEPARTMENT_COMMENT(String DEPARTMENT_COMMENT) {
		this.DEPARTMENT_COMMENT = DEPARTMENT_COMMENT;
	}

	public String getSELF_ASSESSMENT_ID() {
		return SELF_ASSESSMENT_ID;
	}

	public void setSELF_ASSESSMENT_ID(String sELF_ASSESSMENT_ID) {
		SELF_ASSESSMENT_ID = sELF_ASSESSMENT_ID;
	}

	public Integer getDEP_PM() {
		return DEP_PM;
	}

	public void setDEP_PM(Integer dEP_PM) {
		DEP_PM = dEP_PM;
	}

	public Integer getDEP_IC() {
		return DEP_IC;
	}

	public void setDEP_IC(Integer dEP_IC) {
		DEP_IC = dEP_IC;
	}

	public Integer getDEP_SV() {
		return DEP_SV;
	}

	public void setDEP_SV(Integer dEP_SV) {
		DEP_SV = dEP_SV;
	}

	public Integer getDEP_RP() {
		return DEP_RP;
	}

	public void setDEP_RP(Integer dEP_RP) {
		DEP_RP = dEP_RP;
	}

	public Integer getDEP_SD() {
		return DEP_SD;
	}

	public void setDEP_SD(Integer dEP_SD) {
		DEP_SD = dEP_SD;
	}

	public Integer getDEP_TS() {
		return DEP_TS;
	}

	public void setDEP_TS(Integer dEP_TS) {
		DEP_TS = dEP_TS;
	}

	public Integer getDEP_SI() {
		return DEP_SI;
	}

	public void setDEP_SI(Integer dEP_SI) {
		DEP_SI = dEP_SI;
	}

	public Integer getDEP_CW() {
		return DEP_CW;
	}

	public void setDEP_CW(Integer dEP_CW) {
		DEP_CW = dEP_CW;
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

	public Integer getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(Integer sTATUS) {
		STATUS = sTATUS;
	}

	public Double getSELF_SUM() {
		return SELF_SUM;
	}

	public void setSELF_SUM(Double sELF_SUM) {
		SELF_SUM = sELF_SUM;
	}

	public Double getDEP_SUM() {
		return DEP_SUM;
	}

	public void setDEP_SUM(Double dEP_SUM) {
		DEP_SUM = dEP_SUM;
	}

	public String getREVIEW_BY() {
		return REVIEW_BY;
	}

	public void setREVIEW_BY(String rEVIEW_BY) {
		REVIEW_BY = rEVIEW_BY;
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

	public Integer getREVIEW_RESULT() {
		return REVIEW_RESULT;
	}

	public void setREVIEW_RESULT(Integer REVIEW_RESULT) {
		this.REVIEW_RESULT = REVIEW_RESULT;
	}

	public String getASSESSMENT_YEAR() {
		return ASSESSMENT_YEAR;
	}

	public void setASSESSMENT_YEAR(String aSSESSMENT_YEAR) {
		ASSESSMENT_YEAR = aSSESSMENT_YEAR;
	}

	public String getREVIEW_LEVEL() {
		return REVIEW_LEVEL;
	}

	public void setREVIEW_LEVEL(String rEVIEW_LEVEL) {
		REVIEW_LEVEL = rEVIEW_LEVEL;
	}

	@Override
	public String toString() {
		return "SelfAssessment [SELF_ASSESSMENT_ID=" + SELF_ASSESSMENT_ID + ", USER_ID=" + USER_ID + ", PM=" + PM
				+ ", IC=" + IC + ", SV=" + SV + ", RP=" + RP + ", SD=" + SD + ", TS=" + TS + ", SI=" + SI + ", CW=" + CW
				+ ", DEP_PM=" + DEP_PM + ", DEP_IC=" + DEP_IC + ", DEP_SV=" + DEP_SV + ", DEP_RP=" + DEP_RP
				+ ", DEP_SD=" + DEP_SD + ", DEP_TS=" + DEP_TS + ", DEP_SI=" + DEP_SI + ", DEP_CW=" + DEP_CW
				+ ", EXCELLENT_EXAMPLES=" + EXCELLENT_EXAMPLES + ", SELF_COMMENT=" + SELF_COMMENT + ", LEADER_COMMENT="
				+ LEADER_COMMENT + ", DEPARTMENT_COMMENT=" + DEPARTMENT_COMMENT + ", ADD_TIME=" + ADD_TIME
				+ ", UPDATE_TIME=" + UPDATE_TIME + ", STATUS=" + STATUS + ", SELF_SUM=" + SELF_SUM + ", DEP_SUM="
				+ DEP_SUM + ", REVIEW_BY=" + REVIEW_BY + ", REVIEW_RESULT=" + REVIEW_RESULT + ", REVIEW_LEVEL="
				+ REVIEW_LEVEL + ", ASSESSMENT_YEAR=" + ASSESSMENT_YEAR + ", userName=" + userName + ", orgName="
				+ orgName + "]";
	}

}
