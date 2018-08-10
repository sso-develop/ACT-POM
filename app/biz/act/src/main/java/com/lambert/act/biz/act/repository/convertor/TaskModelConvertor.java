package com.lambert.act.biz.act.repository.convertor;


import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.task.Task;

import com.lambert.act.biz.act.repository.model.TaskModel;
import com.lambert.act.common.uitl.DateUtil;

public class TaskModelConvertor {
	
	public static TaskModel convertor2Model(Task task ){
		TaskModel model = new TaskModel();
		
		model.setAssignee(task.getAssignee());
		model.setCreateTime(DateUtil.format(task.getCreateTime(), DateUtil.noSecondFormat));
		model.setId(task.getId());
		model.setProcessInstanceId(task.getProcessInstanceId());
		model.setName(task.getName());
		model.setProcessDefinitionId(task.getProcessDefinitionId());
		return model;
	}
	
	public static List<TaskModel> convertor2Model(List<Task> tasks){
		List<TaskModel> list =  new ArrayList<>();
		for (Task task : tasks) {
			list.add(convertor2Model(task));
		}
		return list;
		
	}
	

}
