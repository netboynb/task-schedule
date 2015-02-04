package com.netboy.zk.counter;

import org.apache.curator.framework.CuratorFramework;

import com.netboy.zk.Client.ClientFactory;
import com.netboy.zk.common.MagUtils;

import junit.framework.TestCase;

/** 
 * @author  quzhu.wl/netboy 
 * @date 创建时间：2015年1月26日 下午8:00:23 
 * @func 
 * @version 1.0  
 */
public class CounterTest extends TestCase {
	private ClientFactory clientFactory = new ClientFactory(MagUtils.Zk_URL,2000,null,null,null,"0");
	
	public void testInit() {
		CuratorFramework client = clientFactory.getClient().getCuratorClient();
		Counter counter = new Counter(client, MagUtils.ZK_COUNTER_PATH);
		counter.init();
	}

	public void testGet() {
		CuratorFramework client = clientFactory.getClient().getCuratorClient();
		Counter counter = new Counter(client, MagUtils.ZK_COUNTER_PATH);
		long result = counter.get();
		System.out.println("counter = "+result);
	}

	public void testIncrement() {
		CuratorFramework client = clientFactory.getClient().getCuratorClient();
		Counter counter = new Counter(client, MagUtils.ZK_COUNTER_PATH);
		long result = counter.get();
		System.out.println("before increase counter="+result);
		result = counter.increment();
		System.out.println("after  increase counter="+result);
	}

	public void testDecrement() {
		CuratorFramework client = clientFactory.getClient().getCuratorClient();
		Counter counter = new Counter(client, MagUtils.ZK_COUNTER_PATH);
		long result = counter.get();
		System.out.println("before decrement counter="+result);
		result = counter.decrement();
		System.out.println("after  decrement counter="+result);
	}

}
