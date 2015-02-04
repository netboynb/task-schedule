package com.netboy.zk.cache;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.data.Stat;

/**
 * @author quzhu.wl/netboy
 * @date 创建时间：2015年1月28日 下午9:44:11
 * @func
 * @version 1.0
 */
public class ZkCacheListener implements TreeCacheListener {

	@Override
	public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
		switch (event.getType()) {
		case NODE_ADDED: {
			System.out.println("Node added: " + ZKPaths.getNodeFromPath(event.getData().getPath()));
			break;
		}

		case NODE_UPDATED: {
			System.out.println("Node changed: " + ZKPaths.getNodeFromPath(event.getData().getPath()));
			String path = event.getData().getPath();
			String data = new String(event.getData().getData());
			Stat stat = event.getData().getStat();
			System.out.println(path +"'s data change to ["+data+"] "+stat.toString());
			break;
		}

		case NODE_REMOVED: {
			System.out.println("Node removed: " + ZKPaths.getNodeFromPath(event.getData().getPath()));
			break;
		}
		case CONNECTION_SUSPENDED: {
			System.out.println("Node removed: " + ZKPaths.getNodeFromPath(event.getData().getPath()));
			break;
		}
		case CONNECTION_RECONNECTED: {
			System.out.println("Node removed: " + ZKPaths.getNodeFromPath(event.getData().getPath()));
			break;
		}
		case INITIALIZED: {
			System.out.println("Node removed: " + ZKPaths.getNodeFromPath(event.getData().getPath()));
			break;
		}
		}
	}

}
