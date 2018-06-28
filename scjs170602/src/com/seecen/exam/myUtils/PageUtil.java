package com.seecen.exam.myUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 处理分页的实体工具类,用泛型可以做成任意对象的分页
 * 
 * @author wanlei
 *
 */
public class PageUtil<T> implements Serializable{
	private static final long serialVersionUID = 1L;
	private int pageNum;// 当前页码
	private int size;// 每页显示的条数
	private int start;// 查询的开始位置
	private int end;// 查询的尾部位置
	private int count;// 总记录数
	private int pages;// 总页数

	private List<T> list;// 分页后的对象集合
	private T bean;//分页的对象

	private String[] cha;//符号数组
	
	

	public String[] getCha() {
		return cha;
	}

	public void setCha(String[] cha) {
		this.cha = cha;
	}

	public T getBean() {
		return bean;
	}

	public void setBean(T bean) {
		this.bean = bean;
	}

	public int getPageNum() {
		// 页码默认为1
		if (pageNum == 0) {
			pageNum = 1;
		}
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getSize() {
		// 每页显示的条数默认为5
		if (size == 0) {
			size = 5;
		}
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getStart() {
		//计算开始位置
		start = (this.getPageNum() - 1) * getSize() + 1;
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		//计算末尾位置
		end = getSize() * getPageNum();
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPages() {
		// 计算总页数
		pages = (int) Math.ceil(1.0 * getCount() / getSize());
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
