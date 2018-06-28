package com.seecen.exam.day0821.jdbc.work;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * 会员业务类 1. 录入数据 ---》输入会员名称，会员等级 2. 修改数据 ---》输入要修改的会员编号，输入新名称，新等级 3. 删除数据
 * ---》输入要删除的会员编号 4. 查询数据 ---》显示所有数据
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月21日
 */
public class MemberBiz {
	/**
	 * 打印系统菜单
	 */
	public void printMenu() {
		System.out.println("*******欢迎进入会员管理系统***********");
		System.out.println("\t1.录入数据");
		System.out.println("\t2.修改数据");
		System.out.println("\t3.删除数据");
		System.out.println("\t4.查询数据");
		System.out.println("*********************************");
	}

	/**
	 * 添加会员信息
	 * @param m 要添加的会员对象
	 * @return 添加成功返回影响行数,否则返回0
	 * @throws SQLException SQL异常
	 */
	public int addMember(Member m,Connection con, Statement stmt) throws SQLException {
		String sql = "insert into member_tab values(seq_member.nextval,'"
				+ m.getMemberName() + "','" + m.getMemberGrade() + "')";
		int num = stmt.executeUpdate(sql);
		return num;
	}

	/**
	 * 修改会员信息
	 * @param m 新的会员对象
	 * @return 修改成功返回影响行数,否则返回0
	 * @throws SQLException SQL异常
	 */
	public int updateMember(Member m,Connection con, Statement stmt) throws SQLException {
		String sql = "update member_tab set memberName='"
				+ m.getMemberName() + "',memberGrade='"
				+ m.getMemberGrade() + "' where memberId="
				+ m.getMemberId() + "";
		int num = stmt.executeUpdate(sql);
		return num;
	}

	/**
	 * 删除会员信息
	 * @param id 会员编号
	 * @return 删除成功返回影响行数,否则返回0
	 * @throws SQLException SQL异常
	 */
	public int deleteMember(int id,Connection con, Statement stmt) throws SQLException {
		String sql = "delete from member_tab where memberId=" + id + "";
		int num = stmt.executeUpdate(sql);
		return num;
	}

	
	/**
	 * 查询会员信息
	 * 
	 * @return 会员对象数组
	 * @throws SQLException
	 *             SQL异常
	 */
	public ArrayList<Member> queryMember(Connection con, Statement stmt ,ResultSet rs) throws SQLException {
		String sql = "select * from member_tab order by memberId";
		rs = stmt.executeQuery(sql);
		ArrayList<Member> list = new ArrayList<Member>();
		while (rs.next()) {
			int id = rs.getInt("memberId");
			String name = rs.getString("memberName");
			String grade = rs.getString("memberGrade");
			Member m = new Member();
			m.setMemberId(id);
			m.setMemberName(name);
			m.setMemberGrade(grade);
			list.add(m);
		}
		return list;
	}
}
