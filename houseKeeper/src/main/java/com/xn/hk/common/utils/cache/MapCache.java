package com.xn.hk.common.utils.cache;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: MapCache
 * @Package: com.xn.hk.common.utils.cache
 * @Description: map的缓存类
 * @Author: wanlei
 * @Date: 2018年11月12日 上午11:04:24
 */
public class MapCache<K, V> extends TimerTask implements IMapCache<K, V>, Serializable {
	private static final long serialVersionUID = -5163817326010036146L;
	private Logger logger = LoggerFactory.getLogger(MapCache.class);
	private int cacheSize = -1;
	ConcurrentHashMap<K, V> cacheMap = new ConcurrentHashMap<K, V>();
	// 创建key的ListCache缓存列表
	private ListCache<K> keyList = new ListCache<K>() {
		private static final long serialVersionUID = -2422034408358408025L;

		public void onRemoveFromCache(K element) {
			cacheMap.remove(element);
		};
	};

	/**
	 * 构造函数，不限制缓存大小，不定期清理
	 */
	public MapCache() {
		this(-1);
	}

	/**
	 * 构造函数
	 * 
	 * @param cacheSize
	 *            缓存大小,-1不限制
	 */
	public MapCache(int cacheSize) {
		this(cacheSize, -1);
	}

	/**
	 * 构造函数
	 * 
	 * @param cacheSize
	 *            缓存大小,-1不限制
	 * @param period
	 *            清理周期,-1不清理
	 */
	public MapCache(int cacheSize, int period) {
		this(cacheSize, period, 0);
	}

	/**
	 * 构造函数
	 * 
	 * @param cacheSize
	 *            缓存大小,-1不限制
	 * @param period
	 *            清理周期,-1不清理
	 * @param delay
	 *            延期启动时间，单位分钟
	 */
	public MapCache(int cacheSize, int period, int delay) {
		this.cacheSize = cacheSize;
		if (period > 0) {
			Timer timer = new Timer();
			TimerManager.register(timer);
			timer.schedule(this, delay * 60 * 1000, period * 60 * 1000);
		}
		if (cacheSize > 0 && period > 0) {
			logger.debug("启用MapCache缓存成功，缓存容量[cacheSize]={},定时清理周期[period]={},延期启动时间[delay]={}", cacheSize, period,
					delay);
		}
	}

	public boolean inCacheKey(K key) {
		if (null == key) {
			return false;
		}
		return cacheMap.keySet().contains(key);
	}

	public boolean inCacheValue(V value) {
		return cacheMap.values().contains(value);
	}

	@Override
	public void cache(K k, V v) {
		keyList.cache(k);
		cacheMap.put(k, v);
		logger.debug("缓存[key={},value={}]", k, v);
	}

	@Override
	public void remove(K key) {
		cacheMap.remove(key);
	}

	@Override
	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	@Override
	public int getCacheSize() {
		return cacheSize;
	}

	@Override
	public void clear() {
		keyList.clear();
		cacheMap.clear();
	}

	@Override
	public void run() {
		keyList.run();
	}

	@Override
	public V get(K k) {
		return cacheMap.get(k);
	}

	public Set<K> getKeys() {
		return cacheMap.keySet();
	}

	public Collection<V> getValues() {
		return cacheMap.values();
	}

	/**
	 * 测试方法,过5秒缓存一个字符串
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// 创建一个MapCache<String,String>定时缓存
		MapCache<String, String> cache = new MapCache<String, String>(5000, 1);
		for (int i = 0; i < 100000; i++) {
			cache.cache("key" + i, "value" + i);
			try {
				Thread.sleep(5 * 1000);// 睡眠5秒
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
