package com.netboy.quartz.job;

import org.quartz.JobExecutionContext;

import com.netboy.quartz.stateless.JobDO;

public class SimpleJob extends JobAbstract {

	@Override
	public boolean doJob(JobExecutionContext context, JobDO taskJob) {
		System.out.println(taskJob.toString());
		return true;
	}
}
