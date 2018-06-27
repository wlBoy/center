package com.xn.hk.common.utils.page;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @Title: BasePage
 * @Package: com.xn.ad.common.utils
 * @Description: 处理分页的实体类,用泛型可以做成任意对象的分页
 * @Company: 杭州讯牛
 * @Author: wanlei
 * @Date: 2017-11-30 下午05:16:12
 */
public class BasePage<T> implements Serializable {
	private static final long serialVersionUID = -6757878885165506333L;
	/**
	 * 当前页码
	 */
	private Integer pageNum;
	/**
	 * 每页显示的条数
	 */
	private Integer size;
	/**
	 * 开始索引
	 */
	private Integer start;
	/**
	 * 结束索引
	 */
	private Integer end;
	/**
	 * 总记录数
	 */
	private Integer count;
	/**
	 * 总页数
	 */
	private Integer pages;
	/**
	 * 分页后的对象list列表
	 */
	private List<T> list;
	/**
	 * 分页的对象
	 */
	private T bean;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public T getBean() {
		return bean;
	}

	public void setBean(T bean) {
		this.bean = bean;
	}

	public Integer getPageNum() {
		// 页码默认为1
		if (pageNum == null) {
			pageNum = 1;
		}
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getSize() {
		// 每页显示的条数默认为5
		if (size == null) {
			size = 5;
		}
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getStart() {
		// 计算开始位置
		// oracle数据库:start = (this.getPageNum() - 1) * getSize() + 1;
		// mysql数据库如下:
		start = (this.getPageNum() - 1) * getSize();
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getEnd() {
		// 计算末尾位置
		end = getSize() * getPageNum();
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getPages() {
		// 计算总页数
		pages = (int) Math.ceil(1.0 * getCount() / getSize());
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
