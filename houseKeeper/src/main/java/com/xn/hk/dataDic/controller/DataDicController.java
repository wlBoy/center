package com.xn.hk.dataDic.controller;

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
import com.xn.hk.common.utils.log.LogType;
import com.xn.hk.common.utils.page.BasePage;
import com.xn.hk.common.utils.string.StringUtil;
import com.xn.hk.dataDic.model.DataDic;
import com.xn.hk.dataDic.service.DataDicService;
import com.xn.hk.dataDic.service.DataDicTermService;

/**
 * 
 * @ClassName: DataDicController
 * @Package: com.xn.hk.data.dic.controller
 * @Description: 数据字典的控制层
 * @Author: wanlei
 * @Date: 2018年11月7日 下午6:54:51
 */
@Controller
@RequestMapping(value = "/data/dic")
public class DataDicController {
	/**
	 * 记录日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(DataDicController.class);

	/**
	 * 注入service层
	 */
	@Autowired
	private DataDicService dataDicService;
	@Autowired
	private DataDicTermService dataDicTermService;

	/**
	 * 实现数据字典分页
	 * 
	 * @param type
	 *            账务类别实体(用来封装查询条件)
	 * @param pages
	 *            分页对象
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/showAllDataDic.do")
	public ModelAndView showAllDataDic(DataDic datadic, BasePage<DataDic> pages, HttpSession session) {
		ModelAndView mv = new ModelAndView("datadic/showAllDataDic");
		// 封装查询条件
		pages.setBean(datadic);
		List<DataDic> datadics = dataDicService.pageList(pages);
		// 将list封装到分页对象中
		pages.setList(datadics);
		mv.addObject(Constant.PAGES, pages);
		return mv;
	}

	/**
	 * 添加数据字典
	 * 
	 * @param dataDic
	 *            数据字典实体
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/add.do")
	public ModelAndView add(DataDic dataDic, HttpSession session) {
		int result = dataDicService.insert(session, "添加数据字典", LogType.DATA_DIC_LOG.getType(), dataDic);
		if (result == Constant.ZERO_VALUE) {
			logger.error("添加数据字典{}失败!", dataDic.getDataDicName());
		} else {
			logger.info("添加数据字典{}成功!", dataDic.getDataDicName());
			session.setAttribute(Constant.TIP_MSG, StringUtil.genTipMsg("添加数据字典成功!", true));
		}
		return View.DATA_DIC_REDITRCT_ACTION;
	}

	/**
	 * 修改数据字典
	 * 
	 * @param dataDic
	 *            数据字典实体
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/update.do")
	public ModelAndView update(DataDic dataDic, HttpSession session) {
		int result = dataDicService.update(session, "修改数据字典", LogType.DATA_DIC_LOG.getType(), dataDic);
		if (result == Constant.ZERO_VALUE) {
			logger.error("修改数据字典{}失败!", dataDic.getDataDicName());
		} else {
			logger.info("修改数据字典{}成功!", dataDic.getDataDicName());
			session.setAttribute(Constant.TIP_MSG, StringUtil.genTipMsg("修改数据字典成功!", true));
		}
		return View.DATA_DIC_REDITRCT_ACTION;
	}

	/**
	 * 根据ID数组删除一个或多个数据字典
	 * 
	 * @param dataDicIds
	 *            数据字典ID数组
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/delete.do")
	public ModelAndView delete(Integer[] dataDicIds, HttpSession session) {
		boolean flag = true;
		for (Integer dataDicId : dataDicIds) {
			DataDic dataDic = dataDicService.findById(dataDicId);
			// 根据数据字典代码查找该数据字典项个数
			int result = dataDicTermService.findCountByDataDicCode(dataDic.getDataDicCode());
			// 当个数大于0，即存在字典项，只要其中一个含有字典项，就不允许删除
			if (result > 0) {
				flag = false;
				break;
			}
		}
		// 1.当该数据字典中含有数据字典项，是不允许删除的
		if (!flag) {
			session.setAttribute(Constant.TIP_MSG, StringUtil.genTipMsg("删除数据字典失败，其中含有数据字典项!", false));
			return View.DATA_DIC_REDITRCT_ACTION;
		}
		// 2.批量删除数据字典
		int result = dataDicService.batchDelete(session, "删除数据字典", LogType.DATA_DIC_LOG.getType(), dataDicIds);
		if (result == Constant.ZERO_VALUE) {
			logger.error("删除失败,该数组不存在!");
		} else {
			logger.info("删除数据字典成功!");
			session.setAttribute(Constant.TIP_MSG, StringUtil.genTipMsg("删除数据字典成功!", true));
		}
		return View.DATA_DIC_REDITRCT_ACTION;
	}
}
