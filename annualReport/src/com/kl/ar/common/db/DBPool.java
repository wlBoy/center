package com.kl.ar.common.db;

import java.io.InputStream;
import java.util.Properties;

import com.kl.ar.common.constant.Constant;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 
 * @package:com.kl.ar.common.db
 * @Description: c3p0连接池
 * @author: wanlei
 * @date: 2019年12月21日下午2:33:24
 */
public class DBPool {

	private static DBPool instance;

	private ComboPooledDataSource dataSource;

	static {
		instance = new DBPool();
	}

	private DBPool() {
		try {
			dataSource = new ComboPooledDataSource();
			Properties prop = new Properties();
			InputStream in = DBPool.class.getClassLoader().getResourceAsStream(Constant.DB_PROPERTIES);
			prop.load(in);
			dataSource.setDriverClass(prop.getProperty(Constant.DB_JDBC_DRIVER));
			dataSource.setJdbcUrl(prop.getProperty(Constant.DB_URL));
			dataSource.setUser(prop.getProperty(Constant.DB_USERNAME));
			dataSource.setPassword(prop.getProperty(Constant.DB_PASSWORD));
			// 处理数据库连接超时，重连问题
			dataSource.setTestConnectionOnCheckin(false);
			dataSource.setTestConnectionOnCheckout(true);
			dataSource.setInitialPoolSize(10);
			dataSource.setMaxPoolSize(50);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ComboPooledDataSource getDataSource() {
		return instance.dataSource;
	}

}
