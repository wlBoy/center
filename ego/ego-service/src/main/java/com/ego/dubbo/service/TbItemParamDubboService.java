package com.ego.dubbo.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItemParam;

public interface TbItemParamDubboService {
	/**
	 * 分页查询数据,
	 * @param page
	 * @param rows
	 * @return 包含:当前也显示数据和总条数
	 */
	EasyUIDataGrid showPage(int page,int rows);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int delByIds(String ids) throws Exception;
	/**
	 * 根据类目id查询参数模板
	 * @param catId
	 * @return
	 */
	TbItemParam selByCatid(long catId);
	/**
	 * 新增,支持主键自增
	 * @param param
	 * @return
	 */
	int insParam(TbItemParam param);
}
