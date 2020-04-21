package com.kl.ar.vote.entity;

/**
 * 
 * @ClassName: VoteResultByWeight
 * @Package: com.kl.ar.vote.entity
 * @Description: 根据权重分数计算的结果实体类
 * @Author: wanlei
 * @Date: 2020年1月15日 下午5:14:36
 */
public class VoteResultByWeight {

	private String candidateId;// 候选人ID
	private String candidateName;// 候选人名称
	private String candidateDept;// 候选人部门
	// 根据权重比分计算金，银，铜各票数及总分数
	private int totalCount;// 总票数
	private int goldCount;// 金奖票数
	private int silverCount;// 银奖数
	private int bronzeCount;// 铜奖数
	private int sumScore;// 总分数
	private String voteYear;// 评选年份

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

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getGoldCount() {
		return goldCount;
	}

	public void setGoldCount(int goldCount) {
		this.goldCount = goldCount;
	}

	public int getSilverCount() {
		return silverCount;
	}

	public void setSilverCount(int silverCount) {
		this.silverCount = silverCount;
	}

	public int getBronzeCount() {
		return bronzeCount;
	}

	public void setBronzeCount(int bronzeCount) {
		this.bronzeCount = bronzeCount;
	}

	public int getSumScore() {
		return sumScore;
	}

	public void setSumScore(int sumScore) {
		this.sumScore = sumScore;
	}

	public String getVoteYear() {
		return voteYear;
	}

	public void setVoteYear(String voteYear) {
		this.voteYear = voteYear;
	}

	public String getCandidateDept() {
		return candidateDept == null ? "无部门" : candidateDept;
	}

	public void setCandidateDept(String candidateDept) {
		this.candidateDept = candidateDept;
	}

	@Override
	public String toString() {
		return "VoteResultByWeight [candidateId=" + candidateId + ", candidateName=" + candidateName
				+ ", candidateDept=" + candidateDept + ", totalCount=" + totalCount + ", goldCount=" + goldCount
				+ ", silverCount=" + silverCount + ", bronzeCount=" + bronzeCount + ", sumScore=" + sumScore
				+ ", voteYear=" + voteYear + "]";
	}

}
