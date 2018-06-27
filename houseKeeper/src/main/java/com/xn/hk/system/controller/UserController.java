package com.xn.hk.system.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.xn.hk.common.constant.Constant;
import com.xn.hk.common.utils.encryption.MD5Util;
import com.xn.hk.common.utils.page.BasePage;
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
 * @Description: 处理账户的控制层
 * @Company: 杭州讯牛
 * @Author: wanlei
 * @Date: 2017-11-28 下午03:22:28
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
	@Autowired
	private ModuleService ms;

	/**
	 * 账户登录
	 * 
	 * @param user
	 *            用户实体
	 * @param rememberMe
	 *            是否选中记住账户
	 * @param verifyCodeInput
	 *            验证码
	 * @param session
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/login.do")
	public ModelAndView login(User user, String rememberMe, String verifyCodeInput, HttpSession session,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		// 实现记住密码的功能
		// 创建Cookie
		Cookie nameCookie = new Cookie("userName", user.getUserName());
		Cookie pswCookie = new Cookie("userPwd", user.getUserPwd());
		log.info(user.getUserName() + "选择是否保存cookie:" + rememberMe);
		// 获取是否保存Cookie
		if (rememberMe == null) {
			// 不保存Cookie
			nameCookie.setMaxAge(0);
			pswCookie.setMaxAge(0);
		} else {
			// 设置Cookie的父路径
			nameCookie.setPath(session.getServletContext().getContextPath() + "/");
			pswCookie.setPath(session.getServletContext().getContextPath() + "/");
			// 保存Cookie的时间长度，单位为秒
			nameCookie.setMaxAge(7 * 24 * 60 * 60);
			pswCookie.setMaxAge(7 * 24 * 60 * 60);
			// 加入Cookie到响应头
			response.addCookie(nameCookie);
			response.addCookie(pswCookie);
		}
		// 通过用户名查找该用户是否存在
		User u = us.findByName(user.getUserName());
		if (u != null) {
			// 账户存在
			if (u.getUserState() == 1) {
				// 账户被冻结
				log.error("该账户已冻结，请联系管理员!");
				session.setAttribute("msg",
						"<script>$(function(){swal('OMG!', '该账户已冻结，请联系管理员!', 'error');});</script>");
				mv.setViewName("redirect:/manager/login.jsp");
			} else {
				// 使用MD5加密(用户登录密码+登录密码key)存入数据库中,提高密码的加密程度
				user.setUserPwd(MD5Util.MD5(user.getUserPwd() + Constant.PASSWORD_KEY));
				// 正常通过，可以登录
				user = us.login(user);
				if (user != null) {
					// 取到用户输入的验证码和session中的验证码比较
					String verifyCodeValue = (String) session.getAttribute("verifyCodeValue");
					if (verifyCodeInput.equalsIgnoreCase(verifyCodeValue)) {
						// 根据已登录用户的角色查询可访问的一级模块数组
						List<Module> oneModules = ms.findModuleByRoleId(1, user.getRole().getRoleId());
						// 根据已登录用户的角色查询可访问的二级模块数组
						List<Module> twoModules = ms.findModuleByRoleId(2, user.getRole().getRoleId());
						// 保存该账户角色可访问的一级模块数组和所有的二级模块列表，以便页面遍历
						session.setAttribute("oneModules", oneModules);
						session.setAttribute("twoModules", twoModules);
						log.info(user.getUserName() + "登录成功!");
						// 将该用户保存至session中
						session.setAttribute(Constant.LOGIN_SESSION_USER_KEY, user);
						mv.setViewName("redirect:/manager/home.jsp");
					} else {
						log.error("验证码错误!");
						session.setAttribute("msg",
								"<script>$(function(){swal('OMG!', '验证码错误!', 'error');});</script>");
						mv.setViewName("redirect:/manager/login.jsp");
					}
				} else {
					log.error("登录密码错误!");
					session.setAttribute("msg", "<script>$(function(){swal('OMG!', '登录密码错误!', 'error');});</script>");
					mv.setViewName("redirect:/manager/login.jsp");
				}
			}
		} else {
			// 账户不存在
			log.error("该账户不存在!");
			session.setAttribute("msg", "<script>$(function(){swal('OMG!', '该账户不存在!', 'error');});</script>");
			mv.setViewName("redirect:/manager/login.jsp");
		}
		return mv;
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
	public void generate(HttpServletResponse response, HttpSession session) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String verifyCodeValue = drawImg(output);
		// 将验证码放入session中,以便登录时校验
		session.setAttribute("verifyCodeValue", verifyCodeValue);
		try {
			ServletOutputStream out = response.getOutputStream();
			output.writeTo(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 绘画验证码
	 * 
	 * @param output
	 *            ByteArrayOutputStream
	 * @return 验证码字符串
	 */
	private String drawImg(ByteArrayOutputStream output) {
		String code = "";
		// 随机产生6个字符
		for (int i = 0; i < 6; i++) {
			code += randomChar();
		}
		// 设置验证码的宽高
		int width = 100;
		int height = 48;
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		Font font = new Font("Times New Roman", Font.PLAIN, 26);
		// 调用Graphics2D绘画验证码
		Graphics2D g = bi.createGraphics();
		g.setFont(font);
		Color color = new Color(66, 2, 82);
		g.setColor(color);
		g.setBackground(new Color(226, 226, 240));
		g.clearRect(0, 0, width, height);
		FontRenderContext context = g.getFontRenderContext();
		Rectangle2D bounds = font.getStringBounds(code, context);
		double x = (width - bounds.getWidth()) / 2;
		double y = (height - bounds.getHeight()) / 2;
		double ascent = bounds.getY();
		double baseY = y - ascent;
		g.drawString(code, (int) x, (int) baseY);
		g.dispose();
		try {
			ImageIO.write(bi, "jpg", output);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return code;
	}

	/**
	 * 随机产生一个字符
	 * 
	 * @return 一个随机字符
	 */
	private char randomChar() {
		Random r = new Random();
		String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		return s.charAt(r.nextInt(s.length()));
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
		ModelAndView mv = new ModelAndView("redirect:/manager/login.jsp");
		// 销毁session中的user
		session.removeAttribute(Constant.LOGIN_SESSION_USER_KEY);
		log.info("注销成功!");
		session.setAttribute("msg", "<script>$(function(){swal('Good!', '注销成功!', 'success');});</script>");
		return mv;
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
		mv.addObject("pages", pages);
		// 查询所有的角色
		List<Role> roles = rs.findAll();
		mv.addObject("roles", roles);
		log.info("所有角色个数为:" + roles.size());
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
		ModelAndView mv = new ModelAndView("redirect:showAllUser.do");
		int result = us.delete(userIds);
		if (result == 0) {
			log.error("删除失败,该数组不存在!");
		} else {
			log.info("删除用户成功!");
			session.setAttribute("msg", "<script>$(function(){swal('Good!', '删除用户成功!', 'success');});</script>");
		}
		return mv;
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
		ModelAndView mv = new ModelAndView("redirect:showAllUser.do");
		// 使用MD5加密(用户登录密码+登录密码key)存入数据库中,提高密码的加密程度
		user.setUserPwd(MD5Util.MD5(user.getUserPwd() + Constant.PASSWORD_KEY));
		int result = us.add(user);
		if (result == 0) {
			log.error("添加用户失败!");
		} else {
			log.info("添加用户成功!");
			session.setAttribute("msg", "<script>$(function(){swal('Good!', '添加用户成功!', 'success');});</script>");
		}
		return mv;
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
		ModelAndView mv = new ModelAndView("redirect:showAllUser.do");
		// 使用MD5加密(用户登录密码+登录密码key)存入数据库中,提高密码的加密程度
		user.setUserPwd(MD5Util.MD5(user.getUserPwd() + Constant.PASSWORD_KEY));
		int result = us.update(user);
		if (result == 0) {
			log.error("修改用户失败!");
		} else {
			log.info("修改用户成功!");
			session.setAttribute("msg", "<script>$(function(){swal('Good!', '修改用户成功!', 'success');});</script>");
		}
		return mv;
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
		ModelAndView mv = new ModelAndView("redirect:showAllUser.do");
		us.changeState(userId);
		log.info("切换用户状态成功!");
		return mv;
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
	public ModelAndView uploadFace(User user, MultipartFile file, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView("redirect:showAllUser.do");
		// 获取上传文件的原名称来构建新文件名
		String oldName = file.getOriginalFilename();
		String newName = UUID.randomUUID().toString().replaceAll("-", "")
				+ oldName.substring(oldName.lastIndexOf("."), oldName.length());
		// 本地存储路径,此路径在server.xml中已配置图片虚拟路径对应
		File dir = new File(Constant.USER_FACE_PATH, newName);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// MultipartFile自带的解析方法
		file.transferTo(dir);
		user.setUserFace(newName);
		// 更新用户信息
		int result = us.uploadFace(user);
		if (result == 0) {
			log.error("上传头像失败!");
		} else {
			log.info("上传头像成功!");
			session.setAttribute("msg", "<script>$(function(){swal('Good!', '上传头像成功!', 'success');});</script>");
		}
		return mv;
	}
}
