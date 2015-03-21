package com.netboy.schedule.job;

import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * 流程包括：删除zk节点、触发操作动作、检查合并结果、zk写节点、删除注册任务节点、回写数据库，有限状态机执行，单线程执行不需要考虑并发问题
 * 
 * @author quzhu.wl 2015年3月18日下午3:28:31
 */
public class ScanStatusPipleJob implements Runnable {
	private static final Logger LOGGER = LoggerFactory.getLogger(ScanStatusPipleJob.class);
	private int offLine = 0;// 0 未执行，1 正在执行，2执行成功， -1 执行失败
	private int optIndex = 0;// 优化索引事件状态
	private int optDone = 0;// 是否完成合并的事件状态
	private int onLine = 0;// 节点执行上线的事件状态
	private int delDone = 0;// 删除执行完机器列表的事件状态
	private int wrDb = 0;// 回写到数据库的事件状态
	private JobManager jobManager;
	private TaskDO taskDO;
	private String jobId;

	public ScanStatusPipleJob(TaskDO taskDO, String jobId) {
		this.taskDO = taskDO;
		this.jobId = jobId;
	}

	public ScanStatusPipleJob(TaskDO taskDO) {
		this.taskDO = taskDO;
		this.jobId = taskDO.getJobGroup() + "-" + taskDO.getJobName();
	}

	/**
	 * 流程管道化，由状态值控制流程操作
	 */
	@Override
	public void run() {
		// 所有流程走完就终止任务,从定时调度的list中去掉
		if ((offLine + optIndex + optDone + onLine + delDone + wrDb) == 12) {
			try {
				Future future = jobManager.getJobFutureMap().remove(jobId);
				future.cancel(true);
			} catch (Exception e) {
				LOGGER.error("the jobId [{}] 's future is null", jobId);
			} finally {
				LOGGER.info("the jobId [{}]'s has get out scheduleAtFixedRatePool", jobId);
			}
		} else {
			
		}
	}

}
