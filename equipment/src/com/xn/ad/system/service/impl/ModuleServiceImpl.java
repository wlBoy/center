package com.xn.ad.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xn.ad.common.dao.BaseDao;
import com.xn.ad.common.enums.ModuleLevel;
import com.xn.ad.common.service.impl.BaseServiceImpl;
import com.xn.ad.system.dao.ModuleDao;
import com.xn.ad.system.model.Module;
import com.xn.ad.system.service.ModuleService;

/**
 * 
 * @ClassName: ModuleServiceImpl
 * @PackageName: com.xn.ad.system.service.impl
 * @Description: 模块管理的service实现层
 * @author wanlei
 * @date 2018年5月11日 下午4:51:04
 */
@Service
public class ModuleServiceImpl extends BaseServiceImpl<Module> implements
		ModuleService {

	@SuppressWarnings("unused")
	private final static Logger log = Logger.getLogger(ModuleServiceImpl.class);
	@Autowired
	private ModuleDao md;

	/**
	 * 实现父类的方法，指定所用的dao
	 */
	public BaseDao<Module> getDao() {
		return md;
	}

	/**
	 * 根据已登录用户的角色查询可访问的所有模块
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 可访问的所有模块列表
	 */
	public List<Module> getRoleModule(Integer roleId) {
		Module model = new Module();
		List<Module> module = new ArrayList<Module>();
		model.setRoleId(roleId);
		model.setModuleLevel(ModuleLevel.ONE.key);
		List<Module> moduleOne = md.getModuleList(model);
		model.setModuleLevel(ModuleLevel.TWO.key);
		List<Module> moduleTwo = md.getModuleList(model);
		// 登记关系配置
		module.addAll(moduleOne);
		for (Module m : module) {
			for (Module cm : moduleTwo) {
				if (cm.getParentId().equals(m.getModuleId())) {
					m.getChildren().add(cm);
				}
			}
		}
		return module;
	}

	/**
	 * 根据已登录用户的角色查询可访问的一级模块
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 可访问的一级模块列表
	 */
	public List<Module> getParentList() {
		Module model = new Module();
		model.setModuleLevel(ModuleLevel.ONE.key);
		return md.getParentList(model);
	}
}
