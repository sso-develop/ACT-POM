package com.lambert.act.biz.act.repository;

import java.util.Map;

import com.lambert.act.biz.act.repository.model.TaskModel;
import com.lambert.act.biz.act.repository.query.TaskQueryObj;
import com.lambert.act.common.uitl.result.DefaultResult;
import com.lambert.act.common.uitl.result.Pager;

public interface ActTaskMananger {
	
	public DefaultResult<Pager> findPersonTask(TaskQueryObj queryObj);
	public DefaultResult<Boolean> assign(String taskId,String userId);
	public DefaultResult<Boolean> claim(String taskId,String userId);
	public DefaultResult<Boolean> completeTask(String id,String processInstanceId,Map<String, Object> variableMap,String comment);
	/**
	 * 
	 * @param id  task id
	 * @return
	 */
	public DefaultResult<TaskModel> queryTaskById(String id);
}
