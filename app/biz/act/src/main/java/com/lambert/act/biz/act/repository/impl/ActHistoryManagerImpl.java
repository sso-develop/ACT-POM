package com.lambert.act.biz.act.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.task.Comment;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lambert.act.biz.act.repository.ActHistoryManager;
import com.lambert.act.biz.act.repository.convertor.HistoricActivityInstanceConvertor;
import com.lambert.act.biz.act.repository.model.HistoricActivityInstanceModel;
import com.lambert.act.common.uitl.result.DefaultResult;
import com.lambert.act.common.uitl.result.DefaultResultCode;
import com.lambert.act.common.uitl.result.Pager;

public class ActHistoryManagerImpl implements ActHistoryManager{
	private static final Logger LOGGER = LoggerFactory.getLogger(ActHistoryManagerImpl.class);
	@Autowired
	private HistoryService historyService;
	@Autowired
	private TaskService taskService;
	@Override
	public DefaultResult<Pager> findHistoricActivityInstance() {
		try {
			List<HistoricActivityInstance> activityInstances =  historyService.createHistoricActivityInstanceQuery()//
					.orderByHistoricActivityInstanceStartTime()
					.desc()
			    	.list();
			List<HistoricActivityInstanceModel> modelList = new ArrayList<HistoricActivityInstanceModel>();
			for (HistoricActivityInstance historicActivityInstance : activityInstances) {
				 String processInstanceId = historicActivityInstance.getProcessInstanceId();
			        List<HistoricVariableInstance> list =historyService
			                .createHistoricVariableInstanceQuery()
			                .processInstanceId(processInstanceId)
			                .list();
			        List<Comment> comments = null;
			        if(StringUtils.isNotBlank(historicActivityInstance.getTaskId())){
			        	comments = taskService.getTaskComments(historicActivityInstance.getTaskId());
			        }
			        modelList.add(HistoricActivityInstanceConvertor.convertor2Model(historicActivityInstance, list,comments));
			        
			       
			}
			Pager pager = new Pager();
			pager.setResult(modelList);
			return new DefaultResult<Pager>(pager);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			return new DefaultResult(DefaultResultCode.SERVER_EXCEPTION, ex.getMessage());
		}
	}

}
