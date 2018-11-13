package com.xn.hk.common.utils.tool;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: BlockCpuUtil
 * @Package: kl.comm.tools
 * @Description: 通过大量的运算消耗CPU的工具类
 * @Author: wanl
 * @Date: 2018年9月27日 下午8:44:35
 */
public class BlockCpuUtil {
	private static final Logger logger = LoggerFactory.getLogger(BlockCpuUtil.class);
	// 线程list
	private static List<BlockCpuThread> threadList = new ArrayList<BlockCpuThread>();
	/**
	 * 初始化8个CPU非运行线程，线程名为blockCpu(i)
	 */
	static {
		for (int i = 0; i < WarnLevel.LEVE_RED.getId().intValue(); i++) {
			BlockCpuThread thread = new BlockCpuThread(false, "blockCpu" + i);
			threadList.add(thread);
		}

	}

	/**
	 * 消耗cpu的方法
	 * 
	 * @param level
	 *            使用级别，级别越大，消耗的资源越多
	 */
	public synchronized static void runBlockCpuThread(int level) {
		// 计算正在运行的线程数
		int runningThreadNum = getRunningThreads(threadList).size();
		logger.info("{} threads is running!", runningThreadNum);
		if (runningThreadNum > level) {
			// 停掉(runningThreadNumber-level)个正在运行的线程
			List<BlockCpuThread> runningThread = getRunningThreads(threadList);
			for (int i = 0; i < (runningThreadNum - level); i++) {
				runningThread.get(i).setRunningFlag(false);
			}
		} else {
			// 启动(level-runningThreadNumber)个睡眠的线程
			List<BlockCpuThread> sleepThread = getSleepThreads(threadList);
			for (int i = 0; i < (level - runningThreadNum); i++) {
				BlockCpuThread th = sleepThread.get(i);
				th.setRunningFlag(true);
				th.start();// 启动线程
			}
		}

	}

	/**
	 * 得到所有运行的线程列表
	 * 
	 * @param threadList
	 * @return
	 */
	public static List<BlockCpuThread> getRunningThreads(List<BlockCpuThread> threadList) {
		List<BlockCpuThread> runningThread = new ArrayList<>();
		for (BlockCpuThread thread : threadList) {
			if (thread.isRunningFlag()) {
				runningThread.add(thread);
			}
		}
		return runningThread;
	}

	/**
	 * 得到所有睡眠的线程列表
	 * 
	 * @param threadList
	 * @return
	 */
	public static List<BlockCpuThread> getSleepThreads(List<BlockCpuThread> threadList) {
		List<BlockCpuThread> runningThread = new ArrayList<>();
		for (BlockCpuThread thread : threadList) {
			if (!thread.isRunningFlag()) {
				runningThread.add(thread);
			}
		}
		return runningThread;
	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		BlockCpuUtil.runBlockCpuThread(2);// 启动2个线程计算
		Thread.sleep(10000);
		BlockCpuUtil.runBlockCpuThread(4);// 启动4个线程计算
		Thread.sleep(10000);
		BlockCpuUtil.runBlockCpuThread(0);// 启动0个线程计算,即关闭所有线程
	}

}
