package com.lambert.act.biz.act.repository;

import java.util.Map;

import com.lambert.act.common.uitl.result.DefaultResult;
import com.lambert.act.common.uitl.result.Pager;

public interface ActTaskMananger {
	
	public DefaultResult<Pager> findPersonTask();
	public DefaultResult<Boolean> completeTask(String id,String processInstanceId,Map<String, Object> variableMap,String comment);
}
