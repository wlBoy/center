package com.kl.ar.common.constant;

import java.util.ArrayList;
import java.util.List;

import com.kl.ar.common.cfg.ConfigUtil;

/**
 * 
 * @package:com.kl.ar.common.constant
 * @Description: 年度评选的奖项枚举类
 * @author: wanlei
 * @date: 2019年12月21日下午2:14:30
 */
public enum PrizeType {
	EXCELLENT_EMPLOYEE_PRIZE("excellentEmployeePrize", "优秀员工奖", 15), GOLD_PRIZE("goldPrize", "金奖", 2), SILVER_PRIZE(
			"silverPrize", "银奖", 5), BRONZE_PRIZE("bronzePrize", "铜奖", 8), BEST_TEAM_PRIZE("bestTeamPrize", "最佳团队奖",
					3), CREATIVE_PRIZE("creativePrize", "创新奖", 1), QUARLITY_PRIZE("qualityPrize", "质量奖",
							1), MARKET_DEVELOPMENT_PRIZE("marketDevelopmentPrize", "市场开拓奖", 1), DELIGENT_PRIZE(
									"deligentPrize", "勤勉尽责奖", 1), PRODUCT_INNOVATION_PRIZE("productInnovationPrize",
											"产品革新奖", 1), MOST_POTENTIAL_PRIZE("mostPotentialPrize", "最具潜力奖",
													1), DEMO_POROJECT_PRIZE("demoProjectPrize", "示范项目奖",
															1), NEW_COMER_PRIZE("newComerPrize", "新人奖",
																	1), GOOD_WIFE_PRIZE("goodWifePrize", "贤内助奖", 2);
	private String code;// 编码
	private String desc;// 描述
	private Integer count;// 人数

	private PrizeType(String code, String desc, Integer count) {
		this.code = code;
		this.desc = desc;
		this.count = count;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * 通过code取到其描述
	 *
	 * @param type
	 * @return
	 */
	public static String getCodeByDesc(String code) {
		String desc = null;
		PrizeType[] types = PrizeType.values();
		for (PrizeType type : types) {
			if (code.equals(type.getCode())) {
				desc = type.getDesc();
			}
		}
		return desc;
	}

	/**
	 * 获取所有的奖项列表
	 * 
	 * @return
	 */
	public static List<PrizeType> getAllPrizeTypeList(Integer year) {
		List<PrizeType> list = new ArrayList<PrizeType>();
		switch (year) {
		case 2018:
			list.add(GOLD_PRIZE);
			list.add(SILVER_PRIZE);
			list.add(BRONZE_PRIZE);
			list.add(BEST_TEAM_PRIZE);
			list.add(CREATIVE_PRIZE);
			list.add(QUARLITY_PRIZE);
			list.add(GOOD_WIFE_PRIZE);
			break;
		default:
			for (PrizeType type : PrizeType.values()) {
				// 排查优秀员工奖，因为其包含金，银，铜奖
				if (type == EXCELLENT_EMPLOYEE_PRIZE) {
					continue;
				}
				list.add(type);
			}
			break;
		}
		return list;
	}

	/**
	 * 获取当前年份所有的奖项列表(jsp遍历用到)
	 * 
	 * @return
	 */
	public static List<PrizeType> getAllPrizeTypeList() {
		Integer currentYear = Integer.parseInt(ConfigUtil.getInstance().loadCfgMap().get(Constant.CURRENT_YEAR));
		return getAllPrizeTypeList(currentYear);
	}

	/**
	 * 获取所有的奖项code(js用到)
	 * 
	 * @return
	 */
	public static String getAllPrizeCodeList() {
		StringBuilder sb = new StringBuilder();
		for (PrizeType type : PrizeType.values()) {
			sb.append(type.getCode());
			sb.append(",");
		}
		return sb.toString();
	}
}
