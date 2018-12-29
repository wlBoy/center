package com.xn.hk.common.utils.html;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * @ClassName: JsoupUtil
 * @Package: com.xn.hk.common.utils.html
 * @Description: 使用jsoup解析HTML的工具类
 * @Author: wanlei
 * @Date: 2018年12月29日 下午5:51:25
 */
public class JsoupUtil {

	/*
	 *  jsoup 是一款Java 的HTML解析器，可直接解析某个URL地址、HTML文本内容。它提供了一套非常省力的API，可通过DOM，CSS以及类似于jQuery的操作方法来取出和操作数据。
		网页获取和解析速度飞快，推荐使用。
		主要功能如下：
		1. 从一个URL，文件或字符串中解析HTML；
		2. 使用DOM或CSS选择器来查找、取出数据；
		3. 可操作HTML元素、属性、文本；
	 * 
	 */
	/**
	 * 根据元素id获取HTML元素
	 * 
	 * @param document
	 * @param id
	 * @return
	 */
	public static Element getElementById(Document document, String id) {
		Element element = null;
		if (document != null && id != null && !"".equals(id.trim())) {
			element = document.getElementById(id);
		}
		return element;
	}

	/**
	 * 根据id获取HTML元素
	 * 
	 * @param element
	 * @param id
	 * @return
	 */
	public static Element getElementById(Element element, String id) {
		Element resultElement = null;
		if (element != null) {
			resultElement = element.getElementById(id);
		}
		return resultElement;
	}

	/**
	 * 根据元素标签(tagName)获取HTMl元素
	 * 
	 * @param document
	 * @param tagName
	 * @return
	 */
	public static Elements getElementsByTagName(Document document, String tagName) {
		Elements elements = null;
		if (document != null && tagName != null && !"".equals(tagName)) {
			elements = document.getElementsByTag(tagName);
		}
		return elements;
	}

	/**
	 * 根据元素标签(tagName)获取HTMl元素
	 * 
	 * @param element
	 * @param tagName
	 * @return
	 */
	public static Elements getElementsByTagName(Element element, String tagName) {
		Elements resultElements = null;
		if (element != null && tagName != null && !"".equals(tagName)) {
			resultElements = element.getElementsByTag(tagName);
		}
		return resultElements;
	}

	/**
	 * 根据className(样式名称)获取HTML元素集合
	 * 
	 * @param document
	 * @param className
	 * @return
	 */
	public static Elements getElementsByClassName(Document document, String className) {
		Elements elements = null;
		if (document != null && className != null && !"".equals(className.trim())) {
			elements = document.getElementsByClass(className);
		}
		return elements;
	}

	/**
	 * 根据className(样式名称)获取HTML元素集合
	 * 
	 * @param element
	 * @param className
	 * @return
	 */
	public static Elements getElementsByClassName(Element element, String className) {
		Elements resultElements = null;
		if (element != null && className != null && !"".equals(className)) {
			resultElements = element.getElementsByClass(className);
		}
		return resultElements;
	}

	/**
	 * 根据元素是否具有属性元素key返回元素集合
	 * 
	 * @param document
	 * @param attributeNameKey
	 *            元素属性key值
	 * @return
	 */
	public static Elements getElementsByAttributeNameKey(Document document, String attributeNameKey) {
		Elements elements = null;
		if (document != null && attributeNameKey != null && !"".equals(attributeNameKey)) {
			elements = document.getElementsByAttribute(attributeNameKey);
		}
		return elements;
	}

	/**
	 * 根据元素是否具有属性元素key返回元素集合
	 * 
	 * @param element
	 * @param attributeNameKey
	 *            元素属性key值
	 * @return
	 */
	public static Elements getElementsByAttributeNameKey(Element element, String attributeNameKey) {
		Elements resultElements = null;
		if (element != null && attributeNameKey != null && !"".equals(attributeNameKey)) {
			resultElements = element.getElementsByAttribute(attributeNameKey);
		}
		return resultElements;
	}

