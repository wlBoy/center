package com.xn.hk.exam.model;

import java.io.Serializable;

import com.xn.hk.common.constant.EnabledEnum;
import com.xn.hk.common.constant.StatusEnum;

/**
 * 
 * @Title: Question
 * @Package: com.xn.hk.exam.model
 * @Description: 题目的实体类
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

	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", type=" + type + ", questionTitle=" + questionTitle
				+ ", optionA=" + optionA + ", optionB=" + optionB + ", optionC=" + optionC + ", optionD=" + optionD
				+ ", answer=" + answer + ", curday=" + curday + ", questionStatus=" + questionStatus + ", isOk=" + isOk
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", remark=" + remark
				+ ", questionScore=" + questionScore + ", userSolution=" + userSolution + "]";
	}

	/**
	 * 拼接日志内容(排除空信息和可指定记录内容字段)
	 * 
	 * @return 日志内容
	 */
	public String getLogContent() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		if (getQuestionId() != null) {
			sb.append("题目ID=" + getQuestionId() + ",");
		}
		if (getType().getTypeName() != null) {
			sb.append("题型名称=" + getType().getTypeName());
		}
		if (getQuestionTitle() != null) {
			sb.append("题目标题=" + getQuestionTitle() + ",");
		}
		if (getOptionA() != null) {
			sb.append("选项A=" + getOptionA() + ",");
		}
		if (getOptionB() != null) {
			sb.append("选项B=" + getOptionB() + ",");
		}
		if (getOptionC() != null) {
			sb.append("选项C=" + getOptionC() + ",");
		}
		if (getOptionD() != null) {
			sb.append("选项D=" + getOptionD() + ",");
		}
		if (getAnswer() != null) {
			sb.append("题目答案=" + getAnswer() + ",");
		}
		if (getCurday() != null) {
			sb.append("创建日期=" + getCurday() + ",");
		}
		if (getQuestionStatus() != null) {
			sb.append("题目状态=" + StatusEnum.getDescByCode(getQuestionStatus()) + ",");
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
