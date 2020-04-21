package com.kl.ar.common.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.kl.ar.common.constant.HtmlType;
import com.kl.ar.report.entity.AdminSuggest;
import com.kl.ar.report.entity.ProductSuggest;
import com.kl.ar.report.entity.ProjectSuggest;

/**
 * 
 * @package:com.kl.ar.common.util
 * @Description: 解析html的工具类
 * @author: wanlei
 * @date: 2019年12月21日下午2:59:25
 */
public class ResolveHtmlUtil {

	/**
	 * 解析 管理建议
	 * 
	 * @param data
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public static List<AdminSuggest> resolveAdminSuggest(String data, String userId, String reportId) throws Exception {
		// 将table结构中的所有td标签中的值取出来
		List<String> tdVals = resolveData(data, HtmlType.ADMIN_SUGGEST.getCols());
		List<AdminSuggest> suggests = new ArrayList<AdminSuggest>();
		AdminSuggest suggest = null;
		// 通过列数对数据进行包装
		for (int i = 0; i < tdVals.size(); i++) {
			String colVal = tdVals.get(i);
			int currentCol = (i + 1) % HtmlType.ADMIN_SUGGEST.getCols();
			switch (currentCol) {
			case 1:
				suggest = new AdminSuggest();
				suggest.setID(UUID.randomUUID().toString());
				suggest.setREPORT_ID(reportId);
				suggest.setUSER_ID(userId);
				suggest.setNAME(colVal);
				suggest.setADD_TIME(new Date());
				break;
			case 2:
				suggest.setCONTENT(colVal);
				break;
			case 0:
				suggest.setSUGGEST(colVal);
				suggests.add(suggest);
				break;
			}
		}
		return suggests;
	}

	/**
	 * 解析 项目建议
	 * 
	 * @param data
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public static List<ProjectSuggest> resolvePorjectSuggest(String data, String userId, String reportId)
			throws Exception {
		// 将table结构中的所有td标签中的值取出来
		List<String> tdVals = resolveData(data, HtmlType.PROJECT_SUGGEST.getCols());

		List<ProjectSuggest> suggests = new ArrayList<ProjectSuggest>();
		ProjectSuggest suggest = null;
		// 通过列数对数据进行包装
		for (int i = 0; i < tdVals.size(); i++) {
			String colVal = tdVals.get(i);
			int currentCol = (i + 1) % HtmlType.PROJECT_SUGGEST.getCols();
			switch (currentCol) {
			case 1:
				suggest = new ProjectSuggest();
				suggest.setID(UUID.randomUUID().toString());
				suggest.setREPORT_ID(reportId);
				suggest.setUSER_ID(userId);
				suggest.setNAME(colVal);
				suggest.setADD_TIME(new Date());
				break;
			case 2:
				suggest.setCONTENT(colVal);
				break;
			case 0:
				suggest.setSUGGEST(colVal);
				suggests.add(suggest);
				break;
			}
		}
		return suggests;
	}

	/**
	 * 解析 产品建议
	 * 
	 * @param data
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public static List<ProductSuggest> resolveProductSuggest(String data, String userId, String reportId)
			throws Exception {
		// 将table结构中的所有td标签中的值取出来
		List<String> tdVals = resolveData(data, HtmlType.PRODUCT_SUGGEST.getCols());

		List<ProductSuggest> suggests = new ArrayList<ProductSuggest>();
		ProductSuggest suggest = null;
		// 通过列数对数据进行包装
		for (int i = 0; i < tdVals.size(); i++) {
			String colVal = tdVals.get(i);
			int currentCol = (i + 1) % HtmlType.PRODUCT_SUGGEST.getCols();
			switch (currentCol) {
			case 1:
				suggest = new ProductSuggest();
				suggest.setID(UUID.randomUUID().toString());
				suggest.setREPORT_ID(reportId);
				suggest.setUSER_ID(userId);
				suggest.setNAME(colVal);
				suggest.setADD_TIME(new Date());
				break;
			case 2:
				suggest.setCONTENT(colVal);
				break;
			case 3:
				suggest.setSOURCE(colVal);
				break;
			case 0:
				suggest.setSUGGEST(colVal);
				suggests.add(suggest);
				break;
			}
		}
		return suggests;
	}

	/**
	 * 解析td标签中的数据，并对结构进行校验
	 * 
	 * @param data
	 * @param cols
	 * @return
	 * @throws Exception
	 */
	private static List<String> resolveData(String data, int cols) throws Exception {
		if (StringUtils.isBlank(data.trim())) {
			throw new Exception("数据为空，数据解析失败！");
		}

		Document doc = Jsoup.parse(data);
		Elements tds = doc.select("tr td");
		if (tds.size() % cols != 0) {
			throw new Exception("表格结构错误，数据解析失败！");
		}

		// 构造数据
		List<String> tdVals = new ArrayList<String>();
		for (Element td : tds) {
			tdVals.add(removeHtmlTag(td.html()));
		}
		// 剔除空数据行
		List<String> cleanTdVals = new ArrayList<String>();
		// 空数据列计数
		int emptyCol = 0;
		for (int i = 0; i < tdVals.size(); i++) {
			String val = tdVals.get(i);
			if (StringUtils.isBlank(val.trim())) {
				emptyCol++;
			}
			if ((i + 1) % cols == 0) {
				if (emptyCol < cols) {
					cleanTdVals.addAll(tdVals.subList(i - cols + 1, i + 1));
				}
				emptyCol = 0;
			}

		}
		return cleanTdVals;
	}

	/**
	 * 剔除字符串中的html标签,空格，制表符，换行等
	 * 
	 * @param htmlStr
	 * @return
	 */
	public static String removeHtmlTag(String htmlStr) {
		// 过滤文章内容中的html
		htmlStr = htmlStr.replaceAll("</?[^<]+>", "");
		// 去除字符串中的空格 回车 换行符 制表符 等
		htmlStr = htmlStr.replaceAll("\\s*|\t|\r|\n", "");
		// 去除空格
		htmlStr = htmlStr.replaceAll("&nbsp;", "");
		return htmlStr;
	}
	/**
	 * 测试方法
	 * @param args
	 */
	public static void main(String[] args) {
		String data = "<table style=\"width: 100%;\"><tbody><tr class=\"firstRow\" contenteditable=\"false\"><th valign=\"top\" style=\"width: 20%; text-align: left; font-weight: normal; background-color: rgb(231, 238, 241);\">产品名称</th><th valign=\"top\" style=\"width: 30%; text-align: left; font-weight: normal; background-color: rgb(231, 238, 241);\">问题与缺陷</th><th valign=\"top\" style=\"width: 30%; text-align: left; font-weight: normal; background-color: rgb(231, 238, 241);\">改进方向与建议措施</th><th valign=\"top\" style=\"width: 20%; text-align: left; font-weight: normal; background-color: rgb(231, 238, 241);\">借鉴来源</th></tr><tr><td valign=\"top\"><br/></td><td valign=\"top\"><br/></td><td valign=\"top\"><br/></td><td valign=\"top\"><br/></td></tr><tr><td valign=\"top\"><br/></td><td valign=\"top\"><br/></td><td valign=\"top\"><br/></td><td valign=\"top\"><br/></td></tr><tr><td valign=\"top\"><br/></td><td valign=\"top\"><br/></td><td valign=\"top\"><br/></td><td valign=\"top\"><br/></td></tr></tbody></table>";
		try {
			List<AdminSuggest> resolveAdminSuggest = resolveAdminSuggest(data, "001", "123");
			Gson gson = new Gson();
			System.out.println(gson.toJson(resolveAdminSuggest));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
