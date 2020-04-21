package com.kl.ar.common.cache;

import java.util.ArrayList;
import java.util.Timer;

/**
 * 
 * @ClassName: TimerManager
 * @Package: com.xn.hk.common.utils.cache
 * @Description: 定时任务的管理类
 * @Author: wanlei
 * @Date: 2018年11月12日 上午11:01:23
 */
public class TimerManager {
	// 定时任务列表
	private static ArrayList<Timer> list = new ArrayList<Timer>();

	/**
	 * 注册一个定时任务
	 * 
	 * @param timer
	 *            定时任务
	 */
	public synchronized static void register(Timer timer) {
		list.add(timer);
	}

	/**
	 * 移除一个定时任务
	 * 
	 * @param timer
	 *            定时任务
	 */
	public static synchronized void remove(Timer timer) {
		list.remove(timer);
	}

	/**
	 * 取消所有的定时任务
	 */
	public static void cancel() {
		for (Timer timer : list) {
			timer.purge();
			timer.cancel();
		}
		list.clear();
	}

}
