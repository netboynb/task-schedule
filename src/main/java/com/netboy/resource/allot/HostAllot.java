package com.netboy.resource.allot;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author netboy 2015年3月6日下午10:17:56
 */
public class HostAllot extends Allot {
	private static final Logger LOGGER = LoggerFactory.getLogger(HostAllot.class);
	private String errorInfo;
	private final int STATUS = 2;//部署成功状态

	/**
	 * 
	 * @param metaList
	 *            参与计算的所有实例元素列表
	 * @return 将实例元素进行分组，每组是一个实例元素列表
	 */
	public List<List<InstanceMeta>> getResult(List<InstanceMeta> metaList) {
		return (List<List<InstanceMeta>>) super.doCompute(metaList);
	}

	@Override
	public Object doLogic(Object obj) {
		Map<Integer, Map<Integer, Integer>> result = new HashMap<Integer, Map<Integer,Integer>>();
		
		List<List<InstanceMeta>> treeList = new LinkedList<List<InstanceMeta>>();
		//将实例链表根据索引优化的原则 分为多个批次，存在一个二维链表中
		hostsAlg((List<InstanceMeta>)obj, treeList);
		int index = 1;
		//存储 每一批次中的实例的节点和列号  并以批次为key
		for(List<InstanceMeta> list : treeList){
			Map<Integer, Integer> nodeIdSeqIdxMap = new HashMap<Integer, Integer>();
			for(InstanceMeta meta : list){
				nodeIdSeqIdxMap.put(meta.getNodeId(), meta.getSeqIdx());
			}
			result.put(index, nodeIdSeqIdxMap);
			index++;
		}
		return result;
	}
	/**
	 * 生成一个链表，元素为InstanceMeta链表，每个链表为一个批次
	 */
	public void hostsAlg(List<InstanceMeta> list,List<List<InstanceMeta>> treeList){
		Map<Integer, List<InstanceMeta>> nodeHostMap = new HashMap<Integer, List<InstanceMeta>>();
		//本次实例列表所生成的树状结构
		List<List<InstanceMeta>> oneTreeList = new LinkedList<List<InstanceMeta>>(); 
		List<Integer> nodeList = new LinkedList<Integer>();
		//整理元素，形成多个树形列表
		for(InstanceMeta iDo : list){
			int nodeId = iDo.getNodeId();
			List<InstanceMeta> tempList = nodeHostMap.get(nodeId);
			if(tempList == null){
				List<InstanceMeta> linkedList = new LinkedList<InstanceMeta>();
				linkedList.add(iDo);
				nodeHostMap.put(nodeId, linkedList);
				nodeList.add(nodeId);
			}else{
				tempList.add(iDo);
			}
		}
		Collections.sort(nodeList);
		//构建 算法 分组树形结构
		//节点 对应实例最多的节点对应为根节点，若所有节点对应的实例数目相同，则选取0为根节点
		Integer bigestNode = 0;
		int instanceNum = 0;
		for(Entry<Integer, List<InstanceMeta>> entry: nodeHostMap.entrySet()){
			int nodeId = entry.getKey();
			int size = entry.getValue().size();
			if(size > instanceNum){
				bigestNode = nodeId;
				instanceNum = size;
			}
		}
		
		List<InstanceMeta> bigestInstanceList = nodeHostMap.get(bigestNode);
		//对应实例最多的节点对应为根节点
		for(int i = 0;i<instanceNum;i++){
			List<InstanceMeta> childList = new LinkedList<InstanceMeta>();
			childList.add(bigestInstanceList.get(i));
			oneTreeList.add(i, childList);
		}
		//剔除实例数最多的节点
		nodeList.remove(new Integer(bigestNode));
		
		//冲突列表
		List<InstanceMeta> conflictList = new LinkedList<InstanceMeta>();
		for(int nodeId : nodeList){
			List<InstanceMeta> nodeHostList = nodeHostMap.get(nodeId);
			List<List<InstanceMeta>> tempTreeList = new LinkedList<List<InstanceMeta>>(oneTreeList);
			for(InstanceMeta iDo : nodeHostList){
				//遍历 所有的子treelist
				boolean isAdd = true;
				boolean isConflict = true;
				for(List<InstanceMeta> childTreeList : tempTreeList){
					//遍历子treelist ip不同则可以添加，否则到另一个子treelist中对比
					for(InstanceMeta memDo : childTreeList){
						if(memDo.isEqualsIp(iDo)){
							isAdd = false;
							break;
						}
					}
					if(isAdd){
						childTreeList.add(iDo);
						//添加成功后，剩余子treelist不在遍历
						//剩余元素 对 本子treelist 不再遍历
						tempTreeList.remove(childTreeList);
						isConflict = false;
						break;
					}
					isAdd = true;
				}
				//所有都尝试了 但都冲突，则添加冲突列表中
				if(isConflict){
					conflictList.add(iDo);
				}
			}
		}
		treeList.addAll(oneTreeList);
		//递归处理冲突列表
		if(conflictList.size() > 0){
			hostsAlg(conflictList,treeList);
		}else{
			return;
		}
		
	}
}
