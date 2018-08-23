package com.xn.hk.exam.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xn.hk.common.dao.BaseDao;
import com.xn.hk.common.service.impl.BaseServiceImpl;
import com.xn.hk.exam.dao.PaperClientDao;
import com.xn.hk.exam.dao.ScoreDao;
import com.xn.hk.exam.model.Paper;
import com.xn.hk.exam.model.Question;
import com.xn.hk.exam.model.Score;
import com.xn.hk.exam.service.PaperClientService;

/**
 * 
 * @Title: PaperService
 * @Package: com.xn.hk.exam.service.impl
 * @Description: 处理前台试卷的service实现层
 * @Author: wanlei
 * @Date: 2018年1月11日 下午4:04:12
 */
@Service
public class PaperClientServiceImpl extends BaseServiceImpl<Paper> implements PaperClientService {
	@Autowired
	private PaperClientDao pcd;
	@Autowired
	private ScoreDao sd;

	/**
	 * 指定特定的dao
	 */
	public BaseDao<Paper> getDao() {
		return pcd;
	}

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
	public int addScoreAndSolution(Score score, List<Question> qlist, List<String> userList) {
		// 将用户填的答案保存到答案表,方便批阅试卷
		for (int i = 0; i < qlist.size(); i++) {
			pcd.addUserSolution(score.getPaper().getPaperId(), score.getExamPaperId(), qlist.get(i).getQuestionId(),
					userList.get(i));
		}
		// 将用户的客观题的分数保存到分数表中
		sd.insert(score);
		return qlist.size() + 1;
	}

	/**
	 * 查询该用户该试卷的填写的用户答案
	 * 
	 * @param paperId
	 *            试卷ID
	 * @param userId
	 *            用户ID
	 * @return 用户答案数组
	 */
	public List<String> findSolutionByPaperIdAndUserId(Integer paperId, Integer userId) {
		return pcd.findSolutionByPaperIdAndUserId(paperId, userId);
	}

}
