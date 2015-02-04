package com.netboy.zk.cache;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.utils.CloseableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author quzhu.wl/netboy
 * @date 创建时间：2015年1月28日 下午8:22:53
 * @func 节点以下 tree状缓存
 * 
 * @version 1.0
 */
public class ZkCache {
	private static final Logger LOGGER = LoggerFactory.getLogger(ZkCache.class);
	
	private CuratorFramework curatorClient;
	private String path;
	private TreeCache treeCache;
	
	public ZkCache(CuratorFramework curatorClient,String path){
		this.curatorClient = curatorClient;
		this.path = path;
	}
	
	public void init(){
		treeCache = new TreeCache(curatorClient, path);
		try {
			treeCache.start();
			addListener();
		} catch (Exception e) {
			LOGGER.error("add treecache listener fail path [{}],info={}",path,e.toString());
		}
	}
	
	/**
	 * 添加监听器,添加一次即可，每次变更都有通知
	 */
	private void addListener() {
		treeCache.getListenable().addListener(new ZkCacheListener());
	}
	
	/**
	 * 返回 缓存中制定路径的值，如果该路径不存在，则返回null
	 */
	public String getData(String path){
		ChildData  childData = treeCache.getCurrentData(path);
		if(childData == null ){
			return null;
		}
		return new String(childData.getData());
	}
	
	/**
	 * 关闭
	 */
	public void close(){
		 CloseableUtils.closeQuietly(treeCache);
	}
}
