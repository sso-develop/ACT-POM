package com.lambert.act.biz.act.repository.model;

import java.util.List;

public class TaskModel {
	 /*System.out.println("任务ID：" + task.getId());  
     System.out.println("任务办理人：" + task.getAssignee());  
     System.out.println("任务名称:" + task.getName());  
     System.out.println("任务创建时间：" + task.getCreateTime());  
     System.out.println("流程实例ID：" + task.getProcessInstanceId());  */
	
	private String id;
	private String assignee;
	private String name;
	private String createTime;
	private String processInstanceId;
	private String processDefinitionId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	
	
}
