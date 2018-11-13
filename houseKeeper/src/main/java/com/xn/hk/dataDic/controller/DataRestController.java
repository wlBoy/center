package com.xn.hk.dataDic.controller;

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
@RequestMapping("/data/dic/rest")
public class DataRestController {
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
			return Result.genErrorTip("数据字典ID不能为空!");
		}
		DataDic dataDic = dataDicService.findById(dataDicId);
		if (dataDic == null) {
			return Result.genErrorTip("该数据字典不存在!");
		}
		return Result.genSuccessTip("查到该数据字典!", dataDic);
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
			return Result.genErrorTip("数据字典代码不能为空!");
		}
		DataDic dataDic = dataDicService.findByName(dataDicCode);
		if (dataDic == null) {
			return Result.genErrorTip("该数据字典不存在!");
		}
		return Result.genSuccessTip("查到该数据字典!", dataDic);
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
			return Result.genErrorTip("数据字典项ID不能为空!");
		}
		DataDicTerm dataDicTerm = dataDicTermService.findById(dataDicTermId);
		if (dataDicTerm == null) {
			return Result.genErrorTip("该数据字典项不存在!");
		}
		return Result.genSuccessTip("查到该数据字典项!", dataDicTerm);
	}

	/**
	 * 根据数据字典项代码和所属数据字典代码查找该数据字典项
	 * 
	 * @param dataDicTermCode
	 *            数据字典项代码
	 * @param dataDicCode
	 *            数据字典代码
	 * @return 结果提示信息实体
	 */
	@RequestMapping(value = "/findByDataDicTermCode.do", method = RequestMethod.POST)
	public Result findByDataDicTermCode(String dataDicTermCode, String dataDicCode) {
		// 校验参数非空性
		if (StringUtil.isEmpty(dataDicTermCode)) {
			return Result.genErrorTip("数据字典项代码不能为空!");
		}
		DataDicTerm dataDicTerm = dataDicTermService.findTermCodeAndDataDicCode(dataDicTermCode, dataDicCode);
		if (dataDicTerm == null) {
			return Result.genErrorTip("该数据字典项不存在!");
		}
		return Result.genSuccessTip("查到该数据字典项!", dataDicTerm);
	}
}
