package com.netboy.zk.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.framework.recipes.locks.StandardLockInternalsDriver;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @author  quzhu.wl/netboy 
 * @date 创建时间：2015年2月1日 下午2:31:23 
 * @func 
 * @version 1.0  
 */
public class LockFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(LockFactory.class);
	/**
	 * 创建一个可重入锁，同一时间只有一个用户可以获取锁，其它用户阻塞获取，锁的节点值是客户端ip
	 * isPersist==false 临时节点
	 * == true 永久节点
	 */
	public static InterProcessMutex createLock(CuratorFramework curatorClient,String lockPath,ConnectionStateListener listener,boolean isPersist){
		if(listener != null){
			curatorClient.getConnectionStateListenable().addListener(listener);
		}
		//锁节点为永久序列节点
		if(isPersist){
			return new InterProcessMutex(curatorClient, lockPath,new PersistLockInternalsDriver());
		}
		//锁节点为临时序列节点
		return new InterProcessMutex(curatorClient, lockPath,new StandardLockInternalsDriver());
	}

	/**
	 * 创建一个读写锁，注意该锁是一个临时节点，锁的节点值是客户端ip
	 */
	public static InterProcessReadWriteLock createReadWriteLock(CuratorFramework curatorClient,String path,ConnectionStateListener listener){
		if(listener != null){
			curatorClient.getConnectionStateListenable().addListener(listener);
		}
		return new InterProcessReadWriteLock(curatorClient, path);
	}
	
}
