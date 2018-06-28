package com.xn.ad.equip.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xn.ad.common.utils.JsonResult;
import com.xn.ad.equip.model.Equip;
import com.xn.ad.equip.model.Type;
import com.xn.ad.equip.service.EquipService;
import com.xn.ad.equip.service.TypeService;
import com.xn.ad.system.model.User;
/**
 * 
 * @ClassName: EquipController 
 * @PackageName: com.xn.ad.equip.controller
 * @Description: 设备的控制层
 * @author wanlei
 * @date 2018年5月11日 下午5:37:38
 */
@Controller
@RequestMapping(value = "/equip/equip")
public class EquipController {
	/**
	 * 记录日志
	 */
	private static final Logger log = Logger.getLogger(EquipController.class);
	@Autowired
	private EquipService es;
	@Autowired
	private TypeService ts;
	/**
	 * 设备列表
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getTableData")
	public Map<String, Object> getTableData(int pageSize, int pageNumber,
			String equipName) {
		/* 所需参数 */
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("start", (pageNumber - 1) * pageSize);
		param.put("size", pageSize);
		param.put("equipName", equipName);
		return es.pageList(param);
	}

	/**
	 * 显示编辑页面
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/showedit")
	public ModelAndView showedit(Integer equipId) {
		ModelAndView mv = new ModelAndView("/equip/equip/equip-edit");
		List<Type> types = ts.findAll();
		Equip equip = es.findById(equipId);
		mv.addObject("types", types);
		mv.addObject("e", equip);
		log.info("该设备信息为:" + equip);
		return mv;
	}

	/**
	 * 展示设备类型（添加操作要用）
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/showtype")
	public ModelAndView showtype() {
		ModelAndView mv = new ModelAndView("/equip/equip/equip-edit");
		List<Type> types = ts.findAll();
		mv.addObject("types", types);
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
		int r = es.delete(idArry);
		if (r > 0) {
			return new JsonResult<String>(0, "删除成功!");
		}
		return new JsonResult<String>(-1, "删除失败!");
	}

	/**
	 * 添加设备
	 * 
	 * @param equip
	 * @return
	 * @RequestBody 用于接受前台application/json; charset=utf-8格式的json数据然后注入到bean中
	 */
	@ResponseBody
	@RequestMapping(value = "/add")
	public JsonResult<String> add(@RequestBody Equip equip,HttpSession session) {
		Equip e = es.findByName(equip.getEquipName());
		if (e != null) {
			return new JsonResult<String>(-1, "设备名重复，请重新填写");
		}
		// 从session中拿出当前用户信息
		User user = (User)session.getAttribute("user");
		equip.setAddPeople(user.getUserName());
		int r = es.add(equip);
		if (r > 0) {
			return new JsonResult<String>(0, "添加设备成功!");
		}
		return new JsonResult<String>(-1, "添加设备失败!");
	}

	/**
	 * 更改设备
	 * 
	 * @param equip
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update")
	public JsonResult<String> update(@RequestBody Equip equip) {
		int r = es.update(equip);
		if (r > 0) {
			return new JsonResult<String>(0, "更新设备成功!");
		}
		return new JsonResult<String>(-1, "修改设备失败!");
	}

}
