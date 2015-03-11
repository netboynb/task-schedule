package com.netboy.resource.allot;

import com.netboy.resource.alg.AlgInterface;

public abstract class Allot {
	/* 使用资源计算的算法 */
	private AlgInterface alg;
	
	/**
	 * 获取资源计算后的结果
	 * @param obj
	 * @return
	 */
	public Object doCompute(Object  obj){
		Object  temp= doLogic(obj);
		return alg.doCompute(temp);
	}
	
	/**
	 *  对应分配时的业务逻辑处理
	 * @param obj
	 * @return
	 */
	public abstract Object doLogic(Object obj);

	public void setAlg(AlgInterface alg) {
		this.alg = alg;
	}
	
}
