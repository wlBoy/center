package com.seecen.exam.day0822;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库工具类(老师封装的)
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月22日
 */
public class DBUtils {

	private static final String DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
	private static final String USER_NAME = "scott";
	private static final String PWD = "admin";
	/**
	 * 注册数据库驱动
	 */
	static {
		try {
			Class.forName(DRIVER_CLASS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取数据库连接
	 * 
	 * @return 数据库连接
	 * @throws Exception
	 */
	public static Connection getCon() throws Exception {
		Connection con = DriverManager.getConnection(URL, USER_NAME, PWD);
		return con;
	}

	/**
	 * 释放数据库所有资源
	 * 
	 * @param rs
	 *            结果集
	 * @param stmt
	 *            指令
	 * @param con
	 *            数据库连接
	 * @throws Exception
	 */
	public static void closeAll(ResultSet rs, Statement stmt, Connection con)
			throws Exception {
		if (rs != null)
			rs.close();
		if (stmt != null)
			stmt.close();
		if (con != null)
			con.close();
	}

	/**
	 * 该方法可执行数据库的增，删，改操作
	 * 
	 * @param sql
	 *            要执行的增，删，改SQL语句
	 * @param param
	 *            预编译的SQL语句中的'?'参数的字符串数组,可变参数
	 * @return 返回执行操作后影响的条数
	 */
	public static int doUpdate(String sql, Object... param) {
		int result = 0;
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = getCon();
			pstm = con.prepareStatement(sql);
			for (int i = 0; i < param.length; i++) {
				pstm.setObject(i + 1, param[i]);
			}
			result = pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeAll(null, pstm, con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 查找部门
	 * 
	 * @param sql
	 *            要执行的查询SQL语句
	 * @param param
	 *            预编译的SQL语句中的'?'参数的字符串数组,可变参数
	 * @return 部门对象数组
	 */
	public static List<Dept> findDept(String sql, Object... param) {
		ResultSet rs = null;
		PreparedStatement pstm = null;
		Connection con = null;
		List<Dept> list = new ArrayList<Dept>();
		;
		try {
			con = getCon();
			pstm = con.prepareStatement(sql);
			// 有参数就会进去设置
			for (int i = 0; i < param.length; i++) {
				pstm.setObject(i + 1, param[i]);
			}
			rs = pstm.executeQuery();
			while (rs.next()) {
				Dept dept = new Dept();
				dept.setDeptno(rs.getInt("deptno"));
				dept.setDname(rs.getString("dname"));
				dept.setLoc(rs.getString("loc"));
				list.add(dept);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeAll(rs, pstm, con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 数据库数据的批处理操作
	 * 
	 * @param sql
	 *            要执行的增，删，改SQL语句
	 * @param ids
	 *            要执行的数组条件
	 * @return 返回执行操作后影响的条数
	 */
	public static int doBatch(String sql, List<Integer> ids) {
		int result = 0;
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = getCon();
			pstm = con.prepareStatement(sql);
			for (int i = 0; i < ids.size(); i++) {
				int id = ids.get(i);
				pstm.setInt(1, id);
				// 将当前指令添加到批处理集合中
				pstm.addBatch();
			}
			// 执行所有批处理
			pstm.executeBatch();
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeAll(null, pstm, con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
