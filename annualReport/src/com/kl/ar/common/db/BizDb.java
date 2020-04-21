package com.kl.ar.common.db;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kl.ar.common.util.ExecutorInTrans;

/**
 * 
 * @package:com.kl.ar.common.db
 * @Description: 数据库操作类
 * @author: wanlei
 * @date: 2019年12月21日下午2:18:35
 */
public class BizDb {
	private static final Log logger = LogFactory.getLog(BizDb.class);

	/**
	 * 执行查询语句，返回数据列表
	 * 
	 * @param sql
	 * @param param
	 *            多参，SQL语句中需要查询的值
	 * @return 返回List<Map<String, Object>>
	 * @throws Exception
	 */
	public static List<Map<String, Object>> queryList(String sql, Object... param) throws Exception {
		ResultSetHandler<List<Map<String, Object>>> handler = new MapListHandler();
		Connection conn = null;
		try {
			conn = DBUtils.getConn();
			QueryRunner qr = new QueryRunner();
			log(sql, param);
			return qr.query(conn, sql, handler, param);
		} catch (Exception e) {
			throw e;
		} finally {
			DBUtils.close(conn);
		}
	}

	/**
	 * 执行查询语句，返回数据列表
	 * 
	 * @param sql
	 * @param param
	 *            多参，SQL语句中需要查询的值
	 * @return 返回List<T>
	 * @throws Exception
	 */
	public static <T> List<T> queryList(String sql, Class<T> clazz, Object... param) throws Exception {
		Connection conn = null;
		try {
			conn = DBUtils.getConn();
			QueryRunner qr = new QueryRunner();
			log(sql, param);
			return qr.query(conn, sql, new BeanListHandler<>(clazz, new BasicRowProcessor()), param);
		} catch (Exception e) {
			throw e;
		} finally {
			DBUtils.close(conn);
		}
	}

