package com.netboy.shedule.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netboy.schedule.job.TaskDO;
import com.netboy.zk.Client.ZkClient;

public class OnlineZkHanlder extends ZkBaseHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(OnlineZkHanlder.class);

	/**
	 * 在zk上写对应的节点
	 */
	@Override
	public boolean doHandle(TaskDO taskDO) {
		onLine(taskDO);
		if (taskDO.isSuccess()) {
			taskDO.setSuccess(false);
			return true;
		}
		return false;
	}

	/**
	 * 上线 ，在zk上写对应的节点，eg：add /serviceName/opt/0/127.0.0.1:9001
	 */
	public void onLine(TaskDO taskDO) {
		ZkClient zkClient = build(taskDO);
		// 删除对应节点
		for (String node : taskDO.getNodeList()) {
			try {
				zkClient.createPath(node);
			} catch (Exception e) {
				LOGGER.error("node [{}] delete fail,info = {}", node, e.toString());
			}
		}
		taskDO.setSuccess(true);
		return;
	};
}
