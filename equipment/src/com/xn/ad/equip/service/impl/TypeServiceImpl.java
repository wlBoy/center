package com.xn.ad.equip.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xn.ad.common.dao.BaseDao;
import com.xn.ad.common.service.impl.BaseServiceImpl;
import com.xn.ad.equip.dao.TypeDao;
import com.xn.ad.equip.model.Type;
import com.xn.ad.equip.service.TypeService;

/**
 * 
 * @ClassName: TypeServiceImpl
 * @PackageName: com.xn.ad.equip.service.impl
 * @Description: 设备类别的service实现层
 * @author wanlei
 * @date 2018年5月11日 下午5:32:10
 */
@Service
public class TypeServiceImpl extends BaseServiceImpl<Type> implements
		TypeService {
	@Autowired
	private TypeDao td;

	/**
	 * 实现父类的方法，指定所用的dao
	 */
	public BaseDao<Type> getDao() {
		return td;
	}

}
