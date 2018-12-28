package com.xn.hk.common.utils.excel;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.xn.hk.system.model.Role;
import com.xn.hk.system.model.User;

/**
 * 
 * @Title: ExcelUtil
 * @Package: com.xn.ad.common.utils
 * @Description: EXCEL导入和导出的操作工具类
 * @Author: wanlei
 * @Date: 2018-1-20 上午11:28:00
 */
public class ExcelUtil {
	public final static String excel2003L = ".xls"; // 2003- 版本的excel
	public final static String excel2007U = ".xlsx"; // 2007+ 版本的excel

	/**
	 * 将EXCEL表格中的数据转换为Map<Integer,List<List<Object>>>集合 (支持多个sheet)
	 * 
	 * @param in
	 *            EXCEL表格文件的输入流
	 * @param fileName
	 *            EXCEL表格文件名
	 * @return Map<Integer,List<List<Object>>>
	 *         key为第几个sheet的数据，List<List<Object>>代表对应sheet的数据
	 * @throws Exception
	 */
	public static Map<Integer, List<List<Object>>> readExcel(InputStream in, String fileName) throws Exception {
		Sheet sheet = null;
		Row row = null;
		Map<Integer, List<List<Object>>> map = new HashMap<Integer, List<List<Object>>>();
		// 创建Excel工作薄
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(in);
		// 拿到对应的FormulaEvaluator对象(计算Excel表格中公式值的类)
		XSSFFormulaEvaluator xssfFormulaEvaluator = new XSSFFormulaEvaluator(xssfWorkbook);
		// 循环遍历EXCEL中的所有sheet工作簿
		for (int i = 0; i < xssfWorkbook.getNumberOfSheets(); i++) {
			List<List<Object>> list = new ArrayList<List<Object>>();
			sheet = xssfWorkbook.getSheetAt(i);
			// 工作簿为空，跳过
			if (sheet == null) {
				continue;
			}
			// 遍历当前sheet中的所有行,从第一行到最后一行,索引从0开始
			for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
				row = sheet.getRow(j);
				if (row == null || row.getFirstCellNum() == j) {
					continue;
				}
				// 遍历当前sheet中的所有列,从第一列到最后一列,索引从0开始
				List<Object> li = new ArrayList<Object>();
				for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
					li.add(getCellValue(row.getCell(y), xssfFormulaEvaluator));
				}
				list.add(li);
			}
			map.put(i, list);
		}
		return map;
	}

	/**
	 * 对获取EXCEL表格单元格中的内容进行格式化取值
	 * 
	 * @param cell
	 *            EXCEL表格单元格
	 * @param formulaEvaluator
	 *            计算EXCEL表格单元格中的公式对象
	 * @return EXCEL表格单元格中的内容
	 */
	private static Object getCellValue(Cell cell, FormulaEvaluator formulaEvaluator) {
		Object value = null;
		// 格式化数字
		DecimalFormat df = new DecimalFormat("0.0");
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			// 匹配字符串
			value = cell.getRichStringCellValue().getString();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			// 匹配数字,默认返回的是double类型,保留一位小数
			long longVal = Math.round(cell.getNumericCellValue());
			if (Double.parseDouble(longVal + ".0") == cell.getNumericCellValue()) {
				value = longVal;
			} else {
				value = df.format(cell.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_FORMULA:
			// 匹配公式
			value = df.format((formulaEvaluator.evaluate(cell).getNumberValue()));
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			// 匹配Boolean类型
			value = cell.getBooleanCellValue();
			break;
		case Cell.CELL_TYPE_BLANK:
			// 匹配空值
			value = "";
			break;
		default:
			break;
		}
		return value;
	}

	/*---------------分割线,以上是将EXCEL数据导出,以下是生成EXCEL表格----------------------*/

	/**
	 * 多列头创建EXCEL
	 * 
	 * @param xssfWorkbook
	 *            2007以后的工作簿对象，兼容2003
	 * @param clazz
	 *            数据源model类型,字节码
	 * @param list
	 *            需要导入的数据list列表
	 * @param sheetNum
	 *            第几个(0代表第一个)
	 * @param excel
	 *            对应的List<ExcelBean>对象
	 * @param sheetName
	 *            工作簿名称
	 * @throws Exception
	 */
	public static <T> void createExcelByJavaBean(XSSFWorkbook xssfWorkbook, Class<T> clazz, List<T> list,
			Integer sheetNum, List<ExcelBean> excel, String sheetName) throws Exception {
		// 生成一个sheet
		XSSFSheet xssfSheet = xssfWorkbook.createSheet();
		xssfWorkbook.setSheetName(sheetNum, sheetName);
		// 以下为excel的字体样式以及excel的标题与内容的创建,下面会具体分析
		createFont(xssfWorkbook);// 字体样式
		createTableHeader(xssfSheet, excel);// 创建标题（头）
		createTableRows(xssfSheet, sheetNum, excel, list, clazz);// 创建内容
	}

	// 表头
	private static XSSFCellStyle fontStyle;
	// 内容
	private static XSSFCellStyle fontStyle2;

	/**
	 * 创建ECXEL表格的表头和内容样式
	 * 
	 * @param workbook
	 *            ECXEL表格对象
	 */
	private static void createFont(XSSFWorkbook workbook) {
		// 表头
		fontStyle = workbook.createCellStyle();
		XSSFFont font1 = workbook.createFont();
		font1.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		font1.setFontName("黑体");
		font1.setFontHeightInPoints((short) 14);// 设置字体大小
		fontStyle.setFont(font1);
		fontStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN); // 下边框
		fontStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);// 左边框
		fontStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);// 上边框
		fontStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);// 右边框
		fontStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中
		// 内容
		fontStyle2 = workbook.createCellStyle();
		XSSFFont font2 = workbook.createFont();
		font2.setFontName("宋体");
		font2.setFontHeightInPoints((short) 10);// 设置字体大小
		fontStyle2.setFont(font2);
		fontStyle2.setBorderBottom(XSSFCellStyle.BORDER_THIN); // 下边框
		fontStyle2.setBorderLeft(XSSFCellStyle.BORDER_THIN);// 左边框
		fontStyle2.setBorderTop(XSSFCellStyle.BORDER_THIN);// 上边框
		fontStyle2.setBorderRight(XSSFCellStyle.BORDER_THIN);// 右边框
		fontStyle2.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中
	}

	/**
	 * 根据传入的map数据填充EXCEL表格头
	 * 
	 * @param excel
	 *            对应的List<ExcelBean>对象
	 * @param map
	 *            已经封装好的map数据(即要生成EXCEL中的内容)
	 */
	private static final void createTableHeader(XSSFSheet sheet, List<ExcelBean> excels) {
		int startIndex = 0;// cell起始位置
		int endIndex = 0;// cell终止位置
		XSSFRow row = sheet.createRow(0);
		for (int x = 0; x < excels.size(); x++) {
			// 合并单元格
			if (excels.get(x).getCols() > 1) {
				if (x == 0) {
					endIndex += excels.get(x).getCols() - 1;
					CellRangeAddress range = new CellRangeAddress(0, 0, startIndex, endIndex);
					sheet.addMergedRegion(range);
					startIndex += excels.get(x).getCols();
				} else {
					endIndex += excels.get(x).getCols();
					CellRangeAddress range = new CellRangeAddress(0, 0, startIndex, endIndex);
					sheet.addMergedRegion(range);
					startIndex += excels.get(x).getCols();
				}
				XSSFCell cell = row.createCell(startIndex - excels.get(x).getCols());
				cell.setCellValue(excels.get(x).getHeadTextName());// 设置内容
				if (excels.get(x).getCellStyle() != null) {
					cell.setCellStyle(excels.get(x).getCellStyle());// 设置格式
				}
				cell.setCellStyle(fontStyle);
			} else {
				XSSFCell cell = row.createCell(x);
				cell.setCellValue(excels.get(x).getHeadTextName());// 设置内容
				if (excels.get(x).getCellStyle() != null) {
					cell.setCellStyle(excels.get(x).getCellStyle());// 设置格式
				}
				cell.setCellStyle(fontStyle);
			}
		}
	}

	/**
	 * 根据传入的map数据(头信息)和list集合的内容数据和类的字节码生成EXCEL表格
	 * 
	 * @param sheet
	 *            ECXEL表格对象
	 * @param sheetNum
	 *            第几个(0代表第一个)
	 * @param excel
	 *            对应的List<ExcelBean>对象
	 * @param objs
	 *            要封装的数据内容
	 * @param clazz
	 *            要封装的Javabean字节码
	 * @throws Exception
	 */
	private static <T> void createTableRows(XSSFSheet sheet, Integer sheetNum, List<ExcelBean> ems, List<T> objs,
			Class<T> clazz) throws Exception {
		int rowindex = 1;// 从第一行开始写内容
		List<Integer> widths = new ArrayList<Integer>(ems.size());
		for (Object obj : objs) {
			// 创建行
			XSSFRow row = sheet.createRow(rowindex);
			for (int i = 0; i < ems.size(); i++) {
				String value = "";
				ExcelBean em = (ExcelBean) ems.get(i);
				// 特殊处理序号这一列
				if ("序号".equals(em.getHeadTextName())) {
					value = String.valueOf(rowindex);
				} else {
					value = getCellValueByReflect(clazz, obj, value, em);
				}
				XSSFCell cell = row.createCell(i);
				cell.setCellValue(value);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(fontStyle2);
				// 获得最大列宽
				int width = value.getBytes().length * 300;
				// 还未设置，设置当前
				if (widths.size() <= i) {
					widths.add(width);
					continue;
				}
				// 比原来大，更新数据
				if (width > widths.get(i)) {
					widths.set(i, width);
				}
			}
			rowindex++;
		}
		// 设置列宽
		for (int index = 0; index < widths.size(); index++) {
			Integer width = widths.get(index);
			width = width < 2500 ? 2500 : width + 300;
			width = width > 10000 ? 10000 + 300 : width + 300;
			sheet.setColumnWidth(index, width);
		}
	}

	/**
	 * 通过反射实现表头与javaBean字段的映射，得到javaBean中的值返回
	 * 
	 * @param clazz
	 * @param obj
	 * @param value
	 * @param em
	 * @return javaBean中对应属性的值
	 * @throws Exception
	 */
	private static <T> String getCellValueByReflect(Class<T> clazz, Object obj, String value, ExcelBean em)
			throws Exception {
		// 获得get方法
		PropertyDescriptor pd = new PropertyDescriptor(em.getPropertyName(), clazz);
		Method getMethod = pd.getReadMethod();
		Object rtn = getMethod.invoke(obj);
		// 如果是日期类型 进行 转换
		if (rtn != null) {
			if (rtn instanceof Date) {
				value = formatDate((Date) rtn);
			} else if (rtn instanceof BigDecimal) {
				NumberFormat nf = new DecimalFormat("#,##0.00");
				value = nf.format((BigDecimal) rtn).toString();
			} else if ((rtn instanceof Integer) && (Integer.valueOf(rtn.toString()) < 0)) {
				// 小于0的数字显示--
				value = "--";
			} else {
				value = rtn.toString();
			}
		}
		return value;
	}

	/**
	 * 把日期时间格式化为yyyy-MM-dd格式
	 * 
	 * @return 格式化后的日期时间字符串
	 */
	private static String formatDate(Date date) {
		if (date != null) {
			SimpleDateFormat dateStyle = new SimpleDateFormat("yyyy-MM-dd");
			return dateStyle.format(date);
		}
		return "";
	}

	/**
	 * 创建多个sheet的EXCEL表格，兼容EXCEL2003和EXCEL2007格式的文件
	 * 
	 * @param xssfWorkbook
	 *            EXCEL2007对象
	 * @param sheetNum
	 *            (sheet的位置，0表示第一个表格中的第一个sheet)
	 * @param sheetName
	 *            （sheet的名称）
	 * @param headers
	 *            （表格的标题）
	 * @param list
	 *            （表格的数据）
	 * @throws Exception
	 */
	public static void createExcelByString(XSSFWorkbook xssfWorkbook, int sheetNum, String sheetName, String[] headers,
			List<List<String>> list) throws Exception {
		// 生成一个表格
		XSSFSheet sheet = xssfWorkbook.createSheet();
		xssfWorkbook.setSheetName(sheetNum, sheetName);
		// 设置表格默认列宽度为20个字节
		sheet.setDefaultColumnWidth((short) 20);
		// 生成一个样式
		XSSFCellStyle style = xssfWorkbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		// 生成一个字体
		XSSFFont font = xssfWorkbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 指定当单元格内容显示不下时自动换行
		style.setWrapText(true);
		// 产生表格标题行
		XSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			XSSFCell cell = row.createCell((short) i);

			cell.setCellStyle(style);
			XSSFRichTextString text = new XSSFRichTextString(headers[i]);
			cell.setCellValue(text.toString());
		}
		// 遍历集合数据，产生数据行
		if (list != null) {
			int index = 1;
			for (List<String> m : list) {
				row = sheet.createRow(index);
				int cellIndex = 0;
				for (String str : m) {
					XSSFCell cell = row.createCell((short) cellIndex);
					cell.setCellValue(str.toString());
					cellIndex++;
				}
				index++;
			}
		}
	}

	/*-------------------分割线,以上是生成EXCEL表格,以下是EXCEL导入和导出的demo及本地测试------------------*/
	/**
	 * 导出多个sheet的EXCEL表格
	 * 
	 * @param bot
	 * @return
	 * @throws Exception
	 */
	private XSSFWorkbook testExportExcelByString() throws Exception {
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 定义EXCEL表格的头标题
		String[] roleHeaders = { "序号", "角色名", "备注", "创建时间" };
		String[] userHeaders = { "序号", "用户名", "角色名", "备注", "创建时间" };
		// 角色数据列表
		List<Role> roleList = getRoleList();
		// 用户数据列表
		List<User> userList = getUserList();
		// 生成多个sheet
		ExcelUtil.createExcelByString(workbook, 0, "角色汇总", roleHeaders, covertRoleData(roleList));
		ExcelUtil.createExcelByString(workbook, 1, "用户汇总", userHeaders, covertUserData(userList));
		return workbook;
	}

	/**
	 * 导出多个sheet的EXCEL表格
	 * 
	 * @return
	 * @throws Exception
	 */
	private XSSFWorkbook testExportExcelByJavaBean() throws Exception {
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 角色数据列表
		List<Role> roleList = getRoleList();
		// 用户数据列表
		List<User> userList = getUserList();
		List<ExcelBean> excel = new ArrayList<ExcelBean>();
		// 设置第一个sheet的标题和对应的字段名
		excel.add(new ExcelBean("序号", null));
		excel.add(new ExcelBean("角色名", "roleName"));
		excel.add(new ExcelBean("备注", "remark"));
		excel.add(new ExcelBean("创建时间", "createTime"));
		List<ExcelBean> excel1 = new ArrayList<ExcelBean>();
		// 设置第二个sheet的标题和对应的字段名
		excel1.add(new ExcelBean("序号", null));
		excel1.add(new ExcelBean("用户名", "userName"));
		excel1.add(new ExcelBean("角色", "role"));
		excel1.add(new ExcelBean("备注", "remark"));
		excel1.add(new ExcelBean("创建时间", "createTime"));
		// 生成多个sheet
		ExcelUtil.createExcelByJavaBean(workbook, Role.class, roleList, 0, excel, "角色汇总");
		ExcelUtil.createExcelByJavaBean(workbook, User.class, userList, 1, excel1, "用户汇总");
		return workbook;
	}

	/**
	 * 生成角色数据列表
	 * 
	 * @return
	 */
	private List<Role> getRoleList() {
		List<Role> roleList = new ArrayList<Role>();
		Role role1 = new Role();
		role1.setRoleName("role1");
		role1.setRemark("role1");
		role1.setCreateTime(formatDate(new Date()));
		Role role2 = new Role();
		role2.setRoleName("role2");
		role2.setRemark("role2");
		role2.setCreateTime(formatDate(new Date()));
		roleList.add(role1);
		roleList.add(role2);
		return roleList;
	}

	/**
	 * 生成用户数据列表
	 * 
	 * @return
	 */
	private List<User> getUserList() {
		List<User> userList = new ArrayList<User>();
		Role role1 = new Role();
		role1.setRoleName("role1");
		role1.setRemark("role1");
		role1.setCreateTime(formatDate(new Date()));
		Role role2 = new Role();
		role2.setRoleName("role2");
		role2.setRemark("role2");
		role2.setCreateTime(formatDate(new Date()));
		User user1 = new User();
		user1.setUserName("user1");
		user1.setRole(role1);
		user1.setRemark("user1");
		user1.setCreateTime(formatDate(new Date()));
		User user2 = new User();
		user2.setUserName("user2");
		user2.setRole(role1);
		user2.setRemark("user2");
		user2.setCreateTime(formatDate(new Date()));
		userList.add(user1);
		userList.add(user2);
		return userList;
	}

	/**
	 * 将List<userList>转换成List<List<String>> 描述:EXCEL表格的列与javaBean的对应关系
	 * 
	 * @param userList
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
	 * 将List<roleList>转换成List<List<String>> 描述:EXCEL表格的列与javaBean的对应关系
	 * 
	 * @param roleList
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
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		File file = new File("D:/testExport.xlsx");
		System.out.println("*****测试生成EXCEL*********");
		ExcelUtil eu = new ExcelUtil();
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
//			XSSFWorkbook workbook = eu.testExportExcelByString();
			XSSFWorkbook workbook = eu.testExportExcelByJavaBean();
			// 原理就是将所有的数据一起写入，然后再关闭输入流
			workbook.write(out);
			workbook.close();
			out.close();
			System.out.println("导出EXCEL成功!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("*****测试读取EXCEL*********");
		FileInputStream in = new FileInputStream(file);
		Map<Integer, List<List<Object>>> mapList = readExcel(in, file.getName());
		for (Integer key : mapList.keySet()) {
			if (key == 0) {
				System.out.println("第一个sheet工作簿中的数据为:");
				for (List<Object> objs : mapList.get(key)) {
					System.out.println(objs.get(0) + "-" + objs.get(1) + "-" + objs.get(2) + "-" + objs.get(3));
				}
			} else if (key == 1) {
				System.out.println("第二个sheet工作簿中的数据为:");
				for (List<Object> objs : mapList.get(key)) {
					System.out.println(objs.get(0) + "-" + objs.get(1) + "-" + objs.get(2) + "-" + objs.get(3) + "-"
							+ objs.get(4));
				}

			}
		}
	}
}
