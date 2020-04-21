package com.kl.ar.vote.entity;

/**
 * 
 * @package:com.kl.ar.vote.entity
 * @Description: 评选结果实体类
 * @author: wanlei
 * @date: 2019年12月21日下午3:23:52
 */
public class VoteResultSum {
	// 数据库表名
	public static String TABLE = "vote_result_sum";
	private int id;
	private String candidateId;
	private String prizeType;
	private String voteId;
	private int voteYear;

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public String getPrizeType() {
		return prizeType;
	}

	public void setPrizeType(String prizeType) {
		this.prizeType = prizeType;
	}

	public String getVoteId() {
		return voteId;
	}

	public void setVoteId(String voteId) {
		this.voteId = voteId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVoteYear() {
		return voteYear;
	}

	public void setVoteYear(int voteYear) {
		this.voteYear = voteYear;
	}

	@Override
	public String toString() {
		return "VoteResultSum [id=" + id + ", candidateId=" + candidateId + ", prizeType=" + prizeType + ", voteId="
				+ voteId + ", voteYear=" + voteYear + "]";
	}

}
