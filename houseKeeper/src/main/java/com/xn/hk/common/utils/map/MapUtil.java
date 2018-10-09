package com.xn.hk.common.utils.map;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.xn.hk.common.utils.string.DateFormatUtil;
import com.xn.hk.system.model.Role;
import com.xn.hk.system.model.User;

/**
 * 
 * @ClassName: MapUtil
 * @Package: com.xn.hk.common.utils.map
 * @Description: 操作map的工具类
 * @Author: wanlei
 * @Date: 2018年10月9日 上午9:35:11
 */
public class MapUtil {
	/**
	 * 使用反射将map转换为实体对象
	 * 
	 * @param map
	 *            要转换的map数据集合
	 * @param beanClass
	 *            转换成实体对象的字节码
	 * @return 实体对象
	 * @throws Exception
	 */
	public static Object map2ObjByReflect(Map<String, Object> map, Class<?> beanClass) throws Exception {
		if (map == null)
			return null;
		Object obj = beanClass.newInstance();
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			int mod = field.getModifiers();
			if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
				continue;
			}
			field.setAccessible(true);
			field.set(obj, map.get(field.getName()));
		}
		return obj;
	}

	/**
	 * 使用反射将实体对象转换为map
	 * 
	 * @param map
	 *            要转换的map数据集合
	 * @param beanClass
	 *            转换成实体对象的字节码
	 * @return 实体对象
	 * @throws Exception
	 */
	public static Map<String, Object> obj2MapByReflect(Object obj) throws Exception {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] declaredFields = obj.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			// 将serialVersionUID属性排除在外
			if ("serialVersionUID".equals(field.getName())) {
				continue;
			}
			field.setAccessible(true);
			map.put(field.getName(), field.get(obj));
		}
		return map;
	}

	/**
	 * 使用BeanUtils工具将map转换为实体对象
	 * 
	 * @param map
	 *            要转换的map数据集合
	 * @param beanClass
	 *            转换成实体对象的字节码
	 * @return 实体对象
	 * @throws Exception
	 */
	public static Object map2ObjByBeanUtils(Map<String, Object> map, Class<?> beanClass) throws Exception {
		if (map == null)
			return null;
		Object obj = beanClass.newInstance();
		BeanUtils.populate(obj, map);
		return obj;
	}

	public static void main(String[] args) throws Exception {
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("userId", 1);
		userMap.put("userName", "张三");
		userMap.put("userPwd", "zhangsan");
		userMap.put("userState", 0);
		userMap.put("isOk", 0);
		userMap.put("createTime", DateFormatUtil.formatDateTime(new Date()));
		userMap.put("updateTime", DateFormatUtil.formatDateTime(new Date()));
		userMap.put("remark", "备注");
		User user = (User) map2ObjByReflect(userMap, User.class);
		System.out.println(user.toString());
		Role role = new Role();
		role.setRoleId(1);
		role.setRoleName("测试模块");
		role.setIsOk(0);
		role.setCreateTime(DateFormatUtil.formatDateTime(new Date()));
		role.setUpdateTime(DateFormatUtil.formatDateTime(new Date()));
		System.out.println(role);
		Map<String, Object> roleMap = obj2MapByReflect(role);
		for (String key : roleMap.keySet()) {
			System.out.println(key + "=" + roleMap.get(key));
		}
	}
}
