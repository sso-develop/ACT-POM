package com.lambert.act.biz.act.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lambert.act.biz.act.repository.ActTaskMananger;
import com.lambert.act.biz.act.repository.convertor.TaskModelConvertor;
import com.lambert.act.biz.act.repository.model.TaskModel;
import com.lambert.act.common.uitl.result.DefaultResult;
import com.lambert.act.common.uitl.result.DefaultResultCode;
import com.lambert.act.common.uitl.result.Pager;

public class ActTaskManangerImpl implements ActTaskMananger {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActTaskManangerImpl.class);
	@Autowired
	private TaskService taskService;

	@Override
	public DefaultResult<Pager> findPersonTask() {
		try {
			List<TaskModel> list = taskQuery(null);
			Pager pager = new Pager();
			pager.setResult(list);
			return new DefaultResult<Pager>(pager);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			return new DefaultResult(DefaultResultCode.SERVER_EXCEPTION, ex.getMessage());
		}
		
	}
	private List<TaskModel> taskQuery(String assignee) {
		List<Task> list = null;
		if(StringUtils.isBlank(assignee)){
			 list = taskService // 与任务相关的service（正在执行）
						.createTaskQuery() // 创建一个任务查询对象
						.list();
		}else{
			 list = taskService // 与任务相关的service（正在执行）
						.createTaskQuery() // 创建一个任务查询对象
						.taskAssignee(assignee).list();
		}
	
		return TaskModelConvertor.convertor2Model(list);

	}
	@Override
	public DefaultResult<Boolean> completeTask(String id,String processInstanceId,Map<String, Object> variableMap,String comment) {
		try {
			Map<String,Object> vars = new HashMap<String, Object>();
			if(variableMap != null && variableMap.size() > 0){
				taskService.setVariables(id, variableMap); //全局
				//taskService.setVariablesLocal(id, variableMap);//当前
			}
			if(StringUtils.isNotBlank(comment)){
				// 添加任务评论
				taskService.addComment(id, processInstanceId, comment);
			}
			taskService.complete(id);
			return new DefaultResult<Boolean>(true);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			return new DefaultResult(DefaultResultCode.SERVER_EXCEPTION, ex.getMessage());
		}
	}

}
