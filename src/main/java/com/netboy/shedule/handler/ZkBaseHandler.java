package com.netboy.shedule.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.netboy.mysql.manager.DbManager;
import com.netboy.resource.allot.InstanceMeta;
import com.netboy.schedule.job.TaskDO;
import com.netboy.zk.Client.ClientFactory;
import com.netboy.zk.Client.ZkClient;
import com.netboy.zk.Client.ZkClientPool;

public abstract class ZkBaseHandler extends BaseHandler {
	private DbManager dbManager;
	
	/**
	 * 
	 * 组装zk上vsearch节点 /serviceName/opt/0/127.0.0.1:9001
	 */
	protected ZkClient build(TaskDO taskDO) {
		String zkUrl = taskDO.getZkUrl();
		ZkClient zkClient = ZkClientPool.get(zkUrl);
		if (zkClient == null) {
			ClientFactory clientFactory = new ClientFactory(zkUrl, 2000, 2000, null, null, null);
			zkClient = clientFactory.getClient();
			ZkClientPool.put(zkUrl, zkClient);
		}
		// 构建节点串
		Map<Integer, Integer> nodeIdSeqIdxMap = taskDO.getNodeIdSeqIdxMap();
		Long appId = taskDO.getAppId();
		int size = nodeIdSeqIdxMap.size();
		List<String> nodeIpPorts = new ArrayList<String>(size);
		List<InstanceMeta> ids = new ArrayList<InstanceMeta>(size);
		Map<String, Integer> ipHttpPortMap = new HashMap<String, Integer>(size);
		for (Entry<Integer, Integer> entry : nodeIdSeqIdxMap.entrySet()) {
			InstanceMeta instanceDO = new InstanceMeta();
			instanceDO.setAppId(appId);
			instanceDO.setNodeId(entry.getKey());
			instanceDO.setSeqIdx(entry.getValue());
			ids.add(instanceDO);
		}
		List<InstanceMeta> list = (List<InstanceMeta>)dbManager.query(ids);
		for (InstanceMeta instanceDO : list) {
			String ip = instanceDO.getIp();
			int nettyPort = instanceDO.getNettyPort();
			int httpPort = instanceDO.getHttpPort();
			String nodeIpPort = instanceDO.getNodeId() + "/" + ip + ":" + nettyPort;
			nodeIpPorts.add(nodeIpPort);
			ipHttpPortMap.put(ip, httpPort);
		}
		taskDO.setIpHttpPortMap(ipHttpPortMap);
		List<String> nodeList = new ArrayList<String>();

		for (String nodeIpPort : nodeIpPorts) {
			String temp = "/" + taskDO.getJobName() + "/opt/" + nodeIpPort;
			nodeList.add(temp);
		}
		taskDO.setNodeList(nodeList);

		return zkClient;
	}

	public DbManager getDbManager() {
		return dbManager;
	}

	public void setDbManager(DbManager dbManager) {
		this.dbManager = dbManager;
	}

}
