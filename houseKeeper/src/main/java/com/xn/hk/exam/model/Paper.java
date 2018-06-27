package com.xn.hk.exam.model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 
 * @Title: Paper
 * @Package: com.xn.hk.exam.model
 * @Description: 试卷实体类
 * @Company: 杭州讯牛
 * @Author: wanlei
 * @Date: 2018年1月8日 上午11:13:45
 */
public class Paper implements Serializable {
	private static final long serialVersionUID = 3098295831014537183L;
	/**
	 * 试卷ID
	 */
	private Integer paperId;
	/**
	 * 出卷人ID
	 */
	private Integer createPaperId;
	/**
	 * 出卷人姓名
	 */
	private String createPaperName;
	/**
	 * 试卷标题
	 */
	private String paperName;
	/**
	 * 试卷总分
	 */
	private Integer paperScore;
	/**
	 * 试卷总时间
	 */
	private Integer totalTime;
	/**
	 * 试卷开考时间
	 */
	private String startTime;
	/**
	 * 试卷结束时间
	 */
	private String endTime;
	/**
	 * 创建日期
	 */
	private String curday;
	/**
	 * 是否可考,默认0:正常;1:冻结(不能考该张试卷)
	 */
	private Integer isAllowed;
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
	 * 试卷配置信息
	 */
	private Integer[] typeIds;
	private Integer[] typeNums;
	private Integer[] typeScores;
	private Integer[] questionIds;
	/**
	 * 考卷人ID
	 */
	private Integer examPaperId;
	/**
	 * 试卷状态
	 */
	private Integer paperStatus;

	public Integer getPaperId() {
		return paperId;
	}

	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}

	public Integer getCreatePaperId() {
		return createPaperId;
	}

	public void setCreatePaperId(Integer createPaperId) {
		this.createPaperId = createPaperId;
	}

	public String getCreatePaperName() {
		return createPaperName;
	}

	public void setCreatePaperName(String createPaperName) {
		this.createPaperName = createPaperName;
	}

	public String getPaperName() {
		return paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

	public Integer getPaperScore() {
		return paperScore;
	}

	public void setPaperScore(Integer paperScore) {
		this.paperScore = paperScore;
	}

	public Integer getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Integer totalTime) {
		this.totalTime = totalTime;
	}

	public String getStartTime() {
		return startTime.substring(0, startTime.length() - 2);
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime.substring(0, endTime.length() - 2);
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCurday() {
		return curday;
	}

	public void setCurday(String curday) {
		this.curday = curday;
	}

	public Integer getIsAllowed() {
		return isAllowed;
	}

	public void setIsAllowed(Integer isAllowed) {
		this.isAllowed = isAllowed;
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

	public Integer[] getTypeIds() {
		return typeIds;
	}

	public void setTypeIds(Integer[] typeIds) {
		this.typeIds = typeIds;
	}

	public Integer[] getTypeNums() {
		return typeNums;
	}

	public void setTypeNums(Integer[] typeNums) {
		this.typeNums = typeNums;
	}

	public Integer[] getTypeScores() {
		return typeScores;
	}

	public void setTypeScores(Integer[] typeScores) {
		this.typeScores = typeScores;
	}

	public Integer[] getQuestionIds() {
		return questionIds;
	}

	public void setQuestionIds(Integer[] questionIds) {
		this.questionIds = questionIds;
	}

	public Integer getExamPaperId() {
		return examPaperId;
	}

	public void setExamPaperId(Integer examPaperId) {
		this.examPaperId = examPaperId;
	}

	public Integer getPaperStatus() {
		return paperStatus;
	}

	public void setPaperStatus(Integer paperStatus) {
		this.paperStatus = paperStatus;
	}

	@Override
	public String toString() {
		return "Paper [paperId=" + paperId + ", createPaperId=" + createPaperId + ", createPaperName=" + createPaperName
				+ ", paperName=" + paperName + ", paperScore=" + paperScore + ", totalTime=" + totalTime
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", curday=" + curday + ", isAllowed="
				+ isAllowed + ", isOk=" + isOk + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", remark=" + remark + ", typeIds=" + Arrays.toString(typeIds) + ", typeNums="
				+ Arrays.toString(typeNums) + ", typeScores=" + Arrays.toString(typeScores) + ", questionIds="
				+ Arrays.toString(questionIds) + ", examPaperId=" + examPaperId + ", paperStatus=" + paperStatus + "]";
	}

}
