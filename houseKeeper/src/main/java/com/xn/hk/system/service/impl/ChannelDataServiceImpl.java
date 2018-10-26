package com.xn.hk.system.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
		List<List<Object>> listob = ExcelUtil.getBankListByExcel(in, fileName);
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
	public XSSFWorkbook exportAll() throws Exception {
		// 查询要生成EXCEL表格中的内容数据
		List<ChannelData> list = channelDataDao.findAll();
		// 生成EXCEL格式文件
		List<ExcelBean> excel = new ArrayList<ExcelBean>();
		Map<Integer, List<ExcelBean>> map = new LinkedHashMap<Integer, List<ExcelBean>>();
		XSSFWorkbook xssfWorkbook = null;
		// 设置标题栏
		excel.add(new ExcelBean("日期", "curday", 0));
		excel.add(new ExcelBean("合作方", "partner", 0));
		excel.add(new ExcelBean("应用名字", "appName", 0));
		excel.add(new ExcelBean("渠道号", "channelNum", 0));
		excel.add(new ExcelBean("激活数", "activeNum", 0));
		excel.add(new ExcelBean("单价", "fee", 0));
		excel.add(new ExcelBean("总金额", "sumFee", 0));
		excel.add(new ExcelBean("开发状态", "status", 0));
		excel.add(new ExcelBean("合作方式", "partnerType", 0));
		map.put(0, excel);
		String sheetName = "每天渠道数据";
		// 调用ExcelUtil的方法,将查询出的结果封装到EXCEL文件中
		xssfWorkbook = ExcelUtil.createExcelFile(ChannelData.class, list, map, sheetName);
		return xssfWorkbook;
	}

}
