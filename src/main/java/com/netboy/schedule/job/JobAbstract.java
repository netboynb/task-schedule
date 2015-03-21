package com.netboy.schedule.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netboy.schedule.utils.JobUtils;

@DisallowConcurrentExecution
public abstract class JobAbstract implements Job {
	private static final Logger LOGGER = LoggerFactory.getLogger(JobAbstract.class);
	private int port;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		TaskDO taskJob = (TaskDO) context.getMergedJobDataMap().get(JobUtils.SCHEDULE_JOB);
		Object jobInfo = new Object();
		if (taskJob != null) {
			jobInfo = new Object[] { taskJob.getJobId(), taskJob.getJobGroup(), taskJob.getJobName() };
		}
		if (doJob(context, taskJob)) {
			LOGGER.info("[success]--id:[{}]--group:[{}]--job:[{}]", jobInfo);
		} else {
			LOGGER.error("[  fail ]--id:[{}]--group:[{}]--job:[{}]", jobInfo);
		}
	}

	/**
	 * do your task logic
	 * 
	 * @param context
	 * @param taskJob
	 * @return true \ false 
	 */
	public abstract boolean doJob(JobExecutionContext context, TaskDO taskJob);

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
}
