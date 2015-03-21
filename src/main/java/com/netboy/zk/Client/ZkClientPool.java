package com.netboy.zk.Client;

import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ZkClientPool {
	// 维持 client实例缓存
	private static ConcurrentHashMap<String, ZkClient> pool;
	// 维持 key值队列
	private static Queue<String> keyQueue = new ConcurrentLinkedQueue<String>();
	private static ReentrantLock lock = new ReentrantLock();
	private  static final int   POOL_SIZE = 5;
	
	static{
		pool = new ConcurrentHashMap<String, ZkClient>(POOL_SIZE);
	}
	/**
	 * 获取 key（ip:port）值对应的zkclient
	 * 
	 * @param key
	 * @return
	 */
	public static ZkClient get(String key) {
		ZkClient server = null;
		try {
			lock.lock();
			server = pool.get(key);
		} finally {
			lock.unlock();
		}
		return server;
	}

	/**
	 * 将对象put 进缓存中
	 */
	public static void put(String key, ZkClient server) {
		try {
			lock.lock();
			if (!pool.containsKey(key)) {
				// 去除首部的缓存
				if (pool.size() >= POOL_SIZE) {
					String tempKey = keyQueue.remove();
					ZkClient temp = pool.remove(tempKey);
					temp.close();
				}
				keyQueue.add(key);
				pool.put(key, server);
			}
		} finally {
			lock.unlock();
		}
	}
}
