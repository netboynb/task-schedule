package com.netboy.zk.Client;

import junit.framework.TestCase;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.GetChildrenBuilder;
import org.apache.curator.framework.imps.CuratorFrameworkImpl;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;

import com.netboy.zk.common.MagUtils;

/** 
 * @author  quzhu.wl/netboy 
 * @date 创建时间：2015年1月26日 下午8:38:48 
 * @func 
 * @version 1.0 
	 * 创建永久节点：
	client.create().forPath("/root", "".getBytes());
	创建临时节点
	client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/tmp", "".getBytes());
	读数据
	client.getData().watched().inBackground().forPath("/test"); 
	检查节点
	client.checkExists().forPath("/root"); 
	异步删除节点：
	client.delete().inBackground().forPath("/root"); 
	注册观察者，当节点变动时触发 
	client.getData().usingWatcher(new Watcher() {
	@Override
	public void process(WatchedEvent event) {
	System.out.println("node is changed");
	}
	}).inBackground().forPath("/test"); 
 */
public class ClientFactoryTest extends TestCase {
	private ClientFactory clientFactory = new ClientFactory(MagUtils.Zk_URL,2000,null,null,null,null);
	
	public void testClientFactory() {
		fail("Not yet implemented");
	}

	public void testSetData(){
		ZkClient client = clientFactory.getClient();
		
		try {
			int temp = client.checkExists(MagUtils.ZK_NODE);
			if(temp != 1){
				client.createPath(MagUtils.ZK_NODE,"");
			}
			
			//ZKPaths.mkdirs(client.getZookeeperClient().getZooKeeper(), MagUtils.ZK_NODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
