package com.netboy.scheduler.demo;

import static org.quartz.DateBuilder.evenMinuteDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class HelloSheduler {

	public void run() throws SchedulerException {
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler = schedulerFactory.getScheduler();
		scheduler.start();
		// define the job and tie it to our HelloJob class
		JobDetail job = newJob(HelloJob.class).withIdentity("job1", "group1").build();

		// computer a time that is on the next round minute
		//Date runTime = evenMinuteDate(new Date());

		// Trigger the job to run now, and then repeat every 40 seconds
		Trigger trigger = newTrigger().withIdentity("trigger1", "group1").startNow().build();
		try {
			// wait 65 seconds to show job
			Thread.sleep(2000);
			// executing...
		} catch (Exception e) {
		}
		// Tell quartz to schedule the job using our trigger
		scheduler.scheduleJob(job, trigger);
		scheduler.shutdown();
	}

	public static void main(String[] args) {
		HelloSheduler helloSheduler = new HelloSheduler();
		try {
			helloSheduler.run();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
