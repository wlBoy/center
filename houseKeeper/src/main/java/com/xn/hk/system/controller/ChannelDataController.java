package com.xn.hk.system.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.xn.hk.common.utils.page.BasePage;
import com.xn.hk.system.model.ChannelData;
import com.xn.hk.system.service.ChannelDataService;

/**
 * 
 * @Title: ChannelDataController
 * @Package: com.xn.ad.data.controller
 * @Description: 处理渠道数据的service控制层
 * @Company: 杭州讯牛
 * @Author: wanlei
 * @Date: 2018-1-19 下午05:54:50
 */
@Controller
@RequestMapping(value = "/data/channel")
public class ChannelDataController {
	/**
	 * 记录日志
	 */
	private static final Logger log = Logger.getLogger(ChannelDataController.class);
	@Autowired
	private ChannelDataService cds;

	/**
	 * EXCEL表格数据导入
	 * 
	 * @param upfile
	 *            文件上传文件
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping("/importExcel.do")
	public ModelAndView importExcel(MultipartFile upfile, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView("redirect:showAllChannelData.do");
		// 获取上传的文件的输入流
		InputStream in = upfile.getInputStream();
		// 获取上传的文件的文件名
		String fileName = upfile.getOriginalFilename();
		// 数据导入
		int result = cds.importExcelInfo(in,fileName);
		if (result > 0) {
			log.info("导入Excel数据成功!");
			session.setAttribute("msg", "<script>$(function(){swal('Good!', '导入Excel数据成功!', 'success');});</script>");
		}
		in.close();
		return mv;
	}

	/**
	 * 将页面所有数据导出EXCEL文件(即下载)
	 * 
	 * @param fileName
	 *            文件名
	 * @param response
	 *            response
	 * @throws Exception
	 */
	@RequestMapping("/exportAll.do")
	@ResponseBody
	public void exportAll(String fileName, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		// 防止下载的文件名中文乱码
		response.setHeader("Content-Disposition",
				"attachment;filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1") + ".xlsx");
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		XSSFWorkbook workbook = null;
		// 导出Excel文件
		workbook = cds.exportAll();
		OutputStream output;
		try {
			output = response.getOutputStream();
			BufferedOutputStream bot = new BufferedOutputStream(output);
			bot.flush();
			workbook.write(bot);
			bot.close();
			log.info("导出EXCEL成功!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 实现渠道数据分页
	 * 
	 * @param user
	 *            用户实体(用来封装查询条件)
	 * @param pages
	 *            分页对象
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/showAllChannelData.do")
	public ModelAndView showAllChannelData(ChannelData cd, BasePage<ChannelData> pages) {
		ModelAndView mv = new ModelAndView("system/showAllChannelData");
		// 封装查询条件
		pages.setBean(cd);
		List<ChannelData> list = cds.pageList(pages);
		// 将list封装到分页对象中
		pages.setList(list);
		mv.addObject("pages", pages);
		log.info("分页大小为:" + list.size());
		return mv;
	}

}
