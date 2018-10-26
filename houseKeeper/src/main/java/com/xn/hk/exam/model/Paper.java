package com.xn.hk.exam.model;

import java.io.Serializable;
import java.util.Arrays;

import com.xn.hk.common.constant.EnabledEnum;
import com.xn.hk.common.constant.StatusEnum;

/**
 * 
 * @Title: Paper
 * @Package: com.xn.hk.exam.model
 * @Description: 试卷实体类
 * @Company: 杭州讯牛
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
		// 解决mysql数据库datetime类型取出时间多个.0的问题，截取前19位即可，格式为:yyyy-MM-dd HH:mm:ss
		return startTime.length() == 19 ? startTime : startTime.substring(0, 19);
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		// 解决mysql数据库datetime类型取出时间多个.0的问题，截取前19位即可，格式为:yyyy-MM-dd HH:mm:ss
		return endTime.length() == 19 ? endTime : endTime.substring(0, 19);
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
		// 解决mysql数据库datetime类型取出时间多个.0的问题，截取前19位即可，格式为:yyyy-MM-dd HH:mm:ss
		return createTime.length() == 19 ? createTime : createTime.substring(0, 19);
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		// 解决mysql数据库datetime类型取出时间多个.0的问题，截取前19位即可，格式为:yyyy-MM-dd HH:mm:ss
		return updateTime.length() == 19 ? updateTime : updateTime.substring(0, 19);
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

	/**
	 * 拼接日志内容(排除空信息和可指定记录内容字段)
	 * 
	 * @return 日志内容
	 */
	public String getLogContent() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		if (getPaperId() != null) {
			sb.append("试卷ID=" + getPaperId() + ",");
		}
		if (getCreatePaperId() != null) {
			sb.append("创建人ID=" + getCreatePaperId() + ",");
		}
		if (getCreatePaperName() != null) {
			sb.append("创建人姓名=" + getCreatePaperName() + ",");
		}
		if (getPaperName() != null) {
			sb.append("试卷名称=" + getPaperName() + ",");
		}
		if (getPaperScore() != null) {
			sb.append("试卷分数=" + getPaperScore() + ",");
		}
		if (getTotalTime() != null) {
			sb.append("试卷总时间=" + getTotalTime() + ",");
		}
		if (startTime != null) {
			sb.append("开考时间=" + getStartTime() + ",");
		}
		if (endTime != null) {
			sb.append("结束时间=" + getEndTime() + ",");
		}
		if (getCurday() != null) {
			sb.append("创建日期=" + getCurday() + ",");
		}
		if (getIsAllowed() != null) {
			sb.append("试卷状态=" + StatusEnum.getDescByCode(getIsAllowed()) + ",");
		}
		if (getIsOk() != null) {
			sb.append("是否可用=" + EnabledEnum.getDescByCode(getIsOk()) + ",");
		}
		if (createTime != null) {
			sb.append("创建时间=" + getCreateTime() + ",");
		}
		if (updateTime != null) {
			sb.append("更新时间=" + getUpdateTime() + ",");
		}
		if (getRemark() != null) {
			sb.append("备注信息=" + getRemark() + ",");
		}
		// 去除最后一个,后再拼接]
		String str = sb.toString();
		str = str.substring(0, str.length() - 1) + "]";
		return str;
	}
}
