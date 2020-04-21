package com.ego.cart.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;

public interface CartService {
	/**
	 * 加入购物车
	 * @param id
	 * @param num
	 */
	void addCart(long id,int num,HttpServletRequest request);
	/**
	 * 显示购物车
	 * @return
	 */
	List<TbItemChild> showCart(HttpServletRequest request);
	/**
	 * 根据id修改数量
	 * @param id
	 * @param num
	 * @return
	 */
	EgoResult update(long id,int num,HttpServletRequest request) ;
	/**
	 * 删除购物车商品
	 * @param id
	 * @param req
	 * @return
	 */
	EgoResult delete(long id,HttpServletRequest req);
}
