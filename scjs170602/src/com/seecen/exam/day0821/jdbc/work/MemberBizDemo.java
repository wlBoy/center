package com.seecen.exam.day0821.jdbc.work;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import com.seecen.exam.myUtils.JdbcUtil;

/**
 * 测试会员业务类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月21日
 */
public class MemberBizDemo {
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		MemberBiz mb = new MemberBiz();
		String answer = null;
		JdbcUtil ju = new JdbcUtil();
		Connection con = ju.getConnByFile();
		Statement stmt = null;
		ResultSet rs = null;
		stmt = con.createStatement();
		try {
			do {
				mb.printMenu();
				System.out.print("请选择菜单:");
				int operator = sc.nextInt();
				switch (operator) {
				case 1:
					System.out.println("【录入数据】");
					Member m = new Member();
					System.out.print("请输入会员名称:");
					m.setMemberName(sc.next());
					System.out.print("请输入会员等级:");
					m.setMemberGrade(sc.next());
					if (mb.addMember(m, con, stmt) > 0) {
						System.out.println("插入数据成功!");
					} else {
						System.out.println("插入数据失败!");
					}
					break;
				case 2:
					System.out.println("【修改数据】");
					Member m1 = new Member();
					System.out.print("请输入要修改的会员编号:");
					m1.setMemberId(sc.nextInt());
					System.out.print("请输入会员名称:");
					m1.setMemberName(sc.next());
					System.out.print("请输入会员等级:");
					m1.setMemberGrade(sc.next());
					if (mb.updateMember(m1, con, stmt) > 0) {
						System.out.println("修改数据成功!");
					} else {
						System.out.println("修改数据失败!");
					}
					break;
				case 3:
					System.out.println("【删除数据】");
					System.out.print("请输入要删除的会员编号:");
					if (mb.deleteMember(sc.nextInt(), con, stmt) > 0) {
						System.out.println("删除数据成功!");
					} else {
						System.out.println("删除数据失败!");
					}
					break;
				case 4:
					System.out.println("【查询数据】");
					System.out.println("所有会员信息如下:");
					ArrayList<Member> list = mb.queryMember(con, stmt, rs);
					for (Member me : list) {
						System.out.println(me.getMemberId() + "\t"
								+ me.getMemberName() + "\t"
								+ me.getMemberGrade());
					}
					break;
				default:
					System.out.println("请输入正确的菜单选项!");
					break;
				}
				System.out.print("是否要继续操作系统(y/n):");
				answer = sc.next();
			} while (answer.equals("y"));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
		System.out.println("谢谢使用!");
		System.exit(1);
		sc.close();
	}
}
