package com.xn.hk.common.utils.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xn.hk.common.utils.date.DateUtil;

/**
 * 
 * @ClassName: JdbcUtil
 * @Package: com.xn.hk.common.utils.jdbc
 * @Description: jdbc操作数据库的工具类
 * @Author: wanlei
 * @Date: 2018年11月1日 下午4:06:23
 */
public class JdbcUtil {
	private static final Logger logger = LoggerFactory.getLogger(JdbcUtil.class);

	/**
	 * 可以执行新增，修改，删除
	 *
	 * @param sql
	 *            sql语句
	 * @param bindArgs
	 *            绑定参数
	 * @return 影响的行数
	 */
	public static int executeUpdate(String sql, Object[] bindArgs) {
		// 影响的行数
		int affectRowCount = -1;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			// 从数据库连接池中获取数据库连接
			connection = DbConnPool.getInstance().getConn();
			// 执行SQL预编译
			preparedStatement = connection.prepareStatement(sql);
			// 设置不自动提交，以便于在出现异常的时候数据库回滚
			connection.setAutoCommit(false);
			if (bindArgs != null) {
				// 绑定参数设置sql占位符中的值
				for (int i = 0; i < bindArgs.length; i++) {
					preparedStatement.setObject(i + 1, bindArgs[i]);
				}
			}
			// 执行sql
			affectRowCount = preparedStatement.executeUpdate();
			connection.commit();
			String operate;
			if (sql.toUpperCase().indexOf("DELETE FROM") != -1) {
				operate = "删除";
			} else if (sql.toUpperCase().indexOf("INSERT INTO") != -1) {
				operate = "添加";
			} else {
				operate = "修改";
			}
			logger.info("成功" + operate + "了" + affectRowCount + "行，执行的sql为:" + sql + "!");
		} catch (Exception e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					logger.error("事务回滚失败，原因为:{}", e1);
				}
			}
			logger.error("执行executeUpdate方法失败，原因为:{}", e);
		} finally {
			closeAll(null, preparedStatement, connection);
		}
		return affectRowCount;
	}

	/**
	 * 释放数据库所有资源
	 * 
	 * @param rs
	 * @param pstmt
	 * @param conn
	 */
	public static void closeAll(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		// 如果rs不空，关闭rs
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				logger.error("关闭结果集失败，原因为:{}", e);
			}
		}
		// 如果pstmt不空，关闭pstmt
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				logger.error("执行预编译指令失败，原因为:{}", e);
			}
		}
		// 如果conn不空，关闭conn
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error("执行数据库连接失败，原因为:{}", e);
			}
		}
	}

	/**
	 * 释放数据库所有资源
	 * 
	 * @param rs
	 * @param stmt
	 * @param conn
	 */
	public static void closeAll(ResultSet rs, Statement stmt, Connection conn) {
		// 如果rs不空，关闭rs
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				logger.error("关闭结果集失败，原因为:{}", e);
			}
		}
		// 如果stmt不空，关闭stmt
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				logger.error("执行编译指令失败，原因为:{}", e);
			}
		}
		// 如果conn不空，关闭conn
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error("执行数据库连接失败，原因为:{}", e);
			}
		}
	}

	/**
	 * 执行查询
	 *
	 * @param sql
	 *            要执行的sql语句
	 * @param bindArgs
	 *            绑定的参数
	 * @return List<Map<String, Object>>结果集对象
	 * @throws SQLException
	 *             SQL执行异常
	 */
	public static List<Map<String, Object>> executeQuery(String sql, Object[] bindArgs) {
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			// 获取数据库连接池中的连接
			connection = DbConnPool.getInstance().getConn();
			preparedStatement = connection.prepareStatement(sql);
			if (bindArgs != null) {
				// 设置sql占位符中的值
				for (int i = 0; i < bindArgs.length; i++) {
					preparedStatement.setObject(i + 1, bindArgs[i]);
				}
			}
			// 执行sql语句，获取结果集
			resultSet = preparedStatement.executeQuery();
			// 将结果集转换为List<Map<String,Object>>存储
			datas = ResultSetToList(resultSet);
			logger.info("成功执行的sql为:{}", sql);
		} catch (Exception e) {
			logger.error("执行executeQuery方法失败，原因为:{}", e);
		} finally {
			closeAll(resultSet, preparedStatement, connection);
		}
		return datas;
	}

	/**
	 * 将结果集中的数据按照List<Map<String,Object>>集合返回
	 * 
	 * @param rs
	 *            结果集
	 * @return List<Map<String,Object>>
	 * @throws SQLException
	 */
	private static List<Map<String, Object>> ResultSetToList(ResultSet rs) throws SQLException {
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int colCount = rsmd.getColumnCount();
		List<String> colNameList = new ArrayList<String>();
		for (int i = 0; i < colCount; i++) {
			colNameList.add(rsmd.getColumnName(i + 1));
		}
		while (rs.next()) {
			for (int i = 0; i < colCount; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				String key = colNameList.get(i);
				Object value = rs.getString(colNameList.get(i));
				map.put(key, value);
				results.add(map);
			}
		}
		return results;
	}

	/**
	 * 执行数据库插入操作
	 *
	 * @param valueMap
	 *            插入数据表中key为列名和value为列对应的值的Map对象
	 * @param tableName
	 *            要插入的数据库的表名
	 * @return 影响的行数
	 */
	public static int insert(String tableName, Map<String, Object> valueMap) {
		// 获取数据库插入的Map的键值对的值
		Set<String> keySet = valueMap.keySet();
		Iterator<String> iterator = keySet.iterator();
		// 要插入的字段sql，其实就是用key拼起来的
		StringBuilder columnSql = new StringBuilder();
		// 要插入的字段值，其实就是？
		StringBuilder unknownMarkSql = new StringBuilder();
		Object[] bindArgs = new Object[valueMap.size()];
		int i = 0;
		while (iterator.hasNext()) {
			String key = iterator.next();
			columnSql.append(i == 0 ? "" : ",");
			columnSql.append(key);

			unknownMarkSql.append(i == 0 ? "" : ",");
			unknownMarkSql.append("?");
			bindArgs[i] = valueMap.get(key);
			i++;
		}
		// 开始拼接要插入的sql语句
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO ");
		sql.append(tableName);
		sql.append(" (");
		sql.append(columnSql);
		sql.append(" )  VALUES (");
		sql.append(unknownMarkSql);
		sql.append(" )");
		return executeUpdate(sql.toString(), bindArgs);
	}

	/**
	 * 执行更新操作
	 *
	 * @param tableName
	 *            表名
	 * @param valueMap
	 *            要更改的值
	 * @param whereMap
	 *            条件
	 * @return 影响的行数
	 */
	public static int update(String tableName, Map<String, Object> valueMap, Map<String, Object> whereMap) {
		// 获取数据库插入的Map的键值对的值
		Set<String> keySet = valueMap.keySet();
		Iterator<String> iterator = keySet.iterator();
		// 开始拼接要更新的sql语句
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE ");
		sql.append(tableName);
		sql.append(" SET ");
		// 要更改的的字段sql，其实就是用key拼起来的
		StringBuilder columnSql = new StringBuilder();
		int i = 0;
		List<Object> objects = new ArrayList<>();
		while (iterator.hasNext()) {
			String key = iterator.next();
			columnSql.append(i == 0 ? "" : ",");
			columnSql.append(key + " = ? ");
			objects.add(valueMap.get(key));
			i++;
		}
		sql.append(columnSql);

		// 更新的条件:要更改的的字段sql，其实就是用key拼起来的
		StringBuilder whereSql = new StringBuilder();
		int j = 0;
		if (whereMap != null && whereMap.size() > 0) {
			whereSql.append(" WHERE ");
			iterator = whereMap.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				whereSql.append(j == 0 ? "" : " AND ");
				whereSql.append(key + " = ? ");
				objects.add(whereMap.get(key));
				j++;
			}
			sql.append(whereSql);
		}
		return executeUpdate(sql.toString(), objects.toArray());
	}

	/**
	 * 执行删除操作
	 *
	 * @param tableName
	 *            要删除的表名
	 * @param whereMap
	 *            删除的条件
	 * @return 影响的行数
	 */
	public static int delete(String tableName, Map<String, Object> whereMap) {
		// 准备删除的sql语句
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM ");
		sql.append(tableName);
		// 删除的条件:要删除的的字段sql，其实就是用key拼起来的
		StringBuilder whereSql = new StringBuilder();
		Object[] bindArgs = null;
		if (whereMap != null && whereMap.size() > 0) {
			bindArgs = new Object[whereMap.size()];
			whereSql.append(" WHERE ");
			/** 获取数据库插入的Map的键值对的值 **/
			Set<String> keySet = whereMap.keySet();
			Iterator<String> iterator = keySet.iterator();
			int i = 0;
			while (iterator.hasNext()) {
				String key = iterator.next();
				whereSql.append(i == 0 ? "" : " AND ");
				whereSql.append(key + " = ? ");
				bindArgs[i] = whereMap.get(key);
				i++;
			}
			sql.append(whereSql);
		}
		return executeUpdate(sql.toString(), bindArgs);
	}

	/**
	 * 执行查询操作(查询总记录数)
	 *
	 * @param tableName
	 *            要删除的表名
	 * @param whereMap
	 *            删除的条件
	 * @return 影响的行数
	 */
	public static int queryCount(String tableName, Map<String, Object> whereMap) {
		Object[] bindArgs = null;
		// 准备查询的sql语句
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(1) AS COUNT FROM ");
		sql.append(tableName);
		// 查询的条件:要查询的的字段sql，其实就是用key拼起来的
		StringBuilder whereSql = new StringBuilder();
		if (whereMap != null && whereMap.size() > 0) {
			bindArgs = new Object[whereMap.size()];
			whereSql.append(" WHERE ");
			// 获取数据库插入的Map的键值对的值
			Set<String> keySet = whereMap.keySet();
			Iterator<String> iterator = keySet.iterator();
			int i = 0;
			while (iterator.hasNext()) {
				String key = iterator.next();
				whereSql.append(i == 0 ? "" : " AND ");
				whereSql.append(key + " = ? ");
				bindArgs[i] = whereMap.get(key);
				i++;
			}
			sql.append(whereSql);
		}
		List<Map<String, Object>> list = executeQuery(sql.toString(), bindArgs);
		return Integer.parseInt(String.valueOf(list.get(0).get("COUNT")));
	}

	/**
	 * 执行查询操作(最简单的条件查询)
	 *
	 * @param tableName
	 *            要删除的表名
	 * @param whereMap
	 *            删除的条件
	 * @return 影响的行数
	 */
	public static List<Map<String, Object>> queryList(String tableName, Map<String, Object> whereMap) {
		Object[] bindArgs = null;
		// 准备查询的sql语句
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM ");
		sql.append(tableName);
		// 查询的条件:要查询的的字段sql，其实就是用key拼起来的
		StringBuilder whereSql = new StringBuilder();
		if (whereMap != null && whereMap.size() > 0) {
			bindArgs = new Object[whereMap.size()];
			whereSql.append(" WHERE ");
			// 获取数据库插入的Map的键值对的值
			Set<String> keySet = whereMap.keySet();
			Iterator<String> iterator = keySet.iterator();
			int i = 0;
			while (iterator.hasNext()) {
				String key = iterator.next();
				whereSql.append(i == 0 ? "" : " AND ");
				whereSql.append(key + " = ? ");
				bindArgs[i] = whereMap.get(key);
				i++;
			}
			sql.append(whereSql);
		}
		return executeQuery(sql.toString(), bindArgs);
	}

	/**
	 * 执行查询操作(带排序字段的条件查询)
	 *
	 * @param tableName
	 *            要删除的表名
	 * @param whereMap
	 *            删除的条件
	 * @param orderBy
	 *            排序的字段名
	 * @param isAsc
	 *            true为升序，false为降序
	 * @return 影响的行数
	 */
	public static List<Map<String, Object>> queryList(String tableName, Map<String, Object> whereMap, String orderBy,
			boolean isAsc) {
		Object[] bindArgs = null;
		// 准备查询的sql语句
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM ");
		sql.append(tableName);
		// 查询的条件:要查询的的字段sql，其实就是用key拼起来的
		StringBuilder whereSql = new StringBuilder();
		if (whereMap != null && whereMap.size() > 0) {
			bindArgs = new Object[whereMap.size()];
			whereSql.append(" WHERE ");
			// 获取数据库插入的Map的键值对的值
			Set<String> keySet = whereMap.keySet();
			Iterator<String> iterator = keySet.iterator();
			int i = 0;
			while (iterator.hasNext()) {
				String key = iterator.next();
				whereSql.append(i == 0 ? "" : " AND ");
				whereSql.append(key + " = ? ");
				bindArgs[i] = whereMap.get(key);
				i++;
			}
			sql.append(whereSql);
		}
		sql.append(" ORDER BY ");
		sql.append(orderBy);
		if (isAsc) {
			sql.append(" ASC ");
		} else {
			sql.append(" DESC ");
		}
		return executeQuery(sql.toString(), bindArgs);
	}

	/**
	 * 执行查询操作(带排序字段带分页的条件查询,分页只支持mysql分页)
	 *
	 * @param tableName
	 *            要删除的表名
	 * @param whereMap
	 *            删除的条件
	 * @param orderBy
	 *            排序的字段名
	 * @param isAsc
	 *            true为升序，false为降序
	 * @param start
	 *            开始位置
	 * @param end
	 *            结束位置
	 * @return 影响的行数
	 */
	public static List<Map<String, Object>> queryList(String tableName, Map<String, Object> whereMap, String orderBy,
			boolean isAsc, int start, int end) {
		Object[] bindArgs = null;
		// 准备查询的sql语句
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM ");
		sql.append(tableName);
		// 查询的条件:要查询的的字段sql，其实就是用key拼起来的
		StringBuilder whereSql = new StringBuilder();
		if (whereMap != null && whereMap.size() > 0) {
			bindArgs = new Object[whereMap.size()];
			whereSql.append(" WHERE ");
			// 获取数据库插入的Map的键值对的值
			Set<String> keySet = whereMap.keySet();
			Iterator<String> iterator = keySet.iterator();
			int i = 0;
			while (iterator.hasNext()) {
				String key = iterator.next();
				whereSql.append(i == 0 ? "" : " AND ");
				whereSql.append(key + " = ? ");
				bindArgs[i] = whereMap.get(key);
				i++;
			}
			sql.append(whereSql);
		}
		sql.append(" ORDER BY ");
		sql.append(orderBy);
		if (isAsc) {
			sql.append(" ASC ");
		} else {
			sql.append(" DESC ");
		}
		sql.append(" LIMIT ");
		sql.append(start);
		sql.append(",");
		sql.append(end);
		return executeQuery(sql.toString(), bindArgs);
	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int flag = 4;
		if (flag == 1) {
			System.out.println("***********测试插入**********");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("role_id", 112);
			map.put("role_name", "测试角色");
			map.put("create_time", DateUtil.formatDateTime(new Date()));
			map.put("update_time", DateUtil.formatDateTime(new Date()));
			map.put("remark", "test");
			System.out.println(insert("tb_xn_sys_role", map));
		} else if (flag == 2) {
			System.out.println("***********测试更新**********");
			Map<String, Object> map = new HashMap<>();
			map.put("remark", "test");
			Map<String, Object> whereMap = new HashMap<>();
			whereMap.put("role_id", "111");
			System.out.println(update("tb_xn_sys_role", map, whereMap));
		} else if (flag == 3) {
			System.out.println("***********测试删除**********");
			Map<String, Object> whereMap = new HashMap<>();
			whereMap.put("role_id", "111");
			System.out.println(delete("tb_xn_sys_role", whereMap));
		} else if (flag == 4) {
			System.out.println("***********测试查询总数**********");
			Map<String, Object> whereMap = new HashMap<>();
			System.out.println(queryCount("tb_xn_sys_role", whereMap));
		} else {
			System.out.println("***********测试查询列表**********");
			Map<String, Object> whereMap = new HashMap<>();
			List<Map<String, Object>> list = queryList("tb_xn_sys_role", whereMap, "role_id", false, 3, 6);
			for (Map<String, Object> map : list) {
				Set<String> keys = map.keySet();
				for (String key : keys) {
					System.out.print(key + ":" + map.get(key) + ",");
				}
				System.out.println("");
			}
		}

	}
}
