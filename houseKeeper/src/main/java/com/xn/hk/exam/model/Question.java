package com.xn.hk.exam.model;

import java.io.Serializable;

/**
 * 
 * @Title: Question
 * @Package: com.xn.hk.exam.model
 * @Description: 题目的实体类
 * @Company: 杭州讯牛
 * @Author: wanlei
 * @Date: 2018年1月8日 上午11:07:07
 */
public class Question implements Serializable {
	private static final long serialVersionUID = 5401434347023550114L;
	/**
	 * 题目ID
	 */
	private Integer questionId;
	/**
	 * 题型实体
	 */
	private QuestionType type;
	/**
	 * 题目标题
	 */
	private String questionTitle;
	/**
	 * 选择A
	 */
	private String optionA;
	/**
	 * 选择B
	 */
	private String optionB;
	/**
	 * 选择C
	 */
	private String optionC;
	/**
	 * 选择D
	 */
	private String optionD;
	/**
	 * 正确答案
	 */
	private String answer;
	/**
	 * 创建日期
	 */
	private String curday;
	/**
	 * 题目状态,默认0:正常;1:冻结(不能被分配给试卷)
	 */
	private Integer questionStatus;
	/**
	 * 是否有效：0：未删状态(默认)，1：已删状态
	 */
	private Integer isOk;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 最后更新时间
	 */
	private String updateTime;
	/**
	 * 备注信息
	 */
	private String remark;

	/**
	 * 题目分数,供在线考试页面显示
	 */
	private Integer questionScore;
	/**
	 * 
	 */
	private String userSolution;
	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public QuestionType getType() {
		if (type == null) {
			type = new QuestionType();
		}
		return type;
	}

	public void setType(QuestionType type) {
		this.type = type;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public String getOptionA() {
		return optionA;
	}

	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}

	public String getOptionB() {
		return optionB;
	}

	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}

	public String getOptionC() {
		return optionC;
	}

	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}

	public String getOptionD() {
		return optionD;
	}

	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getCurday() {
		return curday;
	}

	public void setCurday(String curday) {
		this.curday = curday;
	}

	public Integer getQuestionStatus() {
		return questionStatus;
	}

	public void setQuestionStatus(Integer questionStatus) {
		this.questionStatus = questionStatus;
	}

	public Integer getIsOk() {
		return isOk;
	}

	public void setIsOk(Integer isOk) {
		this.isOk = isOk;
	}

	public String getCreateTime() {
		return createTime.substring(0, createTime.length() - 2);
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime.substring(0, updateTime.length() - 2);
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getQuestionScore() {
		return questionScore;
	}

	public void setQuestionScore(Integer questionScore) {
		this.questionScore = questionScore;
	}

	public String getUserSolution() {
		return userSolution;
	}

	public void setUserSolution(String userSolution) {
		this.userSolution = userSolution;
	}

}
