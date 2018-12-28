package com.xn.hk.common.utils.db;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: DbUtil
 * @Package: com.xn.hk.common.utils.db
 * @Description: 使用commons-dbutils访问数据库
 * @Author: wanlei
 * @Date: 2018年12月28日 上午10:18:52
 */
public class DbUtil {
	private static final Logger logger = LoggerFactory.getLogger(DbUtil.class);

	/**
	 * 执行查询语句，返回数据列表
	 * 
	 * @param sql
	 * @param param
	 *            多参，SQL语句中需要查询的值
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> queryList(String sql, Object... param) throws Exception {
		ResultSetHandler<List<Map<String, Object>>> handler = new MapListHandler();
		Connection conn = null;
		try {
			conn = DbConnPool.getInstance().getConn();
			QueryRunner qr = new QueryRunner();
			log(sql, param);
			return qr.query(conn, sql, handler, param);
		} catch (Exception e) {
			logger.error("queryList出错，原因为:{}", e);
			return null;
		} finally {
			DbConnPool.getInstance().closeConn(conn);
		}
	}

	/**
	 * 执行查询语句，返回数据列表
	 * 
	 * @param sql
	 * @param param
	 *            多参，SQL语句中需要查询的值
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> queryList(String sql, Class<T> clazz, Object... param) throws Exception {
		Connection conn = null;
		try {
			conn = DbConnPool.getInstance().getConn();
			QueryRunner qr = new QueryRunner();
			log(sql, param);
			return qr.query(conn, sql, new BeanListHandler<>(clazz, new BasicRowProcessor()), param);
		} catch (Exception e) {
			logger.error("queryList出错，原因为:{}", e);
			return null;
		} finally {
			DbConnPool.getInstance().closeConn(conn);
		}
	}

	/**
	 * 执行查询语句，返回一行数据
	 * 
	 * @param sql
	 * @param param
	 *            多参，SQL语句中需要查询的值
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> queryOneRow(String sql, Object... param) throws Exception {
		ResultSetHandler<List<Map<String, Object>>> handler = new MapListHandler();
		Connection conn = null;
		try {
			conn = DbConnPool.getInstance().getConn();
			QueryRunner qr = new QueryRunner();
			log(sql, param);
			List<Map<String, Object>> list = qr.query(conn, sql, handler, param);
			if (list != null && list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("queryOneRow出错，原因为:{}", e);
			return null;
		} finally {
			DbConnPool.getInstance().closeConn(conn);
		}
	}

	/**
	 * 执行查询语句，返回一行数据
	 * 
	 * @param sql
	 * @param param
	 *            多参，SQL语句中需要查询的值
	 * @return
	 * @throws Exception
	 */
	public static <T> T queryOneRow(String sql, Class<T> clazz, Object... param) throws Exception {
		Connection conn = null;
		try {
			conn = DbConnPool.getInstance().getConn();
			QueryRunner qr = new QueryRunner();
			log(sql, param);
			List<T> list = qr.query(conn, sql, new BeanListHandler<>(clazz, new BasicRowProcessor()), param);
			if (list != null && list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("queryOneRow出错，原因为:{}", e);
			return null;
		} finally {
			DbConnPool.getInstance().closeConn(conn);
		}
	}

	/**
	 * 执行查询语句，返回一行数据(传参数据库链接，保持事务)
	 * 
	 * @param conn
	 * @param sql
	 * @param clazz
	 * @param param
	 * @return
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
	 * 执行修改语句
	 * 
	 * @param sql
	 * @param param
	 * @throws Exception
	 */
	public static int update(String sql, Object... param) throws Exception {
		Connection conn = null;
		try {
			conn = DbConnPool.getInstance().getConn();
			QueryRunner qr = new QueryRunner();
			log(sql, param);
			int rowNum = qr.update(conn, sql, param);
			logger.debug("影响了" + rowNum + "行数据");
			return rowNum;
		} catch (Exception e) {
			logger.error("update出错，原因为:{}", e);
			return 0;
		} finally {
			DbConnPool.getInstance().closeConn(conn);
		}
	}

	/**
	 * 执行修改语句
	 * 
	 * @param sql
	 * @param param
	 * @throws Exception
	 */
	public static int batchUpdate(String sql, Object... param) throws Exception {
		Connection conn = null;
		try {
			conn = DbConnPool.getInstance().getConn();
			QueryRunner qr = new QueryRunner();
			log(sql, param);
			int rowNum = qr.update(conn, sql, param);
			logger.debug("影响了" + rowNum + "行数据");
			return rowNum;
		} catch (Exception e) {
			logger.error("update出错，原因为:{}", e);
			return 0;
		} finally {
			DbConnPool.getInstance().closeConn(conn);
		}
	}

	/**
	 * 支持事务的方法
	 * 
	 * @param executor
	 * @throws Exception
	 */
	public static void executeInTrans(ExecutorInTrans executor) throws Exception {
		Connection conn = null;
		try {
			conn = DbConnPool.getInstance().getConn();
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
				logger.debug("回滚事务.....");
			}
			logger.error("executeInTrans出错，原因为:{}", e);
		} finally {
			DbConnPool.getInstance().closeConn(conn);
		}
	}

	/**
	 * 记录sql语句执行日志
	 * 
	 * @param sql
	 * @param param
	 */
	public static void log(String sql, Object... param) {
		if (logger.isDebugEnabled()) {
			StringBuffer sb = new StringBuffer("");
			for (Object p : param) {
				sb.append(p).append(",");
			}
			logger.debug("执行sql语句 :" + sql + ", 参数:" + sb);
		}
	}

	/**
	 * 执行修改语句
	 * 
	 * @param sql
	 * @param param
	 * @throws Exception
	 */
	public static void insert(Object param, String table) throws Exception {
		Connection conn = null;
		try {
			conn = DbConnPool.getInstance().getConn();
			insert(param, conn, table);
		} catch (Exception e) {
			logger.error("insert出错，原因为:{}", e);
		} finally {
			DbConnPool.getInstance().closeConn(conn);
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
			conn = DbConnPool.getInstance().getConn();
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
			logger.error("insert出错，原因为:{}", e);
			return 0;
		} finally {
			DbConnPool.getInstance().closeConn(conn);
		}
	}

	/**
	 * 插入数据
	 * 
	 * @param obj
	 * @param conn
	 * @param table
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	private static void insert(Object obj, Connection conn, String table)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		Class clazz = obj.getClass();
		Method[] methods = clazz.getDeclaredMethods();
		Field[] fields = clazz.getDeclaredFields();

		// 获取操作的表单名字,[这里类名必须和表名一致]
		// String table = clazz.getSimpleName().toLowerCase();
		String fieldsName = "";
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getName().equals("cert") || Modifier.toString(fields[i].getModifiers()).contains("public")) {
				continue;
			}
			fieldsName = fieldsName + fields[i].getName() + ",";
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
					placeholder = placeholder + "?" + ",";
				}
			}
		}
		placeholder = placeholder.substring(0, placeholder.length() - 1);
		// 拼接sql语句
		String sql = "insert into " + table + "(" + fieldsName + ")" + " values " + "(" + placeholder + ")";
		System.out.println(sql);
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
					// 为指定的占位符进行赋值
					pst.setObject(index++, p);
				}
			}
		}
		// 执行已经加载的sql语句
		pst.executeUpdate();
	}
}
