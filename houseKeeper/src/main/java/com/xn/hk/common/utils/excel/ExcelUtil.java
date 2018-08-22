package com.xn.hk.common.utils.excel;

import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * @Title: ExcelUtil
 * @Package: com.xn.ad.common.utils
 * @Description: 操作EXCEL导入和导出的工具类
 * @Author: wanlei
 * @Date: 2018-1-20 上午11:28:00
 */
public class ExcelUtil {
	private final static String excel2003L = ".xls"; // 2003- 版本的excel
	private final static String excel2007U = ".xlsx"; // 2007+ 版本的excel

	/**
	 * 将EXCEL表格中的数据转换为list集合
	 * 
	 * @param in
	 *            EXCEL表格文件的输入流
	 * @param fileName
	 *            EXCEL表格文件名
	 * @return EXCEL表格所有的数据集合
	 * @throws Exception
	 */
	public static List<List<Object>> getBankListByExcel(InputStream in, String fileName) throws Exception {
		List<List<Object>> list = null;
		// 创建Excel工作薄
		Workbook work = getWorkbook(in, fileName);
		// 拿到对应的FormulaEvaluator对象(计算Excel表格中公式值的类)
		FormulaEvaluator formulaEvaluator = getFormulaEvaluator(fileName, work);
		if (null == work) {
			throw new Exception("创建Excel工作薄为空！");
		}
		Sheet sheet = null;
		Row row = null;
		list = new ArrayList<List<Object>>();
		// 遍历当前sheet中的所有行,从第一行到最后一首行,索引从0开始
		for (int i = 0; i < work.getNumberOfSheets(); i++) {
			sheet = work.getSheetAt(i);
			if (sheet == null) {
				continue;
			}
			// 遍历当前sheet中的所有行,从第一行到最后一首行,索引从0开始
			for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
				row = sheet.getRow(j);
				if (row == null || row.getFirstCellNum() == j) {
					continue;
				}
				// 遍历所有的列,从第一行到最后一首行,索引从0开始
				List<Object> li = new ArrayList<Object>();
				for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
					li.add(getCellValue(row.getCell(y), formulaEvaluator));
				}
				list.add(li);
			}
		}
		return list;
	}

	/**
	 * 判断传入的文件名是否为EXCEL格式的文件并获取相应的FormulaEvaluator对象(计算Excel表格中公式值的类)
	 * 
	 * @param fileName
	 * @param work
	 * @return
	 * @throws Exception
	 */
	private static FormulaEvaluator getFormulaEvaluator(String fileName, Workbook work) throws Exception {
		// 计算Excel表格中公式值的类
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		FormulaEvaluator formulaEvaluator = null;
		if (excel2003L.equals(fileType)) {
			// 2003-
			formulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) work);
		} else if (excel2007U.equals(fileType)) {
			// 2007+
			formulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) work);
		} else {
			throw new Exception("解析的文件格式有误！");
		}
		return formulaEvaluator;
	}

	/**
	 * 判断传入的文件名是否为EXCEL格式的文件并获取相应的Workbook(Excel工作薄)对象
	 * 
	 * @param inStr
	 *            EXCEL表格文件的输入流
	 * @param fileName
	 *            EXCEL表格文件名
	 * @return EXCEL表格所有的数据集合
	 * @throws Exception
	 */
	public static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
		Workbook wb = null;
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		if (excel2003L.equals(fileType)) {
			// 2003-
			wb = new HSSFWorkbook(inStr);
		} else if (excel2007U.equals(fileType)) {
			// 2007+
			wb = new XSSFWorkbook(inStr);
		} else {
			throw new Exception("解析的文件格式有误！");
		}
		return wb;
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
	public static Object getCellValue(Cell cell, FormulaEvaluator formulaEvaluator) {
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

	/*-------------------------------------------------分割线,以上是将EXCEL数据导出,以下是生成EXCEL表格-------------------------------------------*/
	/**
	 * 多列头创建EXCEL
	 * 
	 * @param sheetName
	 *            工作簿名称
	 * @param clazz
	 *            数据源model类型
	 * @param objs
	 *            excel标题列以及对应model字段名
	 * @param map
	 *            标题列行数以及cell字体样式
	 * @return
	 * @throws Exception
	 */
	public static <T> XSSFWorkbook createExcelFile(Class<T> clazz, List<T> objs, Map<Integer, List<ExcelBean>> map,
			String sheetName) throws Exception {
		// 创建新的Excel 工作簿
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 在Excel工作簿中建一工作表，其名为缺省值, 也可以指定Sheet名称
		XSSFSheet sheet = workbook.createSheet(sheetName);
		// 以下为excel的字体样式以及excel的标题与内容的创建,下面会具体分析
		createFont(workbook);// 字体样式
		createTableHeader(sheet, map);// 创建标题（头）
		createTableRows(sheet, map, objs, clazz);// 创建内容
		return workbook;
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
	public static void createFont(XSSFWorkbook workbook) {
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
	 * @param sheet
	 *            ECXEL表格对象
	 * @param map
	 *            已经封装好的map数据(即要生成EXCEL中的内容)
	 */
	public static final void createTableHeader(XSSFSheet sheet, Map<Integer, List<ExcelBean>> map) {
		int startIndex = 0;// cell起始位置
		int endIndex = 0;// cell终止位置

		for (Map.Entry<Integer, List<ExcelBean>> entry : map.entrySet()) {
			XSSFRow row = sheet.createRow(entry.getKey());
			List<ExcelBean> excels = entry.getValue();
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
	}

	/**
	 * 根据传入的map数据(头信息)和list集合的内容数据和类的字节码生成EXCEL表格
	 * 
	 * @param sheet
	 *            ECXEL表格对象
	 * @param map
	 *            已经封装好的头信息
	 * @param objs
	 *            要封装的数据内容
	 * @param clazz
	 *            要封装的Javabean字节码
	 * @throws Exception
	 */
	public static <T> void createTableRows(XSSFSheet sheet, Map<Integer, List<ExcelBean>> map, List<T> objs,
			Class<T> clazz) throws Exception {
		int rowindex = map.size();
		int maxKey = 0;
		List<ExcelBean> ems = new ArrayList<ExcelBean>();
		for (Map.Entry<Integer, List<ExcelBean>> entry : map.entrySet()) {
			if (entry.getKey() > maxKey) {
				maxKey = entry.getKey();
			}
		}
		ems = map.get(maxKey);
		List<Integer> widths = new ArrayList<Integer>(ems.size());
		for (Object obj : objs) {
			// 创建行
			XSSFRow row = sheet.createRow(rowindex);
			for (int i = 0; i < ems.size(); i++) {
				ExcelBean em = (ExcelBean) ems.get(i);
				// 获得get方法
				PropertyDescriptor pd = new PropertyDescriptor(em.getPropertyName(), clazz);
				Method getMethod = pd.getReadMethod();
				Object rtn = getMethod.invoke(obj);
				String value = "";
				// 如果是日期类型 进行 转换
				if (rtn != null) {
					if (rtn instanceof Date) {
						value = dateToString((Date) rtn, "yyyy-MM-dd");
					} else if (rtn instanceof BigDecimal) {
						NumberFormat nf = new DecimalFormat("#,##0.00");
						value = nf.format((BigDecimal) rtn).toString();
					} else if ((rtn instanceof Integer) && (Integer.valueOf(rtn.toString()) < 0)) {
						value = "--";
					} else {
						value = rtn.toString();
					}
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
	 * 把日期时间格式化为指定格式
	 * 
	 * @param dt
	 *            java.util.Date
	 * @return 格式化后的日期时间字符串
	 */
	public static String dateToString(Date date, String pattern) {
		String newDate = "";
		Locale locale = Locale.CHINESE;
		SimpleDateFormat dateStyle = new SimpleDateFormat(pattern, locale);
		newDate = dateStyle.format(date);
		return newDate;
	}

}
