package com.netboy.zk.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.StandardLockInternalsDriver;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.CharMatcher;

/** 
 * @author  quzhu.wl/netboy 
 * @date 创建时间：2015年2月1日 下午5:56:56 
 * @func 
 * @version 1.0  
 */
public class PersistLockInternalsDriver extends StandardLockInternalsDriver {
	private static final Logger LOGGER = LoggerFactory.getLogger(PersistLockInternalsDriver.class);

	/**
	 * 
	 */
    @Override
    public String createsTheLock(CuratorFramework client, String path, byte[] lockNodeBytes) throws Exception
    {
        String ourPath;
        if ( lockNodeBytes != null )
        {
        	ourPath = client.create().creatingParentsIfNeeded().withProtection().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath(path, lockNodeBytes);
        }
        else
        {
            ourPath = client.create().creatingParentsIfNeeded().withProtection().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath(path);
        }
        return ourPath;
    }
}
