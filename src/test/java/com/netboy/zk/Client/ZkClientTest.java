package com.netboy.zk.Client;

import org.apache.zookeeper.data.Stat;

import com.netboy.zk.common.MagUtils;

import junit.framework.TestCase;

/** 
 * @author  quzhu.wl/netboy 
 * @date 创建时间：2015年1月29日 下午1:43:03 
 * @func 
 * @version 1.0  
 */
public class ZkClientTest extends TestCase {
	private ClientFactory clientFactory = new ClientFactory(MagUtils.Zk_URL,2000,null,null,null,null);
	private String path="/example/node_1/node_2/node_3/node_4";
	
	public ZkClient init(){
		return clientFactory.getClient();
	}
	public void testCheckExists() {
		ZkClient zkClient = init();
		int result = zkClient.checkExists(path);
		if(result==1){
			System.out.println(path + "  exist ");
		}else if (result == 0) {
			System.out.println(path + " is not exist ");
		}else {
			System.out.println("check exception");
		}
	}

	public void testCreatePathString() {
		ZkClient zkClient = init();
		zkClient.createPath(path);
	}

	public void testCreatePathStringString() {
		ZkClient zkClient = init();
		zkClient.createPath(path,"0");
		zkClient.close();
	}

	public void testGetPathDataString() {
		ZkClient zkClient = init();
		String data = zkClient.getPathData(path);
		zkClient.close();
		System.out.println(path + "'s data = "+data);
	}

	public void testGetPathDataStringStat() {
		ZkClient zkClient = init();
		Stat stat = new Stat();
		String data = zkClient.getPathData(path, stat);
		zkClient.close();
		System.out.println(path + "'s data = "+data+stat.toString());
	}

	public void testSetPathData() {
		ZkClient client = init();
		client.setPathData(path, "0");
	}

	public void testDeletePath() {
		ZkClient zkClient = init();
		zkClient.deletePath("/example/node_1/node_2");
	}

}
