package com.kl.ar.common.cache;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * 
 * @ClassName: ICache
 * @Package: com.xn.hk.common.utils.cache
 * @Description: List缓存接口
 * @Author: wanlei
 * @Date: 2018年11月12日 上午10:50:12
 */
public interface ICache<T> {
	/**
	 * 缓存一个实体
	 * 
	 * @param t
	 *            实体
	 */
	public void cache(T t);

	/**
	 * 缓存中是否存在该实体
	 * 
	 * @param t
	 *            实体
	 * @return 存在返回true，否则返回false
	 */
	public boolean inCache(T t);

	/**
	 * 移除一个实体
	 * 
	 * @param t
	 *            实体
	 */
	public void remove(T t);

	/**
	 * 设置缓存大小
	 * 
	 * @param cacheSize
	 *            缓存大小
	 */
	public void setCacheSize(int cacheSize);

	/**
	 * 拿到缓存大小
	 * 
	 * @return 缓存大小
	 */
	public int getCacheSize();

	/**
	 * 清除所有缓存
	 */
	public void clear();

	/**
	 * 得到所有的value值集合
	 * 
	 * @return 返回非阻塞并发列表
	 */
	public ConcurrentLinkedDeque<T> getValues();
}
