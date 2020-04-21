package com.kl.ar.vote.entity;

import java.util.Date;

/**
 * 
 * @package:com.kl.ar.vote.entity
 * @Description: 评选实体类
 * @author: wanlei
 * @date: 2019年12月21日下午3:23:16
 */
public class VoteResult {
	// 数据库表名
	public static String TABLE = "vote_result";
	private String id;
	private String voterId;
	private String voterName;
	private String goldPrize;
	private String silverPrize;
	private String bronzePrize;
	private String bestTeamPrize;
	private String creativePrize;
	private String qualityPrize;
	// 2019年新增奖项
	private String marketDevelopmentPrize;
	private String deligentPrize;
	private String productInnovationPrize;
	private String mostPotentialPrize;
	private String demoProjectPrize;
	private String newComerPrize;
	private String goodWifePrize;
	private Integer voteYear;
	private Date voteTime;
	private Integer voteStatus;
	private Integer ticketStatus;

	public static String getTABLE() {
		return TABLE;
	}

	public static void setTABLE(String tABLE) {
		TABLE = tABLE;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVoterId() {
		return voterId;
	}

	public void setVoterId(String voterId) {
		this.voterId = voterId;
	}

	public String getVoterName() {
		return voterName;
	}

	public void setVoterName(String voterName) {
		this.voterName = voterName;
	}

	public String getGoldPrize() {
		return goldPrize;
	}

	public void setGoldPrize(String goldPrize) {
		this.goldPrize = goldPrize;
	}

	public String getSilverPrize() {
		return silverPrize;
	}

	public void setSilverPrize(String silverPrize) {
		this.silverPrize = silverPrize;
	}

	public Integer getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(Integer ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public String getBronzePrize() {
		return bronzePrize;
	}

	public void setBronzePrize(String bronzePrize) {
		this.bronzePrize = bronzePrize;
	}

	public String getBestTeamPrize() {
		return bestTeamPrize;
	}

	public void setBestTeamPrize(String bestTeamPrize) {
		this.bestTeamPrize = bestTeamPrize;
	}

	public String getCreativePrize() {
		return creativePrize;
	}

	public void setCreativePrize(String creativePrize) {
		this.creativePrize = creativePrize;
	}

	public String getQualityPrize() {
		return qualityPrize;
	}

	public void setQualityPrize(String qualityPrize) {
		this.qualityPrize = qualityPrize;
	}

	public String getGoodWifePrize() {
		return goodWifePrize;
	}

	public void setGoodWifePrize(String goodWifePrize) {
		this.goodWifePrize = goodWifePrize;
	}

	public Integer getVoteYear() {
		return voteYear;
	}

	public void setVoteYear(Integer voteYear) {
		this.voteYear = voteYear;
	}

	public Date getVoteTime() {
		return voteTime;
	}

	public void setVoteTime(Date voteTime) {
		this.voteTime = voteTime;
	}

	public Integer getVoteStatus() {
		return voteStatus;
	}

	public void setVoteStatus(Integer voteStatus) {
		this.voteStatus = voteStatus;
	}

	public String getMarketDevelopmentPrize() {
		return marketDevelopmentPrize;
	}

	public void setMarketDevelopmentPrize(String marketDevelopmentPrize) {
		this.marketDevelopmentPrize = marketDevelopmentPrize;
	}

	public String getDeligentPrize() {
		return deligentPrize;
	}

	public void setDeligentPrize(String deligentPrize) {
		this.deligentPrize = deligentPrize;
	}

	public String getProductInnovationPrize() {
		return productInnovationPrize;
	}

	public void setProductInnovationPrize(String productInnovationPrize) {
		this.productInnovationPrize = productInnovationPrize;
	}

	public String getMostPotentialPrize() {
		return mostPotentialPrize;
	}

	public void setMostPotentialPrize(String mostPotentialPrize) {
		this.mostPotentialPrize = mostPotentialPrize;
	}

	public String getDemoProjectPrize() {
		return demoProjectPrize;
	}

	public void setDemoProjectPrize(String demoProjectPrize) {
		this.demoProjectPrize = demoProjectPrize;
	}

	public String getNewComerPrize() {
		return newComerPrize;
	}

	public void setNewComerPrize(String newComerPrize) {
		this.newComerPrize = newComerPrize;
	}

	@Override
	public String toString() {
		return "VoteResult [id=" + id + ", voterId=" + voterId + ", voterName=" + voterName + ", goldPrize=" + goldPrize
				+ ", silverPrize=" + silverPrize + ", bronzePrize=" + bronzePrize + ", bestTeamPrize=" + bestTeamPrize
				+ ", creativePrize=" + creativePrize + ", qualityPrize=" + qualityPrize + ", marketDevelopmentPrize="
				+ marketDevelopmentPrize + ", deligentPrize=" + deligentPrize + ", productInnovationPrize="
				+ productInnovationPrize + ", mostPotentialPrize=" + mostPotentialPrize + ", demoProjectPrize="
				+ demoProjectPrize + ", newComerPrize=" + newComerPrize + ", goodWifePrize=" + goodWifePrize
				+ ", voteYear=" + voteYear + ", voteTime=" + voteTime + ", voteStatus=" + voteStatus + ", ticketStatus="
				+ ticketStatus + "]";
	}

}
