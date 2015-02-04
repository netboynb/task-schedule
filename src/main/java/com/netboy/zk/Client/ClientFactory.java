package com.netboy.zk.Client;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkImpl;
import org.apache.curator.retry.ExponentialBackoffRetry;

/** 
 * @author  quzhu.wl/netboy 
 * @date 创建时间：2015年1月25日 下午9:02:42 
 * @func zk 客户端
 * @version 1.0  
 */
public class ClientFactory {
	private String zkurl;
	private Integer timeout;
	private Integer sessionTimeout;
	private RetryPolicy retryPolicy;
	private String nameSpace;
	private String defaultData ="0";
	private CuratorFramework client;
	private Integer retrySleepTime=1000;
	
	public ClientFactory(String zkurl, Integer timeout,Integer sessionTimeout,RetryPolicy retryPolicy,String
			nameSpace,String defaultData){
		this.zkurl = zkurl;
		this.timeout = timeout;
		this.sessionTimeout = sessionTimeout;
		this.retryPolicy = retryPolicy;
		this.nameSpace = nameSpace;
		this.defaultData = defaultData;
	}
	
	/**
	 * 
	*  将注入的参数进行初始化
	 */
	private void init(){
		CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();
		if( zkurl != null && 
			timeout != null && 
			sessionTimeout != null && 
			retryPolicy!= null && 
			nameSpace!= null && 
			defaultData!= null){
			this.client = builder.connectString(zkurl)  
			        .sessionTimeoutMs(sessionTimeout)  
			        .connectionTimeoutMs(timeout)  
			        .canBeReadOnly(false)  
			        .retryPolicy(retryPolicy)  
			        .namespace(nameSpace)  
			        .defaultData(defaultData.getBytes())  
			        .build();  
			
		}else if (zkurl != null && timeout != null  && retryPolicy!= null) {
			this.client = builder.connectString(zkurl)  
			        .connectionTimeoutMs(timeout)  
			        .retryPolicy(retryPolicy)  
			        .build();  

		}else if (zkurl != null && timeout != null && defaultData != null) {
			this.client = builder.connectString(zkurl)  
			        .connectionTimeoutMs(timeout)  
			        .retryPolicy(new ExponentialBackoffRetry(retrySleepTime, 3))
			        .defaultData(defaultData.getBytes())
			        .build(); 
			
		}else if (zkurl != null && timeout != null) {
			this.client = builder.connectString(zkurl)  
			        .connectionTimeoutMs(timeout)  
			        .retryPolicy(new ExponentialBackoffRetry(retrySleepTime, 3))  
			        .build(); 
		}else{
			throw new RuntimeException("init client,you at least  have <zkurl> and <timeout>");
		}
		client.start();
	}
	


	/**
	 * 
	*  获取zk 客户端
	 */
	public ZkClient getClient(){
		if(client==null){
			init();
		}
		return new ZkClient((CuratorFrameworkImpl)client);
	}
	
	public String getZkurl() {
		return zkurl;
	}
	public void setZkurl(String zkurl) {
		this.zkurl = zkurl;
	}
	public Integer getTimeout() {
		return timeout;
	}
	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}
	public Integer getSessionTimeout() {
		return sessionTimeout;
	}
	public void setSessionTimeout(Integer sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}
	public RetryPolicy getRetryPolicy() {
		return retryPolicy;
	}
	public void setRetryPolicy(RetryPolicy retryPolicy) {
		this.retryPolicy = retryPolicy;
	}
	public String getNameSpace() {
		return nameSpace;
	}
	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}
	public String getDefaultData() {
		return defaultData;
	}
	public void setDefaultData(String defaultData) {
		this.defaultData = defaultData;
	}
	public void setRetrySleepTime(Integer retrySleepTime) {
		this.retrySleepTime = retrySleepTime;
	}
	
}
