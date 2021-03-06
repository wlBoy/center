package com.xn.hk.exam.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xn.hk.common.constant.StatusEnum;
import com.xn.hk.common.dao.BaseDao;
import com.xn.hk.common.service.impl.BaseServiceImpl;
import com.xn.hk.common.utils.page.BasePage;
import com.xn.hk.exam.dao.PaperDao;
import com.xn.hk.exam.dao.QuestionDao;
import com.xn.hk.exam.model.Paper;
import com.xn.hk.exam.service.PaperService;

/**
 * 
 * @Title: PaperService
 * @Package: com.xn.hk.exam.service.impl
 * @Description: 处理后台试卷的service实现层
 * @Author: wanlei
 * @Date: 2018年1月11日 下午4:04:12
 */
@Service
public class PaperServiceImpl extends BaseServiceImpl<Paper> implements PaperService {
	@Autowired
	private PaperDao paperDao;
	@Autowired
	private QuestionDao questionDao;

	/**
	 * 指定特定的dao
	 */
	public BaseDao<Paper> getDao() {
		return paperDao;
	}

	/**
	 * 根据试卷ID切换试卷状态
	 * 
	 * @param paperId
	 *            试卷ID
	 * @return 影响条数
	 */
	public int changeState(Integer paperId) {
		Paper p = paperDao.findById(paperId);
		if (p.getIsAllowed().intValue() == StatusEnum.ACTIVE.getCode().intValue()) {
			return paperDao.changeState(StatusEnum.LOCKED.getCode(), paperId);
		} else {
			return paperDao.changeState(StatusEnum.ACTIVE.getCode(), paperId);
		}
	}

	/**
	 * 添加试卷
	 * 
	 * @param paper
	 *            试卷实体
	 * @return 影响条数
	 */
	public int addPaper(Paper paper) {
		// 添加试卷的基本信息
		paperDao.insert(paper);
		// 添加配置表的信息
		for (int i = 0; i < paper.getTypeIds().length; i++) {
			paperDao.configPaper(paper.getPaperId(), paper.getTypeIds()[i], paper.getTypeNums()[i], paper.getTypeScores()[i]);
		}
		// 根据配置表生成随机数量题型的题目
		for (int i = 0; i < paper.getTypeIds().length; i++) {
			Integer[] qids = questionDao.genQuestionIds(paper.getTypeIds()[i], paper.getTypeNums()[i]);
			paper.setQuestionIds(qids);
			for (int j = 0; j < qids.length; j++) {
				// 添加生成表信息
				paperDao.createPaper(paper.getPaperId(), paper.getTypeIds()[i], qids[j]);
			}
		}
		return paper.getTypeIds().length * 3 + 1;
	}

	/**
	 * 根据试卷ID数组删除一个或多个试卷
	 * 
	 * @param paper
	 *            试卷ID数组
	 * @return 影响条数
	 */
	public int deletePaper(Integer[] paperIds) {
		for (int i = 0; i < paperIds.length; i++) {
			// 删除试卷的基本模板信息
			paperDao.delete(paperIds[i]);
			// 删除配置表信息
			paperDao.deleteConfig(paperIds[i]);
			// 删除生成表信息
			paperDao.deleteCreate(paperIds[i]);
		}
		return paperIds.length;
	}

	/**
	 * 后台实现已考(待批阅)试卷分页,用于教师批阅试卷
	 * 
	 * @param pages
	 *            试卷分页
	 * @return 待批阅试卷列表
	 */
	public List<Paper> pageRemainReadList(BasePage<Paper> pages) {
		pages.setCount(paperDao.pageRemainReadCount(pages));
		return paperDao.pageRemainReadList(pages);
	}

	/**
	 * 在试卷配置表中根据试卷ID查询该试卷简答题的分数
	 * 
	 * @param paperId
	 *            试卷ID
	 * @return 简答题的分数
	 */
	public Integer findScoreByPaperId(Integer paperId) {
		return paperDao.findScoreByPaperId(paperId);
	}

}
