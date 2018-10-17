package com.xn.hk.common.utils.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xn.hk.common.utils.string.StringUtil;

/**
 * 
 * @ClassName: XmlOpt
 * @Package: com.xn.hk.common.utils.xml
 * @Description: xml操作工具类
 * @Author: wanlei
 * @Date: 2018年10月17日 下午7:57:40
 */
public class XmlOpt {
	private static final Logger logger = LoggerFactory.getLogger(XmlOpt.class);

	/**
	 * 生成空的xml文件头
	 * 
	 * @param xmlPath
	 *            xml文件路径
	 * @return Document对象
	 */
	public static Document createEmptyXmlFile(String xmlPath) {
		if (StringUtil.isEmpty(xmlPath)) {
			logger.error("xml文件路径为空!");
			return null;
		}
		XMLWriter output;
		Document document = DocumentHelper.createDocument();
		OutputFormat format = OutputFormat.createPrettyPrint();
		try {
			output = new XMLWriter(new FileWriter(xmlPath), format);
			output.write(document);
			output.close();
		} catch (IOException e) {
			logger.error("创建xml文件头失败，原因为:" + e);
			return null;
		}
		return document;
	}

	/**
	 * 添加孩子节点元素
	 * 
	 * @param parent
	 *            父节点
	 * @param childName
	 *            孩子节点名称
	 * @param childValue
	 *            孩子节点值
	 * @return 新增节点
	 */
	public static Element addChild(Element parent, String childName, String childValue) {
		Element child = parent.addElement(childName);// 添加节点元素
		child.setText(childValue == null ? "" : childValue); // 为元素设值
		return child;
	}

	/**
	 * 将文档对象写入对应的文件中
	 * 
	 * @param document
	 *            文档对象
	 * @param path
	 *            写入文档的路径
	 */
	public final static void writeXMLToFile(Document document, String path) {
		if (document == null || StringUtil.isEmpty(path)) {
			return;
		}
		XMLWriter writer = null;
		try {
			writer = new XMLWriter(new FileWriter(path));
			writer.write(document);
			writer.close();
		} catch (IOException e) {
			logger.error("写入文档失败，原因为:" + e);
		}
	}

