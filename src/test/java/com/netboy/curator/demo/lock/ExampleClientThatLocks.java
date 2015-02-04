/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.netboy.curator.demo.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.utils.CloseableUtils;

import com.netboy.zk.common.MagUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class ExampleClientThatLocks
{
    private final InterProcessReadWriteLock lock;
    private final FakeLimitedResource resource;
    private final String clientName;
    private CuratorFramework client;
    private final String lockPath;
    private AtomicLong count = new AtomicLong();

    public ExampleClientThatLocks(CuratorFramework client, String lockPath, FakeLimitedResource resource, String clientName)
    {
        this.resource = resource;
        this.clientName = clientName;
        this.lock = new InterProcessReadWriteLock(client, lockPath);
        this.lockPath = lockPath;
        this.client = client;
    }

    public void     doWork(long time, TimeUnit unit) throws Exception
    {
    	if(!client.getState().equals(CuratorFrameworkState.STARTED)){
    		client.start();
    	}
    	InterProcessMutex  mutex = lock.writeLock();
        if ( !mutex.acquire(time, unit))
        {
            throw new IllegalStateException(clientName + " could not acquire the lock");
        }
        try
        {	Long num = count.incrementAndGet();
        	String numStr = num.toString();
            client.setData().forPath(lockPath, numStr.getBytes());
            System.out.println("update to  " +num);
            //resource.use();
        }
        finally
        {
            System.out.println(clientName + " releasing the lock");
            mutex.release(); // always release the lock in a finally block
            CloseableUtils.closeQuietly(client);
        }
    }
}
