package com.xn.hk.exam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xn.hk.common.dao.BaseDao;
import com.xn.hk.common.service.impl.BaseServiceImpl;
import com.xn.hk.exam.dao.QuestionTypeDao;
import com.xn.hk.exam.model.QuestionType;
import com.xn.hk.exam.service.QuestionTypeService;
/**
 * 
 * @Title: QuestionTypeServiceImpl
 * @Package: com.xn.hk.exam.service.impl
 * @Description: 处理题型的service实现层
 * @Author: wanlei
 * @Date: 2018年1月8日 上午10:39:20
 */
@Service
public class QuestionTypeServiceImpl extends BaseServiceImpl<QuestionType> implements QuestionTypeService {
	@Autowired
	private QuestionTypeDao questionTypeDao;
	/**
	 * 指定特定的dao
	 */
	public BaseDao<QuestionType> getDao() {
		return questionTypeDao;
	}

}
