package com.xn.hk.exam.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xn.hk.common.constant.Constant;
import com.xn.hk.common.utils.page.BasePage;
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
	private static final Logger log = Logger.getLogger(ScoreController.class);
	@Autowired
	private ScoreService ss;
	@Autowired
	private UserService us;

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
		// 从session中拿出当前用户信息,将它塞入分页对象中去
		User user = (User) session.getAttribute(Constant.LOGIN_SESSION_USER_KEY);
		score.setExamPaperId(user.getUserId());
		ModelAndView mv = new ModelAndView("examClient/showPersonalScore");
		// 封装查询条件
		pages.setBean(score);
		// 试卷分页
		List<Score> scores = ss.showPersonalList(pages);
		// 将list封装到分页对象中
		pages.setList(scores);
		mv.addObject("pages", pages);
		// 查询所有用户信息，供下拉框显示
		List<User> users = us.findAll();
		mv.addObject("users", users);
		log.info("用户的个数为:" + users.size());
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
		List<Score> scores = ss.pageList(pages);
		// 将list封装到分页对象中
		pages.setList(scores);
		mv.addObject("pages", pages);
		// 查询所有用户信息，供下拉框显示
		List<User> users = us.findAll();
		mv.addObject("users", users);
		log.info("用户的个数为:" + users.size());
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
		ModelAndView mv = new ModelAndView("redirect:showAllScore.do");
		// 计算简单题总分
		Integer sum = 0;
		for (Integer s : scores) {
			sum += s;
		}
		// 根据试卷ID和用户ID给该试卷主观题打分
		ss.updateScore(paperId, userId, sum);
		session.setAttribute("msg", "<script>$(function(){swal('Good!', '批阅试卷成功!', 'success');});</script>");
		return mv;
	}
}
