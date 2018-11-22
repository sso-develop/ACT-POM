package com.lambert.act.common.service.facade.rpc;

import java.util.Map;

import com.lambert.act.common.service.facade.condition.TaskCondition;
import com.lambert.act.common.service.facade.result.ActPager;
import com.lambert.act.common.service.facade.result.ActResult;

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
	/**
	 * 
	 * @param taskId
	 * @param processInstanceId
	 * @param variableMap
	 * @param comment
	 */
	ActResult<Boolean> completeTask(String taskId,String processInstanceId,Map<String, Object> variableMap,String comment);
	/**
	 * 
	 * @param condition
	 */
	ActResult<ActPager> selectTaskPagerByCondition(TaskCondition condition);
}
