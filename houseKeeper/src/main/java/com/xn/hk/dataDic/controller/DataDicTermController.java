package com.xn.hk.dataDic.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.xn.hk.common.constant.Constant;
import com.xn.hk.common.constant.View;
import com.xn.hk.common.utils.log.LogType;
import com.xn.hk.common.utils.page.BasePage;
import com.xn.hk.common.utils.string.StringUtil;
import com.xn.hk.dataDic.model.DataDicTerm;
import com.xn.hk.dataDic.service.DataDicTermService;

/**
 * 
 * @ClassName: DataDicController
 * @Package: com.xn.hk.data.dic.controller
 * @Description: 数据字典项项的控制层
 * @Author: wanlei
 * @Date: 2018年11月7日 下午6:54:51
 */
@Controller
@RequestMapping(value = "/data/dic/term")
public class DataDicTermController {
	/**
	 * 记录日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(DataDicTermController.class);

	/**
	 * 注入service层
	 */
	@Autowired
	private DataDicTermService dataDicTermService;

	/**
	 * 实现数据字典项分页
	 * 
	 * @param dataDicTerm
	 *            数据字典项实体(用来封装查询条件)
	 * @param pages
	 *            分页对象
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/showTermByDataDicCode.do")
	public ModelAndView showTermByDataDicCode(DataDicTerm dataDicTerm, BasePage<DataDicTerm> pages,
			HttpSession session) {
		ModelAndView mv = new ModelAndView("datadic/showTermByDataDicCode");
		// 封装查询条件
		pages.setBean(dataDicTerm);
		List<DataDicTerm> dataDicTerms = dataDicTermService.pageList(pages);
		// 将list封装到分页对象中
		pages.setList(dataDicTerms);
		mv.addObject(Constant.PAGES, pages);
		// 将所属数据字典代码传递到jsp中
		mv.addObject(Constant.DATA_DIC_CODE, dataDicTerm.getDataDicCode());
		return mv;
	}

	/**
	 * 添加数据字典项
	 * 
	 * @param dataDicTerm
	 *            数据字典项实体
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/add.do")
	public ModelAndView add(DataDicTerm dataDicTerm, HttpSession session,RedirectAttributes attr) {
		int result = dataDicTermService.insert(session, "添加数据字典项", LogType.DATA_DIC_TERM_LOG.getType(), dataDicTerm);
		if (result == Constant.ZERO_VALUE) {
			logger.error("添加数据字典项{}失败!", dataDicTerm.getDataDicTermCode());
		} else {
			logger.info("添加数据字典项{}成功!", dataDicTerm.getDataDicTermCode());
			session.setAttribute(Constant.TIP_MSG, StringUtil.genTipMsg("添加数据字典项成功!", Constant.SUCCESS_TIP));
		}
		// 重定向至数据字典项分页的Action并将所属数据字典代码传递过去，保证操作完还是在显示所有的数据字典项列表的页面
		attr.addAttribute(Constant.DATA_DIC_CODE, dataDicTerm.getDataDicCode());
		return View.DATA_DIC_TERM_REDITRCT_ACTION;
	}

	/**
	 * 修改数据字典项
	 * 
	 * @param dataDic
	 *            数据字典项实体
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/update.do")
	public ModelAndView update(DataDicTerm dataDicTerm, HttpSession session,RedirectAttributes attr) {
		int result = dataDicTermService.update(session, "修改数据字典项", LogType.DATA_DIC_TERM_LOG.getType(), dataDicTerm);
		if (result == Constant.ZERO_VALUE) {
			logger.error("修改数据字典项{}失败!", dataDicTerm.getDataDicTermCode());
		} else {
			logger.info("修改数据字典项{}成功!", dataDicTerm.getDataDicTermCode());
			session.setAttribute(Constant.TIP_MSG, StringUtil.genTipMsg("修改数据字典项成功!", Constant.SUCCESS_TIP));
		}
		// 重定向至数据字典项分页的Action并将所属数据字典代码传递过去，保证操作完还是在显示所有的数据字典项列表的页面
		attr.addAttribute(Constant.DATA_DIC_CODE, dataDicTerm.getDataDicCode());
		return View.DATA_DIC_TERM_REDITRCT_ACTION;
	}

	/**
	 * 根据ID数组删除一个或多个数据字典项
	 * 
	 * @param dataDicIds
	 *            数据字典项ID数组
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/delete.do")
	public ModelAndView delete(Integer[] dataDicTermIds, HttpSession session,RedirectAttributes attr) {
		// 根据字典项ID查找所属数据字典
		DataDicTerm dataDicTerm = dataDicTermService.findById(dataDicTermIds[0]);
		int result = dataDicTermService.batchDelete(session, "删除数据字典项", LogType.DATA_DIC_TERM_LOG.getType(),
				dataDicTermIds);
		if (result == Constant.ZERO_VALUE) {
			logger.error("删除失败,该数组不存在!");
		} else {
			logger.info("删除数据字典项成功!");
			session.setAttribute(Constant.TIP_MSG, StringUtil.genTipMsg("删除数据字典项成功!", Constant.SUCCESS_TIP));
		}
		// 重定向至数据字典项分页的Action并将所属数据字典代码传递过去，保证操作完还是在显示所有的数据字典项列表的页面
		attr.addAttribute(Constant.DATA_DIC_CODE, dataDicTerm.getDataDicCode());
		return View.DATA_DIC_TERM_REDITRCT_ACTION;
	}
}
