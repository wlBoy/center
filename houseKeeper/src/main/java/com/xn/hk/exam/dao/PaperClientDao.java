package com.xn.hk.exam.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xn.hk.common.dao.BaseDao;
import com.xn.hk.exam.model.Paper;

/**
 * 
 * @Title: PaperClientDao
 * @Package: com.xn.hk.exam.dao
 * @Description: 处理后台试卷的dao数据访问层
 * @Author: wanlei
 * @Date: 2018年1月17日 下午4:07:27
 */
public interface PaperClientDao extends BaseDao<Paper> {
	/**
	 * 将用户的答案保存到用户答案表
	 * 
	 * @param paperId
	 *            试卷ID
	 * @param userId
	 *            用户ID
	 * @param questionId
	 *            题目ID
	 * @param solution
	 *            用户答案
	 * @return 返回影响条数
	 */
	int addUserSolution(@Param(value = "paperId") Integer paperId, @Param(value = "userId") Integer userId,
			@Param(value = "questionId") Integer questionId, @Param(value = "solution") String solution);

	/**
	 * 查询该用户该试卷的填写的用户答案
	 * 
	 * @param paperId
	 *            试卷ID
	 * @param userId
	 *            用户ID
	 * @return 用户答案数组
	 */
	List<String> findSolutionByPaperIdAndUserId(@Param(value = "paperId") Integer paperId,
			@Param(value = "userId") Integer userId);

}
