package com.lambert.act.biz.act.repository.convertor;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.task.Comment;

import com.lambert.act.biz.act.repository.model.CommentModel;
import com.lambert.act.biz.act.repository.model.HistoricActivityInstanceModel;
import com.lambert.act.biz.act.repository.model.HistoricVariableInstanceModel;
import com.lambert.act.common.uitl.DateUtil;

public class HistoricActivityInstanceConvertor {
	public static HistoricActivityInstanceModel convertor2Model(HistoricActivityInstance activityInstance,List<HistoricVariableInstance> variableInstances,List<Comment> comments){
		HistoricActivityInstanceModel model = new HistoricActivityInstanceModel();
		model.setId(activityInstance.getId());
		model.setStartTime(DateUtil.format(activityInstance.getStartTime(), DateUtil.newFormat));
		model.setEndTime(DateUtil.format(activityInstance.getEndTime(), DateUtil.newFormat));
		model.setActivityName(activityInstance.getActivityName());
		model.setAssignee(activityInstance.getAssignee());
		model.setTaskId(activityInstance.getTaskId());
		List<HistoricVariableInstanceModel> instanceModels = new ArrayList<HistoricVariableInstanceModel>();
		if(variableInstances != null && variableInstances.size() > 0){
			for (HistoricVariableInstance historicVariableInstance : variableInstances) {
				HistoricVariableInstanceModel m = new HistoricVariableInstanceModel();
				m.setValue(historicVariableInstance.getValue());
				m.setVariableName(historicVariableInstance.getVariableName());
			    instanceModels.add(m);
			}
		}
		List<CommentModel> commentModels = new ArrayList<CommentModel>();
		if(comments != null && comments.size() > 0){
			for (Comment comment : comments) {
				CommentModel m = new CommentModel();
				m.setFullMessage(comment.getFullMessage());
				commentModels.add(m);
			}
		}
		model.setComments(commentModels);
		model.setVariables(instanceModels);
		return model;
	}
	public static HistoricActivityInstanceModel convertor2Model(HistoricActivityInstance activityInstance){
		return convertor2Model(activityInstance,null,null);
	}
	
	public static List<HistoricActivityInstanceModel> convertor2Model(List<HistoricActivityInstance> activityInstances){
		List<HistoricActivityInstanceModel> list = new ArrayList<HistoricActivityInstanceModel>();
		for (HistoricActivityInstance historicActivityInstance : activityInstances) {
			list.add(convertor2Model(historicActivityInstance));
		}
		return list;
	}
}
