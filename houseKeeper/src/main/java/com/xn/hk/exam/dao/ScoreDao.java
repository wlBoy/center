package com.xn.hk.exam.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xn.hk.common.dao.BaseDao;
import com.xn.hk.common.utils.page.BasePage;
import com.xn.hk.exam.model.Score;

/**
 * 
 * @Title: ScoreDao
 * @Package: com.xn.hk.exam.dao
 * @Description: 处理前台分数的dao数据访问层
 * @Author: wanlei
 * @Date: 2018年1月11日 下午3:59:42
 */
public interface ScoreDao extends BaseDao<Score> {
	/**
	 * 前台显示用户个人的分数总记录数(即用户个人所有的已考试卷)
	 * 
	 * @param pages
	 *            分页对象
	 * @return ModelAndView
	 */
	int showPersonalCount(BasePage<Score> pages);

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
	Score findByPaperIdAndUserId(@Param(value = "paperId") Integer paperId, @Param(value = "userId") Integer userId);

	/**
	 * 根据试卷ID和用户ID更新简答题分数和试卷状态
	 * 
	 * @param paperId
	 *            试卷ID
	 * @param userId
	 *            用户ID
	 * @param sum
	 *            简答题分数
	 * @return 影响条数
	 */
	int updateScore(@Param(value = "paperId") Integer paperId, @Param(value = "userId") Integer userId,
			@Param(value = "sum") Integer sum);

}
