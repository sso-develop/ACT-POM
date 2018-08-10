package com.lambert.act.biz.act.repository.impl;

import java.io.InputStream;
import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lambert.act.biz.act.repository.ActRepositoryDefinitionManager;
import com.lambert.act.biz.act.repository.convertor.ProcessDefinitionConvertor;
import com.lambert.act.biz.act.repository.model.ProcessDefinitionModel;
import com.lambert.act.common.uitl.result.DefaultResult;
import com.lambert.act.common.uitl.result.DefaultResultCode;
import com.lambert.act.common.uitl.result.Pager;

public class ActRepositoryDefinitionManagerImpl implements ActRepositoryDefinitionManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActRepositoryDefinitionManagerImpl.class);
	@Autowired
	private RepositoryService repositoryService;
	
	@Override
	public DefaultResult<Pager> findProcessDefinition() {
		try {
			Pager pager = new Pager();
			List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()// 创建一个流程定义查询
					/* 指定查询条件,where条件 */
					// .deploymentId(deploymentId)//使用部署对象ID查询
					// .processDefinitionId(processDefinitionId)//使用流程定义ID查询
					// .processDefinitionKey(processDefinitionKey)//使用流程定义的KEY查询
					// .processDefinitionNameLike(processDefinitionNameLike)//使用流程定义的名称模糊查询

					/* 排序 */
					.orderByProcessDefinitionVersion().asc()// 按照版本的升序排列
					// .orderByProcessDefinitionName().desc()//按照流程定义的名称降序排列

					.list();// 返回一个集合列表，封装流程定义
			// .singleResult();//返回唯一结果集
			// .count();//返回结果集数量
			// .listPage(firstResult, maxResults)//分页查询

			List<ProcessDefinitionModel> processDefinitionModelList = ProcessDefinitionConvertor.convertor2Model(list);
			
			pager.setResult(processDefinitionModelList);
			return new DefaultResult<Pager>(pager);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			return new DefaultResult(DefaultResultCode.SERVER_EXCEPTION, ex.getMessage());
		}
		
		
	}

	@Override
	public DefaultResult<Boolean> createDeployment(String filename, InputStream is) {
		try {
			repositoryService.createDeployment().addInputStream(filename, is).deploy();
			return new DefaultResult<Boolean>(true);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			return new DefaultResult(DefaultResultCode.SERVER_EXCEPTION, ex.getMessage());
		}
	}

	@Override
	public DefaultResult<Boolean> deleteDeploy(String deploymentId) {
		try {
			repositoryService.deleteDeployment(deploymentId,true);
			return new DefaultResult<Boolean>(true);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			return new DefaultResult(DefaultResultCode.SERVER_EXCEPTION, ex.getMessage());
		}
	}

	@Override
	public InputStream showresource(String id) {
		return repositoryService.getProcessDiagram(id);

	}
}
