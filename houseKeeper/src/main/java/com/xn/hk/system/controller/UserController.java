package com.xn.hk.system.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.xn.hk.common.constant.Constant;
import com.xn.hk.common.utils.EnumStatus;
import com.xn.hk.common.utils.encryption.MD5Util;
import com.xn.hk.common.utils.page.BasePage;
import com.xn.hk.common.utils.string.Pinyin4jUtil;
import com.xn.hk.common.utils.string.StringUtil;
import com.xn.hk.system.model.Module;
import com.xn.hk.system.model.Role;
import com.xn.hk.system.model.User;
import com.xn.hk.system.service.ModuleService;
import com.xn.hk.system.service.RoleService;
import com.xn.hk.system.service.UserService;

/**
 * 
 * @Title: UserController
 * @Package: com.xn.ad.system.controller
 * @Description: 处理账户的控制层，redirect不能访问WEB-INF下的JSP页面，只有转发才能访问得到，所以可以先重定向到Action，再转发JSP页面
 * @Author: wanlei
 * @Date: 2017-11-28 下午03:22:28
 */
@Controller
@RequestMapping(value = "/system/user")
public class UserController {
	/**
	 * 记录日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final ModelAndView USER_REDITRCT_ACTION = new ModelAndView("redirect:showAllUser.do");// 重定向分页所有用户的Action
	private static final ModelAndView USER_REDITRCT_LOGIN_VIEW = new ModelAndView("redirect:tologin.do");// 重定向到登录页面
	private static final ModelAndView USER_REDITRCT_HOME_VIEW = new ModelAndView("redirect:tohome.do");// 重定向到后台首页页面
	private static final ModelAndView USER_REDITRCT_UPDATEPWD_VIEW = new ModelAndView("redirect:toUpdatePwd.do");// 重定向到后台修改密码页面
	/**
	 * 注入service层
	 */
	@Autowired
	private UserService us;
	@Autowired
	private RoleService rs;
	@Autowired
	private ModuleService ms;

	/**
	 * 转到WEB-INF下的登录页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/tologin.do")
	public String toLoginJsp() {
		return "login";
	}

	/**
	 * 转到WEB-INF下的后台首页页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/tohome.do")
	public String toHomeJsp() {
		return "home";
	}

	/**
	 * 转到WEB-INF下的后台欢迎页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/towelcome.do")
	public String toWelcomeJsp() {
		return "welcome";
	}

	/**
	 * 转到WEB-INF下的修改密码页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toUpdatePwd.do")
	public String toUpdatePwdJsp() {
		return "updatePwd";
	}

	/**
	 * 账户登录
	 * 
	 * @param user
	 *            用户实体
	 * @param session
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/login.do")
	public ModelAndView login(User user, HttpSession session, HttpServletResponse response) {
		String userName = user.getUserName();
		// 使用MD5加密(用户登录密码+登录密码key)存入数据库中,提高密码的加密程度
		String userPwd = MD5Util.MD5(user.getUserPwd() + Constant.PASSWORD_KEY);
		// 1.保存cookie实现记住密码功能
		saveCookie(user, session, response);
		// 2.取到用户输入的验证码和session中的验证码比较
		String verifyCodeValue = (String) session.getAttribute(Constant.VERIFY_CODE_KEY);
		if (!user.getVerifyCode().equalsIgnoreCase(verifyCodeValue)) {
			logger.error("验证码错误!");
			session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("验证码错误!", "error"));
			return USER_REDITRCT_LOGIN_VIEW;
		}
		// 3.通过用户名查找该用户是否存在
		User u = us.findByName(userName);
		if (u != null) {
			// 4.账户被冻结
			if (u.getUserState().intValue() == EnumStatus.ISLOCKED.getCode().intValue()) {
				logger.error("用户{}已冻结，请联系管理员!", user.getUserName());
				session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("该账户已冻结，请联系管理员!", "error"));
				return USER_REDITRCT_LOGIN_VIEW;
			} else {
				// 5.正常通过，可以登录
				if (userPwd.equals(u.getUserPwd())) {
					// 根据已登录用户的角色查询可访问的一级模块数组
					List<Module> oneModules = ms.findModuleByRoleId(Constant.ONE_MODULES_VALUE,
							u.getRole().getRoleId());
					// 根据已登录用户的角色查询可访问的二级模块数组
					List<Module> twoModules = ms.findModuleByRoleId(Constant.TWO_MODULES_VALUE,
							u.getRole().getRoleId());
					// 保存该账户角色可访问的一级模块数组和所有的二级模块列表，以便页面遍历
					session.setAttribute(Constant.ONE_MODULES_KEY, oneModules);
					session.setAttribute(Constant.TWO_MODULES_KEY, twoModules);
					logger.info("用户{}登录成功!", userName);
					// 将该用户保存至session中
					session.setAttribute(Constant.SESSION_USER_KEY, u);
					return USER_REDITRCT_HOME_VIEW;
				} else {
					logger.error("用户{}登录,密码错误!", userName);
					session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("登录密码错误!", "error"));
					return USER_REDITRCT_LOGIN_VIEW;
				}
			}
		} else {
			// 账户不存在
			logger.error("用户{}不存在!", userName);
			session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("该账户不存在!", "error"));
			return USER_REDITRCT_LOGIN_VIEW;
		}
	}

	/**
	 * 保存cookie实现记住密码功能
	 * 
	 * @param user
	 * @param session
	 * @param response
	 */
	private void saveCookie(User user, HttpSession session, HttpServletResponse response) {
		// 创建Cookie
		Cookie nameCookie = new Cookie(Constant.USERNAME_KEY, user.getUserName());
		Cookie pwdCookie = new Cookie(Constant.USERPWD_KEY, user.getUserPwd());
		logger.info("{}选择{}保存cookie:", user.getUserName(), user.getRememberMe());
		if (user.getRememberMe() == null) {
			// 不保存Cookie
			nameCookie.setMaxAge(0);
			pwdCookie.setMaxAge(0);
		} else {
			// rememberMe的值为on时，代表选中，设置Cookie的父路径
			nameCookie.setPath(session.getServletContext().getContextPath() + "/");
			pwdCookie.setPath(session.getServletContext().getContextPath() + "/");
			// 保存Cookie的时间长度，单位为秒
			nameCookie.setMaxAge(7 * 24 * 60 * 60);
			pwdCookie.setMaxAge(7 * 24 * 60 * 60);
			// 加入Cookie到响应头
			response.addCookie(nameCookie);
			response.addCookie(pwdCookie);
		}
	}

