package com.ego.manage.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.manage.service.TbContentService;
import com.ego.pojo.TbContent;
import com.ego.redis.dao.JedisDao;

@Service
public class TbContentServiceImpl implements TbContentService {
	@Reference
	private TbContentDubboService tbContentDubboServiceImpl;
	@Resource
	private JedisDao jedisDaoImpl;
	@Value("${redis.bigpic.key}")
	private String key;
	@Override
	public EasyUIDataGrid showContent(long categoryId, int page, int rows) {
		return tbContentDubboServiceImpl.selContentByPage(categoryId, page, rows);
	}

	@Override
	public int save(TbContent content) {
		Date date = new Date();
		content.setCreated(date);
		content.setUpdated(date);
		
		int index = tbContentDubboServiceImpl.insContent(content);
		
		//判断redis中是否有缓存数据
		if(jedisDaoImpl.exists(key)){
			String value = jedisDaoImpl.get(key);
			if(value!=null&&!value.equals("")){
				List<HashMap> list = JsonUtils.jsonToList(value, HashMap.class);
				
				HashMap<String,Object> map = new HashMap<>();
				
				map.put("srcB", content.getPic2());
				map.put("height", 240);
				map.put("alt", "对不起,加载图片失败");
				map.put("width", 670);
				map.put("src", content.getPic());
				map.put("widthB", 550);
				map.put("href", content.getUrl() );
				map.put("heightB", 240);
				
				//保证六个
				if(list.size()==6){
					list.remove(5);
				}
				list.add(0, map);
				
				
				
				
				
				jedisDaoImpl.set(key, JsonUtils.objectToJson(list));
			}
		}
		
		
		return index;
	}
}
