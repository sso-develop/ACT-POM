package com.lambert.act.biz.act.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lambert.act.biz.act.repository.ActTaskMananger;
import com.lambert.act.biz.act.repository.convertor.TaskModelConvertor;
import com.lambert.act.biz.act.repository.model.TaskModel;
import com.lambert.act.biz.act.repository.query.TaskQueryObj;
import com.lambert.act.common.uitl.result.DefaultResult;
import com.lambert.act.common.uitl.result.DefaultResultCode;
import com.lambert.act.common.uitl.result.Pager;

public class ActTaskManangerImpl implements ActTaskMananger {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActTaskManangerImpl.class);
	@Autowired
	private TaskService taskService;
	@Autowired
	IdentityService identityService;

	@Override
	public DefaultResult<Pager> findPersonTask(TaskQueryObj queryObj) {
		try {
			List<TaskModel> list = taskQuery(queryObj.getAssignee());
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
						.orderByTaskCreateTime()
						.desc()
						.list();
		}else{
			 list = taskService // 与任务相关的service（正在执行）
						.createTaskQuery() // 创建一个任务查询对象
						.orderByTaskCreateTime()
						.desc()
						.taskAssignee(assignee).list();
		}
		List<TaskModel> taskModels = new ArrayList<TaskModel>();
		for (Task task : list) {
			TaskModel model = TaskModelConvertor.convertor2Model(task);
			if(StringUtils.isNotBlank(task.getAssignee())){
				User user = identityService.createUserQuery().userId(task.getAssignee()).singleResult();
				if(user != null) model.setAssigneeName(user.getLastName() + " " + user.getFirstName());
				else model.setAssigneeName("该办理人已被删除");
			}
			taskModels.add(model);
		}
		return taskModels;

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
	@Override
	public DefaultResult<Boolean> assign(String taskId,String userId) {
		try {
			taskService.setAssignee(taskId, userId);
			return new DefaultResult<Boolean>(true);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			return new DefaultResult(DefaultResultCode.SERVER_EXCEPTION, ex.getMessage());
		}
	}
	@Override
	public DefaultResult<Boolean> claim(String taskId, String userId) {
		try {
			taskService.claim(taskId, userId);
			return new DefaultResult<Boolean>(true);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			return new DefaultResult(DefaultResultCode.SERVER_EXCEPTION, ex.getMessage());
		}
	}
	@Override
	public DefaultResult<TaskModel> queryTaskById(String id) {
		// TODO Auto-generated method stub
		try {
			Task task = taskService // 与任务相关的service（正在执行）
			.createTaskQuery() // 创建一个任务查询对象
			.taskId(id).singleResult();
			return new DefaultResult<TaskModel>(TaskModelConvertor.convertor2Model(task));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return new DefaultResult(DefaultResultCode.SERVER_EXCEPTION, e.getMessage());
		}
	}

}
