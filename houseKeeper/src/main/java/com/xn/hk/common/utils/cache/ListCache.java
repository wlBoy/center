package com.xn.hk.common.utils.cache;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: ListCache
 * @Package: com.xn.hk.common.utils.cache
 * @Description: List集合的缓存类
 * @Author: wanlei
 * @Date: 2018年11月12日 上午11:04:24
 */
public class ListCache<T> extends TimerTask implements ICache<T>, Serializable {
	private static final long serialVersionUID = -7878097465990652850L;
	private Logger logger = LoggerFactory.getLogger(ListCache.class);
	private int cacheSize = -1;
	private ConcurrentLinkedDeque<T> cacheList = new ConcurrentLinkedDeque<T>();
	private ConcurrentHashMap<Integer, Long> keyDateMap = new ConcurrentHashMap<Integer, Long>();
	private T topElement = null;
	private long lastClearTime = System.currentTimeMillis();

	protected ListCache() {
		this(-1);
	}

	/**
	 * 构造函数,立即启动
	 * 
	 * @param period
	 *            清理周期，单位分钟
	 */
	public ListCache(int cacheSize) {
		this(cacheSize, -1);
	}

	public ListCache(int cacheSize, int period) {
		this(cacheSize, period, 0);
	}

	/**
	 * 构造函数
	 * 
	 * @param maxSize
	 *            最大缓存条目
	 * @param period
	 *            清理周期，单位分钟
	 * @param delay
	 *            延期启动时间，单位分钟
	 */
	protected ListCache(int cacheSize, int period, int delay) {
		this.cacheSize = cacheSize;
		if (period > 0) {
			Timer timer = new Timer();
			TimerManager.register(timer);
			timer.schedule(this, delay * 60 * 1000, period * 60 * 1000);
		}
		if (cacheSize > 0 && period > 0) {
			logger.debug("启用ListCache缓存成功，缓存容量[cacheSize]={},定时清理周期[period]={},延期启动时间[delay]={}", cacheSize, period, delay);
		}
	}

	public boolean inCache(T element) {
		return cacheList.contains(element);
	}

	@Override
	public void cache(T t) {
		if (cacheList.contains(t)) {
			cacheList.remove(t);
			cacheList.push(t);
		} else {
			cacheList.push(t);
		}
		logger.info("缓存{}成功", t);
		// 缓存超标，清理末尾对象
		if (-1 != cacheSize && cacheList.size() > cacheSize) {
			onRemoveFromCache(cacheList.pollLast());
		}
		keyDateMap.put(t.hashCode(), System.currentTimeMillis());
	}

	@Override
	public void remove(T t) {
		cacheList.remove(t);
		keyDateMap.remove(t.hashCode());
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
		cacheList.clear();
		keyDateMap.clear();
	}

	@Override
	public void run() {
		T t = cacheList.peekLast();
		while (null != t) {
			long cacheTime = keyDateMap.get(t.hashCode());
			if (cacheTime < lastClearTime) {
				remove(t);
				onRemoveFromCache(t);
			} else {
				break;
			}
			t = cacheList.peekLast();
		}
		lastClearTime = System.currentTimeMillis();

		if (null == topElement) {
			// 首次清理,记录最前面的元素
			topElement = cacheList.peek();
			// 清理，直到topElement
			synchronized (cacheList) {
				while (topElement != cacheList.peekLast()) {
					T keyElement = cacheList.pollLast();
					onRemoveFromCache(keyElement);
				}
			}
		} else {
			// 如果存在则需要清理，如果不存在则不需要清理
			synchronized (cacheList) {
				if (cacheList.contains(topElement)) {
					T keyElement = null;
					do {
						keyElement = cacheList.pollLast();
						onRemoveFromCache(keyElement);
					} while (topElement != keyElement);
				}
			}
			topElement = cacheList.peek();
		}

	}

	public void onRemoveFromCache(T element) {
		remove(element);
	};

	public ConcurrentLinkedDeque<T> getValues() {
		return cacheList;
	}

	/**
	 * 测试方法,过5秒缓存一个字符串
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// 创建一个List<String>定时缓存
		ListCache<String> cache = new ListCache<String>(5000, 1);
		for (int i = 0; i < 100000; i++) {
			cache.cache("" + i);
			try {
				Thread.sleep(5 * 1000);// 睡眠5秒
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
