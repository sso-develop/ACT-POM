package com.lambert.act.biz.service.impl;

import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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

}
