package com.xn.hk.exam.service;

import java.util.List;

import com.xn.hk.common.service.BaseService;
import com.xn.hk.common.utils.page.BasePage;
import com.xn.hk.exam.model.Score;

/**
 * 
 * @Title: ScoreService
 * @Package: com.xn.hk.exam.service
 * @Description: 处理分数的service层
 * @Author: wanlei
 * @Date: 2018年1月11日 下午4:03:28
 */
public interface ScoreService extends BaseService<Score> {
	/**
	 * 前台显示用户个人的分数列表(即用户个人所有的已考试卷)
	 * 
	 * @param pages
	 *            分页对象
	 * @return ModelAndView
	 */
	List<Score> showPersonalList(BasePage<Score> pages);

	/**
	 * 查询该用户该试卷的分数表和试卷基本信息
	 * 
	 * @param paperId
	 *            试卷ID
	 * @param userId
	 *            用户ID
	 * @return 分数实体(包含试卷信息)
	 */
	Score findByPaperIdAndUserId(Integer paperId, Integer userId);

	/**
	 * 根据试卷ID和用户ID更新简答题分数和试卷状态
	 * 
	 * @param paperId
	 *            试卷ID
	 * @param userId
	 *            用户ID
	 * @param sum
	 *            简答题分数
	 * @return
	 */
	int updateScore(Integer paperId, Integer userId, Integer sum);

}
