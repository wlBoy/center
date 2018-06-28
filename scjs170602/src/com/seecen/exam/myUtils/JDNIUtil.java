package com.seecen.exam.myUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * 通过数据库连接池(JNDI)对数据库操作的工具类
 * 
 * @DrivingExam
 * @author 【万磊】
 * @2017年8月22日
 */
public class JDNIUtil {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	/**
	 * 通过数据库连接池(JNDI)来获取连接
	 * @return 数据库连接
	 * @throws Exception
	 */
	public Connection getConnByDataSource() throws Exception {
		//实例化JNDL,通过JDNI(Java命名和目录接口)获取数据库连接
		Context initContext = new InitialContext();
		DataSource ds = (DataSource)initContext.lookup("java:/comp/env/jdbc/myoracle");
		Connection conn = ds.getConnection();
		return conn;
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
			conn = getConnByDataSource();
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
			conn = getConnByDataSource();
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
}
