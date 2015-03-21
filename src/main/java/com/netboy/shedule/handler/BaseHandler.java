package com.netboy.shedule.handler;

import com.netboy.schedule.job.JobManager;
import com.netboy.schedule.job.TaskDO;



public abstract class BaseHandler {
	private JobManager jobManager;
	
	public abstract boolean doHandle(TaskDO taskDO);

	public JobManager getJobManager() {
		return jobManager;
	}

	public void setJobManager(JobManager jobManager) {
		this.jobManager = jobManager;
	}
	
}
