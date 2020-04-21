package com.kl.ar.org.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kl.ar.common.constant.Constant;
import com.kl.ar.common.constant.View;
import com.kl.ar.common.controller.BaseController;
import com.kl.ar.common.db.BizDb;
import com.kl.ar.org.entity.CrmOrg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @package:com.kl.ar.org.controller
 * @Description: 机构控制器
 * @author: wanlei
 * @date: 2019年12月21日下午3:00:33
 */
@WebServlet("/org")
public class OrgServlet extends BaseController {
	private static final Log logger = LogFactory.getLog(OrgServlet.class);
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter(Constant.OP);
		try {
			switch (op) {
			// 获取所有的机构数据
			case "getOrgData":
				getOrgData(request, response);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			logger.error("获取机构数据失败，原因为:" + e);
			request.setAttribute(Constant.ERROR_INFO, Constant.TIP_SYSTEM_ERROR);
			request.getRequestDispatcher(View.ERROR_JSP).forward(request, response);
		}

	}

	/**
	 * 获取评分列表json数据
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 * @throws IOException
	 */
	public void getOrgData(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
		String orgName = request.getParameter("orgName");
		String sql = "SELECT pathid AS id,sName AS text FROM " + CrmOrg.TABLE + " WHERE sName LIKE CONCAT('%',?,'%')";
		List<Map<String, Object>> orgs = BizDb.queryList(sql, orgName);
		if (CollectionUtils.isEmpty(orgs)) {
			orgs = new ArrayList<Map<String, Object>>();
		}
		response.getWriter().write(gson.toJson(orgs));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
