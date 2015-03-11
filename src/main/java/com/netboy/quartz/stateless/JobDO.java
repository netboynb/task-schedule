package com.netboy.quartz.stateless;

import java.io.Serializable;
import java.util.Map;

public class JobDO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String jobId;

	private String jobName;

	private String jobGroup;

	/** 任务状态 0禁用 1启用 2删除 */
	private String jobStatus;

	private String cronExpression;

	/** job's desc */
	private String desc;
	
	private String ips;//逗号分隔
	/**
	 * job's data
	 */
	private Map<String, Object> dataMap;

	public JobDO(String jobId, String jobName, String jobGroup, String jobStatus, String cronExpression, String desc,String ips) {
		this.jobId = jobId;
		this.jobName = jobName;
		this.jobGroup = jobGroup;
		this.jobStatus = jobStatus;
		this.cronExpression = cronExpression;
		this.desc = desc;
		this.ips = ips;
	}

	public JobDO(String jobName, String jobGroup, String cronExpression,String ips) {
		this(null, jobName, jobGroup, null, cronExpression, null, ips);
	}
	public JobDO(String jobName, String jobGroup, String cronExpression){
		this(null, jobName, jobGroup, null, cronExpression, null, null);
	}
	public JobDO(String jobName, String jobGroup) {
		this(null, jobName, jobGroup, null, null, null, null);
	}

	public JobDO() {

	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public String getIps() {
		return ips;
	}

	public void setIps(String ips) {
		this.ips = ips;
	}

	public String toString() {
		return "id=" + jobId + "--group=" + jobGroup + "--job=" + jobName;
	}

}
