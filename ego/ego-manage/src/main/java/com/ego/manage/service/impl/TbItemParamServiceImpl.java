package com.ego.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.manage.pojo.TbItemParamChild;
import com.ego.manage.service.TbItemParamService;
import com.ego.pojo.TbItemParam;

@Service
public class TbItemParamServiceImpl implements TbItemParamService {
	@Reference
	private TbItemParamDubboService tbItemParamDubboServiceImpl;
	@Reference
	private TbItemCatDubboService tbItemCatDubboServiceImpl;
	@Override
	public EasyUIDataGrid showPage(int page, int rows) {
		EasyUIDataGrid datagrid = tbItemParamDubboServiceImpl.showPage(page, rows);
		List<TbItemParam> list = (List<TbItemParam>) datagrid.getRows();
		List<TbItemParamChild> listChild = new ArrayList<>();
		for (TbItemParam param : list) {
			TbItemParamChild child = new TbItemParamChild();
			
			child.setCreated(param.getCreated());
			child.setId(param.getId());
			child.setItemCatId(param.getItemCatId());
			child.setParamData(param.getParamData());
			child.setUpdated(param.getUpdated());
			child.setItemCatName(tbItemCatDubboServiceImpl.selById(child.getItemCatId()).getName());
			
			
			listChild.add(child);
		}
		datagrid.setRows(listChild);
		return datagrid;
	}
	@Override
	public int delete(String ids) throws Exception {
		return tbItemParamDubboServiceImpl.delByIds(ids);
	}
	@Override
	public EgoResult showParam(long catId) {
		EgoResult er = new EgoResult();
		TbItemParam param = tbItemParamDubboServiceImpl.selByCatid(catId);
		if(param!=null){
			er.setStatus(200);
			er.setData(param);
		}
		return er;
	}
	@Override
	public EgoResult save(TbItemParam param) {
		Date date = new Date();
		param.setCreated(date);
		param.setUpdated(date);
		int index = tbItemParamDubboServiceImpl.insParam(param);
		EgoResult er = new EgoResult();
		if(index>0){
			er.setStatus(200);
		}
		return er;
	}

}
