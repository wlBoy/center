package com.xn.hk.system.service;


import java.io.InputStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.xn.hk.common.service.BaseService;
import com.xn.hk.system.model.ChannelData;


/**
 * 
 * @Title: ChannelDataService
 * @Package: com.xn.ad.data.service
 * @Description: 处理渠道数据的service层
 * @Author: wanlei
 * @Date: 2018-1-19 下午05:53:24
 */
public interface ChannelDataService extends BaseService<ChannelData>{
	/**
	 * EXCEL表格数据导入
	 * @param in 上传的文件的输入流
	 * @param fileName 上传的文件的文件名
	 * @throws Exception 
	 */
	int importExcelInfo(InputStream in, String fileName) throws Exception;
	/**
	 * 导出EXCEL文件
	 * @return EXCEL文件对象
	 * @throws Exception 
	 */
	XSSFWorkbook exportExcel() throws Exception;
}
