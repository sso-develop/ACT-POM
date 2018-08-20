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
import com.lambert.act.biz.act.repository.query.HistoricActivityInstanceQueryObj;
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
	public DefaultResult<Pager> findHistoricActivityInstanceByPager(HistoricActivityInstanceQueryObj queryObj) {
		try {
			
			  // 页数、每页数量 转换为行数
	        int startRow = (queryObj.getCurrentPage() - 1) * queryObj.getPageSize();
	        int pageSize = queryObj.getPageSize();
			
			List<HistoricActivityInstance> activityInstances =  historyService.createHistoricActivityInstanceQuery()//
					.processInstanceId(queryObj.getProcessInstanceId())
					.orderByHistoricActivityInstanceStartTime()
					.desc()
					.listPage(startRow, pageSize);
			
			
			long count =  historyService.createHistoricActivityInstanceQuery()//
					.processInstanceId(queryObj.getProcessInstanceId())
					.count();
			
			
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
			pager.setTotalCount((int)count);
			pager.setPageNumber(queryObj.getCurrentPage());
			pager.setPageSize(queryObj.getPageSize());
			return new DefaultResult<Pager>(pager);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			return new DefaultResult(DefaultResultCode.SERVER_EXCEPTION, ex.getMessage());
		}
	}
	@Override
	public DefaultResult<List<HistoricActivityInstanceModel>> findHistoricActivityInstance(
			HistoricActivityInstanceQueryObj queryObj) {
		try {
			List<HistoricActivityInstance> activityInstances =  historyService.createHistoricActivityInstanceQuery()//
					.orderByHistoricActivityInstanceStartTime()
					.processInstanceId(queryObj.getProcessInstanceId())
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
			return new DefaultResult<List<HistoricActivityInstanceModel>>(modelList);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			return new DefaultResult(DefaultResultCode.SERVER_EXCEPTION, ex.getMessage());
		}
	}

}
