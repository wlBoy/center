package com.xn.hk.common.utils.tool;

/**
 * 
 * @ClassName: BlockCpuThread
 * @Package: kl.comm.tools
 * @Description: 阻塞CPU的线程类
 * @Author: wanl
 * @Date: 2018年9月27日 下午7:13:02
 */
public class BlockCpuThread extends Thread {
	// 线程启动标识
	private boolean runningFlag = true;

	public BlockCpuThread() {
		super();
	}

	public BlockCpuThread(boolean runningFlag, String threadName) {
		super();
		this.runningFlag = runningFlag;
		this.setName(threadName);
	}

	public boolean isRunningFlag() {
		return runningFlag;
	}

	public void setRunningFlag(boolean runningFlag) {
		this.runningFlag = runningFlag;
	}

	/**
	 * 计算消耗CPU,当线程启动标识为true时，计算；否则跳出死循环,不计算
	 */
	@Override
	@SuppressWarnings("unused")
	public void run() {
		for (;;) {
			if (runningFlag) {
				double test = Math.random() * Math.random() * Math.random() * Math.random();
			} else {
				break;
			}
		}
	}

}
