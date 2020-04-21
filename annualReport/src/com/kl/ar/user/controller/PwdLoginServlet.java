package com.kl.ar.user.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kl.ar.common.cfg.ConfigUtil;
import com.kl.ar.common.constant.Constant;
import com.kl.ar.common.constant.View;
import com.kl.ar.common.controller.BaseController;
import com.kl.ar.common.db.BizDb;
import com.kl.ar.org.entity.CrmOrg;
import com.kl.ar.user.entity.CrmUser;

/**
 * 
 * @package:com.kl.ar.user.controller
 * @Description: 用户名密码登录控制器
 * @author: wanlei
 * @date: 2019年12月21日下午3:17:15
 */
@WebServlet("/pwdLogin")
public class PwdLoginServlet extends BaseController {
	private static final Log logger = LogFactory.getLog(PwdLoginServlet.class);
	private static final long serialVersionUID = 1L;

	public PwdLoginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter(Constant.OP);
		try {
			switch (op) {
			// 密码登录
			case "login":
				login(request, response);
				break;
			// 转到密码登录界面
			case "toLogin":
				request.getRequestDispatcher(View.LOGIN_JSP).forward(request, response);
				break;
			// 转到session过期页面
			case "toSession":
				request.getRequestDispatcher(View.SESSION_JSP).forward(request, response);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			logger.error("密码登录失败，原因为:" + e);
			request.setAttribute(Constant.ERROR_INFO, Constant.TIP_SYSTEM_ERROR);
			request.getRequestDispatcher(View.ERROR_JSP).forward(request, response);
		}
	}

	/**
	 * 口令密码登录
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String userName = request.getParameter("username");
			logger.info("----- 用户名密码登录：【用户名：" + userName + "】 -----");
			if (StringUtils.isBlank(userName)) {
				request.setAttribute(Constant.ERROR_INFO, Constant.TIP_USERNAME_EMPTY);
				request.getRequestDispatcher(View.LOGIN_JSP).forward(request, response);
				return;
			}
			String pwd = request.getParameter("password");
			if (StringUtils.isBlank(pwd)) {
				request.setAttribute(Constant.ERROR_INFO, Constant.TIP_PASSWORD_EMPTY);
				request.getRequestDispatcher(View.LOGIN_JSP).forward(request, response);
				return;
			}
			// 获取配置文件中的默认登录密码
			String defaultLoginPwd = ConfigUtil.getInstance().loadCfgMap().get(Constant.DEFAULT_LOGIN_PWD);
			String sql = "select u.*,d.sName AS depName from " + CrmUser.TABLE + " u LEFT JOIN " + CrmOrg.TABLE
					+ " d ON u.idDep = d.ORGID where u.sName=?";
			CrmUser user = BizDb.queryOneRow(sql, CrmUser.class, userName);
			// 用户名不存在
			if (user == null) {
				request.setAttribute(Constant.ERROR_INFO, Constant.TIP_USERNAME_NOT_EXIST);
				request.getRequestDispatcher(View.LOGIN_JSP).forward(request, response);
				return;
			}
			// 密码不正确
			if (!defaultLoginPwd.equals(pwd)) {
				request.setAttribute(Constant.ERROR_INFO, Constant.TIP_PWD_ERROR);
				request.getRequestDispatcher(View.LOGIN_JSP).forward(request, response);
				return;
			}
			// 判断是否为领导并将登录信息放置session中，重定向至系统首页
			isLeader(request, user);
			request.getSession().setAttribute(Constant.USER_IN_SESSION, user);
			response.sendRedirect(View.RS_TOINDEX_ACTION);
		} catch (Exception e) {
			logger.error("用户名密码登录失败，原因为:", e);
			request.setAttribute(Constant.ERROR_INFO, Constant.TIP_SYSTEM_ERROR);
			request.getRequestDispatcher(View.ERROR_JSP).forward(request, response);
		}
	}

	/**
	 * 获取主管部门
	 * 
	 * @param request
	 * @param user
	 * @throws Exception
	 */
	private void isLeader(HttpServletRequest request, CrmUser user) throws Exception {
		// 获取用户其所属部门及其子部门的LIST集合
		String sql = "SELECT ORGID,pathid FROM " + CrmOrg.TABLE + " WHERE idEmployee = ?";
		List<Map<String, Object>> orgMapList = BizDb.queryList(sql, user.getUSERID());
		// 当某用户兼职多个部门的权限时，以逗号隔开，将其兼职部门权限也添加至权限MAP
		String partTimeOrgIdStr = user.getPartTime();
		if (StringUtils.isNotBlank(partTimeOrgIdStr)) {
			String[] partTimeOrgIdArray = partTimeOrgIdStr.split(",");
			for (String partTimeOrgId : partTimeOrgIdArray) {
				if(StringUtils.isNotBlank(partTimeOrgId)) {
					Map<String, Object> partTimeOrgMap = new HashMap<String, Object>();
					partTimeOrgMap.put("ORGID", partTimeOrgId);
					partTimeOrgMap.put("pathid", getOrgPathId(partTimeOrgId));
					orgMapList.add(partTimeOrgMap);
				}
			}
		}
		// 部门权限列表为空时，则代表为普通员工，则不向session中添加部门权限LIST
		if (orgMapList.size() > 0) {
			request.getSession().setAttribute(Constant.USER_LEADER_ORG, orgMapList);
		}
	}

	/**
	 * 通过机构id拿到对应机构的pathId
	 * 
	 * @param orgid
	 * @return
	 * @throws Exception
	 */
	public static String getOrgPathId(String orgid) throws Exception {
		String sql = "SELECT * FROM " + CrmOrg.TABLE + " where ORGID=?";
		CrmOrg org = BizDb.queryOneRow(sql, CrmOrg.class, orgid);
		return org.getPathid();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
