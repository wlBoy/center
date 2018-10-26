package com.xn.hk.common.utils.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 
 * @ClassName: TestTask
 * @Package: com.xn.hk.common.utils.quartz
 * @Description: 测试quartz定时任务的类(一定要实现org.quartz.Job接口，复写父类中的excute方法，定时任务方可生效)
 * @Author: wanlei
 * @Date: 2018年10月18日 下午4:36:14
 */
public class TestTask implements Job {
	/**
	 * 此方法写定时调用的逻辑功能代码
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("测试quartz定时任务!");
	}

}
