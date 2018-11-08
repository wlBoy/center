package com.xn.hk.dataDic.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xn.hk.common.constant.Result;
import com.xn.hk.common.utils.string.StringUtil;
import com.xn.hk.dataDic.model.DataDic;
import com.xn.hk.dataDic.model.DataDicTerm;
import com.xn.hk.dataDic.service.DataDicService;
import com.xn.hk.dataDic.service.DataDicTermService;

/**
 * 
 * @Title: DataRestController
 * @Package: com.xn.hk.account.controller
 * @Description: 处理数据字典管理中的所有ajax请求(接口)
 * @Author: wanlei
 * @Date: 2018年1月8日 上午9:17:36
 */
@RestController
@RequestMapping("/data/rest")
public class DataRestController {
	/**
	 * 记录日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(DataRestController.class);
	/**
	 * 注入service层
	 */
	@Autowired
	private DataDicService dataDicService;
	@Autowired
	private DataDicTermService dataDicTermService;

	/**
	 * 根据数据字典Id查询该数据字典
	 * 
	 * @param dataDicId
	 *            数据字典Id
	 * @return 结果提示信息实体
	 */
	@RequestMapping(value = "/findByDataDicId.do", method = RequestMethod.POST)
	public Result findByDataDicId(Integer dataDicId) {
		// 校验参数非空性
		if (dataDicId == null) {
			return Result.genNullParamTip("数据字典ID");
		}
		DataDic dataDic = dataDicService.findById(dataDicId);
		if (dataDic == null) {
			logger.info("findByDataDicId-->查到{}数据字典!", dataDicId);
			return new Result(Result.FAILURE, "该数据字典不存在!", null);
		}
		logger.info("findByDataDicId-->查到{}数据字典!", dataDicId);
		return Result.genTip(Result.SUCCESS, "查到该数据字典!", dataDic);
	}

	/**
	 * 根据数据字典代码查找该数据字典
	 * 
	 * @param dataDicCode
	 *            数据字典代码
	 * @return 结果提示信息实体
	 */
	@RequestMapping(value = "/findByDataDicCode.do", method = RequestMethod.POST)
	public Result findByDataDicCode(String dataDicCode) {
		// 校验参数非空性
		if (StringUtil.isEmpty(dataDicCode)) {
			return Result.genNullParamTip("数据字典代码");
		}
		DataDic dataDic = dataDicService.findByName(dataDicCode);
		if (dataDic == null) {
			logger.info("findByDataDicCode-->{}数据字典不存在!", dataDicCode);
			return Result.genTip(Result.FAILURE, "该数据字典不存在!", null);
		}
		logger.info("findByDataDicCode-->查到{}数据字典!", dataDicCode);
		return Result.genTip(Result.SUCCESS, "查到该数据字典!", dataDic);
	}

	/**
	 * 根据数据字典项Id查询该数据字典项
	 * 
	 * @param dataDicTermId
	 *            数据字典项Id
	 * @return 结果提示信息实体
	 */
	@RequestMapping(value = "/findByDataDicTermId.do", method = RequestMethod.POST)
	public Result findByDataDicTermId(Integer dataDicTermId) {
		// 校验参数非空性
		if (dataDicTermId == null) {
			return Result.genNullParamTip("数据字典项ID");
		}
		DataDicTerm dataDicTerm = dataDicTermService.findById(dataDicTermId);
		if (dataDicTerm == null) {
			logger.info("findByDataDicTermId-->查到{}数据字典项!", dataDicTermId);
			return new Result(Result.FAILURE, "该数据字典项不存在!", null);
		}
		logger.info("findByDataDicTermId-->查到{}数据字典项!", dataDicTermId);
		return Result.genTip(Result.SUCCESS, "查到该数据字典项!", dataDicTerm);
	}

	/**
	 * 根据数据字典项代码查找该数据字典项
	 * 
	 * @param dataDicCode
	 *            数据字典项代码
	 * @return 结果提示信息实体
	 */
	@RequestMapping(value = "/findByDataDicTermCode.do", method = RequestMethod.POST)
	public Result findByDataDicTermCode(String dataDicTermCode) {
		// 校验参数非空性
		if (StringUtil.isEmpty(dataDicTermCode)) {
			return Result.genNullParamTip("数据字典项代码");
		}
		DataDicTerm dataDicTerm = dataDicTermService.findByName(dataDicTermCode);
		if (dataDicTerm == null) {
			logger.info("findByDataDicTermCode-->{}数据字典项不存在!", dataDicTermCode);
			return Result.genTip(Result.FAILURE, "该数据字典项不存在!", null);
		}
		logger.info("findByDataDicTermCode-->查到{}数据字典项!", dataDicTermCode);
		return Result.genTip(Result.SUCCESS, "查到该数据字典项!", dataDicTerm);
	}
}
