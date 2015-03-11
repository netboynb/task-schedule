package com.netboy.resource.alg;

import java.util.List;
import java.util.Map;

import com.netboy.resource.allot.InstanceMeta;


/**
 * 机器资源分配算法
 *	1、同一机器上，一次只执行一个实例；
 *	2、同一节点下，一次只执行一个实例
 *
 * @author netboy 2015年3月6日下午11:22:32
 */

public class HostAllotAlg implements AlgInterface {

	/**
	 * 计算逻辑
	 */
	@Override
	public Object doCompute(Object obj) {
		List<InstanceMeta> metaList = (List<InstanceMeta>) obj;
		
		//Map<int, Map<String, InstanceMeta>>  
		
		return null;
	}


}
