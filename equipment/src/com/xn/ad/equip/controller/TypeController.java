package com.xn.ad.equip.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xn.ad.common.utils.JsonResult;
import com.xn.ad.equip.model.Type;
import com.xn.ad.equip.service.TypeService;

/**
 * 
 * @ClassName: TypeController
 * @PackageName: com.xn.ad.equip.controller
 * @Description: 设备类别的控制层
 * @author wanlei
 * @date 2018年5月11日 下午5:36:09
 */
@Controller
@RequestMapping(value = "/equip/type")
public class TypeController {
	/**
	 * 记录日志
	 */
	private static final Logger log = Logger.getLogger(TypeController.class);
	@Autowired
	private TypeService ts;

	/**
	 * 设备类别列表
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getTableData")
	public Map<String, Object> getTableData(int pageSize, int pageNumber,
			String typeName) {
		/* 所需参数 */
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("start", (pageNumber - 1) * pageSize);
		param.put("size", pageSize);
		param.put("typeName", typeName);
		return ts.pageList(param);
	}

	/**
	 * 显示编辑页面
	 * 
	 * @param typeId
	 * @return
	 */
	@RequestMapping(value = "/showedit")
	public ModelAndView showedit(Integer typeId) {
		ModelAndView mv = new ModelAndView("/equip/type/type-edit");
		Type type = ts.findById(typeId);
		mv.addObject("t", type);
		log.info("该设备类别信息为:" + type);
		return mv;
	}

	/**
	 * 删除设备类别
	 * 
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete")
	public JsonResult<String> delete(String ids) {
		Object[] idArry = ids.split(",");
		int r = ts.delete(idArry);
		if (r > 0) {
			return new JsonResult<String>(0, "删除成功!");
		}
		return new JsonResult<String>(-1, "删除失败!");
	}

	/**
	 * 添加设备类别
	 * 
	 * @param type
	 * @return
	 * @RequestBody 用于接受前台application/json; charset=utf-8格式的json数据然后注入到bean中
	 */
	@ResponseBody
	@RequestMapping(value = "/add")
	public JsonResult<String> add(@RequestBody Type type) {
		Type t = ts.findByName(type.getTypeName());
		if (t != null) {
			return new JsonResult<String>(-1, "设备类别名重复，请重新填写");
		}
		int r = ts.add(type);
		if (r > 0) {
			return new JsonResult<String>(0, "添加设备类别成功!");
		}
		return new JsonResult<String>(-1, "添加设备类别失败!");
	}

	/**
	 * 更改设备类别
	 * 
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update")
	public JsonResult<String> update(@RequestBody Type type) {
		int r = ts.update(type);
		if (r > 0) {
			return new JsonResult<String>(0, "更新设备类别成功!");
		}
		return new JsonResult<String>(-1, "修改设备类别失败!");
	}

}
