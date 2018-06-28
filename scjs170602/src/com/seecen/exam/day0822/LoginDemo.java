package com.seecen.exam.day0822;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.ResultSet;

import com.seecen.exam.myUtils.JdbcUtil;

/**
 * 模拟登录 防止SQL注入
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月22日
 */
public class LoginDemo {
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("请输入用户名:");
		String username = br.readLine();
		System.out.print("请输入密码:");
		String pwd = br.readLine();
		//使用工具类进行数据库操作
		JdbcUtil ju = new JdbcUtil();
		ResultSet rs = ju.executeQuery("select * from t_student where sname= ? and sclass = ?", username,pwd);
		if (rs.next()) {
			System.out.println("登录成功!");
		}else {
			System.out.println("用户名或密码错误！");
		}
		ju.closeAll();
		br.close();
	/*	查询所有数据库记录
		JdbcUtil ju = new JdbcUtil();
		ResultSet rs = ju.executeQuery("select * from t_student order by sno");
		try {
			while (rs.next()) {
				int sno = rs.getInt("sno");
				String sname = rs.getString("sname");
				String ssex = rs.getString("ssex");
				Date sbirthday = rs.getDate("sbirthday");
				String sclass = rs.getString("sclass");
				System.out.println(sno + "\t" + sname + "\t" + ssex + "\t"
						+ sbirthday + "\t" + sclass);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ju.closeAll();
	 */
	}
	/*public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("请输入用户名:");
		String username = br.readLine();
		System.out.print("请输入密码:");
		String pwd = br.readLine();

		// String sql = "select count(1) from t_student where sname='" +
		// username
		// + "' and sclass='" + pwd + "'";
		String sql = "select count(1) from t_student where sname= ? and sclass = ? ";
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:orcl", "scott", "admin");
			// stmt = con.createStatement();
			// 预编译,预先编译,提升效率
			// 防止sql注入
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, pwd);
			// rs = stmt.executeQuery(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int result = rs.getInt(1);
				if (result > 0) {
					System.out.println("登录成功!");
				} else {
					System.out.println("用户名或密码错误！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		br.close();
	}*/

}