	/**
	 * 根据xml文件路径取得document对象
	 * 
	 * @param xmlPath
	 *            xml文件路径
	 * @return Document对象
	 */
	public static Document getDocument(String xmlPath) {
		if (StringUtil.isEmpty(xmlPath)) {
			logger.error("xml文件路径为空!");
			return null;
		}
		File file = new File(xmlPath);
		// 文件不存在时，创建空的xml文件头
		if (!file.exists()) {
			return createEmptyXmlFile(xmlPath);
		}
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(xmlPath);
		} catch (DocumentException e) {
			logger.error("创建document对象失败，原因为:" + e);
		}
		return document;
	}

	/**
	 * 得到根节点
	 * 
	 * @param document
	 *            Document对象
	 * @return 返回根节点元素
	 */
	public static Element getRootNode(Document document) {
		if (document == null) {
			logger.error("document对象为空!");
			return null;
		}
		Element root = document.getRootElement();
		return root;
	}

	/**
	 * 根据路径直接拿到根节点
	 * 
	 * @param xmlPath
	 *            xml文件路径
	 * @return 返回根节点元素
	 */
	public static Element getRootNode(String xmlPath) {
		if (StringUtil.isEmpty(xmlPath)) {
			logger.error("xml文件路径为空!");
			return null;
		}
		Document document = getDocument(xmlPath);
		if (document == null) {
			logger.error("document对象为空!");
			return null;
		}
		return getRootNode(document);
	}

	/**
	 * 得到指定元素的迭代器
	 * 
	 * @param ele
	 *            指定元素
	 * @return 返回指定元素的迭代器
	 */
	@SuppressWarnings("unchecked")
	public static Iterator<Element> getIterator(Element ele) {
		if (ele == null) {
			logger.error("Element为空!");
			return null;
		}
		Iterator<Element> iterator = ele.elementIterator();
		return iterator;
	}

	/**
	 * 根据父节点名称得到指定的子节点名称
	 * 
	 * @param parent
	 *            父节点名称
	 * @param childName
	 *            指定的子节点名称
	 * @return 返回指定的子节点名称
	 */
	@SuppressWarnings("unchecked")
	public static List<Element> getChildElements(Element parent, String childName) {
		childName = childName.trim();
		if (parent == null) {
			logger.error("parent元素为空!");
			return null;
		}
		childName += "//";
		List<Element> childElements = parent.selectNodes(childName);
		return childElements;
	}

	/**
	 * 得到该元素的所有子节点
	 * 
	 * @param node
	 *            指定元素
	 * @return 返回所有子节点列表
	 */
	public static List<Element> getChildList(Element node) {
		if (node == null) {
			logger.error("该元素为空!");
			return null;
		}
		Iterator<Element> itr = getIterator(node);
		if (itr == null) {
			logger.error("该元素的迭代器为空!");
			return null;
		}
		List<Element> childList = new ArrayList<Element>();
		while (itr.hasNext()) {
			Element kidElement = itr.next();
			if (kidElement != null) {
				childList.add(kidElement);
			}
		}
		return childList;
	}

	/**
	 * 
	 * @方法功能描述 : 查询没有子节点的节点，使用xpath方式
	 * @param parent
	 * @param nodeNodeName
	 * @返回类型：Node
	 */
	public static Node getSingleNode(Element parent, String nodeNodeName) {
		nodeNodeName = nodeNodeName.trim();
		String xpath = "//";
		if (parent == null)
			return null;
		if (nodeNodeName == null || nodeNodeName.equals(""))
			return null;
		xpath += nodeNodeName;
		Node kid = parent.selectSingleNode(xpath);
		return kid;
	}

	/**
	 * 
	 * @方法功能描述：得到子节点，不使用xpath
	 * @param parent
	 * @param childName
	 * @返回类型：Element
	 */
	@SuppressWarnings("rawtypes")
	public static Element getChild(Element parent, String childName) {
		childName = childName.trim();
		if (parent == null)
			return null;
		if (childName == null || childName.equals(""))
			return null;
		Element e = null;
		Iterator it = getIterator(parent);
		while (it != null && it.hasNext()) {
			Element k = (Element) it.next();
			if (k == null)
				continue;
			if (k.getName().equalsIgnoreCase(childName)) {
				e = k;
				break;
			}
		}
		return e;
	}

	/**
	 * 判断节点是否还有子节点
	 * 
	 * @param e
	 *            该元素
	 * @return 有返回true
	 */
	public static boolean hasChild(Element e) {
		if (e == null) {
			return false;
		}
		return e.hasContent();
	}

	/**
	 * 遍历指定节点的所有属性
	 * 
	 * @param e
	 *            指定节点
	 * @return 返回所有属性列表
	 */
	@SuppressWarnings("unchecked")
	public static List<Attribute> getAttributeList(Element e) {
		if (e == null) {
			return null;
		}
		List<Attribute> attributeList = new ArrayList<Attribute>();
		Iterator<Attribute> atrIterator = e.attributeIterator();
		if (atrIterator == null)
			return null;
		while (atrIterator.hasNext()) {
			Attribute attribute = atrIterator.next();
			attributeList.add(attribute);
		}
		return attributeList;
	}

	/**
	 * 得到指定节点的指定属性
	 * 
	 * @param element
	 *            指定节点
	 * @param attrName
	 *            指定属性
	 * @return 返回指定属性
	 */
	public static Attribute getAttribute(Element element, String attrName) {
		attrName = attrName.trim();
		if (element == null) {
			return null;
		}
		if (StringUtil.isEmpty(attrName)) {
			return null;
		}
		Attribute attribute = element.attribute(attrName);
		return attribute;
	}

	/**
	 * 获取指定节点指定属性的值
	 * 
	 * @param element
	 *            指定节点
	 * @param attrName
	 *            指定属性
	 * @return 返回指定属性的值
	 */
	public static String attrValue(Element e, String attrName) {
		attrName = attrName.trim();
		if (e == null) {
			return null;
		}
		if (StringUtil.isEmpty(attrName)) {
			return null;
		}
		return e.attributeValue(attrName);
	}

	/**
	 * 得到指定节点的所有属性及属性值
	 * 
	 * @param e
	 *            指定节点
	 * @return 返回所有属性及属性值map集合
	 */
	public static Map<String, String> getNodeAttrMap(Element e) {
		Map<String, String> attrMap = new HashMap<String, String>();
		if (e == null) {
			return null;
		}
		List<Attribute> attributes = getAttributeList(e);
		for (Attribute attribute : attributes) {
			String attrValueString = attrValue(e, attribute.getName());
			attrMap.put(attribute.getName(), attrValueString);
		}
		return attrMap;
	}

	/**
	 * 遍历指定节点的下没有子节点的元素的text值
	 * 
	 * @param e
	 *            指定节点
	 * @return 没有子节点的元素的text值map集合
	 */
	public static Map<String, String> getSingleNodeText(Element e) {
		Map<String, String> map = new HashMap<String, String>();
		if (e == null)
			return null;
		List<Element> kids = getChildList(e);
		for (Element e2 : kids) {
			if (e2.getTextTrim() != null) {
				map.put(e2.getName(), e2.getTextTrim());
			}
		}
		return map;
	}

	/**
	 * 
	 * @方法功能描述：遍历根节点下，没有子节点的元素节点，并将此节点的text值放入map中返回
	 * @param xmlFilePath
	 * @返回类型：Map<String,String>
	 */
	public static Map<String, String> getSingleNodeText(String xmlFilePath) {
		xmlFilePath = xmlFilePath.trim();
		if (xmlFilePath == null || xmlFilePath.equals("")) {
			return null;
		}
		Element rootElement = getRootNode(xmlFilePath);
		if (rootElement == null || !hasChild(rootElement)) {
			return null;
		}
		return getSingleNodeText(rootElement);
	}

	public enum Flag {
		one, more
	}

	/**
	 * 
	 * @方法功能描述:根据xml路径和指定的节点的名称，得到指定节点,从根节点开始找
	 * @param xmlFilePath
	 * @param tagName
	 * @param flag
	 *            : 指定元素的个数
	 * @返回类型：Element 指定的节点
	 * 
	 */
	@SuppressWarnings("all")
	public static <T> T getNameNode(String xmlFilePath, String tagName, Flag flag) {
		xmlFilePath = xmlFilePath.trim();
		tagName = tagName.trim();
		if (xmlFilePath == null || tagName == null || xmlFilePath.equals("") || tagName.equals(""))
			return null;
		Element rootElement = getRootNode(xmlFilePath);
		if (rootElement == null)
			return null;
		List<Element> tagElementList = getNameElement(rootElement, tagName);
		if (tagElementList == null)
			return null;
		switch (flag) {
		case one:
			return (T) tagElementList.get(0);
		}
		return (T) tagElementList;
	}

	/**
	 * 
	 * @方法功能描述:得到指定节点下所有子节点的属性集合
	 * @param e
	 * @返回类型：Map<Integer,Object>
	 */
	public static Map<Integer, Object> getNameNodeAllKidsAttributeMap(Element parent) {
		Map<Integer, Object> allAttrMap = new HashMap<Integer, Object>();
		if (parent == null)
			return null;
		List<Element> childlElements = getChildList(parent);
		if (childlElements == null)
			return null;
		for (int i = 0; i < childlElements.size(); i++) {
			Element childElement = childlElements.get(i);
			Map<String, String> attrMap = getNodeAttrMap(childElement);
			allAttrMap.put(i, attrMap);
		}
		return allAttrMap;
	}

	/**
	 * 
	 * @方法功能描述:根据xml文件名路径和指定的节点名称得到指定节点所有子节点的所有属性集合
	 * @param xmlFileName
	 * @param nodeName
	 * @返回类型：Map<Integer,Object>
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getNameNodeAllAttributeMap(String xmlFilePath, String nodeName, Flag flag) {
		nodeName = nodeName.trim();
		Map<String, String> allAttrMap = null;
		Map<Integer, Map<String, String>> mostKidsAllAttriMap = new HashMap<Integer, Map<String, String>>();
		if (xmlFilePath == null || nodeName == null || xmlFilePath.equals("") || nodeName.equals(""))
			return null;
		switch (flag) {
		case one:
			Element nameNode = getNameNode(xmlFilePath, nodeName, Flag.one);
			allAttrMap = getNodeAttrMap(nameNode);
			return (T) allAttrMap;
		case more:
			List<Element> nameKidsElements = getNameNode(xmlFilePath, nodeName, Flag.more);
			for (int i = 0; i < nameKidsElements.size(); i++) {
				Element kid = nameKidsElements.get(i);
				allAttrMap = getNodeAttrMap(kid);
				mostKidsAllAttriMap.put(i, allAttrMap);
			}
			return (T) mostKidsAllAttriMap;
		}
		return null;
	}

	/**
	 * 
	 * @方法功能描述:遍历指定的节点下所有的节点
	 * @param element
	 * @返回类型：List<Element>
	 */
	public static List<Element> ransack(Element element, List<Element> allkidsList) {
		if (element == null)
			return null;
		if (hasChild(element)) {
			List<Element> kids = getChildList(element);
			for (Element e : kids) {
				allkidsList.add(e);
				ransack(e, allkidsList);
			}
		}
		return allkidsList;
	}

	/**
	 * 
	 * @方法功能描述:得到指定节点下的指定节点集合
	 * @param element
	 * @param nodeName
	 * @返回类型：List<Element>
	 */
	public static List<Element> getNameElement(Element element, String nodeName) {
		nodeName = nodeName.trim();
		List<Element> kidsElements = new ArrayList<Element>();
		if (element == null)
			return null;
		if (StringUtil.isEmpty(nodeName))
			return null;
		List<Element> allKids = ransack(element, new ArrayList<Element>());
		for (int i = 0; i < allKids.size(); i++) {
			Element kid = allKids.get(i);
			if (nodeName.equals(kid.getName()))
				kidsElements.add(kid);
		}
		return kidsElements;
	}

	/**
	 * 
	 * @方法功能描述:验证节点是否唯一
	 * @param element
	 * @返回类型：int 节点唯一返回1,节点不唯一返回大于一的整型数据
	 */
	public static int validateSingle(Element element) {
		int j = 1;
		if (element == null)
			return j;
		Element parent = element.getParent();
		List<Element> kids = getChildList(parent);
		for (Element kid : kids) {
			if (element.equals(kid))
				j++;
		}
		return j;
	}
}
