package com.kl.ar.common.util;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * @package:com.kl.ar.common.util
 * @Description:  EXCEL导入和导出的操作工具类
 * @author: wanlei
 * @date: 2019年12月21日下午2:57:36
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
	public static void createExcel(XSSFWorkbook xssfWorkbook, int sheetNum, String sheetName, String[] headers,
			List<List<String>> list) throws Exception {
		// 生成一个表格
		XSSFSheet sheet = xssfWorkbook.createSheet();
		xssfWorkbook.setSheetName(sheetNum, sheetName);
		// 设置表格默认列宽度为32个字节
		sheet.setDefaultColumnWidth((short) 32);
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
					cell.setCellValue(str);
					cellIndex++;
				}
				index++;
			}
		}
	}

}
