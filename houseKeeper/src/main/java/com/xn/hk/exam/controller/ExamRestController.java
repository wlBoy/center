package com.xn.hk.exam.controller;

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
 * @Author: wanlei
 * @Date: 2017-11-30 下午01:12:25
 */
@RestController
@RequestMapping("/exam/rest")
public class ExamRestController {
	/**
	 * 注入service层
	 */
	@Autowired
	private QuestionTypeService questionTypeService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private PaperService paperService;

	/**
	 * 根据题型Id查询该题型
	 * 
	 * @param userId
	 *            题型Id
	 * @return 题型实体
	 */
	@RequestMapping(value = "/findByTypeId.do", method = RequestMethod.GET)
	public QuestionType findByTypeId(Integer typeId) {
		return questionTypeService.findById(typeId);
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
		return questionTypeService.findByName(typeName);
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
		return questionService.findById(questionId);
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
		return paperService.findById(paperId);
	}
}
