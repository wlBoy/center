package com.xn.hk.system.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xn.hk.common.dao.BaseDao;
import com.xn.hk.common.service.impl.BaseServiceImpl;
import com.xn.hk.common.utils.excel.ExcelBean;
import com.xn.hk.common.utils.excel.ExcelUtil;
import com.xn.hk.system.dao.ChannelDataDao;
import com.xn.hk.system.dao.RoleDao;
import com.xn.hk.system.dao.UserDao;
import com.xn.hk.system.model.ChannelData;
import com.xn.hk.system.model.Role;
import com.xn.hk.system.model.User;
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
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;

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
		// 初始化sheet
		Map<Integer, List<ExcelBean>> sheetMap = new LinkedHashMap<Integer, List<ExcelBean>>();
		// 定义一个EXCEL对象
		List<ExcelBean> excel = new ArrayList<ExcelBean>();
		List<ExcelBean> excel1 = new ArrayList<ExcelBean>();
		// 设置第一个sheet的标题和对应的字段名
		excel.add(new ExcelBean("日期", "curday"));
		excel.add(new ExcelBean("合作方", "partner"));
		excel.add(new ExcelBean("应用名字", "appName"));
		excel.add(new ExcelBean("渠道号", "channelNum"));

		excel1.add(new ExcelBean("激活数", "activeNum"));
		excel1.add(new ExcelBean("单价", "fee"));
		excel1.add(new ExcelBean("总金额", "sumFee"));
		excel1.add(new ExcelBean("开发状态", "status"));
		excel1.add(new ExcelBean("合作方式", "partnerType"));
		String sheetName = "每天渠道数据";

		sheetMap.put(0, excel);
		// 生成EXCEL表格中的第一个sheet工作簿
		ExcelUtil.createExcel(xssfWorkbook, ChannelData.class, list, 0, excel, "每天渠道数据");
		ExcelUtil.createExcel(xssfWorkbook, ChannelData.class, list, 0, excel, "每天渠道数据");

		// 这样可实现创建多个sheet的EXCEL表格
		// ExcelUtil.createExcel(xssfWorkbook, ChannelData2.class, list2, 1, excel1,
		// "sheetName2");
		return xssfWorkbook;
	}

	/**
	 * 导出多个sheet的EXCEL表格
	 * 
	 * @param bot
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook exportExcel1(OutputStream bot) throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 定义EXCEL表格的头标题
		String[] roleHeaders = { "序号", "角色名", "备注", "创建时间" };
		String[] userHeaders = { "序号", "用户名", "角色名", "备注", "创建时间" };
		// 角色数据列表
		List<Role> roleList = roleDao.findAll();
		// 用户数据列表
		List<User> userList = userDao.findAll();
		// 生成多个sheet
		ExcelUtil.exportExcel(workbook, 0, "角色汇总", roleHeaders, covertRoleData(roleList), bot);
		ExcelUtil.exportExcel(workbook, 1, "用户汇总", userHeaders, covertUserData(userList), bot);
		return workbook;
	}

	/**
	 * 将List<userList>转换成List<List<String>>
	 * 
	 * @param adminSuggestList
	 * @return
	 */
	private static List<List<String>> covertUserData(List<User> userList) {
		int count = 0;
		List<List<String>> listSum = new ArrayList<List<String>>();
		for (User user : userList) {
			count++;
			List<String> list = new ArrayList<String>();
			list.add(String.valueOf(count));
			list.add(user.getUserName());
			list.add(user.getRole().getRoleName());
			list.add(user.getRemark());
			list.add(user.getCreateTime());
			listSum.add(list);
		}
		return listSum;
	}

	/**
	 * 将List<roleList>转换成List<List<String>>
	 * 
	 * @param reportList
	 * @return
	 */
	private static List<List<String>> covertRoleData(List<Role> roleList) {
		int count = 0;
		List<List<String>> listSum = new ArrayList<List<String>>();
		for (Role role : roleList) {
			count++;
			List<String> list = new ArrayList<String>();
			list.add(String.valueOf(count));
			list.add(role.getRoleName());
			list.add(role.getRemark());
			list.add(role.getCreateTime());
			listSum.add(list);
		}
		return listSum;
	}

	/**
	 * 本地测试导出EXCEL
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ChannelDataServiceImpl cds = new ChannelDataServiceImpl();
		File file = new File("D:/test.xls");
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			HSSFWorkbook workbook = cds.exportExcel1(out);
			// 原理就是将所有的数据一起写入，然后再关闭输入流。
			workbook.write(out);
			workbook.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
