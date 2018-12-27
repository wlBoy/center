package com.xn.hk.system.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xn.hk.common.dao.BaseDao;
import com.xn.hk.common.service.impl.BaseServiceImpl;
import com.xn.hk.common.utils.excel.ExcelBean;
import com.xn.hk.common.utils.excel.ExcelUtil;
import com.xn.hk.system.dao.ChannelDataDao;
import com.xn.hk.system.model.ChannelData;
import com.xn.hk.system.service.ChannelDataService;

/**
 * 
 * @Title: ChannelDataServiceImpl
 * @Package: com.xn.ad.data.service.impl
 * @Description: 处理渠道数据的service实现层
 * @Author: wanlei
 * @Date: 2018-1-19 下午05:52:51
 */

@Service
public class ChannelDataServiceImpl extends BaseServiceImpl<ChannelData> implements ChannelDataService {
	@Autowired
	private ChannelDataDao channelDataDao;

	/**
	 * 指定dao层
	 */
	public BaseDao<ChannelData> getDao() {
		return channelDataDao;
	}

	/**
	 * EXCEL表格数据导入
	 * 
	 * @param in
	 *            上传的文件的输入流
	 * @param fileName
	 *            上传的文件的文件名
	 * @throws Exception
	 */
	public int importExcelInfo(InputStream in, String fileName) throws Exception {
		// 调用工具类将EXCEL表格中的内容封装成list集合
		List<List<Object>> listob = ExcelUtil.readExcel(in, fileName);
		List<ChannelData> list = new ArrayList<ChannelData>();
		for (int i = 0; i < listob.size(); i++) {
			List<Object> ob = listob.get(i);
			ChannelData cd = new ChannelData();
			cd.setCurday(String.valueOf(ob.get(0)));
			cd.setPartner(String.valueOf(ob.get(1)));
			cd.setAppName(String.valueOf(ob.get(2)));
			cd.setChannelNum(String.valueOf(ob.get(3)));
			cd.setActiveNum(Integer.parseInt(String.valueOf(ob.get(4))));
			cd.setFee(Double.parseDouble(String.valueOf(ob.get(5))));
			cd.setSumFee(Double.parseDouble(String.valueOf(ob.get(6))));
			cd.setStatus(Integer.parseInt(String.valueOf(ob.get(7))));
			cd.setPartnerType(String.valueOf(ob.get(8)));
			list.add(cd);
		}
		// 批量插入EXCEL表格数据
		channelDataDao.insertInfoBatch(list);
		return list.size();
	}

	/**
	 * 导出EXCEL文件
	 * 
	 * @return EXCEL文件对象
	 * @throws Exception
	 */
	public XSSFWorkbook exportExcel() throws Exception {
		// 创建一个新的Excel2007对象
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
		// 查询要生成EXCEL表格中的内容数据
		List<ChannelData> list = channelDataDao.findAll();
		// 定义一个EXCEL对象
		List<ExcelBean> excel = new ArrayList<ExcelBean>();
		// 设置第一个sheet的标题和对应的字段名
		excel.add(new ExcelBean("日期", "curday"));
		excel.add(new ExcelBean("合作方", "partner"));
		excel.add(new ExcelBean("应用名字", "appName"));
		excel.add(new ExcelBean("渠道号", "channelNum"));
		excel.add(new ExcelBean("激活数", "activeNum"));
		excel.add(new ExcelBean("单价", "fee"));
		excel.add(new ExcelBean("总金额", "sumFee"));
		excel.add(new ExcelBean("开发状态", "status"));
		excel.add(new ExcelBean("合作方式", "partnerType"));
		String sheetName = "每天渠道数据";

		// 生成EXCEL表格中的第一个sheet工作簿
		ExcelUtil.createExcel(xssfWorkbook, ChannelData.class, list, 0, excel, sheetName);
		
		// 这样可实现创建多个sheet的EXCEL表格
		//ExcelUtil.createExcel(xssfWorkbook, ChannelData2.class, list2, 1, excel1, "sheetName2");
		return xssfWorkbook;
	}

}
