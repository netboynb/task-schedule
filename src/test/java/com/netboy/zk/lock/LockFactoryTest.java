package com.netboy.zk.lock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.CreateMode;

import com.netboy.zk.Client.ClientFactory;
import com.netboy.zk.Client.ZkClient;
import com.netboy.zk.common.MagUtils;

import junit.framework.TestCase;

/**
 * @author quzhu.wl/netboy
 * @date 创建时间：2015年2月1日 下午4:40:41
 * @func
 * @version 1.0
 */
public class LockFactoryTest extends TestCase {
	private ClientFactory clientFactory = new ClientFactory(MagUtils.Zk_URL, 2000, null, null, null, null);
	private String path = "/example/node_1";

	public ZkClient init() {
		return clientFactory.getClient();
	}

	public void testCreateLock() throws Exception {
		ZkClient zkClient = init();
		final InterProcessMutex lock = LockFactory.createLock(zkClient.getCuratorClient(), path,
				new LockStateListener(), true);
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						countDownLatch.await();
						lock.acquire();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss | SSS");
						Date date = new Date();
						String time = sdf.format(date);
						String threadName = Thread.currentThread().getName();
						System.out.println(threadName + "  has the lock ， at time = " + time);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						try {
							lock.release();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}

		Thread.sleep(2000);
		countDownLatch.countDown();
		System.out.println();
	}

	public void testCreateReadWriteLock() {
		fail("Not yet implemented");
	}

}
