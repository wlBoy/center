package com.xn.ad.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xn.ad.common.utils.JsonResult;
import com.xn.ad.system.model.Role;
import com.xn.ad.system.model.User;
import com.xn.ad.system.service.RoleService;
import com.xn.ad.system.service.UserService;

/**
 * 
 * @ClassName: UserController
 * @PackageName: com.xn.ad.system.controller
 * @Description: 用户管理的控制层
 * @author wanlei
 * @date 2018年5月11日 下午4:30:58
 */
@Controller
@RequestMapping(value = "/system/user")
public class UserController {
	/**
	 * 记录日志
	 */
	private static final Logger log = Logger.getLogger(UserController.class);
	@Autowired
	private UserService us;
	@Autowired
	private RoleService rs;

	/**
	 * 账户登录
	 * 
	 * @param user
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/login")
	public JsonResult<String> login(User user, HttpServletRequest request) {
		User u = us.login(user);
		if (u != null) {
			// 将登录的用户放入session中
			request.getSession().setAttribute("user", u);
			log.info("用户\"" + u.getUserName() + "\"登录成功");
			Integer roleId = u.getRoleId();
			return new JsonResult<String>(roleId, "登录成功!");
		}
		return new JsonResult<String>(-1, "登录失败!");
	}

	/**
	 * 账户注销
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request) {
		//从session中移除该用户
		request.getSession().removeAttribute("user");
		log.info("用户注销成功");
		return "redirect:/manager/login.jsp";
	}

	/**
	 * 用户列表
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getTableData")
	public Map<String, Object> getTableData(int pageSize, int pageNumber,
			String userName) {
		/* 所需参数 */
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("start", (pageNumber - 1) * pageSize);
		param.put("size", pageSize);
		param.put("userName", userName);
		return us.pageList(param);
	}

	/**
	 * 显示编辑页面
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/showedit")
	public ModelAndView showedit(Integer userId) {
		ModelAndView mv = new ModelAndView("/system/user/user-edit");
		List<Role> roles = rs.findAll();
		User user = us.findById(userId);
		mv.addObject("roles", roles);
		mv.addObject("u", user);
		log.info("该用户信息为:" + user);
		return mv;
	}

	/**
	 * 展示角色（添加操作要用）
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/showrole")
	public ModelAndView showrole() {
		ModelAndView mv = new ModelAndView("/system/user/user-edit");
		List<Role> roles = rs.findAll();
		mv.addObject("roles", roles);
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
		int r = us.delete(idArry);
		if (r > 0) {
			return new JsonResult<String>(0, "删除成功!");
		}
		return new JsonResult<String>(-1, "删除失败!");
	}

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @return
	 * @RequestBody 用于接受前台application/json; charset=utf-8格式的json数据然后注入到bean中
	 */
	@ResponseBody
	@RequestMapping(value = "/add")
	public JsonResult<String> add(@RequestBody User user) {
		User u = us.findByName(user.getUserName());
		if (u != null) {
			return new JsonResult<String>(-1, "用户名重复，请重新填写");
		}
		int r = us.add(user);
		if (r > 0) {
			return new JsonResult<String>(0, "添加用户成功!");
		}
		return new JsonResult<String>(-1, "添加用户失败!");
	}

	/**
	 * 更改用户
	 * 
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update")
	public JsonResult<String> update(@RequestBody User user) {
		int r = us.update(user);
		if (r > 0) {
			return new JsonResult<String>(0, "更新用户成功!");
		}
		return new JsonResult<String>(-1, "修改用户失败!");
	}

}
