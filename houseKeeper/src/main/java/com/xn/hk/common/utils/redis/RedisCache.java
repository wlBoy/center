package com.xn.hk.common.utils.redis;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * 
 * @Title: RedisCache
 * @Package: com.xn.hk.common.utils
 * @Description: 使用第三方内存数据库Redis作为二级缓存
 * @Company: 杭州讯牛
 * @Author: wanlei
 * @Date: 2018年1月12日 下午12:20:03
 */
public class RedisCache implements Cache {
	/**
	 * 记录日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);
	private static JedisConnectionFactory jedisConnectionFactory;
	private final String id;
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	public RedisCache(final String id) {
		if (id == null) {
			throw new IllegalArgumentException("缓存示例需要ID!");
		}
		logger.info("Mybatis-Redis缓存ID为:" + id);
		this.id = id;
	}

	/**
	 * 清除redis缓存
	 */
	public void clear() {
		JedisConnection connection = null;
		try {
			connection = (JedisConnection) jedisConnectionFactory.getConnection();
			connection.flushDb();
			connection.flushAll();
		} catch (JedisConnectionException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	/**
	 * 得到该redis缓存的ID
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * 根据key获取redis缓存的object
	 */
	public Object getObject(Object key) {
		Object result = null;
		JedisConnection connection = null;
		try {
			connection = (JedisConnection) jedisConnectionFactory.getConnection();
			RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
			result = serializer.deserialize(connection.get(serializer.serialize(key)));
		} catch (JedisConnectionException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		return result;
	}

	/**
	 * 获取redis缓存的锁
	 */
	public ReadWriteLock getReadWriteLock() {
		return this.readWriteLock;
	}

	/**
	 * 获取redis缓存的大小
	 */
	public int getSize() {
		int result = 0;
		JedisConnection connection = null;
		try {
			connection = (JedisConnection) jedisConnectionFactory.getConnection();
			result = Integer.valueOf(connection.dbSize().toString());
		} catch (JedisConnectionException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		return result;
	}

	/**
	 * 将一对key-value键值对的值放入redis缓存中
	 */
	public void putObject(Object key, Object value) {
		JedisConnection connection = null;
		try {
			connection = (JedisConnection) jedisConnectionFactory.getConnection();
			RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
			connection.set(serializer.serialize(key), serializer.serialize(value));
		} catch (JedisConnectionException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	/**
	 * 根据key值从redis中删除该key对应的value值
	 */
	public Object removeObject(Object key) {
		JedisConnection connection = null;
		Object result = null;
		try {
			connection = (JedisConnection) jedisConnectionFactory.getConnection();
			RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
			result = connection.expire(serializer.serialize(key), 0);
		} catch (JedisConnectionException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		return result;
	}
	/**
	 * 生成redis连接工厂方法
	 */
	public static void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory) {
		RedisCache.jedisConnectionFactory = jedisConnectionFactory;
	}

}
