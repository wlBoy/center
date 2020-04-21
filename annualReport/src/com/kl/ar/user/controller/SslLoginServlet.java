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
import com.kl.ar.common.util.DateUtil;
import com.kl.ar.common.util.StringUtil;
import com.kl.ar.org.entity.CrmOrg;
import com.kl.ar.user.entity.CrmUser;

/**
 * 
 * @package:com.kl.ar.user.controller
 * @Description: 网关证书登录控制器
 * @author: wanlei
 * @date: 2019年12月21日下午3:20:52
 */
@WebServlet("/login")
public class SslLoginServlet extends BaseController {
	private static final Log logger = LogFactory.getLog(SslLoginServlet.class);
	private static final long serialVersionUID = 1L;

	public SslLoginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 获取配置文件中的网关cookie中的certCn项和certE项
			String enableRegisterUser = ConfigUtil.getInstance().loadCfgMap().get(Constant.ENABLE_REGISTER_USER);
			String koalCnCookie = ConfigUtil.getInstance().loadCfgMap().get(Constant.KOAL_CN_COOKIE);
			String koalECookie = ConfigUtil.getInstance().loadCfgMap().get(Constant.KOAL_E_COOKIE);
			
			String userName = getCookieByName(request, koalCnCookie);
			String email = getCookieByName(request, koalECookie);
			logger.info(String.format("----- 证书登录：【证书cn：" + userName + ",证书邮箱：" + email + "】-----"));
			if (StringUtils.isBlank(userName) && StringUtils.isBlank(email)) {
				request.setAttribute(Constant.ERROR_INFO, Constant.TIP_CERT_USERNAME_EAMIL_EMPTY);
				request.getRequestDispatcher(View.ERROR_JSP).forward(request, response);
				return;
			}
			// 先根据certCn项查询用户，查不到再根据证书邮箱查询用户，如果开启自动注册功能，则根据用户名和邮箱直接反注册一条用户信息
			String sql = "select u.*,d.sName AS depName from " + CrmUser.TABLE + " u LEFT JOIN " + CrmOrg.TABLE
					+ " d ON u.idDep = d.ORGID where u.certcn=?";
			CrmUser user = BizDb.queryOneRow(sql, CrmUser.class, userName);
			if (user == null) {
				String emailsql = "select u.*,d.sName AS depName from " + CrmUser.TABLE + " u LEFT JOIN " + CrmOrg.TABLE
						+ " d ON u.idDep = d.ORGID where u.sMail=?";
				user = BizDb.queryOneRow(emailsql, CrmUser.class, email);
				// 是否开启自动注册
				if (Boolean.valueOf(enableRegisterUser)) {
					// 网关已经校验证书了，根据用户名和邮箱直接反注册一条用户信息，在提供一个修改用户信息的功能即可
					user = registerUserByUserNameAndEmail(userName, email);
				}
			}
			// 反注册失败时，给出错误提示信息
			if (user == null) {
				request.setAttribute(Constant.ERROR_INFO, Constant.TIP_CERT_LOGIN_FAILURE);
				request.getRequestDispatcher(View.ERROR_JSP).forward(request, response);
				return;
			}
			// 判断是否为领导并将登录信息放置session中，重定向至系统首页
			isLeader(request, user);
			request.getSession().setAttribute(Constant.USER_IN_SESSION, user);
			response.sendRedirect(View.RS_TOINDEX_ACTION);
		} catch (Exception e) {
			logger.error("网关证书登录失败，原因为:", e);
			request.setAttribute(Constant.ERROR_INFO, Constant.TIP_SYSTEM_ERROR);
			request.getRequestDispatcher(View.ERROR_JSP).forward(request, response);
		}
	}

	/**
	 * 根据用户名和邮箱直接反注册一条用户信息
	 * 
	 * @param userName
	 * @param email
	 * @throws Exception
	 */
	private static CrmUser registerUserByUserNameAndEmail(String userName, String email) {
		try {
			CrmUser user = new CrmUser();
			String orgId = getRootOrg().getORGID();
			user.setUSERID(StringUtil.genUUIDString().toUpperCase());
			user.setsCode(email);
			user.setsName(userName);
			user.setIdDep(orgId);
			user.setsMail(email);
			user.setCreated(DateUtil.formatDateTime());
			user.setIndate(DateUtil.formatDateTime());
			user.setIdorg(orgId);
			user.setCertcn(userName);
			user.setNeedfilllog("1");
			user.setBmanager("0");
			user.setCreatedBy("ar");
			BizDb.insert(user, CrmUser.TABLE);
			return user;
		} catch (Exception e) {
			logger.error("网关证书登录时，根据用户名和邮箱直接反注册用户失败，原因为:", e);
			return null;
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
		// 当某用户兼职多个部门的权限时,以逗号隔开，将其兼职部门权限也添加至权限MAP
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
	 * 获取根机构信息
	 * 
	 * @param orgid
	 * @return
	 * @throws Exception
	 */
	private static CrmOrg getRootOrg() throws Exception {
		String sql = "SELECT * FROM " + CrmOrg.TABLE + " where idparent is null";
		return BizDb.queryOneRow(sql, CrmOrg.class);
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
