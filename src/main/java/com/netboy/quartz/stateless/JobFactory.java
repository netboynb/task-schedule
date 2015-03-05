package com.netboy.quartz.stateless;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


@DisallowConcurrentExecution
public class JobFactory implements Job {

	 @Override
	    public void execute(JobExecutionContext context) throws JobExecutionException {
	        System.out.println("任务成功运行");
	        TaskJob scheduleJob = (TaskJob)context.getMergedJobDataMap().get(JobUtils.SCHEDULE_JOB);
	        System.out.println("任务名称 = [group= " +scheduleJob.getJobGroup()+ "--job= "+scheduleJob.getJobName() + "]");
	    }
}
