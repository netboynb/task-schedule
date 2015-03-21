package com.netboy.shedule.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netboy.schedule.job.TaskDO;
import com.netboy.zk.Client.ZkClient;


public class OffLineZkHandler extends ZkBaseHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(OffLineZkHandler.class);

	@Override
	public boolean doHandle(TaskDO taskDO) {
		offLine(taskDO);
		if (taskDO.isSuccess()) {
			//回归原始状态
			taskDO.setSuccess(false);
			return true;
		}
		return false;
	}

	/**
	 * 下线 ，将zk上对应的节点删除，eg：删除 /serviceName/opt/0/127.0.0.1:9001
	 */
	public void offLine(TaskDO taskDO) {
		ZkClient zkClient = build(taskDO);
		// 删除对应节点
		for (String node : taskDO.getNodeList()) {
			try {
				zkClient.deletePath(node);
			} catch (Exception e) {
				LOGGER.error("node [{}] delete fail,info = {}", node, e.toString());
			}
		}
		taskDO.setSuccess(true);
		return;
	};
}
