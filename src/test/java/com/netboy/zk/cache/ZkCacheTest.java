package com.netboy.zk.cache;

import com.netboy.zk.Client.ClientFactory;
import com.netboy.zk.Client.ZkClient;
import com.netboy.zk.common.MagUtils;

import junit.framework.TestCase;

/** 
 * @author  quzhu.wl/netboy 
 * @date 创建时间：2015年1月29日 下午8:13:36 
 * @func 
 * @version 1.0  
 */
public class ZkCacheTest extends TestCase {
	private ClientFactory clientFactory = new ClientFactory(MagUtils.Zk_URL,2000,null,null,null,null);
	private String path="/example";
	
	public ZkCache init(){
		ZkClient client =  clientFactory.getClient();
		ZkCache cache = new ZkCache(client.getCuratorClient(), path);
		cache.init();
		return cache;
	}
	
	public void testInit() {
		init();
		while(true){
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("i will sleep 10s");
		}
	}

	public void testGetData() {
		ZkCache cache = init();
		String data = cache.getData(path);
		System.out.println("path-> "+path +"'s data = "+data);
	}

	public void testClose() {
		fail("Not yet implemented");
	}

}
