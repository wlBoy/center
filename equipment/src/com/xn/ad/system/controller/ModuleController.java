package com.xn.ad.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xn.ad.common.utils.JsonResult;
import com.xn.ad.system.model.Module;
import com.xn.ad.system.model.User;
import com.xn.ad.system.service.ModuleService;

/**
 * 
 * @ClassName: ModuleController
 * @PackageName: com.xn.ad.system.controller
 * @Description: 模块管理的控制层
 * @author wanlei
 * @date 2018年5月11日 下午4:39:42
 */
@Controller
@RequestMapping("/system/module")
public class ModuleController {
	/**
	 * 记录日志
	 */
	private static Logger log = Logger.getLogger(ModuleController.class);

	@Autowired
	private ModuleService ms;

	/**
	 * 获取用户的权限列表
	 * 
	 * @param module
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/menuList")
	public ModelAndView getMenuList(HttpServletRequest request)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("user");
		if (u == null || u.getRoleId() == null) {
			mv.setViewName("redirect:/manager/login.jsp");
			log.info("session中的用户过期了!");
		} else {
			List<Module> modules = null;
			modules = ms.getRoleModule(u.getRoleId());
			mv.addObject("menuList", modules);
			mv.setViewName("/index");
		}
		return mv;
	}

	/**
	 * 模块列表
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getTableData")
	public Map<String, Object> getTableData(int pageSize, int pageNumber,
			String moduleName) {
		/* 所需参数 */
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("start", (pageNumber - 1) * pageSize);
		param.put("size", pageSize);
		param.put("moduleName", moduleName);
		return ms.pageList(param);
	}

	/**
	 * 显示编辑页面
	 * 
	 * @param moduleId
	 * @return
	 */
	@RequestMapping(value = "/showedit")
	public ModelAndView showedit(Integer moduleId) {
		ModelAndView mv = new ModelAndView("/system/module/module-edit");
		Module module = ms.findById(moduleId);
		List<Module> modules = ms.getParentList();
		mv.addObject("modules", modules);
		mv.addObject("module", module);
		return mv;
	}

	/**
	 * 展示父级模块（添加操作要用）
	 * 
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/showparent")
	public ModelAndView showparent() {
		ModelAndView mv = new ModelAndView("/system/module/module-edit");
		List<Module> modules = ms.getParentList();
		mv.addObject("modules", modules);
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
		int r = ms.delete(idArry);
		if (r > 0) {
			return new JsonResult<String>(0, "删除成功!");
		}
		return new JsonResult<String>(-1, "删除失败!");
	}

	/**
	 * 添加模块
	 * 
	 * @param user
	 * @return
	 * @RequestBody 用于接受前台application/json; charset=utf-8格式的json数据然后注入到bean中
	 */
	@ResponseBody
	@RequestMapping(value = "/add")
	public JsonResult<String> add(@RequestBody Module module) {
		Module module2 = ms.findByName(module.getModuleName());
		if (module2 != null) {
			return new JsonResult<String>(-1, "模块名称重复，请重新填写");
		}
		int r = ms.add(module);
		if (r > 0) {
			return new JsonResult<String>(0, "添加模块成功!");
		}
		return new JsonResult<String>(-1, "添加模块失败!");
	}

	/**
	 * 更改角色
	 * 
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update")
	public JsonResult<String> update(@RequestBody Module module) {
		int r = ms.update(module);
		if (r > 0) {
			return new JsonResult<String>(0, "更新模块成功!");
		}
		return new JsonResult<String>(-1, "修改模块失败!");
	}

}
