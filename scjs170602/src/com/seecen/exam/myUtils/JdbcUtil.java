package com.seecen.exam.myUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * jdbc操作数据库的工具类
 * 
 * @DrivingExam
 * @author 【万磊】
 * @2017年8月22日
 */
public class JdbcUtil {
	/**
	 * 数据库驱动
	 */
	// public final static String DB_DRIVER = "com.mysql.jdbc.Driver";
	public final static String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	/**
	 * 数据库URL
	 */
	// public final static String DB_URL = "jdbc:mysql://localhost:3306/testmvc";
	public final static String DB_URL = "jdbc:oracle:thin:@192.168.188.83:1521:orcl";
	/**
	 * 数据库用户名
	 */
	// public final static String DB_NAME = "root";
	public final static String DB_NAME = "scott";
	/**
	 * 数据库密码
	 */
	// public final static String DB_PWD = "admin";
	public final static String DB_PWD = " admin";

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	/**
	 * 得到数据库连接
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @return 数据库连接
	 */
	public Connection getConn() throws Exception {
		// 注册驱动
		Class.forName(DB_DRIVER);
		// 获得数据库连接
		conn = DriverManager.getConnection(DB_URL, DB_NAME, DB_PWD);
		return conn;
	}

	/**
	 * 通过src下的配置文件获取数据库的连接
	 * 
	 * @return 数据库连接
	 * @throws Exception
	 */
	public Connection getConnByFile() throws Exception {
		Properties prop = new Properties();
		// 加载JdbcConfig配置文件
		prop.load(this.getClass().getClassLoader().getResourceAsStream("JdbcConfig"));
		String driver = prop.getProperty("oracle_driver");
		String url = prop.getProperty("oracle_url");
		String username = prop.getProperty("oracle_username");
		String password = prop.getProperty("oracle_password");
		Class.forName(driver);
		return DriverManager.getConnection(url, username, password);
	}

	/**
	 * 释放数据库所有资源
	 * 
	 * @throws SQLException
	 */
	public void closeAll() {
		// 如果rs不空，关闭rs
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 如果pstmt不空，关闭pstmt
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 如果conn不空，关闭conn
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 此方法可以进行数据的增、删、改的操作
	 * 
	 * @param preparedSql
	 *            预编译的SQL语句,即增、删、改的SQL操作语句
	 * @param param
	 *            预编译的SQL语句中的'?'参数的字符串数组,可变参数
	 * @return 返回执行操作后影响的条数
	 */
	public int executeUpdate(String preparedSql, Object... param) {
		int num = 0;
		try {
			// 得到数据库连接
			conn = getConn();
			// 得到PreparedStatement对象
			pstmt = conn.prepareStatement(preparedSql);
			if (param != null) {
				// 为预编译sql设置参数
				for (int i = 0; i < param.length; i++) {
					pstmt.setObject(i + 1, param[i]);
				}
			}
			num = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeAll();
		}
		return num;
	}

	/**
	 * 此方法可以进行数据的查询操作
	 * 
	 * @param preparedSql
	 *            预编译的 SQL语句，即查询的SQL语句
	 * @param param
	 *            预编译的SQL语句中的'?'参数的字符串数组,可变参数
	 * @return 返回查询后的数据结果集
	 */
	public ResultSet executeQuery(String preparedSql, Object... param) {
		try {
			conn = getConn();
			pstmt = conn.prepareStatement(preparedSql);
			if (param != null) {
				for (int i = 0; i < param.length; i++) {
					pstmt.setObject(i + 1, param[i]);
				}
			}
			// 执行SQL语句,返回数据集
			rs = pstmt.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * 此方法可以进行数据的查询操作
	 * 
	 * @param preparedSql
	 *            预编译的 SQL语句，即查询的SQL语句
	 * @param objs
	 *            Object类型的参数List集合
	 * @return 返回查询后的数据结果集
	 */
	public ResultSet executeQuery(String preparedSql, List<Object> objs) {
		try {
			conn = getConn();
			pstmt = conn.prepareStatement(preparedSql);
			for (int i = 0; i < objs.size(); i++) {
				if (objs.get(i) != null) {
					pstmt.setObject(i + 1, objs.get(i));
				}
			}
			// 执行SQL语句,返回数据集
			rs = pstmt.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
}
