package com.netboy.quartz.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import com.netboy.quartz.stateless.JobDO;

@DisallowConcurrentExecution
public class JobTask extends JobAbstract {
	@Override
	public boolean doJob(JobExecutionContext context, JobDO taskJob) {
		System.out.println(taskJob.toString());
		System.out.println("port = "+ getPort());
		return true;
	}
}
