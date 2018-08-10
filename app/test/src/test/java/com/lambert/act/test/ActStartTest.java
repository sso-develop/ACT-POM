package com.lambert.act.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lambert.act.test.base.JunitTest;

public class ActStartTest extends JunitTest {
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private FormService formService;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private ManagementService managementService;

	/** 启动流程实例 */
	// @Test
	public void startProcessInstance() {

		/*
		 * 流程定义ID:personalTask:1:4 流程定义名称:personalTask 流程定义的key:personalTask
		 * 流程定义的版本:1 资源名称bpmn文件:process/personalTask.bpmn
		 * 资源名称png文件:process/personalTask.personalTask.png 部署对象ID:1
		 */

		// 流程定义的key
		String processDefinitionKeyString = "personalTask";
		// 启动流程实例的同时，设置流程变量，使用流程便令用来指定任务的办理人,对应.bpmn文件中的#{userID}
		HashMap<String, Object> variables = new HashMap<String, Object>();
		variables.put("userId", "1");

		ProcessInstance pInstance = runtimeService// 与正在执行的流程实例和执行对象相关的Service
				.startProcessInstanceByKey(processDefinitionKeyString, variables);

		System.out.println(pInstance.getId());// 流程实例ID 2501
		System.out.println(pInstance.getProcessDefinitionId());// 流程定义ID
																// personalTask:1:4
	}
	//生成当前流程进度图
	//@Test
	public void traceprocess() {
		String executionid = "2501";
		ProcessInstance process = runtimeService.createProcessInstanceQuery().processInstanceId(executionid)
				.singleResult();
		BpmnModel bpmnmodel = repositoryService.getBpmnModel(process.getProcessDefinitionId());

		List<String> activeActivityIds = runtimeService.getActiveActivityIds(executionid);

		DefaultProcessDiagramGenerator gen = new DefaultProcessDiagramGenerator();

		// 获得历史活动记录实体（通过启动时间正序排序，不然有的线可以绘制不出来）
		List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery()
				.executionId(executionid).orderByHistoricActivityInstanceStartTime().asc().list();

		// 计算活动线
		List<String> highLightedFlows = getHighLightedFlows(
				(ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
						.getDeployedProcessDefinition(process.getProcessDefinitionId()),
				historicActivityInstances);

		InputStream in = gen.generateDiagram(bpmnmodel, "png", activeActivityIds, highLightedFlows, "宋体", "宋体", null,
				1.0);
		
		File file = new File("D:/test.png");

		try {
			inputstreamtofile(in, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//文件输出
	public void inputstreamtofile(InputStream ins, File file) throws IOException {
		OutputStream os = new FileOutputStream(file);
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		os.close();
		ins.close();
	}

	//获取高亮的线
	public List<String> getHighLightedFlows(ProcessDefinitionEntity processDefinitionEntity,
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
