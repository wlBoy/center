package com.xn.hk.exam.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xn.hk.common.constant.Constant;
import com.xn.hk.common.constant.View;
import com.xn.hk.common.utils.page.BasePage;
import com.xn.hk.common.utils.string.StringUtil;
import com.xn.hk.exam.model.Score;
import com.xn.hk.exam.service.ScoreService;
import com.xn.hk.system.model.User;
import com.xn.hk.system.service.UserService;

/**
 * 
 * @Title: ScoreController
 * @Package: com.xn.hk.exam.controller
 * @Description: 处理后台试卷的控制层
 * @Author: wanlei
 * @Date: 2018年1月8日 上午10:42:01
 */
@Controller
@RequestMapping(value = "/exam/score")
public class ScoreController {
	/**
	 * 记录日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(ScoreController.class);

	/**
	 * 注入service层
	 */
	@Autowired
	private ScoreService scoreService;
	@Autowired
	private UserService userService;

	/**
	 * 前台显示用户个人的分数列表(即用户个人所有的已考试卷)
	 * 
	 * @param score
	 *            分数实体(用来封装查询条件)
	 * @param pages
	 *            分页对象
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/showPersonalScore.do")
	public ModelAndView showPersonalScore(Score score, BasePage<Score> pages, HttpSession session) {
		ModelAndView mv = new ModelAndView("examClient/showPersonalScore");
		// 从session中拿出当前用户信息,将它塞入分页对象中去
		User user = (User) session.getAttribute(Constant.SESSION_USER_KEY);
		score.setExamPaperId(user.getUserId());
		// 封装查询条件
		pages.setBean(score);
		// 试卷分页
		List<Score> scores = scoreService.showPersonalList(pages);
		// 将list封装到分页对象中
		pages.setList(scores);
		mv.addObject(Constant.PAGE_KEY, pages);
		// 查询所有用户信息，供下拉框显示
		List<User> users = userService.findAll();
		mv.addObject(Constant.USER_KEY, users);
		return mv;
	}

	/**
	 * 后台显示所欲的分数列表(即所有待批阅试卷),用于教师批阅试卷
	 * 
	 * @param score
	 *            分数实体(用来封装查询条件)
	 * @param pages
	 *            分页对象
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/showAllScore.do")
	public ModelAndView showAllScore(Score score, BasePage<Score> pages) {
		ModelAndView mv = new ModelAndView("exam/showAllScore");
		// 封装查询条件
		pages.setBean(score);
		// 试卷分页
		List<Score> scores = scoreService.pageList(pages);
		// 将list封装到分页对象中
		pages.setList(scores);
		mv.addObject(Constant.PAGE_KEY, pages);
		// 查询所有用户信息，供下拉框显示
		List<User> users = userService.findAll();
		mv.addObject(Constant.USER_KEY, users);
		return mv;
	}

	/**
	 * 后台批阅试卷打分,根据试卷ID和用户ID给该试卷主观题打分
	 * 
	 * @param paperId
	 *            试卷ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateScore.do")
	public ModelAndView updateScore(Integer paperId, Integer userId, Integer[] scores, HttpSession session) {
		// 计算简单题总分
		int sum = Constant.ZERO_VALUE;
		for (int s : scores) {
			sum += s;
		}
		logger.info("用户{}试卷{}简答题总分为:{}", userId, paperId, sum);
		// 根据试卷ID和用户ID给该试卷主观题打分
		scoreService.updateScore(paperId, userId, sum);
		session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("批阅试卷成功!", Constant.SUCCESS_TIP_KEY));
		return View.SCORE_REDITRCT_ACTION;
	}
}
