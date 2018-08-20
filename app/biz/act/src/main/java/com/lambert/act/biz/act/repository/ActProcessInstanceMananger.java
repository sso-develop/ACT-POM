package com.lambert.act.biz.act.repository;

import java.io.InputStream;
import java.util.Map;

import com.lambert.act.common.uitl.result.DefaultResult;

public interface ActProcessInstanceMananger {
	
	public DefaultResult<Boolean> startProcessInstance(String key,Map<String, Object> variableMap);
	
	public InputStream traceprocess(String definitionId,String instanceId);
}
