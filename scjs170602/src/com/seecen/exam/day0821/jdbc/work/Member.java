package com.seecen.exam.day0821.jdbc.work;

/**
 * 会员实体类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月21日
 */
public class Member {
	// 会员编号，会员名称 ，会员等级
	private int memberId;
	private String memberName;
	private String memberGrade;

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberGrade() {
		return memberGrade;
	}

	public void setMemberGrade(String memberGrade) {
		this.memberGrade = memberGrade;
	}

}
