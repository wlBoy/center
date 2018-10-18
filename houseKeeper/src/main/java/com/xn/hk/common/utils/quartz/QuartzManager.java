package com.xn.hk.common.utils.quartz;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: QuartzManager
 * @Package: com.xn.hk.common.utils.quartz
 * @Description: 定时任务管理类,以当前时间为触发频率立刻触发一次执行,然后按照Cron频率依次执行
 * @Author: wanlei
 * @Date: 2018年10月18日 下午4:09:34
 */
public class QuartzManager {
	private static final Logger logger = LoggerFactory.getLogger(QuartzManager.class);
	private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
	private static String JOB_GROUP_NAME = "DEFAULT_EXTJWEB_JOBGROUP_NAME";
	private static String TRIGGER_GROUP_NAME = "DEFAULT_EXTJWEB_TRIGGERGROUP_NAME";

	/**
	 * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
	 * 
	 * @param jobName
	 *            任务名
	 * @param jobClass
	 *            任务
	 * @param time
	 *            cron表达式
	 */
	public static void addJob(String jobName, String jobClass, String time) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			JobDetail jobDetail = newJob().withIdentity(jobName, JOB_GROUP_NAME).build();
			Trigger trigger = newTrigger().withIdentity(jobName, TRIGGER_GROUP_NAME)
					.withSchedule(CronScheduleBuilder.cronSchedule(time).withMisfireHandlingInstructionIgnoreMisfires())
					.build();
			sched.scheduleJob(jobDetail, trigger);
			if (!sched.isShutdown()) {
				sched.start();
			}
		} catch (Exception e) {
			logger.error("创建定时任务{}失败,原因为:{}", jobName, e);
		}
	}

	/**
	 * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
	 * 
	 * @param jobName
	 *            任务名
	 * @param jobClass
	 *            执行任务的.class类名
	 * @param time
	 *            cron表达式
	 */
	public static void addJob(String jobName, Class<? extends Job> jobClass, String time) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			JobDetail jobDetail = newJob(jobClass).withIdentity(jobName, JOB_GROUP_NAME).build();
			Trigger trigger = newTrigger().withIdentity(jobName, TRIGGER_GROUP_NAME)
					.withSchedule(CronScheduleBuilder.cronSchedule(time).withMisfireHandlingInstructionIgnoreMisfires())
					.build();
			sched.scheduleJob(jobDetail, trigger);
			if (!sched.isShutdown()) {
				sched.start();
			}
		} catch (Exception e) {
			logger.error("创建定时任务{}失败,原因为:{}", jobName, e);
		}
	}

	/**
	 * 添加一个定时任务
	 * 
	 * @param jobName
	 *            任务名
	 * @param jobGroupName
	 *            任务组名
	 * @param triggerName
	 *            触发器名
	 * @param triggerGroupName
	 *            触发器组名
	 * @param jobClass
	 *            任务
	 * @param time
	 *            cron表达式
	 */
	@SuppressWarnings("unchecked")
	public static void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName,
			String jobClass, String time) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			JobDetail jobDetail = newJob((Class<Job>) Class.forName(jobClass)).withIdentity(jobName, JOB_GROUP_NAME)
					.build();
			Trigger trigger = newTrigger().withIdentity(jobName, TRIGGER_GROUP_NAME)
					.withSchedule(CronScheduleBuilder.cronSchedule(time).withMisfireHandlingInstructionIgnoreMisfires())
					.build();
			sched.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
			logger.error("创建定时任务{}失败,原因为:{}", jobName, e);
		}
	}

	/**
	 * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
	 * 
	 * @param jobName
	 * @param time
	 */
	public static void modifyJobTime(String jobName, String time) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			CronTrigger trigger = (CronTrigger) sched.getTrigger(new TriggerKey(jobName, TRIGGER_GROUP_NAME));
			if (trigger == null) {
				return;
			}
			String oldTime = trigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(time)) {
				JobDetail jobDetail = sched.getJobDetail(new JobKey(jobName, JOB_GROUP_NAME));
				Class<? extends Job> objJobClass = jobDetail.getJobClass();
				String jobClass = objJobClass.getName();
				removeJob(jobName);
				addJob(jobName, jobClass, time);
			}
		} catch (Exception e) {
			logger.error("修改定时任务{}时间失败,原因为:{}", jobName, e);
		}
	}

	/**
	 * 修改一个任务的触发时间
	 * 
	 * @param triggerName
	 * @param triggerGroupName
	 * @param time
	 */
	public static void modifyJobTime(String triggerName, String triggerGroupName, String time) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			CronTrigger trigger = (CronTrigger) sched.getTrigger(new TriggerKey(triggerName, TRIGGER_GROUP_NAME));
			if (trigger == null) {
				return;
			}
			String oldTime = trigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(time)) {
				Trigger newTrigger = newTrigger().withIdentity(triggerName, TRIGGER_GROUP_NAME)
						.withSchedule(
								CronScheduleBuilder.cronSchedule(time).withMisfireHandlingInstructionIgnoreMisfires())
						.build();
				sched.rescheduleJob(trigger.getKey(), newTrigger);
			}
		} catch (Exception e) {
			logger.error("修改触发器{}时间失败,原因为:{}", triggerName, e);
		}
	}

	/**
	 * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
	 * 
	 * @param jobName
	 *            任务名
	 */
	public static void removeJob(String jobName) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			sched.pauseTrigger(new TriggerKey(jobName, TRIGGER_GROUP_NAME));
			sched.unscheduleJob(new TriggerKey(jobName, TRIGGER_GROUP_NAME));
			sched.deleteJob(new JobKey(jobName, JOB_GROUP_NAME));
		} catch (Exception e) {
			logger.error("移除定时任务{}失败,原因为:{}", jobName, e);
		}
	}

	/**
	 * 移除一个任务
	 * 
	 * @param jobName
	 * @param jobGroupName
	 * @param triggerName
	 * @param triggerGroupName
	 */
	public static void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			sched.pauseTrigger(new TriggerKey(triggerName, triggerGroupName));
			sched.unscheduleJob(new TriggerKey(triggerName, triggerGroupName));
			sched.deleteJob(new JobKey(jobName, jobGroupName));
		} catch (Exception e) {
			logger.error("移除定时任务{}失败,原因为:{}", jobName, e);
		}
	}

	/**
	 * 启动所有定时任务
	 */
	public static void startJobs() {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			sched.start();
		} catch (Exception e) {
			logger.error("开启所有定时任务失败,原因为:{}", e);
		}
	}

	/**
	 * 关闭所有定时任务
	 */
	public static void shutdownJobs() {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			if (!sched.isShutdown()) {
				sched.shutdown();
			}
		} catch (Exception e) {
			logger.error("关闭所有定时任务失败,原因为:{}", e);
		}
	}

	/**
	 * 
	 * 创建简单任务，按给定时间间隔执行
	 * 
	 * @param jobName
	 *            任务名称
	 * @param job
	 *            执行任务的.class类名
	 * @param period
	 *            间隔时间 /秒
	 */
	public static void createSimpleJob(String jobName, Class<? extends Job> job, int period) {
		try {
			JobDetail jobDetail = JobBuilder.newJob(job).withIdentity(jobName, JOB_GROUP_NAME).build();
			SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(period)
					.repeatForever();
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, TRIGGER_GROUP_NAME).startNow()
					.withSchedule(builder.withMisfireHandlingInstructionIgnoreMisfires()).build();
			Scheduler scheduler = gSchedulerFactory.getScheduler();
			scheduler.scheduleJob(jobDetail, trigger);

			if (!scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (SchedulerException e) {
			logger.error("创建简单定时任务{}失败,原因为:{}", jobName, e);
		}
	}

	/**
	 * 
	 * 重新唤醒意外中断的定时任务
	 * 
	 */
	public static void resumeJob() {
		Scheduler scheduler;
		try {
			scheduler = gSchedulerFactory.getScheduler();
			List<String> triggerGroups = scheduler.getTriggerGroupNames();
			for (int i = 0; i < triggerGroups.size(); i++) {
				List<String> triggers = scheduler.getTriggerGroupNames();
				for (int j = 0; j < triggers.size(); j++) {
					Trigger tg = scheduler.getTrigger(new TriggerKey(triggers.get(j), triggerGroups.get(i)));
					if (tg != null && TRIGGER_GROUP_NAME.equals(tg.getDescription())) {
						scheduler.resumeJob(new JobKey(triggers.get(j), triggerGroups.get(i)));
					}
				}
			}
			if (!scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (SchedulerException e) {
			logger.error("重新唤醒意外中断的定时任务,原因为:{}", e);
		}
	}

	public static void main(String[] args) throws SchedulerException {
		/*  try {
		      addJob("TESTJob", TestJob.class, "0/2 * * * * ?");
		  } catch (Exception e) {
		     e.printStackTrace();
		  }
		  addJob("RATimingTask", RATimingTask.class, "0/2 * * * * ?");
		  Scheduler scheduler = gSchedulerFactory.getScheduler();
		  // ①获取调度器中所有的触发器组
		  List<String> triggerGroups = scheduler.getTriggerGroupNames();
		  // ②重新恢复在tgroup1组中，名为trigger1触发器的运行
		  for (int i = 0; i < triggerGroups.size(); i++) {//这里使用了两次遍历，针对每一组触发器里的每一个触发器名，和每一个触发组名进行逐次匹配
		      List<String> triggers = scheduler.getTriggerGroupNames();
		      for (int j = 0; j < triggers.size(); j++) {
		          Trigger tg = scheduler.getTrigger(new TriggerKey(triggers.get(j), triggerGroups.get(i)));
		          // ②-1:根据名称判断
		          if (tg != null && TRIGGER_GROUP_NAME.equals(tg.getDescription())) {//由于我们之前测试没有设置触发器所在组，所以默认为DEFAULT
		              // ②-1:恢复运行
		              scheduler.resumeJob(new JobKey("RATimingTask",
		                  TRIGGER_GROUP_NAME));
		          }
		      }
		  }
		  if(!scheduler.isShutdown()) {
		      scheduler.start();
		  }
		  Scheduler scheduler = gSchedulerFactory.getScheduler();
		  scheduler.resumeJob(new JobKey("RATimingTask", TRIGGER_GROUP_NAME));
		  scheduler.start();*/
		  resumeJob();
	}

}
