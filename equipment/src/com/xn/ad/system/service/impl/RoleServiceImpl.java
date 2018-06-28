package com.xn.ad.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xn.ad.common.dao.BaseDao;
import com.xn.ad.common.service.impl.BaseServiceImpl;
import com.xn.ad.system.dao.RoleDao;
import com.xn.ad.system.model.Role;
import com.xn.ad.system.service.RoleService;

/**
 * 
 * @ClassName: RoleServiceImpl
 * @PackageName: com.xn.ad.system.service.impl
 * @Description: 角色管理的service实现层
 * @author wanlei
 * @date 2018年5月11日 下午4:52:02
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements
		RoleService {
	@Autowired
	private RoleDao rd;

	/**
	 * 实现父类的方法，指定所用的dao
	 */
	public BaseDao<Role> getDao() {
		return rd;
	}

}