	/**
	 * 根据元素是否具有属性元素key并且key对应的值为value获取元素集合
	 * 
	 * @param document
	 * @param attributeKey
	 * @param attributeValue
	 * @return
	 */
	public static Elements getElementsByAttributeNameValue(Document document, String attributeKey,
			String attributeValue) {
		Elements elements = null;
		if (document != null && attributeKey != null && !"".equals(attributeKey.trim()) && attributeValue != null
				&& !"".equals(attributeValue.trim())) {
			elements = document.getElementsByAttributeValue(attributeKey, attributeValue);
		}
		return elements;
	}

	/**
	 * 根据元素是否具有属性元素key并且key对应的值为value获取元素集合
	 * 
	 * @param element
	 * @param attributeKey
	 * @param attributeValue
	 * @return
	 */
	public static Elements getElementsByAttributeNameValue(Element element, String attributeKey,
			String attributeValue) {
		Elements resultElements = null;
		if (element != null && attributeKey != null && !"".equals(attributeKey.trim()) && attributeValue != null
				&& !"".equals(attributeValue.trim())) {
			resultElements = element.getElementsByAttributeValue(attributeKey, attributeValue);
		}
		return resultElements;
	}

	/**
	 * 根据属性key值是否以特定字符串开头获取元素集合
	 * 
	 * @param document
	 * @param attributeValue
	 * @return
	 */
	public static Elements getElementsByAttributeNameStartWithValue(Document document, String keyValue) {
		Elements elements = null;
		if (document != null && keyValue != null && !"".equals(keyValue.trim())) {
			elements = document.getElementsByAttributeStarting(keyValue);
		}
		return elements;
	}

	/**
	 * 根据属性key值是否以特定字符串开头获取元素集合
	 * 
	 * @param element
	 * @param attributeValue
	 * @return
	 */
	public static Elements getElementsByAttributeNameStartWithValue(Element element, String keyValue) {
		Elements elements = null;
		if (element != null && keyValue != null && !"".equals(keyValue.trim())) {
			elements = element.getElementsByAttributeStarting(keyValue);
		}
		return elements;
	}

	/**
	 * 根据属性value值是否被包含在某个元素的某个属性中获取元素集合
	 * 
	 * @param document
	 * @param containValue
	 * @return
	 */
	public static Elements getElementsByAttributeValueContaining(Document document, String attributeKey,
			String containValue) {
		Elements elements = null;
		if (document != null && containValue != null && !"".equals(containValue)) {
			elements = document.getElementsByAttributeValueContaining(attributeKey, containValue);
		}
		return elements;
	}

	/**
	 * 根据属性value值是否被包含在某个元素的某个属性中获取元素集合
	 * 
	 * @param element
	 * @param attributeKey
	 * @param containValue
	 * @return
	 */
	public static Elements getElementsByAttributeValueContaining(Element element, String attributeKey,
			String containValue) {
		Elements elements = null;
		if (element != null && containValue != null && !"".equals(containValue)) {
			elements = element.getElementsByAttributeValueContaining(attributeKey, containValue);
		}
		return elements;
	}

	/**
	 * 根据属性的value值是否以某个字符串结尾获取元素集合
	 * 
	 * @param document
	 * @param attributeKey
	 * @param valueSuffix
	 * @return
	 */
	public static Elements getElementsByAttributeValueEnding(Document document, String attributeKey,
			String valueSuffix) {
		Elements elements = null;
		if (document != null && attributeKey != null && !"".equals(attributeKey) && valueSuffix != null
				&& !"".equals(valueSuffix)) {
			elements = document.getElementsByAttributeValueEnding(attributeKey, valueSuffix);
		}
		return elements;
	}

