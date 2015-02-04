package com.netboy.zk.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @author  quzhu.wl/netboy 
 * @date 创建时间：2015年2月1日 下午4:32:50 
 * @func 
 * @version 1.0  
 */
public class LockStateListener implements ConnectionStateListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(LockStateListener.class);
	 @Override  
     public void stateChanged(CuratorFramework client, ConnectionState newState) {  
     	
         switch (newState){
             case LOST:
             	LOGGER.warn("State Change ,connection lost,please check whether the lock is persist");
                 //客戶端 与服务器之间断开连接,不再持有锁
                 break; 
             default:  
            	 LOGGER.info("State Change ,newState is ---> {}",newState.toString());
         }  
     }  

}
