package com.kl.ar.vote.entity;

/**
 * 
 * @package:com.kl.ar.user.entity
 * @Description: 评选候选人实体类
 * @author: wanlei
 * @date: 2019年12月21日下午3:21:39
 */
public class CandidateUser {
	// 数据库表名
	public static String TABLE = "candidate_user";

	private Integer id;
	private Integer userNo;
	private String userVoteType;
	private String userName;
	private String userDept;
	private Integer selectYear;
	private String addTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserNo() {
		return userNo;
	}

	public void setUserNo(Integer userNo) {
		this.userNo = userNo;
	}

	public String getUserVoteType() {
		return userVoteType;
	}

	public void setUserVoteType(String userVoteType) {
		this.userVoteType = userVoteType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getSelectYear() {
		return selectYear;
	}

	public void setSelectYear(Integer selectYear) {
		this.selectYear = selectYear;
	}

	public String getUserDept() {
		return userDept == null ? "无部门" : userDept;
	}

	public void setUserDept(String userDept) {
		this.userDept = userDept;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	@Override
	public String toString() {
		return "CandidateUser [id=" + id + ", userNo=" + userNo + ", userVoteType=" + userVoteType + ", userName="
				+ userName + ", userDept=" + userDept + ", selectYear=" + selectYear + ", addTime=" + addTime + "]";
	}

}
