package com.xn.hk.common.utils.map;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.xn.hk.common.utils.date.DateUtil;
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
	 * @param clazz
	 *            转换成实体对象的字节码
	 * @return 实体对象
	 * @throws Exception
	 */
	public static Object map2ObjByReflect(Map<String, Object> map, Class<?> clazz) throws Exception {
		if (map == null || map.isEmpty()) {
			return null;
		}
		Object obj = clazz.newInstance();
		// 获取实体中所有的字段
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			// 当字段属性为static或final时，跳过
			int mod = field.getModifiers();
			if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
				continue;
			}
			// 将日期类型的值转换为字符串(格式为:yyyy-MM-dd HH:mm:ss)存储
			Object fieldValue = map.get(field.getName());
			if (fieldValue instanceof Date) {
				fieldValue = DateUtil.formatDateTime((Date) fieldValue);
			}
			field.setAccessible(true);
			field.set(obj, fieldValue);
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
		// 获取实体中所有的字段
		Field[] declaredFields = obj.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			// 当字段属性为static或final时，跳过
			int mod = field.getModifiers();
			if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
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
		if (map == null || map.isEmpty()) {
			return null;
		}
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
		userMap.put("createTime", new Date());
		userMap.put("updateTime", new Date());
		userMap.put("remark", "备注");
		User user = (User) map2ObjByReflect(userMap, User.class);
		System.out.println(user.toString());
		Role role = new Role();
		role.setRoleId(1);
		role.setRoleName("测试模块");
		role.setIsOk(0);
		role.setCreateTime(DateUtil.formatDateTime(new Date()));
		role.setUpdateTime(DateUtil.formatDateTime(new Date()));
		System.out.println(role);
		Map<String, Object> roleMap = obj2MapByReflect(role);
		for (String key : roleMap.keySet()) {
			System.out.println(key + "=" + roleMap.get(key));
		}
		Map<String, Object> userMap1 = new HashMap<String, Object>();
		userMap1.put("userId", 4);
		userMap1.put("userName", "李四");
		userMap1.put("userPwd", "lisi");
		userMap1.put("userState", 1);
		userMap1.put("isOk", 1);
		userMap1.put("createTime", DateUtil.formatDateTime(new Date()));
		userMap1.put("updateTime", DateUtil.formatDateTime(new Date()));
		userMap1.put("remark", "李四");
		User user1 = (User) map2ObjByBeanUtils(userMap1, User.class);
		System.out.println(user1.toString());
	}
}
