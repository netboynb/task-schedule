package com.netboy.schedule.job;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class TaskDO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long appId;
	
	private String jobId;

	private String jobName;

	private String jobGroup;

	/** 任务状态 0禁用 1启用 2删除 */
	private String jobStatus;

	private String cronExpression;

	/** job's desc */
	private String desc;

	private String ips;// 逗号分隔
	
	private Map<Integer, Integer> nodeIdSeqIdxMap;
	
	private Map<String, Integer> ipHttpPortMap;

	private boolean success;

	private String zkUrl;
	private List<String> nodeList;
	

	public TaskDO(String jobId, String jobName, String jobGroup, String jobStatus, String cronExpression, String desc,
			String ips) {
		this.jobId = jobId;
		this.jobName = jobName;
		this.jobGroup = jobGroup;
		this.jobStatus = jobStatus;
		this.cronExpression = cronExpression;
		this.desc = desc;
		this.ips = ips;
	}

	public TaskDO(String jobName, String jobGroup, String cronExpression, String ips) {
		this(null, jobName, jobGroup, null, cronExpression, null, ips);
	}

	public TaskDO(String jobName, String jobGroup, String cronExpression) {
		this(null, jobName, jobGroup, null, cronExpression, null, null);
	}

	public TaskDO(String jobName, String jobGroup) {
		this(null, jobName, jobGroup, null, null, null, null);
	}

	public TaskDO() {

	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public String getZkUrl() {
		return zkUrl;
	}

	public void setZkUrl(String zkUrl) {
		this.zkUrl = zkUrl;
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

	public List<String> getNodeList() {
		return nodeList;
	}

	public void setNodeList(List<String> nodeList) {
		this.nodeList = nodeList;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getIps() {
		return ips;
	}

	public void setIps(String ips) {
		this.ips = ips;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Map<Integer, Integer> getNodeIdSeqIdxMap() {
		return nodeIdSeqIdxMap;
	}

	public void setNodeIdSeqIdxMap(Map<Integer, Integer> nodeIdSeqIdxMap) {
		this.nodeIdSeqIdxMap = nodeIdSeqIdxMap;
	}

	public Map<String, Integer> getIpHttpPortMap() {
		return ipHttpPortMap;
	}

	public void setIpHttpPortMap(Map<String, Integer> ipHttpPortMap) {
		this.ipHttpPortMap = ipHttpPortMap;
	}

	public String toString() {
		return "id=" + jobId + "--group=" + jobGroup + "--job=" + jobName;
	}

}
