package com.netboy.zk.counter;

import java.util.concurrent.TimeUnit;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.framework.recipes.atomic.PromotedToLock;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author quzhu.wl/netboy
 * @date 创建时间：2015年1月25日 下午11:54:58
 * @func 原子计数器
 * @version 1.0
 */
public class Counter {
	private static final Logger LOGGER = LoggerFactory.getLogger(Counter.class);
	private DistributedAtomicLong distributedAtomicLong;
	private CuratorFramework client;
	private String counterPath;
	private RetryPolicy retryPolicy;
	private PromotedToLock promotedToLock;
	private Integer retrySleepTime = 1000;
	private long maxLockTime = 3;

	public Counter(CuratorFramework client, String counterPath, RetryPolicy retryPolicy, PromotedToLock promotedToLock) {
		this.client = client;
		this.counterPath = counterPath;
		this.retryPolicy = retryPolicy;
		this.promotedToLock = promotedToLock;
	}

	public Counter(CuratorFramework client, String counterPath) {
		this.client = client;
		this.counterPath = counterPath;
	}
	
	/**
	 * 初始化配置
	 */
	public void init() {
		if (distributedAtomicLong == null) {
			if (retryPolicy == null && promotedToLock == null) {
				this.retryPolicy = new ExponentialBackoffRetry(retrySleepTime, 3);
				PromotedToLock.Builder builder = PromotedToLock.builder();
				builder.lockPath(counterPath).timeout(maxLockTime, TimeUnit.SECONDS)
						.retryPolicy(new ExponentialBackoffRetry(retrySleepTime, 3));
				this.promotedToLock = builder.build();
			}
			distributedAtomicLong = new DistributedAtomicLong(client, counterPath, retryPolicy, promotedToLock);
		}
	}

	/**
	 * 
	 * 获取计数器值,失败返回-1
	 */
	public long get() {
		if(distributedAtomicLong == null){
			init();
		}
		long result =-1L;
		try {
			AtomicValue<Long> atomicValue = distributedAtomicLong.get();
			if(atomicValue.succeeded()){
				result = atomicValue.preValue();
			}else {
				LOGGER.error("get [{}] counter fail", counterPath);
			}
		} catch (Exception e) {
			LOGGER.error("get [{}] counter fail, Exception={}", counterPath,e.toString());
		}
		return result;
	}

	/**
	 * 
	 * 原子加一,返回变更后的值,失败返回-1
	 */
	public long increment() {
		if(distributedAtomicLong == null){
			init();
		}
		long result =-1L;
		try {
			AtomicValue<Long> atomicValue = distributedAtomicLong.increment();
			if(atomicValue.succeeded()){
				result = atomicValue.postValue();
			}else {
				LOGGER.error("counter {} increment fail", counterPath);
			}
		} catch (Exception e) {
			LOGGER.error("counter {} increment Exception={}", counterPath,e.toString());
		}
		return result;
	}

	/**
	 * 原子减一,返回变更后的值,失败返回-1
	 */
	public long decrement() {
		if(distributedAtomicLong == null){
			init();
		}
		long result =-1L;
		try {
			AtomicValue<Long> atomicValue = distributedAtomicLong.decrement();
			if(atomicValue.succeeded()){
				result = atomicValue.postValue();
			}else {
				LOGGER.error("counter {} decrement fail", counterPath);
			}
		} catch (Exception e) {
			LOGGER.error("counter {} decrement Exception={}", counterPath,e.toString());
		}
		return result;
	}
}
