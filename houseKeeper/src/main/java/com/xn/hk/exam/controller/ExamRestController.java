package com.xn.hk.exam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xn.hk.common.constant.Result;
import com.xn.hk.common.utils.string.StringUtil;
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
 * @Description: 处理考试管理中的所有ajax请求(接口)
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
	 * @return 结果提示信息实体
	 */
	@RequestMapping(value = "/findByTypeId.do", method = RequestMethod.POST)
	public Result findByTypeId(Integer typeId) {
		// 校验参数非空性
		if (typeId == null) {
			return Result.genErrorTip("题型ID不能为空!");
		}
		QuestionType type = questionTypeService.findById(typeId);
		if (type == null) {
			return Result.genErrorTip("该题型不存在!");
		}
		return Result.genSuccessTip("查到该题型!", type);
	}

	/**
	 * 根据题型名查询该题型
	 * 
	 * @param userName
	 *            题型名
	 * @return 结果提示信息实体
	 */
	@RequestMapping(value = "/findByTypeName.do", method = RequestMethod.POST)
	public Result findByTypeName(String typeName) {
		// 校验参数非空性
		if (StringUtil.isEmpty(typeName)) {
			return Result.genErrorTip("题型名称不能为空!");
		}
		QuestionType type = questionTypeService.findByName(typeName);
		if (type == null) {
			return Result.genErrorTip("该题型不存在!");
		}
		return Result.genSuccessTip("查到该题型!", type);
	}

	/**
	 * 根据题目Id查询该题目
	 * 
	 * @param questionId
	 *            题目Id
	 * @return 结果提示信息实体
	 */
	@RequestMapping(value = "/findByQuestionId.do", method = RequestMethod.POST)
	public Result findByQuestionId(Integer questionId) {
		// 校验参数非空性
		if (questionId == null) {
			return Result.genErrorTip("题目ID不能为空!");
		}
		Question question = questionService.findById(questionId);
		if (question == null) {
			return Result.genErrorTip("该题目不存在!");
		}
		return Result.genSuccessTip("查到该题目!", question);
	}

	/**
	 * 根据试卷Id查询该试卷
	 * 
	 * @param paperId
	 *            试卷Id
	 * @return 结果提示信息实体
	 */
	@RequestMapping(value = "/findByPaperId.do", method = RequestMethod.POST)
	public Result findByPaperId(Integer paperId) {
		// 校验参数非空性
		if (paperId == null) {
			return Result.genErrorTip("试卷ID不能为空!");
		}
		Paper paper = paperService.findById(paperId);
		if (paper == null) {
			return Result.genErrorTip("该试卷不存在!");
		}
		return Result.genSuccessTip("查到该试卷!", paper);
	}
}
