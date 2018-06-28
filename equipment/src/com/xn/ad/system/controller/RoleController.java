package com.xn.ad.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.xn.ad.common.utils.JsonResult;
import com.xn.ad.common.utils.ZtreeNode;
import com.xn.ad.system.model.Module;
import com.xn.ad.system.model.Role;
import com.xn.ad.system.model.RoleModule;
import com.xn.ad.system.service.ModuleService;
import com.xn.ad.system.service.RoleModuleService;
import com.xn.ad.system.service.RoleService;

/**
 * 
 * @ClassName: RoleController
 * @PackageName: com.xn.ad.system.controller
 * @Description: 角色管理的控制层
 * @author wanlei
 * @date 2018年5月11日 下午4:31:28
 */
@Controller
@RequestMapping(value = "/system/role")
public class RoleController {
	@Autowired
	private RoleService rs;
	@Autowired
	private ModuleService ms;
	@Autowired
	private RoleModuleService rms;

	/**
	 * 角色列表
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getTableData")
	public Map<String, Object> getTableData(int pageSize, int pageNumber,
			String roleName) {
		/* 所需参数 */
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("start", (pageNumber - 1) * pageSize);
		param.put("size", pageSize);
		param.put("roleName", roleName);
		return rs.pageList(param);
	}

	/**
	 * 显示编辑页面
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/showedit")
	public ModelAndView showedit(Integer roleId) {
		ModelAndView mv = new ModelAndView("/system/role/role-edit");
		Role role = rs.findById(roleId);
		List<ZtreeNode> nodeList = new ArrayList<ZtreeNode>();
		List<Module> modules = ms.findAll();// 所有模块
		Map<Integer, Integer> roleModules = rms.getMapRoleModule(roleId);// 角色所关联的模块
		ZtreeNode node = null;
		// 树形插件使用
		for (Module module : modules) {
			node = new ZtreeNode(module.getModuleId().toString(), module
					.getParentId().toString(), module.getModuleName(),
					roleModules.containsKey(module.getModuleId()));
			nodeList.add(node);
		}
		mv.addObject("nodeList", JSON.toJSONString(nodeList));
		mv.addObject("role", role);
		return mv;
	}

	/**
	 * 显示角色模块页面(添加操作)
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/showpower")
	public ModelAndView showpower() {
		ModelAndView mv = new ModelAndView("/system/role/role-edit");
		List<ZtreeNode> nodeList = new ArrayList<ZtreeNode>();
		List<Module> modules = ms.findAll();// 所有模块
		ZtreeNode node = null;
		for (Module module : modules) {
			node = new ZtreeNode(module.getModuleId().toString(), module
					.getParentId().toString(), module.getModuleName());
			nodeList.add(node);
		}
		mv.addObject("nodeList", JSON.toJSONString(nodeList));
		return mv;
	}

	/**
	 * 删除
	 * 
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete")
	public JsonResult<String> delete(String ids) {
		Object[] idArry = ids.split(",");
		int r = rs.delete(idArry);
		if (r > 0) {
			return new JsonResult<String>(0, "删除成功!");
		}
		return new JsonResult<String>(-1, "删除失败!");
	}

	/**
	 * 添加角色
	 * 
	 * @param user
	 * @return
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/add")
	public JsonResult<String> add(Role role, HttpServletRequest request) {
		String[] ids = request.getParameterValues("ids[]");// 模块ID数组
		if (ids != null) {
			Role role2 = rs.findByName(role.getRoleName());
			if (role2 != null) {
				return new JsonResult<String>(-1, "角色名称重复，请重新填写");
			}
			int r = rs.add(role);// 先添加角色
			Role role3 = rs.findByName(role.getRoleName());// 查找角色
			RoleModule roleModule = new RoleModule();
			for (String strid : ids) {
				roleModule.setRole_id(role3.getRoleId());// 得到添加的角色的ID
				roleModule.setModule_id(Integer.parseInt(strid));// 模块id
				rms.add(roleModule);// 添加角色模块映射
			}
			if (r > 0) {
				return new JsonResult<String>(0, "添加角色成功!");
			}
			return new JsonResult<String>(-1, "添加角色失败!");
		}
		return new JsonResult<String>(-1, "没有填写权限!");
	}

	/**
	 * 更改角色
	 * 
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update")
	public JsonResult<String> update(Role role, HttpServletRequest request) {
		Map<Integer, Integer> roleModules = rms.getMapRoleModule(role
				.getRoleId());// 角色所关联的模块
		String[] ids = request.getParameterValues("ids[]");// 模块ID数组
		if (!roleModules.isEmpty()) {
			for (Integer id : roleModules.values()) {
				rms.delete(id); // 取消授权
			}
		}
		if (ids != null) {
			int r = rs.update(role);
			Role role3 = rs.findByName(role.getRoleName());// 查找角色
			RoleModule roleModule = new RoleModule();
			for (String strid : ids) {
				roleModule.setRole_id(role3.getRoleId());// 得到添加的角色的ID
				roleModule.setModule_id(Integer.parseInt(strid));// 模块id
				rms.add(roleModule);// 添加角色模块映射
			}
			if (r > 0) {
				return new JsonResult<String>(0, "更新角色成功!");
			}
			return new JsonResult<String>(-1, "修改角色失败!");
		}
		return new JsonResult<String>(-1, "没有填写权限!");
	}

}
