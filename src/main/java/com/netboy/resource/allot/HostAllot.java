package com.netboy.resource.allot;

import java.util.ArrayList;
import java.util.List;

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
		List<String> list = new ArrayList<String>();
		List<InstanceMeta> metaList = (List<InstanceMeta>) obj;
		for (InstanceMeta meta : metaList) {
			if (meta.getStatus() != STATUS) {
				list.add(meta.getIp() + "--" + meta.getStatus());
				LOGGER.error("ipPort={},status={}", meta.getIpPort(), meta.getStatus());
				metaList.remove(meta);
			}
		}
		return metaList;
	}

}
