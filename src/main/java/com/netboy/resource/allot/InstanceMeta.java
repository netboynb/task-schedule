package com.netboy.resource.allot;

import java.io.Serializable;

public class InstanceMeta implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long appId;
	private String ip;
	private int nettyPort;
	private int httpPort;
	/** 实例状态，0 未部署，1正在部署， 2 部署成功， -1 部署失败 ，3 等待部署 */
	private int status;
	private int nodeId;
	private String room;
	private String ipPort;
	private int seqIdx;

	public InstanceMeta(Long appId, String ip, int port, int status, int nodeId, String room) {
		this.appId = appId;
		this.ip = ip;
		this.nettyPort = port;
		this.status = status;
		this.nodeId = nodeId;
		this.room = room;
	}
	public InstanceMeta(){
		
	}
	public boolean isEqualsIp( InstanceMeta meta){
		if(ip.equals(meta.getIp())){
			return true;
		}
		return false;
	}
	
	public int getNettyPort() {
		return nettyPort;
	}
	public void setNettyPort(int nettyPort) {
		this.nettyPort = nettyPort;
	}
	public int getHttpPort() {
		return httpPort;
	}
	public void setHttpPort(int httpPort) {
		this.httpPort = httpPort;
	}
	public void setIpPort(String ipPort) {
		this.ipPort = ipPort;
	}
	public int getSeqIdx() {
		return seqIdx;
	}

	public void setSeqIdx(int seqIdx) {
		this.seqIdx = seqIdx;
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
		return ip + ":" + nettyPort;
	}
}
