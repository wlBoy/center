package com.ego.item.service;

import com.ego.dubbo.service.TbItemDescDubboService;

public interface TbItemDescService {
	/**
	 * 根据商品id显示商品描述
	 * @param itemId
	 * @return
	 */
	String showDesc(long itemId);
}
