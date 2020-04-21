package com.ego.dubbo.service;

import com.ego.pojo.TbItemDesc;

public interface TbItemDescDubboService {
	/**
	 * 新增
	 * @param itemDesc
	 * @return
	 */
	int insDesc(TbItemDesc itemDesc);
	/**
	 * 根据主键查询商品描述对象
	 * @param itemid
	 * @return
	 */
	TbItemDesc selByItemid(long itemid);
}
