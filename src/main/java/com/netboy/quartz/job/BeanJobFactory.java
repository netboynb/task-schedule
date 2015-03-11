package com.netboy.quartz.job;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

public class BeanJobFactory extends SpringBeanJobFactory {
	private int port;
	
	 @Override
	 protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
	        JobTask jobTask = (JobTask)super.createJobInstance(bundle);
	        jobTask.setPort(port);
	        return jobTask;
	    }

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	 
}
