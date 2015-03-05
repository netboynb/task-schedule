package com.netboy.scheduler.demo;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloJob implements Job {
	private static final Logger LOGGER = LoggerFactory.getLogger(HelloJob.class);
	
	/**
	 * quartz need 
	 */
	public HelloJob(){
	}
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("hi this is HelloJob");
		
	}

}
