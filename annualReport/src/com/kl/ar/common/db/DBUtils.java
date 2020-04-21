package com.kl.ar.common.db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 
 * @package:com.kl.ar.common.db
 * @Description: 数据库工具，获取或关闭数据库连接
 * @author: wanlei
 * @date: 2019年12月21日下午2:43:11
 */
public class DBUtils {
	/**
	 * 从连接池中获取数据库连接
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConn() throws SQLException {
		return DBPool.getDataSource().getConnection();
	}

	/**
	 * 关闭数据库连接
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static void close(Connection conn) throws SQLException {
		if (conn != null) {
			conn.close();
		}
	}

}
