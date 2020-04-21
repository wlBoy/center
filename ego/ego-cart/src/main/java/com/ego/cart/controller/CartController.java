package com.ego.cart.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.cart.service.CartService;
import com.ego.commons.pojo.EgoResult;

@Controller
public class CartController {
	@Resource
	private CartService cartServiceImpl;
	/**
	 * 添加购物车
	 * @param id
	 * @return
	 */
	@RequestMapping("cart/add/{id}.html")
	public String addCart(@PathVariable long id,int num,HttpServletRequest request){
		cartServiceImpl.addCart(id, num, request);
		return "cartSuccess";
	}
	
	/**
	 * 显示购物车
	 * @return
	 */
	@RequestMapping("cart/cart.html")
	public String showCart(Model model,HttpServletRequest request){
		model.addAttribute("cartList", cartServiceImpl.showCart(request));
		return "cart";
	}
	/**
	 * 修改商品数量
	 * @param id
	 * @param num
	 * @param request
	 * @return
	 */
	@RequestMapping("cart/update/num/{id}/{num}.action")
	@ResponseBody
	public EgoResult update(@PathVariable long id,@PathVariable int num,HttpServletRequest request){
		return cartServiceImpl.update(id, num, request);
	}
	/**
	 * 删除购物车商品
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping("cart/delete/{id}.action")
	@ResponseBody
	public EgoResult delete(@PathVariable long id,HttpServletRequest req){
		return cartServiceImpl.delete(id, req);
	}
}
