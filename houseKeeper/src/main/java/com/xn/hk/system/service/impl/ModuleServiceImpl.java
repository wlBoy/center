package com.xn.hk.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xn.hk.common.constant.Constant;
import com.xn.hk.common.constant.StatusEnum;
import com.xn.hk.common.dao.BaseDao;
import com.xn.hk.common.service.impl.BaseServiceImpl;
import com.xn.hk.common.utils.page.BasePage;
import com.xn.hk.system.dao.ModuleDao;
import com.xn.hk.system.model.Module;
import com.xn.hk.system.service.ModuleService;

/**
 * 
 * @Title: UserServiceImpl
 * @Package: com.xn.ad.system.service.impl
 * @Description: 处理模块的service业务逻辑实现层
 * @Author: wanlei
 * @Date: 2017-11-28 下午03:30:15
 */
@Service
public class ModuleServiceImpl extends BaseServiceImpl<Module> implements ModuleService {
	@Autowired
	private ModuleDao moduleDao;

	/**
	 * 实现父类的方法，指定所用的dao
	 */
	public BaseDao<Module> getDao() {
		return moduleDao;
	}

	/**
	 * 实现模块分页 这个要查询父级模块信息，所以就没用通用的分页方法，重载了一下
	 * 
	 * @param pages
	 *            分页对象
	 * @return 模块列表
	 */
	public List<Module> pageList(BasePage<Module> pages) {
		// 查询分页总记录数
		pages.setCount(moduleDao.pageCount(pages));
		// 分页查询，返回查询结果list
		List<Module> modules = moduleDao.pageList(pages);
		for (Module m : modules) {
			// 一级模块的parentId等于0，排除一级模块
			if (m.getParentId().intValue() != Constant.ZERO_VALUE) {
				// 查询其父级模块
				Module module = moduleDao.findById(m.getParentId());
				if (module != null) {
					// 该父模块存在，将父级模块名称封装到分页列表对象中去
					m.setParentName(module.getModuleName());
				}
			}
		}
		return modules;
	}

	/**
	 * 切换模块状态
	 * 
	 * @param moduleId
	 *            模块ID
	 * @return 返回影响条数
	 */
	public int changeState(Integer moduleId) {
		Integer moduleState = moduleDao.findById(moduleId).getIsAllowed();
		// 拿到当前模块的状态，判断调用不同的方法切换模块状态
		if (moduleState.intValue() == StatusEnum.ACTIVE.getCode().intValue()) {
			return moduleDao.changeState(StatusEnum.LOCKED.getCode(), moduleId);
		} else {
			return moduleDao.changeState(StatusEnum.ACTIVE.getCode(), moduleId);
		}
	}

	/**
	 * 根据模块级别查询该级别的模块列表
	 * 
	 * @param level
	 *            模块级别
	 * @return 模块列表
	 */
	public List<Module> findModuleByLevel(Integer level) {
		return moduleDao.findModuleByLevel(level);
	}

	/**
	 * 根据已登录用户的角色和模块级别查询可访问的模块列表
	 * 
	 * @param level
	 *            模块级别
	 * @param roleId
	 *            用户的角色
	 * @return 模块列表
	 */
	public List<Module> findModuleByRoleId(Integer level, Integer roleId) {
		return moduleDao.findModuleByRoleId(level, roleId);
	}

}
