package com.xn.hk.common.utils.excel;

import java.io.Serializable;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;

/**
 * 
 * @Title: ExcelBean
 * @Package: com.xn.ad.common.utils
 * @Description: excel实体类(用来生成EXCEL时用)
 * @Author: wanlei
 * @Date: 2018-1-20 上午11:19:19
 */
public class ExcelBean implements Serializable {
	private static final long serialVersionUID = 1310196307055720134L;
	/**
	 * 列头（标题）名
	 */
	private String headTextName;
	/**
	 * 对应字段名
	 */
	private String propertyName;
	/**
	 * 合并单元格数
	 */
	private Integer cols;
	/**
	 * EXCEL表格样式
	 */
	private XSSFCellStyle cellStyle;

	public ExcelBean() {

	}

	public ExcelBean(String headTextName, String propertyName) {
		this.headTextName = headTextName;
		this.propertyName = propertyName;
	}

	public ExcelBean(String headTextName, String propertyName, Integer cols) {
		super();
		this.headTextName = headTextName;
		this.propertyName = propertyName;
		this.cols = cols;
	}

	public String getHeadTextName() {
		return headTextName;
	}

	public void setHeadTextName(String headTextName) {
		this.headTextName = headTextName;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Integer getCols() {
		return cols;
	}

	public void setCols(Integer cols) {
		this.cols = cols;
	}

	public XSSFCellStyle getCellStyle() {
		return cellStyle;
	}

	public void setCellStyle(XSSFCellStyle cellStyle) {
		this.cellStyle = cellStyle;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
