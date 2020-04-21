package com.kl.ar.common.constant;

/**
 * 
 * @package:com.kl.ar.common.constant
 * @Description: html表格类型枚举类
 * @author: wanlei
 * @date: 2019年12月21日下午2:06:04
 */
public enum HtmlType {

	ADMIN_SUGGEST("amdin_suggest", 3, "管理建议"), PRODUCT_SUGGEST("product_suggest", 4,
			"产品建议"), PROJECT_SUGGEST("project_suggest", 3, "项目品建议");

	/**
	 * id
	 */
	private String id;
	/**
	 * 列数
	 */
	private int cols;
	/**
	 * 描述
	 */
	private String desc;

	private HtmlType(String id, int cols, String desc) {
		this.id = id;
		this.cols = cols;
		this.desc = desc;
	}

	public String getId() {
		return id;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
