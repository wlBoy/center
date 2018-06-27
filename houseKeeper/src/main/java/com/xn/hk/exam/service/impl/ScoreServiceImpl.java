package com.xn.hk.exam.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xn.hk.common.dao.BaseDao;
import com.xn.hk.common.service.impl.BaseServiceImpl;
import com.xn.hk.common.utils.page.BasePage;
import com.xn.hk.exam.dao.ScoreDao;
import com.xn.hk.exam.model.Score;
import com.xn.hk.exam.service.ScoreService;

/**
 * 
 * @Title: ScoreServiceImpl
 * @Package: com.xn.hk.exam.service.impl
 * @Description: 处理分数的service实现层
 * @Company: 杭州讯牛
 * @Author: wanlei
 * @Date: 2018年1月11日 下午4:04:12
 */
@Service
public class ScoreServiceImpl extends BaseServiceImpl<Score> implements ScoreService {
	@Autowired
	private ScoreDao sd;

	/**
	 * 指定特定的dao
	 */
	public BaseDao<Score> getDao() {
		return sd;
	}

	/**
	 * 前台显示用户个人的分数列表(即用户个人所有的已考试卷)
	 * 
	 * @param pages
	 *            分页对象
	 * @return 分数实体列表
	 */
	public List<Score> showPersonalList(BasePage<Score> pages) {
		int count = sd.showPersonalCount(pages);
		pages.setCount(count);
		return sd.showPersonalList(pages);
	}

	/**
	 * 查询该用户该试卷的分数表和试卷基本信息
	 * 
	 * @param paperId
	 *            试卷ID
	 * @param userId
	 *            用户ID
	 * @return 分数实体(包含试卷信息)
	 */
	public Score findByPaperIdAndUserId(Integer paperId, Integer userId) {
		return sd.findByPaperIdAndUserId(paperId, userId);
	}

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
	public int updateScore(Integer paperId, Integer userId,Integer sum) {
		return sd.updateScore(paperId,userId,sum);
	}

}
