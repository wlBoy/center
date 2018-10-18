package com.xn.hk.common.utils.quartz;

import java.util.Properties;

/**
 * 
 * @ClassName: TimingTask
 * @Package: com.xn.hk.common.utils.quartz
 * @Description: 注册定时任务和读取配置文件启动类
 * @Author: wanlei
 * @Date: 2018年10月18日 下午4:25:26
 */
public class TimingTask {
	private static TimingTask instacne;

	private TimingTask() {
	}

	/**
	 * 懒汉单例模式初始化实例
	 * 
	 * @return
	 */
	public static synchronized TimingTask getInstance() {
		if (instacne == null) {
			instacne = new TimingTask();
		}
		return instacne;
	}

	/**
	 * 
	 * 初始化定时任务，通过调用QuartzManager.addJob方法可添加n个定时任务
	 * 
	 */
	public void initJob() {
		Properties prop = CronCfg.loadCfg();
		// 1.注册一个cron表达式的定时任务,推荐使用这种方式，可配置的
		QuartzManager.addJob(CronCfg.TEST_TASK, TestTask.class, prop.getProperty(CronCfg.TEST_TASK));
		// 2.注册一个简单定时任务，第3个参数为每隔10秒调用一次
		// QuartzManager.createSimpleJob(TEST_TASK, TestTask.class, 10);
	}

	/**
	 * 测试方法，手动调用
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// 手动调用启动定时任务
		TimingTask.getInstance().initJob();
	}

}
