package com.xn.hk.exam.service;

import java.util.List;

import com.xn.hk.common.service.BaseService;
import com.xn.hk.common.utils.page.BasePage;
import com.xn.hk.exam.model.Paper;

/**
 * 
 * @Title: PaperService
 * @Package: com.xn.hk.exam.service
 * @Description: 处理后台试卷的service层
 * @Company: 杭州讯牛
 * @Author: wanlei
 * @Date: 2018年1月11日 下午4:03:28
 */
public interface PaperService extends BaseService<Paper> {
	/**
	 * 根据试卷ID切换试卷状态
	 * 
	 * @param paperId
	 *            试卷ID
	 * @return 影响条数
	 */
	public int changeState(Integer paperId);

	/**
	 * 添加试卷
	 * 
	 * @param paper
	 *            试卷实体
	 * @return 影响条数
	 */
	public int addPaper(Paper paper);

	/**
	 * 根据试卷ID数组删除一个或多个试卷
	 * 
	 * @param paper
	 *            试卷ID数组
	 * @return 影响条数
	 */
	public int deletePaper(Integer[] paperIds);

	/**
	 * 后台实现已考(待批阅)试卷分页,用于教师批阅试卷
	 * 
	 * @param pages
	 *            试卷分页
	 * @return 待批阅试卷列表
	 */
	public List<Paper> pageRemainReadList(BasePage<Paper> pages);

	/**
	 * 在试卷配置表中根据试卷ID查询该试卷简答题的分数
	 * 
	 * @param paperId
	 *            试卷ID
	 * @return 简答题的分数
	 */
	public Integer findScoreByPaperId(Integer paperId);

}
