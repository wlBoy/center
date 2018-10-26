package com.xn.hk.common.utils.redis;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * 
 * @Title: RedisCacheTransfer
 * @Package: com.xn.hk.common.utils
 * @Description: 静态注入中间类
 * @Author: wanlei
 * @Date: 2018年1月12日 下午12:18:25
 */
public class RedisCacheTransfer {
	/**
	 * 调用redis缓存工具类中的生成redis连接工厂方法
	 * 
	 * @param jedisConnectionFactory
	 *            redis连接工厂
	 */
	public void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory) {
		RedisCache.setJedisConnectionFactory(jedisConnectionFactory);
	}
}
