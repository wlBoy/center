package com.xn.ad.equip.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xn.ad.common.dao.BaseDao;
import com.xn.ad.common.service.impl.BaseServiceImpl;
import com.xn.ad.equip.dao.EquipDao;
import com.xn.ad.equip.model.Equip;
import com.xn.ad.equip.service.EquipService;
/**
 * 
 * @ClassName: EquipServiceImpl 
 * @PackageName: com.xn.ad.equip.service.impl
 * @Description: 设备的service实现层
 * @author wanlei
 * @date 2018年5月11日 下午5:34:49
 */
@Service
public class EquipServiceImpl extends BaseServiceImpl<Equip> implements
		EquipService {
	@Autowired
	private EquipDao ed;

	/**
	 * 实现父类的方法，指定所用的dao
	 */
	public BaseDao<Equip> getDao() {
		return ed;
	}

}