	/**
	 * 获取验证码,将其放入session中,以便登录时校验
	 * 
	 * @param response
	 *            response
	 * @param session
	 *            session
	 */
	@RequestMapping("/getVerifyCode.do")
	public void getVerifyCode(HttpServletResponse response, HttpSession session) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		// 获取验证码
		String verifyCodeValue = StringUtil.drawImg(output);
		// 将验证码放入session中,以便登录时校验
		session.setAttribute(Constant.VERIFY_CODE_KEY, verifyCodeValue);
		try {
			ServletOutputStream out = response.getOutputStream();
			output.writeTo(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 账户注销
	 * 
	 * 
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/logoff.do")
	public ModelAndView logoff(HttpSession session) {
		// 销毁session中的user
		session.removeAttribute(Constant.SESSION_USER_KEY);
		logger.info("注销成功!");
		session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("注销成功!", "success"));
		return USER_REDITRCT_LOGIN_VIEW;
	}

	/**
	 * 实现用户分页
	 * 
	 * @param user
	 *            用户实体(用来封装查询条件)
	 * @param pages
	 *            分页对象
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/showAllUser.do")
	public ModelAndView showAllUser(User user, BasePage<User> pages) {
		ModelAndView mv = new ModelAndView("system/showAllUser");
		// 封装查询条件
		pages.setBean(user);
		List<User> users = us.pageList(pages);
		// 将list封装到分页对象中
		pages.setList(users);
		mv.addObject(Constant.PAGE_KEY, pages);
		// 查询所有的角色
		List<Role> roles = rs.findAll();
		mv.addObject(Constant.ROlE_KEY, roles);
		logger.info("所有角色个数为:{}", roles.size());
		return mv;
	}

	/**
	 * 根据ID数组删除一个或多个用户
	 * 
	 * @param userIds
	 *            用户ID数组
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/delete.do")
	public ModelAndView deleteUser(Integer[] userIds, HttpSession session) {
		int result = us.delete(userIds);
		if (result == 0) {
			logger.error("删除失败,该数组不存在!");
		} else {
			logger.info("删除用户成功!");
			session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("删除用户成功!", "success"));
		}
		return USER_REDITRCT_ACTION;
	}

	/**
	 * 添加用户
	 * 
	 * @param user
	 *            用户实体
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/add.do")
	public ModelAndView addUser(User user, HttpSession session) {
		// 生成密码,规则:用户名的拼音，再使用MD5加密(用户登录密码+登录密码key)存入数据库中,提高密码的加密程度
		String userPwd = Pinyin4jUtil.getPinYin(user.getUserName());
		userPwd = MD5Util.MD5(userPwd + Constant.PASSWORD_KEY);
		user.setUserPwd(userPwd);
		int result = us.add(user);
		if (result == 0) {
			logger.error("添加{}用户失败!", user.getUserName());
		} else {
			logger.info("添加{}用户成功!", user.getUserName());
			session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("添加用户成功!", "success"));
		}
		return USER_REDITRCT_ACTION;
	}

	/**
	 * 修改用户
	 * 
	 * @param user
	 *            用户实体
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/update.do")
	public ModelAndView updateUser(User user, HttpSession session) {
		int result = us.update(user);
		if (result == 0) {
			logger.error("修改{}用户失败!", user.getUserName());
		} else {
			logger.info("修改{}用户成功!", user.getUserName());
			session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("修改用户成功!", "success"));
		}
		return USER_REDITRCT_ACTION;
	}

	/**
	 * 根据用户ID切换用户状态
	 * 
	 * @param userId
	 *            用户ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/changeState.do")
	public ModelAndView changeState(Integer userId) {
		us.changeState(userId);
		logger.info("用户{}切换状态成功!", userId);
		return USER_REDITRCT_ACTION;
	}

	/**
	 * 重置用户密码
	 * 
	 * @param userId
	 *            用户ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/resetPwd.do")
	public ModelAndView resetPwd(Integer userId, HttpSession session) {
		User user = us.findById(userId);
		// 生成密码,规则:用户名的拼音，再使用MD5加密(用户登录密码+登录密码key)存入数据库中,提高密码的加密程度
		String userPwd = Pinyin4jUtil.getPinYin(user.getUserName());
		userPwd = MD5Util.MD5(userPwd + Constant.PASSWORD_KEY);
		user.setUserPwd(userPwd);
		int result = us.update(user);
		if (result == 0) {
			logger.error("用户{}重置密码失败!", user.getUserName());
		} else {
			logger.info("用户{}重置密码成功!", user.getUserName());
			session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("重置密码成功!", "success"));
		}
		return USER_REDITRCT_ACTION;
	}

	/**
	 * 修改用户密码
	 * 
	 * @param userId
	 *            用户ID
	 * @param newpassword
	 *            用户新密码
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updatePwd.do")
	public ModelAndView updatePwd(Integer userId, String newpassword, HttpSession session) {
		User user = us.findById(userId);
		// 使用MD5加密(用户登录密码+登录密码key)存入数据库中,提高密码的加密程度
		user.setUserPwd(MD5Util.MD5(newpassword + Constant.PASSWORD_KEY));
		int result = us.update(user);
		if (result == 0) {
			logger.error("用户{}修改密码失败!", user.getUserName());
		} else {
			logger.info("用户{}修改密码成功!", user.getUserName());
			session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("修改密码成功,可以去登录了!", "success"));
		}
		return USER_REDITRCT_UPDATEPWD_VIEW;
	}

	/**
	 * 上传用户头像
	 * 
	 * @param user
	 *            用户
	 * @param MultipartFile
	 *            上传头像文件
	 * @param session
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadFace.do")
	public ModelAndView uploadFace(User user, MultipartFile file, HttpSession session) {
		// 获取上传文件的原名称来构建新文件名
		String oldName = file.getOriginalFilename();
		// 获取原文件名的后缀
		String suffix = oldName.substring(oldName.lastIndexOf("."), oldName.length());
		String newName = StringUtil.genUUIDString() + suffix;
		// 本地存储路径,此路径在server.xml中已配置图片虚拟路径对应
		File dir = new File(Constant.USER_FACE_PATH, newName);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		try {
			// MultipartFile自带的解析方法
			file.transferTo(dir);
			user.setUserFace(newName);
			// 更新用户信息
			us.uploadFace(user);
			session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("上传头像成功!", "success"));
			return USER_REDITRCT_ACTION;
		} catch (Exception e) {
			logger.error("用户{}上传头像失败,原因是:{}", user.getUserName(), e.getMessage());
			session.setAttribute(Constant.TIP_KEY, StringUtil.genTipMsg("上传头像失败!", "error"));
			return USER_REDITRCT_ACTION;
		}
	}
}
