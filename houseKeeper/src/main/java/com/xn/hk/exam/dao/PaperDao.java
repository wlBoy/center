package com.xn.hk.exam.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xn.hk.common.dao.BaseDao;
import com.xn.hk.common.utils.page.BasePage;
import com.xn.hk.exam.model.Paper;

/**
 * 
 * @Title: PaperDao
 * @Package: com.xn.hk.exam.dao
 * @Description: 处理前台试卷的dao数据访问层
 * @Author: wanlei
 * @Date: 2018年1月11日 下午3:59:42
 */
public interface PaperDao extends BaseDao<Paper> {
	/**
	 * 根据试卷ID切换试卷状态
	 * 
	 * @param status
	 *            试卷状态
	 * @param paperId
	 *            试卷ID
	 * @return 影响条数
	 */
	int changeState(@Param(value = "status") Integer status, @Param(value = "paperId") Integer paperId);

	/**
	 * 添加配置表信息
	 * 
	 * @param paperId
	 *            试卷ID
	 * @param typeId
	 *            题型ID
	 * @param typeNum
	 *            题型数量
	 * @param typeScore
	 *            题型分数
	 * @return 影响条数
	 */
	int configPaper(@Param(value = "paperId") Integer paperId, @Param(value = "typeId") Integer typeId,
			@Param(value = "typeNum") Integer typeNum, @Param(value = "typeScore") Integer typeScore);

	/**
	 * 添加生成表信息
	 * 
	 * @param paperId
	 *            试卷ID
	 * @param typeId
	 *            题型ID
	 * @param questionId
	 *            题目ID
	 * @return 影响条数
	 */
	int createPaper(@Param(value = "paperId") Integer paperId, @Param(value = "typeId") Integer typeId,
			@Param(value = "questionId") Integer questionId);

	/**
	 * 删除配置表信息
	 * 
	 * @param paperId
	 *            试卷ID
	 * @return 影响条数
	 */
	int deleteConfig(Integer paperId);

	/**
	 * 删除生成表信息
	 * 
	 * @param paperId
	 *            试卷ID
	 * @return 影响条数
	 */
	int deleteCreate(Integer paperId);

	/**
	 * 后台查询已考(待批阅)试卷总记录数,用于教师批阅试卷
	 * 
	 * @param pages
	 *            试卷分页
	 * @return 待批阅试卷总记录数
	 */
	int pageRemainReadCount(BasePage<Paper> pages);

	/**
	 * 后台实现已考(待批阅)试卷分页,用于教师批阅试卷
	 * 
	 * @param pages
	 *            试卷分页
	 * @return 待批阅试卷列表
	 */
	List<Paper> pageRemainReadList(BasePage<Paper> pages);

	/**
	 * 在试卷配置表中根据试卷ID查询该试卷简答题的分数
	 * 
	 * @param paperId
	 *            试卷ID
	 * @return 简答题的分数
	 */
	Integer findScoreByPaperId(Integer paperId);

}
