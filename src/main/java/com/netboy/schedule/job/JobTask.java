package com.netboy.schedule.job;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netboy.common.ComUtils;
import com.netboy.schedule.utils.JobUtils;

@DisallowConcurrentExecution
public class JobTask  implements Job {
	private static final Logger LOGGER = LoggerFactory.getLogger(JobTask.class);

	private JobManager jobManager;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		TaskDO taskDO = (TaskDO) context.getMergedJobDataMap().get(JobUtils.SCHEDULE_JOB);
		ScheduledExecutorService service = jobManager.getScheduledExecutorService();
		int delay = jobManager.getInitialDelay();
		int Period = jobManager.getPeriod();
		String jobId = taskDO.getJobGroup() + "-" + taskDO.getJobName();

		ScheduledFuture future = service.scheduleAtFixedRate(new ScanStatusPipleJob(taskDO), delay, Period,
				TimeUnit.MINUTES);
		jobManager.getJobFutureMap().put(jobId, future);
		LOGGER.info("scheduler job ={} has added into scheduleAtFixedRatePool", jobId);
	}

	public void setJobManager(JobManager jobManager) {
		this.jobManager = jobManager;
	}

}