	/**
	 * 根据属性的value值是否以某个字符串结尾获取元素集合
	 * 
	 * @param element
	 * @param attributeKey
	 * @param valueSuffix
	 * @return
	 */
	public static Elements getElementsByAttributeValueEnding(Element element, String attributeKey, String valueSuffix) {
		Elements elements = null;
		if (element != null && attributeKey != null && !"".equals(attributeKey) && valueSuffix != null
				&& !"".equals(valueSuffix)) {
			elements = element.getElementsByAttributeValueEnding(attributeKey, valueSuffix);
		}
		return elements;
	}

	/**
	 * 根据属性值value的正则表达式获取元素集合
	 * 
	 * @param document
	 * @param attributeKey
	 * @param pattern
	 * @return
	 */
	public static Elements getElementsByAttributeValueMatching(Document document, String attributeKey,
			Pattern pattern) {
		Elements elements = null;
		if (document != null && attributeKey != null && !"".equals(attributeKey) && pattern != null) {
			elements = document.getElementsByAttributeValueMatching(attributeKey, pattern);
		}
		return elements;
	}

	/**
	 * 根据属性值value的正则表达式获取元素集合
	 * 
	 * @param element
	 * @param attributeKey
	 * @param pattern
	 * @return
	 */
	public static Elements getElementsByAttributeValueMatching(Element element, String attributeKey, Pattern pattern) {
		Elements elements = null;
		if (element != null && attributeKey != null && !"".equals(attributeKey) && pattern != null) {
			elements = element.getElementsByAttributeValueMatching(attributeKey, pattern);
		}
		return elements;
	}

	/**
	 * 根据属性值的value的正则表达式获取元素集合
	 * 
	 * @param document
	 * @param attributeKey
	 * @param regualRegx
	 * @return
	 */
	public static Elements getElementsByAttributeValueMatching(Document document, String attributeKey,
			String regualRegx) {
		Elements elements = null;
		if (document != null && attributeKey != null && !"".equals(attributeKey) && regualRegx != null
				&& !"".equals(regualRegx)) {
			elements = document.getElementsByAttributeValueMatching(attributeKey, regualRegx);
		}
		return elements;
	}

	/**
	 * 根据属性值的value的正则表达式获取元素集合
	 * 
	 * @param element
	 * @param attributeKey
	 * @param regualRegx
	 * @return
	 */
	public static Elements getElementsByAttributeValueMatching(Element element, String attributeKey,
			String regualRegx) {
		Elements elements = null;
		if (element != null && attributeKey != null && !"".equals(attributeKey) && regualRegx != null
				&& !"".equals(regualRegx)) {
			elements = element.getElementsByAttributeValueMatching(attributeKey, regualRegx);
		}
		return elements;
	}

	/**
	 * 返回属性键attributeKey不等于值attributeValue的元素集合
	 * 
	 * @param document
	 * @param attributeKey
	 * @param attributeValue
	 * @return
	 */
	public static Elements getElementsByAttributeValueNot(Document document, String attributeKey,
			String attributeValue) {
		Elements elements = null;
		if (document != null && attributeKey != null && !"".equals(attributeKey) && attributeValue != null
				&& !"".equals(attributeValue)) {
			elements = document.getElementsByAttributeValueNot(attributeKey, attributeValue);
		}
		return elements;
	}

	/**
	 * 返回属性键attributeKey不等于值attributeValue的元素集合
	 * 
	 * @param element
	 * @param attributeKey
	 * @param attributeValue
	 * @return
	 */
	public static Elements getElementsByAttributeValueNot(Element element, String attributeKey, String attributeValue) {
		Elements elements = null;
		if (element != null && attributeKey != null && !"".equals(attributeKey) && attributeValue != null
				&& !"".equals(attributeValue)) {
			elements = element.getElementsByAttributeValueNot(attributeKey, attributeValue);
		}
		return elements;
	}

