package com.xn.hk.exam.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xn.hk.common.dao.BaseDao;
import com.xn.hk.exam.model.Question;

/**
 * 
 * @Title: QuestionDao
 * @Package: com.xn.hk.exam.dao
 * @Description: 处理题目的dao层
 * @Author: wanlei
 * @Date: 2018年1月8日 上午10:35:17
 */
public interface QuestionDao extends BaseDao<Question> {
	/**
	 * 根据题目ID切换题目状态
	 * 
	 * @param status
	 *            题目状态
	 * @param questionId
	 *            题目ID
	 * @return 影响条数
	 */
	int changeState(@Param(value = "status") int status, @Param(value = "questionId") Integer questionId);

	/**
	 * 生成指定数量题型的题目数组
	 * 
	 * @param typeId
	 *            题型ID
	 * @param typeNum
	 *            题型数量
	 * @return 题目ID数组
	 */
	Integer[] genQuestionIds(@Param(value = "typeId") Integer typeId, @Param(value = "typeNum") Integer typeNum);

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
	List<Question> findAnswerByPaperIdAndUserId(@Param(value = "paperId") Integer paperId,
			@Param(value = "userId") Integer userId);

}
