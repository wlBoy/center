package com.xn.hk.common.utils.jdbc;

import java.util.Map;
import java.util.Properties;

import com.xn.hk.common.utils.cfg.InitConfigFile;

/**
 * 
 * @ClassName: DbCfg
 * @Package: com.xn.hk.common.utils.jdbc
 * @Description: 初始化系统配置文件db.properties的操作类
 * @Author: wanlei
 * @Date: 2018年11月1日 下午4:10:22
 */
public class DbCfg {
	/**
	 * 数据库配置文件
	 */
	public final static String DB_CFG = "db.properties";
	/*----------------------------------mysql数据库---------------------------------*/
	/**
	 * 数据库驱动
	 */
	public final static String MYSQL_DRIVER = "mysqlDriver";
	/**
	 * 数据库URL
	 */
	public final static String MYSQL_URL = "mysqlUrl";
	/**
	 * 数据库用户名
	 */
	public final static String MYSQL_USERNAME = "mysqlUsername";
	/**
	 * 数据库密码
	 */
	public final static String MYSQL_PASSWORD = "mysqlPassword";

	/**
	 * 方式一:返回Properties实体,读取systemCfg.ini中所有的配置信息(支持中文)
	 * 
	 * @return 返回Properties实体
	 */
	public static Properties loadCfg() {
		return InitConfigFile.getInstance().loadCfg(DB_CFG);
	}

	/**
	 * 方式二:返回配置信息map集合,读取systemCfg.ini中所有的配置信息(支持中文)
	 * 
	 * @return 返回配置信息map集合
	 */
	public static Map<String, String> loadCfgMap() {
		return InitConfigFile.getInstance().loadCfgMap(DB_CFG);
	}
}
