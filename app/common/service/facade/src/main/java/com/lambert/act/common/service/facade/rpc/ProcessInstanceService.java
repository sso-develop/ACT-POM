package com.lambert.act.common.service.facade.rpc;

import java.util.Map;

/**
 *  流程事件处理工具接口
 * @author lambert  2018-11-06 18:31:29
 *
 */
public interface ProcessInstanceService {
	/**
	 * 创建工单
	 */
	void creactProcess(String key,Map<String, Object> variableMap);
}
