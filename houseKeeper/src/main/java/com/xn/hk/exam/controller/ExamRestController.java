package com.xn.hk.exam.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xn.hk.exam.model.Paper;
import com.xn.hk.exam.model.Question;
import com.xn.hk.exam.model.QuestionType;
import com.xn.hk.exam.service.PaperService;
import com.xn.hk.exam.service.QuestionService;
import com.xn.hk.exam.service.QuestionTypeService;

/**
 * 
 * @Title: RestController
 * @Package: com.xn.ad.system.controller
 * @Description: 处理考试管理中的所有ajax请求
 * @Company: 杭州讯牛
 * @Author: wanlei
 * @Date: 2017-11-30 下午01:12:25
 */
@RestController
@RequestMapping("/exam/rest")
public class ExamRestController {
	/**
	 * 记录日志
	 */
	private static final Logger log = Logger.getLogger(ExamRestController.class);
	@Autowired
	private QuestionTypeService qts;
	@Autowired
	private QuestionService qs;
	@Autowired
	private PaperService ps;

	/**
	 * 根据题型Id查询该题型
	 * 
	 * @param userId
	 *            题型Id
	 * @return 题型实体
	 */
	@RequestMapping(value = "/findByTypeId.do", method = RequestMethod.GET)
	public QuestionType findByTypeId(Integer typeId) {
		QuestionType t = qts.findById(typeId);
		log.info("该题型的信息为:" + t);
		return t;
	}

	/**
	 * 根据题型名查询该题型
	 * 
	 * @param userName
	 *            题型名
	 * @return 题型实体
	 */
	@RequestMapping(value = "/findByTypeName.do", method = RequestMethod.GET)
	public QuestionType findByTypeName(String typeName) {
		QuestionType t = qts.findByName(typeName);
		log.info("该题型的信息为:" + t);
		return t;
	}

	/**
	 * 根据题目Id查询该题目
	 * 
	 * @param questionId
	 *            题目Id
	 * @return 题目实体
	 */
	@RequestMapping(value = "/findByQuestionId.do", method = RequestMethod.GET)
	public Question findByQuestionId(Integer questionId) {
		Question q = qs.findById(questionId);
		log.info("该题目的信息为:" + q);
		return q;
	}

	/**
	 * 根据试卷Id查询该试卷
	 * 
	 * @param paperId
	 *            试卷Id
	 * @return 试卷实体
	 */
	@RequestMapping(value = "/findByPaperId.do", method = RequestMethod.GET)
	public Paper findByPaperId(Integer paperId) {
		Paper p = ps.findById(paperId);
		log.info("该试卷的信息为:" + p);
		return p;
	}
}
