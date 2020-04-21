package com.kl.ar.vote.entity;

/**
 * 
 * @package:com.kl.ar.vote.entity
 * @Description: 评选结果汇总视图
 * @author: wanlei
 * @date: 2019年12月21日下午3:24:42
 */
public class VwVoteResultSum {
	// 数据库表名
	public static String TABLE = "vw_vote_result_sum";
	// 展示每个奖项的票数和候选人情况
	private String candidateId;
	private String candidateName;
	private String candidateDept;
	private String prizeType;
	private String voteYear;
	private String ticketStatus;
	private int count;

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getPrizeType() {
		return prizeType;
	}

	public void setPrizeType(String prizeType) {
		this.prizeType = prizeType;
	}

	public String getVoteYear() {
		return voteYear;
	}

	public void setVoteYear(String voteYear) {
		this.voteYear = voteYear;
	}

	public String getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getCandidateDept() {
		return candidateDept == null ? "无部门" : candidateDept;
	}

	public void setCandidateDept(String candidateDept) {
		this.candidateDept = candidateDept;
	}

	@Override
	public String toString() {
		return "VwVoteResultSum [candidateId=" + candidateId + ", candidateName=" + candidateName + ", candidateDept="
				+ candidateDept + ", prizeType=" + prizeType + ", voteYear=" + voteYear + ", ticketStatus="
				+ ticketStatus + ", count=" + count + "]";
	}

}
