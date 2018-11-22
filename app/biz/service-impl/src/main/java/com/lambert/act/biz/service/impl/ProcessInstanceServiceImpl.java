package com.lambert.act.biz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lambert.act.biz.service.impl.convertor.TaskVOConvertor;
import com.lambert.act.common.service.facade.condition.TaskCondition;
import com.lambert.act.common.service.facade.model.TaskVO;
import com.lambert.act.common.service.facade.result.ActPager;
import com.lambert.act.common.service.facade.result.ActResult;
import com.lambert.act.common.service.facade.result.ActResultCode;
import com.lambert.act.common.service.facade.rpc.ProcessInstanceService;

/**
 *  流程事件处理工具接口
 * @author lambert  2018-11-06 18:31:29
 *
 */
public class ProcessInstanceServiceImpl implements ProcessInstanceService{
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessInstanceServiceImpl.class);
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Override
	public void creactProcess(String key,Map<String, Object> variableMap) {
		System.err.println("================");
		try {
			ProcessInstance pInstance = null;
			if (variableMap != null && variableMap.size() > 0) {
				pInstance = runtimeService// 与正在执行的流程实例和执行对象相关的Service
						.startProcessInstanceByKey(key, variableMap);
			} else {
				pInstance = runtimeService// 与正在执行的流程实例和执行对象相关的Service
						.startProcessInstanceByKey(key);
			}
			//return new DefaultResult<Boolean>(true);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			//return new DefaultResult(DefaultResultCode.SERVER_EXCEPTION, ex.getMessage());
		}
		
	}
	@Override
	public ActResult<Boolean> completeTask(String taskId, String processInstanceId, Map<String, Object> variableMap, String comment) {
		try {
			if(StringUtils.isBlank(taskId)) {
				return new ActResult(ActResultCode.ILLEGAL_PARAMS, "taskId is blank");
			}
			if(variableMap != null && variableMap.size() > 0){
				//taskService.setVariables(taskId, variableMap); //全局
				taskService.setVariablesLocal(taskId, variableMap);//当前
			}
			if(StringUtils.isNotBlank(comment)){
				// 添加任务评论
				taskService.addComment(taskId, processInstanceId, comment);
			}
			taskService.complete(taskId);
			return new ActResult<Boolean>(true);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			return new ActResult(ActResultCode.SERVER_EXCEPTION, ex.getMessage());
		}
		
	}
	/**
	 * @see 
	 */
	@Override
	public ActResult<ActPager> selectTaskPagerByCondition(TaskCondition condition) {
		List<Task> list = taskService // 与任务相关的service（正在执行）
				.createTaskQuery() // 创建一个任务查询对象
				.orderByTaskCreateTime()
				.desc()
				.list();
		List<TaskVO> taskVOs = new ArrayList<TaskVO>();
		for (Task task : list) {
			TaskVO vo = TaskVOConvertor.convertor2Model(task);
			taskVOs.add(vo);
		}
		ActPager pager = new ActPager();
		pager.setResult(taskVOs);
		ActResult<ActPager> result = new ActResult<ActPager>(pager);
		return result;
		
	}

}
