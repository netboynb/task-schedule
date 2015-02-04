package com.netboy.zk.Client;

import org.apache.curator.framework.imps.CuratorFrameworkImpl;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author quzhu.wl/netboy
 * @date 创建时间：2015年1月28日 下午10:28:06
 * @func
 * @version 1.0
 */
public class ZkClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(ZkClient.class);

	private CuratorFrameworkImpl curatorClient;

	protected ZkClient(CuratorFrameworkImpl client) {
		this.curatorClient = client;
	}

	/**
	 * 检验节点是否存在,1 存在，0不存在，-1 异常
	 */
	public int checkExists(String path) {
		Stat stat = null;
		try {
			stat = curatorClient.checkExists().forPath(path);
		} catch (Exception e) {
			LOGGER.error("checkExists path [{}] fail ", path);
			return -1;
		}
		if (stat == null) {
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * 创建永久节点，自动创建父子节点，并返回创建节点成功的串,默认叶子节点的值为创建节点的机器ip
	 */
	public String createPath(String path) {
		String result = null;
		try {
			Stat stat = curatorClient.checkExists().forPath(path);
			if (stat == null) {
				result = curatorClient.create().creatingParentsIfNeeded().forPath(path);
			} else {
				LOGGER.warn("path [{}] had exist ", path);
				result = path;
			}
		} catch (Exception e) {
			LOGGER.error("create path fail ,Exception={}", e.toString());
		}
		return result;
	}

	/**
	 * 创建永久节点，并赋予叶子节点指定的默认值
	 */
	public String createPath(String path, String data) {
		String result = null;
		if (data == null) {
			return createPath(path);
		}
		try {
			result = curatorClient.create().creatingParentsIfNeeded().forPath(path, data.getBytes());
		} catch (Exception e) {
			LOGGER.error("create path fail ,Exception={}", e.toString());
		}
		return result;
	}

	/**
	 * 获取节点的值
	 */
	public String getPathData(String path) {
		String result = null;
		try {
			result = new String(curatorClient.getData().forPath(path));
		} catch (Exception e) {
			LOGGER.error("getPathData fail ,Exception={}", e.toString());
		}
		return result;
	}

	/**
	 * 获取节点的值，并将节点的状态信息保存在参数stat中
	 */
	public String getPathData(String path, Stat stat) {
		String result = null;
		try {
			result = new String(curatorClient.getData().storingStatIn(stat).forPath(path));
		} catch (Exception e) {
			LOGGER.error("getPathData fail ,Exception={}", e.toString());
		}
		return result;
	}

	/**
	 * 设置 节点 数值
	 */
	public Stat setPathData(String path, String data) {
		Stat result = null;
		if (path == null || data == null) {
			LOGGER.error("path AND data can not be null");
			return null;
		}
		try {
			result = curatorClient.setData().forPath(path, data.getBytes());
		} catch (Exception e) {
			LOGGER.error("set {} ' data fail ,Exception={}", path, e.toString());
		}
		return result;
	}

	/**
	 * 删除节点，如果存在子节点也删除，直到删除成功为止
	 */
	public void deletePath(String path) {
		try {
			curatorClient.delete().guaranteed().deletingChildrenIfNeeded().forPath(path);
		} catch (Exception e) {
			LOGGER.error("delete path[{}] fail ,Exception={}", path, e.toString());
		}
	}

	/**
	 * 
	 */
	public void close(){
		CloseableUtils.closeQuietly(curatorClient);
	}
	/**
	 * 获取curator 原生的客户端进行其它复杂操作
	 */
	public CuratorFrameworkImpl getCuratorClient() {
		return curatorClient;
	}

	public void setCuratorClient(CuratorFrameworkImpl curatorClient) {
		this.curatorClient = curatorClient;
	}

}
