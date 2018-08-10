package com.lambert.act.biz.act.repository.convertor;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.repository.ProcessDefinition;

import com.lambert.act.biz.act.repository.model.ProcessDefinitionModel;

public class ProcessDefinitionConvertor {
	
	public static ProcessDefinitionModel convertor2Model(ProcessDefinition processDefinition){
		ProcessDefinitionModel definitionModel = new ProcessDefinitionModel();
		definitionModel.setDeploymentId(processDefinition.getDeploymentId());
		definitionModel.setDiagramResourceName(processDefinition.getDiagramResourceName());
		definitionModel.setId(processDefinition.getId());
		definitionModel.setKey(processDefinition.getKey());
		definitionModel.setName(processDefinition.getName());
		definitionModel.setResourceName(processDefinition.getResourceName());
		definitionModel.setVersion(processDefinition.getVersion());
		return definitionModel;
	}
	
	public static List<ProcessDefinitionModel> convertor2Model(List<ProcessDefinition> processDefinitions){
		List<ProcessDefinitionModel> list = new ArrayList<ProcessDefinitionModel>();
		for (ProcessDefinition processDefinition : processDefinitions) {
			list.add(convertor2Model(processDefinition));
		}
		return list;
	}
}
