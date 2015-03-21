package com.netboy.schedule.job;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

public class BeanJobFactory extends SpringBeanJobFactory {
	private JobManager jobManager;

	@Override
	protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
		JobTask taskJob = (JobTask) super.createJobInstance(bundle);
		taskJob.setJobManager(jobManager);
		return taskJob;
	}

	public void setJobManager(JobManager jobManager) {
		this.jobManager = jobManager;
	}
	 
}
