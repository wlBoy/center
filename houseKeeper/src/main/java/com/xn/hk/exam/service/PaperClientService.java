package com.xn.hk.exam.service;

import java.util.List;

import com.xn.hk.common.service.BaseService;
import com.xn.hk.exam.model.Paper;
import com.xn.hk.exam.model.Question;
import com.xn.hk.exam.model.Score;

/**
 * 
 * @Title: PaperClientService
 * @Package: com.xn.hk.exam.service
 * @Description: 处理前台试卷的service层
 * @Author: wanlei
 * @Date: 2018年1月17日 下午4:04:54
 */
public interface PaperClientService extends BaseService<Paper> {
	/**
	 * 将用户所得客观题分数和用户答案保存至数据库中
	 * 
	 * @param score
	 *            用户所得分数实体
	 * @param qlist
	 *            试卷题目数组
	 * @param userList
	 *            用户答案数组
	 * @return 返回影响条数
	 */
	int addScoreAndSolution(Score score, List<Question> qlist, List<String> userList);

	/**
	 * 查询该用户该试卷的填写的用户答案
	 * 
	 * @param paperId
	 *            试卷ID
	 * @param userId
	 *            用户ID
	 * @return 用户答案数组
	 */
	List<String> findSolutionByPaperIdAndUserId(Integer paperId, Integer userId);

}
