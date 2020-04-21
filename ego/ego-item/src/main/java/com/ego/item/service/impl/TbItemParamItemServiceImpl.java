package com.ego.item.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemParamItemDubboSerice;
import com.ego.item.pojo.ParamItem;
import com.ego.item.pojo.ParamNode;
import com.ego.item.service.TbItemParamItemService;
import com.ego.pojo.TbItemParamItem;

@Service
public class TbItemParamItemServiceImpl implements TbItemParamItemService {
	@Reference
	private TbItemParamItemDubboSerice tbItemParamItemDubboSericeImpl;
	@Override
	public String showParam(long itemId) {
		TbItemParamItem item = tbItemParamItemDubboSericeImpl.selByItemid(itemId);
		List<ParamItem> list = JsonUtils.jsonToList(item.getParamData(), ParamItem.class);
		System.out.println(list);
		StringBuffer sf = new StringBuffer();
		
		for (ParamItem param : list) {
			sf.append("<table width='500' style='color:gray;'>");
			for (int i = 0 ;i<param.getParams().size();i++) {
				if(i==0){
					sf.append("<tr>");
					sf.append("<td align='right' width='30%'>"+param.getGroup()+"</td>");
					sf.append("<td align='right' width='30%'>"+param.getParams().get(i).getK()+"</td>");
					sf.append("<td>"+param.getParams().get(i).getV()+"</td>");
					sf.append("<tr/>");
				}else{
					sf.append("<tr>");
					sf.append("<td> </td>");
					sf.append("<td align='right'>"+param.getParams().get(i).getK()+"</td>");
					sf.append("<td>"+param.getParams().get(i).getV()+"</td>");
					sf.append("</tr>");
				}
			}
			sf.append("</table>");
			sf.append("<hr style='color:gray;'/>");
		}
		return sf.toString();
	}

}
