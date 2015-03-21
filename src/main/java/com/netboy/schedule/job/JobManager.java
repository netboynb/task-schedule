package com.netboy.schedule.job;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

import com.netboy.shedule.handler.BaseHandler;


public class JobManager {
	private ScheduledExecutorService scheduledExecutorService;
	private ConcurrentHashMap<String, Future> jobFutureMap;
	private int poolSize = 10;
	private Map<Integer, BaseHandler> handlerMap;

	private int initialDelay = 2;
	private int period = 10;

	public synchronized void init() {
		scheduledExecutorService = Executors.newScheduledThreadPool(poolSize);
		jobFutureMap = new ConcurrentHashMap<String, Future>();
	}

	public ScheduledExecutorService getScheduledExecutorService() {
		return scheduledExecutorService;
	}

	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}


	public int getInitialDelay() {
		return initialDelay;
	}

	public void setInitialDelay(int initialDelay) {
		this.initialDelay = initialDelay;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public synchronized ConcurrentHashMap<String, Future> getJobFutureMap() {
		return jobFutureMap;
	}

	public void setJobFutureMap(ConcurrentHashMap<String, Future> jobFutureMap) {
		this.jobFutureMap = jobFutureMap;
	}

	public Map<Integer, BaseHandler> getHandlerMap() {
		return handlerMap;
	}

	public void setHandlerMap(Map<Integer, BaseHandler> handlerMap) {
		this.handlerMap = handlerMap;
	}

}
