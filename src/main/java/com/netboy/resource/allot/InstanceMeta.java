package com.netboy.resource.allot;

public class InstanceMeta {
	private Long appId;
	private String ip;
	private int port;
	/** 实例状态，0 未部署，1正在部署， 2 部署成功， -1 部署失败 ，3 等待部署 */
	private int status;
	private int nodeId;
	private String room;
	private String ipPort;

	public InstanceMeta(Long appId, String ip, int port, int status, int nodeId, String room) {
		this.appId = appId;
		this.ip = ip;
		this.port = port;
		this.status = status;
		this.nodeId = nodeId;
		this.room = room;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getIpPort() {
		return ip + ":" + port;
	}
}
