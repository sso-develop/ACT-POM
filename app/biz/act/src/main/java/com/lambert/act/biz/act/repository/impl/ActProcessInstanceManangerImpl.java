package com.lambert.act.biz.act.repository.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.lambert.act.biz.act.repository.ActProcessInstanceMananger;
import com.lambert.act.common.uitl.result.DefaultResult;
import com.lambert.act.common.uitl.result.DefaultResultCode;

public class ActProcessInstanceManangerImpl implements ActProcessInstanceMananger {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActProcessInstanceManangerImpl.class);
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private HistoryService historyService;

	public DefaultResult<Boolean> startProcessInstance(String key, Map<String, Object> variableMap) {
		try {
			ProcessInstance pInstance = null;
			if (variableMap != null && variableMap.size() > 0) {
				pInstance = runtimeService// 与正在执行的流程实例和执行对象相关的Service
						.startProcessInstanceByKey(key, variableMap);
			} else {
				pInstance = runtimeService// 与正在执行的流程实例和执行对象相关的Service
						.startProcessInstanceByKey(key);
			}
			return new DefaultResult<Boolean>(true);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			return new DefaultResult(DefaultResultCode.SERVER_EXCEPTION, ex.getMessage());
		}
	}

	@Override
	public InputStream traceprocess(String definitionId, String instanceId) {
	
		BpmnModel bpmnmodel = repositoryService.getBpmnModel(definitionId);
		List<String> activeActivityIds = runtimeService.getActiveActivityIds(instanceId);

		DefaultProcessDiagramGenerator gen = new DefaultProcessDiagramGenerator();

		// 获得历史活动记录实体（通过启动时间正序排序，不然有的线可以绘制不出来）
		List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery()
				.executionId(instanceId).orderByHistoricActivityInstanceStartTime().asc().list();

		// 计算活动线
		List<String> highLightedFlows = getHighLightedFlows(
				(ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
						.getDeployedProcessDefinition(definitionId),
				historicActivityInstances);
		InputStream in = gen.generateDiagram(bpmnmodel, "png", activeActivityIds, highLightedFlows, "宋体", "宋体", null,
				1.0);
		
		return in;
	}

	// 获取高亮的线
	private List<String> getHighLightedFlows(ProcessDefinitionEntity processDefinitionEntity,
			List<HistoricActivityInstance> historicActivityInstances) {

		List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId
		for (int i = 0; i < historicActivityInstances.size(); i++) {// 对历史流程节点进行遍历
			ActivityImpl activityImpl = processDefinitionEntity
					.findActivity(historicActivityInstances.get(i).getActivityId());// 得
																					// 到节点定义的详细信息
			List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// 用以保存后需开始时间相同的节点
			if ((i + 1) >= historicActivityInstances.size()) {
				break;
			}
			ActivityImpl sameActivityImpl1 = processDefinitionEntity
					.findActivity(historicActivityInstances.get(i + 1).getActivityId());// 将后面第一个节点放在时间相同节点的集合里
			sameStartTimeNodes.add(sameActivityImpl1);
			for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
				HistoricActivityInstance activityImpl1 = historicActivityInstances.get(j);// 后续第一个节点
				HistoricActivityInstance activityImpl2 = historicActivityInstances.get(j + 1);// 后续第二个节点
				if (activityImpl1.getStartTime().equals(activityImpl2.getStartTime())) {// 如果第一个节点和第二个节点开始时间相同保存
					ActivityImpl sameActivityImpl2 = processDefinitionEntity
							.findActivity(activityImpl2.getActivityId());
					sameStartTimeNodes.add(sameActivityImpl2);
				} else {// 有不相同跳出循环
					break;
				}
			}
			List<PvmTransition> pvmTransitions = activityImpl.getOutgoingTransitions();// 取出节点的所有出去的线
			for (PvmTransition pvmTransition : pvmTransitions) {// 对所有的线进行遍历
				ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition.getDestination();// 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
				if (sameStartTimeNodes.contains(pvmActivityImpl)) {
					highFlows.add(pvmTransition.getId());
				}
			}
		}
		return highFlows;
	}
}
