package com.xn.hk.exam.service;

import java.util.List;

import com.xn.hk.common.service.BaseService;
import com.xn.hk.exam.model.Question;

/**
 * 
 * @Title: QuestionService
 * @Package: com.xn.hk.exam.service
 * @Description: 处理题目的dao层
 * @Author: wanlei
 * @Date: 2018年1月8日 上午10:35:17
 */
public interface QuestionService extends BaseService<Question> {
	/**
	 * 根据题目ID切换题目状态
	 * 
	 * @param questionId
	 * @return
	 */
	int changeState(Integer questionId);

	/**
	 * 根据试卷ID查找该试卷的所有题目列表
	 * 
	 * @param paperId
	 *            试卷ID
	 * @return 该试卷的所有题目列表
	 */
	List<Question> findQuestionByPaperId(Integer paperId);

	/**
	 * 根据试卷ID和用户ID查询学生主观题答案和正确答案信息(只要简答题的信息)
	 * 
	 * @param paperId
	 *            试卷ID
	 * @param useId
	 *            用户ID
	 * @return 题目数组
	 */
	List<Question> findAnswerByPaperIdAndUserId(Integer paperId, Integer userId);

}
