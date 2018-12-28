package com.xn.hk.common.utils.db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xn.hk.common.constant.Constant;
import com.xn.hk.common.utils.file.FileUtil;
import com.xn.hk.common.utils.string.StringUtil;

/**
 * 
 * @ClassName: DbInit
 * @Package: com.xn.hk.common.utils.jdbc
 * @Description: 数据库初始化工具类
 * @Author: wanlei
 * @Date: 2018年11月7日 上午10:26:58
 */
public class DbInit {
	private static final Logger logger = LoggerFactory.getLogger(DbInit.class);
	private static final String SQL = "sql";

	/**
	 * 初始化数据库，指定sql文件存放目录，使用程序自动执行sql语句，创建数据库
	 * 
	 * @param sqlFilePath
	 *            sql存放位置,即存放sql文件的目录
	 * @param fileEncoding
	 *            字符集编码
	 * @return 初始化数据库成功返回true，否则返回false
	 */
	public static boolean initDb(String sqlFilePath, String fileEncoding) {
		boolean flag = false;
		Connection conn = null;
		Statement statement = null;
		try {
			logger.info("==数据库开始初始化==");
			long start = System.currentTimeMillis();
			conn = DbConnPool.getInstance().getConn();
			File[] files = new File(sqlFilePath).listFiles();
			List<String> sqls = readSqlFromFiles(fileEncoding, files);
			statement = conn.createStatement();
			for (String sql : sqls) {
				// 当sql为空时，跳过
				if (StringUtil.isEmpty(sql)) {
					continue;
				}
				try {
					statement.execute(sql);
				} catch (Exception e) {
					logger.error("执行这条sql[{}]语句出错，原因为:{}", sql, e);
				}
			}
			flag = true;
			long end = System.currentTimeMillis();
			logger.info("==数据库初始化成功,共耗时{}s完成!==", (end - start) / 1000);
		} catch (Exception e) {
			logger.error("初始化数据库失败,原因为:{} ", e);
		} finally {
			// 关闭连接资源
			JdbcUtil.closeAll(null, statement, conn);
		}
		return flag;
	}

	/**
	 * 从指定文件中读取sql字符串列表，sql中以";"分隔为独立运行的一条sql
	 * 
	 * @param fileEncoding
	 *            字符集
	 * @param files
	 *            文件列表
	 * @return sql字符串列表
	 * @throws IOException
	 */
	private static List<String> readSqlFromFiles(String fileEncoding, File[] files) throws IOException {
		String sqlStr = null;
		List<String> sqls = new ArrayList<String>();
		for (int i = 0; i < files.length; ++i) {
			File sqlFile = files[i];
			String fileSuffix = FileUtil.getFileSuffix(sqlFile.getName());
			// 排除sql以外的文件
			if (!SQL.equalsIgnoreCase(fileSuffix.trim())) {
				continue;
			}
			logger.info("正在读取的sql文件是{}", sqlFile.getName());
			if (StringUtil.isEmpty(fileEncoding)) {
				sqlStr = FileUtil.readFile2String(sqlFile.getAbsolutePath());
			} else {
				sqlStr = FileUtil.readFile2String(sqlFile.getAbsolutePath(), fileEncoding);
			}
			sqls.addAll(parseSqls(sqlStr.split(";")));
		}
		return sqls;
	}

	/**
	 * 解析sql
	 * 
	 * @param sqlArray
	 * @return
	 */
	private static List<String> parseSqls(String[] sqlArray) {
		List<String> sqlList = new ArrayList<String>();
		for (int i = 0; i < sqlArray.length; ++i) {
			String sql = sqlArray[i].trim();
			if (sql.indexOf("^") != -1) {
				if ("SET TERM ^".equalsIgnoreCase(sql.trim()) && sqlArray.length > i + 1) {
					sqlArray[i + 1] = sqlArray[i + 1] + ";END ";
					continue;
				}
				if (!"^".equals(sql.trim().substring(0, 1))) {
					continue;
				}
				sql = sql.trim().substring(1);
				if (sql.trim().length() < 10) {
					continue;
				}
			}
			if (sql.indexOf("--") == 0) {
				int index = sql.indexOf(10);
				if (index == -1) {
					index = sql.indexOf(13);
				}
				++index;
				sql = sql.substring(index);
			}
			if (sql.toLowerCase().indexOf(":new") != -1 && sql.toUpperCase().indexOf("FROM DUAL") != -1
					&& sql.toUpperCase().indexOf("END;") == -1) {
				sqlArray[i + 1] = sqlArray[i] + ";" + sqlArray[i + 1] + ";";
				sqlArray[i + 1] = sqlArray[i + 1].replaceAll("\r", "");
			} else if (sql.trim().toUpperCase().startsWith("DECLARE") && !sql.toUpperCase().endsWith("END;")) {
				if (sql.endsWith(";")) {
					sqlArray[i + 1] = sqlArray[i] + sqlArray[i + 1] + ";";
					sqlArray[i + 1] = sqlArray[i + 1].replaceAll("\r", "");
				} else {
					sqlArray[i + 1] = sqlArray[i] + ";" + sqlArray[i + 1] + ";";
					sqlArray[i + 1] = sqlArray[i + 1].replaceAll("\r", "");
				}
			} else {
				sqlList.add(sql);
			}
		}
		return sqlList;
	}

	/**
	 * 数据库中是否存在某张数据表
	 * 
	 * @param tableName
	 *            表名
	 * @return 存在返回true，否则返回false
	 */
	public static boolean checkTableExist(String tableName) {
		boolean flag = false;
		String querySql = "SELECT 1 FROM " + tableName;
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DbConnPool.getInstance().getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(querySql);
			flag = true;
		} catch (SQLException e) {
			logger.error("查询{}数据表失败,原因为:{}", tableName, e);
		} finally {
			JdbcUtil.closeAll(rs, stmt, conn);
		}
		return flag;
	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
				+ File.separator + "resources";
		System.out.println(initDb(filePath, Constant.UTF8));
		System.out.println(checkTableExist("tb_xn_sys_user"));
	}
}