	/**
	 * 执行查询语句，返回一行数据
	 * 
	 * @param sql
	 * @param param
	 *            多参，SQL语句中需要查询的值
	 * @return 返回Map<String, Object>
	 * @throws Exception
	 */
	public static Map<String, Object> queryOneRow(String sql, Object... param) throws Exception {
		ResultSetHandler<List<Map<String, Object>>> handler = new MapListHandler();
		Connection conn = null;
		try {
			conn = DBUtils.getConn();
			QueryRunner qr = new QueryRunner();
			log(sql, param);
			List<Map<String, Object>> list = qr.query(conn, sql, handler, param);
			if (list != null && list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DBUtils.close(conn);
		}
	}

	/**
	 * 执行查询语句，返回一行数据
	 * 
	 * @param sql
	 * @param param
	 *            多参，SQL语句中需要查询的值
	 * @return 返回<T>
	 * @throws Exception
	 */
	public static <T> T queryOneRow(String sql, Class<T> clazz, Object... param) throws Exception {
		Connection conn = null;
		try {
			conn = DBUtils.getConn();
			QueryRunner qr = new QueryRunner();
			log(sql, param);
			List<T> list = qr.query(conn, sql, new BeanListHandler<>(clazz, new BasicRowProcessor()), param);
			if (list != null && list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DBUtils.close(conn);
		}
	}

	/**
	 * 执行查询语句，返回一行数据(传参数据库链接，保持事务)
	 * 
	 * @param conn
	 * @param sql
	 * @param clazz
	 * @param param
	 * @return 返回<T>
	 * @throws Exception
	 */
	public static <T> T queryOneRow(Connection conn, String sql, Class<T> clazz, Object... param) throws Exception {
		QueryRunner qr = new QueryRunner();
		log(sql, param);
		List<T> list = qr.query(conn, sql, new BeanListHandler<>(clazz, new BasicRowProcessor()), param);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 执行更新操作
	 * 
	 * @param sql
	 * @param param
	 * @throws Exception
	 */
	public static int update(String sql, Object... param) throws Exception {
		Connection conn = null;
		try {
			conn = DBUtils.getConn();
			QueryRunner qr = new QueryRunner();
			log(sql, param);
			int rowNum = qr.update(conn, sql, param);
			logger.debug("执行更新操作，影响了" + rowNum + "行数据");
			return rowNum;
		} catch (Exception e) {
			throw e;
		} finally {
			DBUtils.close(conn);
		}
	}

	/**
	 * 执行批量更新操作
	 * 
	 * @param sql
	 * @param param
	 * @throws Exception
	 */
	public static int batchUpdate(String sql, Object... param) throws Exception {
		Connection conn = null;
		try {
			conn = DBUtils.getConn();
			QueryRunner qr = new QueryRunner();
			log(sql, param);
			int rowNum = qr.update(conn, sql, param);
			logger.debug("执行批量更新操作，影响了" + rowNum + "行数据");
			return rowNum;
		} catch (Exception e) {
			throw e;
		} finally {
			DBUtils.close(conn);
		}
	}

	/**
	 * 支持事物的方法
	 * 
	 * @param executor
	 * @throws Exception
	 */
	public static void executeInTrans(ExecutorInTrans executor) throws Exception {
		Connection conn = null;
		try {
			conn = DBUtils.getConn();
			conn.setAutoCommit(false);
			logger.debug("开启事务.....");
			executor.execute();
			conn.commit();
			logger.debug("提交事务.....");
			conn.setAutoCommit(true);
		} catch (Exception e) {
			if (conn != null) {
				conn.rollback();
				conn.setAutoCommit(true);
				logger.debug("回滚事物.....");
			}
			throw e;
		} finally {
			DBUtils.close(conn);
		}
	}

	/**
	 * 记录sql语句执行日志
	 * 
	 * @param sql
	 * @param param
	 */
	public static void log(String sql, Object... param) {
		StringBuffer sb = new StringBuffer("");
		for (Object p : param) {
			sb.append(p).append(",");
		}
		logger.debug("数据库执行sql语句为:[" + sql + "], 参数为:[" + sb + "]");
	}

	/**
	 * 执行插入操作
	 * 
	 * @param sql
	 * @param param
	 * @throws Exception
	 */
	public static void insert(Object param, String table) throws Exception {
		Connection conn = null;
		try {
			conn = DBUtils.getConn();
			insert(param, conn, table);
		} catch (Exception e) {
			throw e;
		} finally {
			DBUtils.close(conn);
		}
	}

	/**
	 * 获取总数
	 * 
	 * @param sql
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static int queryCount(String sql, Object... param) throws Exception {
		Connection conn = null;
		try {
			conn = DBUtils.getConn();
			QueryRunner qr = new QueryRunner();
			String sql1 = "select count(*) from (" + sql + ") as gc";
			log(sql, param);
			Object[] list = qr.query(conn, sql1, new ArrayHandler(), param);
			if (list != null && list.length > 0) {
				return Integer.parseInt(String.valueOf(list[0]));
			} else {
				return 0;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DBUtils.close(conn);
		}
	}

	/**
	 * 执行插入操作，支持事务
	 * 
	 * @param obj
	 * @param conn
	 * @param table
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static void insert(Object obj, Connection conn, String table) throws Exception {
		// 利用反射拿到实体中的数据和方法
		Class clazz = obj.getClass();
		Method[] methods = clazz.getDeclaredMethods();
		Field[] fields = clazz.getDeclaredFields();
		// 获取操作的表单名字,[这里类名必须和表名一致]
		String fieldsName = "";
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getName().equals("cert") || Modifier.toString(fields[i].getModifiers()).contains("public")) {
				continue;
			}
			fieldsName += fields[i].getName() + ",";
		}
		fieldsName = fieldsName.substring(0, fieldsName.length() - 1);

		// 占位符的设置
		String placeholder = "";
		for (int j = 0; j < fields.length; j++) {
			// 拼接属性的get的方法名
			String str = "get" + fields[j].getName();
			if (fields[j].getName().equals("cert") || Modifier.toString(fields[j].getModifiers()).contains("public")) {
				continue;
			}
			for (int k = 0; k < methods.length; k++) {
				if (str.equalsIgnoreCase(methods[k].getName())) {
					placeholder += "?" + ",";
				}
			}
		}
		placeholder = placeholder.substring(0, placeholder.length() - 1);
		List<Object> param = new ArrayList<Object>();
		// 拼接sql语句
		String sql = "insert into " + table + "(" + fieldsName + ")" + " values " + "(" + placeholder + ")";
		PreparedStatement pst = conn.prepareStatement(sql);
		int index = 1;
		for (int j = 0; j < fields.length; j++) {
			String str = "get" + fields[j].getName();
			if (fields[j].getName().equals("cert") || Modifier.toString(fields[j].getModifiers()).contains("public")) {
				continue;
			}
			// 循环方法名比对
			for (int k = 0; k < methods.length; k++) {
				// 如果当前的属性拼出的get方法名,与方法明集合中的有一样的执行
				if (str.equalsIgnoreCase(methods[k].getName())) {
					// 接收指定的方法执行后的数据
					Object p = methods[k].invoke(obj);
					param.add(p);
					// 为指定的占位符进行赋值
					pst.setObject(index++, p);
				}
			}
		}
		log(sql, param);
		// 执行已经加载的sql语句
		pst.executeUpdate();
	}
}
