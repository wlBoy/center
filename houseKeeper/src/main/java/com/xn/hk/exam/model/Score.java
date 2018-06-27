package com.xn.hk.exam.model;

import java.io.Serializable;

/**
 * 
 * @Title: Score
 * @Package: com.xn.hk.exam.model
 * @Description: 分数实体类(用户答题时)
 * @Company: 杭州讯牛
 * @Author: wanlei
 * @Date: 2018年1月18日 下午2:41:27
 */
public class Score implements Serializable {
	private static final long serialVersionUID = -127132492181277299L;
	/**
	 * 分数ID
	 */
	private Integer scoreId;
	/**
	 * 考试用户ID
	 */
	private Integer examPaperId;
	/**
	 * 考试用户名字
	 */
	private String examPaperName;
	/**
	 * 试卷实体
	 */
	private Paper paper;
	/**
	 * 单选题分数
	 */
	private Integer singleScore;
	/**
	 * 多选题分数
	 */
	private Integer multipleScore;
	/**
	 * 简答题分数
	 */
	private Integer briefScore;
	/**
	 * 创建日期
	 */
	private String curday;
	/**
	 * 交卷时间,即创建时间
	 */
	private String submitTime;
	/**
	 * 试卷状态，0代表未考，1代表已考，2代表已批阅
	 */
	private Integer paperStatus;
	/**
	 * 是否有效：0：未删状态(默认)，1：已删状态
	 */
	private Integer isOk;

	public Integer getScoreId() {
		return scoreId;
	}

	public void setScoreId(Integer scoreId) {
		this.scoreId = scoreId;
	}

	public Integer getExamPaperId() {
		return examPaperId;
	}

	public void setExamPaperId(Integer examPaperId) {
		this.examPaperId = examPaperId;
	}

	public String getExamPaperName() {
		return examPaperName;
	}

	public void setExamPaperName(String examPaperName) {
		this.examPaperName = examPaperName;
	}

	public Paper getPaper() {
		if (paper == null) {
			paper = new Paper();
		}
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	public Integer getSingleScore() {
		return singleScore;
	}

	public void setSingleScore(Integer singleScore) {
		this.singleScore = singleScore;
	}

	public Integer getMultipleScore() {
		return multipleScore;
	}

	public void setMultipleScore(Integer multipleScore) {
		this.multipleScore = multipleScore;
	}

	public Integer getBriefScore() {
		return briefScore == null ? 0 : briefScore;
	}

	public void setBriefScore(Integer briefScore) {
		this.briefScore = briefScore;
	}

	public String getCurday() {
		return curday;
	}

	public void setCurday(String curday) {
		this.curday = curday;
	}

	public String getSubmitTime() {
		return submitTime.substring(0, submitTime.length() - 2);
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public Integer getPaperStatus() {
		return paperStatus;
	}

	public void setPaperStatus(Integer paperStatus) {
		this.paperStatus = paperStatus;
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

}
