package com.lambert.act.biz.act.repository.model;

import java.util.List;

public class HistoricActivityInstanceModel {
	/* System.out.println("任务ID：" + historicActivityInstance.getId());
		 System.out.println("开始时间：" + historicActivityInstance.getStartTime());  
		 System.out.println("结束时间：" + historicActivityInstance.getEndTime());*/
	public String id;
	public String taskId;
	private String activityName;
	
	private List<HistoricVariableInstanceModel> variables;
	private List<CommentModel> comments;
	
	private String assignee;
	public String startTime;
	public String endTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public List<HistoricVariableInstanceModel> getVariables() {
		return variables;
	}
	public void setVariables(List<HistoricVariableInstanceModel> variables) {
		this.variables = variables;
	}
	public List<CommentModel> getComments() {
		return comments;
	}
	public void setComments(List<CommentModel> comments) {
		this.comments = comments;
	}
	
	
}