	/**
	 * 根据选择器匹配的字符串返回 Elements(元素集合)
	 * 
	 * @param document
	 * @param selectStr
	 *            选择器(类似于JQuery)
	 * @return
	 */
	public static Elements getMoreElementsBySelectStr(Document document, String selectStr) {
		if (document == null || selectStr == null || "".equals(selectStr.trim())) {
			return null;
		} else {
			Elements elements = document.select(selectStr);
			if (elements != null && elements.size() > 0) {
				return elements;
			} else {
				return null;
			}
		}
	}

	/**
	 * 根据选择器匹配的字符串返回 Elements(元素集合)
	 * 
	 * @param element
	 * @param selectStr
	 * @return
	 */
	public static Elements getMoreElementsBySelectStr(Element element, String selectStr) {
		if (element == null || selectStr == null || "".equals(selectStr.trim())) {
			return null;
		} else {
			Elements elements = element.select(selectStr);
			if (elements != null && elements.size() > 0) {
				return elements;
			} else {
				return null;
			}
		}
	}

	/**
	 * 根据选择器匹配的字符串返回 Element(单个元素)
	 * 
	 * @param document
	 * @param selectStr
	 *            选择器(类似于JQuery)
	 * @return
	 */
	public static Element getSingleElementBySelectStr(Document document, String selectStr) {
		Elements elements = getMoreElementsBySelectStr(document, selectStr);
		if (elements != null && elements.size() > 0) {
			return elements.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据选择器匹配的字符串返回 Element(单个元素)
	 * 
	 * @param element
	 * @param selectStr
	 * @return
	 */
	public static Element getSingleElementBySelectStr(Element element, String selectStr) {
		Elements elements = getMoreElementsBySelectStr(element, selectStr);
		if (elements != null && elements.size() > 0) {
			return elements.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据选择器匹配的字符串返回单个元素的Html字符串
	 * 
	 * @param document
	 * @param selectStr
	 *            选择器(类似于JQuery)
	 * @return
	 */
	public static String getSingleElementHtmlBySelectStr(Document document, String selectStr) {
		Element element = getSingleElementBySelectStr(document, selectStr);
		if (element != null) {
			return element.html();
		} else {
			return null;
		}
	}

	/**
	 * 根据选择器匹配的字符串返回单个元素的Html字符串
	 * 
	 * @param element
	 * @param selectStr
	 * @return
	 */
	public static String getSingleElementHtmlBySelectStr(Element element, String selectStr) {
		Element ele = getSingleElementBySelectStr(element, selectStr);
		if (ele != null) {
			return ele.html();
		} else {
			return null;
		}
	}

	/**
	 * 根据元素属性名key获取元素属性名value
	 * 
	 * @param element
	 * @param attributeName
	 * @return
	 */
	public static String getAttributeValue(Element element, String attributeName) {
		String attributeValue = null;
		if (element != null && attributeName != null && !"".equals(attributeName)) {
			attributeValue = element.attr(attributeName);
		}
		return attributeValue;
	}

	/**
	 * 从elements集合中获取element并解析成HTML字符串
	 * 
	 * @param elements
	 * @return
	 */
	public static String getSingElementHtml(Elements elements) {
		Element ele = null;
		String htmlStr = null;
		if (elements != null && elements.size() > 0) {
			ele = elements.get(0);
			htmlStr = ele.html();
		}
		return htmlStr;
	}

	/**
	 * 从elements集合中获取element并解析成Text字符串
	 * 
	 * @param elements
	 * @return
	 */
	public static String getSingElementText(Elements elements) {
		Element ele = null;
		String htmlStr = null;
		if (elements != null && elements.size() > 0) {
			ele = elements.get(0);
			htmlStr = ele.text();
		}
		return htmlStr;
	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		File file = new File("F:/example.htm");
		try {
			Document document = Jsoup.parse(file, "GB2312");
			Element element = getElementById(document, "personal-uplayer");
			System.out.println(element.text());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}