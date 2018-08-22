package com.xn.hk.exam.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xn.hk.common.dao.BaseDao;
import com.xn.hk.common.service.impl.BaseServiceImpl;
import com.xn.hk.common.utils.EnumStatus;
import com.xn.hk.exam.dao.QuestionDao;
import com.xn.hk.exam.model.Question;
import com.xn.hk.exam.service.QuestionService;

/**
 * 
 * @Title: QuestionServiceImpl
 * @Package: com.xn.hk.exam.service.impl
 * @Description: 处理题目的service实现层
 * @Author: wanlei
 * @Date: 2018年1月8日 上午10:39:20
 */
@Service
public class QuestionServiceImpl extends BaseServiceImpl<Question> implements QuestionService {
	@Autowired
	private QuestionDao qd;

	/**
	 * 指定特定的dao
	 */
	public BaseDao<Question> getDao() {
		return qd;
	}

	/**
	 * 根据题目ID切换题目状态
	 * 
	 * @param questionId 题目ID
	 * @return 影响条数
	 */
	public int changeState(Integer questionId) {
		Question q = qd.findById(questionId);
		if (q.getQuestionStatus().intValue() == EnumStatus.NORMAL.getCode().intValue()) {
			return qd.changeState(EnumStatus.ISLOCKED.getCode(), questionId);
		} else {
			return qd.changeState(EnumStatus.NORMAL.getCode(), questionId);
		}
	}

	/**
	 * 根据试卷ID查找该试卷的所有题目列表
	 * 
	 * @param paperId
	 *            试卷ID
	 * @return 该试卷的所有题目列表
	 */
	public List<Question> findQuestionByPaperId(Integer paperId) {
		return qd.findQuestionByPaperId(paperId);
	}

	/**
	 * 根据试卷ID和用户ID查询学生主观题答案和正确答案信息(只要简答题的信息)
	 * 
	 * @param paperId
	 *            试卷ID
	 * @param useId
	 *            用户ID
	 * @return 题目数组
	 */
	public List<Question> findAnswerByPaperIdAndUserId(Integer paperId, Integer userId) {
		return qd.findAnswerByPaperIdAndUserId(paperId, userId);
	}

}
