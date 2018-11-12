package com.xn.hk.common.utils.cache;

import java.util.Collection;
import java.util.Set;

/**
 * 
 * @ClassName: IMapCache
 * @Package: com.xn.hk.common.utils.cache
 * @Description: Map缓存接口
 * @Author: wanlei
 * @Date: 2018年11月12日 上午10:51:00
 */
public interface IMapCache<K, V> {
	/**
	 * 缓存一个map
	 * 
	 * @param k
	 *            key
	 * @param v
	 *            value
	 */
	public void cache(K k, V v);

	/**
	 * 缓存中是否存在该key
	 * 
	 * @param k
	 *            key
	 * @return 存在返回true，否则返回false
	 */
	public boolean inCacheKey(K k);

	/**
	 * 缓存中是否存在该value
	 * 
	 * @param v
	 *            value
	 * @return 存在返回true，否则返回false
	 */
	public boolean inCacheValue(V v);

	/**
	 * 根据key移除一个value值
	 * 
	 * @param k
	 *            key
	 */
	public void remove(K k);

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
	 * 根据key拿到对应的value值
	 * 
	 * @param k
	 *            key
	 * @return 返回对应的value值
	 */
	public V get(K k);

	/**
	 * 清除所有缓存
	 */
	public void clear();

	/**
	 * 拿到所有的key的set集合
	 * 
	 * @return
	 */
	public Set<K> getKeys();

	/**
	 * 拿到所有的value的集合
	 * 
	 * @return
	 */
	public Collection<V> getValues();

}
