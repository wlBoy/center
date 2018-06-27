package com.xn.hk.system.dao;

import java.util.List;

import com.xn.hk.common.dao.BaseDao;
import com.xn.hk.system.model.ChannelData;

/**
 * 
 * @Title: ChannelDataDao
 * @Package: com.xn.ad.data.dao
 * @Description: 处理渠道数据的dao层
 * @Company: 杭州讯牛
 * @Author: wanlei
 * @Date: 2018-1-19 下午05:53:33
 */
public interface ChannelDataDao extends BaseDao<ChannelData> {
	/**
	 * 批量插入EXCEL表格数据
	 * 
	 * @param list
	 */
	int insertInfoBatch(List<ChannelData> list);

}
