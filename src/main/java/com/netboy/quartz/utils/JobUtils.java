package com.netboy.quartz.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netboy.quartz.job.JobTask;
import com.netboy.quartz.stateless.JobDO;

public class JobUtils {
	public final static Logger LOGGER = LoggerFactory.getLogger(JobUtils.class);

	public final static String SCHEDULE_JOB = "scheduleJob";

	/**
	 * 添加任务,misfire策略为：不触发立即执行，等待下一次时刻时执行
	 * 
	 * @param scheduleJob
	 * @return -1 添加异常，0 添加成功，1 该调度已经存在
	 */
	public static int addJob(Scheduler scheduler, JobDO job) {
		if (scheduler == null || job == null) {
			LOGGER.error("scheduler == null || job == null");
			return -1;
		}

		LOGGER.info("scheduler add {}", job.toString());
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());

		CronTrigger trigger = null;
		try {
			trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			if (null == trigger) {
				JobDetail jobDetail = JobBuilder.newJob(JobTask.class)
						.withIdentity(job.getJobName(), job.getJobGroup())
						.withDescription(job.getIps())
						.build();
				jobDetail.getJobDataMap().put(SCHEDULE_JOB, job);
				
				// 当无trigger指向该job时，依旧保留在数据库中
				jobDetail.isDurable();
				// 系统重启时 立即执行job
				jobDetail.requestsRecovery();
				// 表达式调度构建器,misfire策略为：不触发立即执行，等待下一次时刻时执行
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression())
						.withMisfireHandlingInstructionDoNothing();

				// 按新的cronExpression表达式构建一个新的trigger
				trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup())
						.withSchedule(scheduleBuilder).build();

				scheduler.scheduleJob(jobDetail, trigger);
				return 0;
			} else {
				LOGGER.warn("the job [{}] has exist", job.toString());
				return 1;
			}
		} catch (SchedulerException e) {
			LOGGER.error("add job [{}] fail,info={}", job.toString(), e.toString());
		}
		return -1;
	}

	/**
	 * 获取所有计划中的任务列表
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	public static List<JobDO> getAllJob(Scheduler scheduler) throws SchedulerException {
		GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
		Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
		List<JobDO> jobList = new ArrayList<JobDO>();
		for (JobKey jobKey : jobKeys) {
			List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
			for (Trigger trigger : triggers) {
				JobDO job = new JobDO();
				job.setJobName(jobKey.getName());
				job.setJobGroup(jobKey.getGroup());
				job.setDesc("trigger:" + trigger.getKey());
				Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
				job.setJobStatus(triggerState.name());
				if (trigger instanceof CronTrigger) {
					CronTrigger cronTrigger = (CronTrigger) trigger;
					String cronExpression = cronTrigger.getCronExpression();
					job.setCronExpression(cronExpression);
				}
				jobList.add(job);
			}
		}
		return jobList;
	}

	/**
	 * 所有正在运行的job
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	public static List<JobDO> getRunningJob(Scheduler scheduler) throws SchedulerException {
		List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
		List<JobDO> jobList = new ArrayList<JobDO>(executingJobs.size());
		for (JobExecutionContext executingJob : executingJobs) {
			JobDO job = new JobDO();
			JobDetail jobDetail = executingJob.getJobDetail();
			JobKey jobKey = jobDetail.getKey();
			Trigger trigger = executingJob.getTrigger();
			job.setJobName(jobKey.getName());
			job.setJobGroup(jobKey.getGroup());
			job.setDesc("trigger:" + trigger.getKey());
			Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
			job.setJobStatus(triggerState.name());
			if (trigger instanceof CronTrigger) {
				CronTrigger cronTrigger = (CronTrigger) trigger;
				String cronExpression = cronTrigger.getCronExpression();
				job.setCronExpression(cronExpression);
			}
			jobList.add(job);
		}
		return jobList;
	}

	/**
	 * 暂停一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public static void pauseJob(Scheduler scheduler, JobDO taskJob) throws SchedulerException {
		JobKey jobKey = JobKey.jobKey(taskJob.getJobName(), taskJob.getJobGroup());
		scheduler.pauseJob(jobKey);
	}

	/**
	 * 恢复一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public static void resumeJob(Scheduler scheduler, JobDO taskJob) throws SchedulerException {
		JobKey jobKey = JobKey.jobKey(taskJob.getJobName(), taskJob.getJobGroup());
		scheduler.resumeJob(jobKey);
	}

	/**
	 * 删除一个job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public static void deleteJob(Scheduler scheduler, JobDO taskJob) throws SchedulerException {
		JobKey jobKey = JobKey.jobKey(taskJob.getJobName(), taskJob.getJobGroup());
		scheduler.deleteJob(jobKey);

	}

	/**
	 * 立即执行job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public static void runAJobNow(Scheduler scheduler, JobDO taskJob) throws SchedulerException {
		JobKey jobKey = JobKey.jobKey(taskJob.getJobName(), taskJob.getJobGroup());
		scheduler.triggerJob(jobKey);
	}

	/**
	 * 更新job时间表达式
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public static void updateJobCron(Scheduler scheduler, JobDO taskJob) throws SchedulerException {
		TriggerKey triggerKey = TriggerKey.triggerKey(taskJob.getJobName(), taskJob.getJobGroup());
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(taskJob.getCronExpression());
		trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
		scheduler.rescheduleJob(triggerKey, trigger);
	}
}
