package com.seecen.exam.day0821.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Jdbc基础掌握
 * 一般步骤:
 * 1. 加载数据库驱动 
 * 2. 获取数据库连接
 * 3. 创建指令
 * 4. 执行指令，得到结果
 * 5. 处理结果，释放数据库资源(一般在finally里释放)
 * @scjs170602
 * @author 【万磊】
 * @2017年8月21日
 */
public class TestJdbc {
	//public static final String ORACLE_URL = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
	public static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
	public static final String ORACLE_username = "scott";
	public static final String ORACLE_password = "admin";
	/*
	 * 各种数据库的URL连接格式:
	 * oracle----> jdbc:oracle:thin:@127.0.0.1:1521:orcl
	 * mssql ----> jdbc:sqlserver://127.0.0.1:1433
	 * db2   ----> jdbc:db2:<db_name>
	 * mysql ----> jdbc:mysql://127.0.0.1:3306/<db_name>
	 */
	public static void main(String[] args) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// 1. 加载数据库驱动
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. 获取数据库连接
			con = DriverManager.getConnection(ORACLE_URL, ORACLE_username,
					ORACLE_password);
			// 默认为自动提交,可设置为false,即手动提交
			con.setAutoCommit(false);
			// 3. 创建指令
			stmt = con.createStatement();
			// 4. 执行指令，得到结果
			// 插入数据
			//String sql = "insert into t_dept values(50,'销售部','china')";
			// 删除数据
			// String sql = "delete from t_dept where deptno=50";
			// 更新数据
			// String sql = "update t_dept set loc='nanchang' where deptno=50 ";
			// 查询数据
			String sql = "select * from t_dept order by deptno desc";
			//该方法可以执行增删改操作
			int num = stmt.executeUpdate(sql);
			//该方法只执行查询操作
			rs = stmt.executeQuery(sql);
			// 5. 处理结果
			if (num > 0) {
				System.out.println("操作成功!");
			} else {
				System.out.println("操作失败!");
			}
			//遍历查询所得到的结果集,有两种方式取(传索引,从1开始或传列名,用了别名就要用别名)
			while (rs.next()) {
				/*int deptno = rs.getInt(1);
				String dname = rs.getString(2);
				String loc = rs.getString(3);*/
				int deptno = rs.getInt("deptno");
				String dname = rs.getString("dname");
				String loc = rs.getString("loc");
				System.out.println(deptno + "-" + dname + "-" + loc);
			}
			//手动提交
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			//如果报错就回滚
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			// 释放数据库资源
			// 如果rs不为空,就关闭,释放数据库资源
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			// 如果stmt不为空,就关闭,释放数据库资源
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			// 如果con不为空,就关闭,释放数据库资源
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
