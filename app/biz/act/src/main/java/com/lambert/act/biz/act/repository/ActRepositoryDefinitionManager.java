package com.lambert.act.biz.act.repository;

import java.io.InputStream;

import com.lambert.act.common.uitl.result.DefaultResult;
import com.lambert.act.common.uitl.result.Pager;

public interface ActRepositoryDefinitionManager {
	
	
	public DefaultResult<Boolean> createDeployment(String filename,InputStream is);
	public DefaultResult<Boolean> deleteDeploy(String deploymentId);
	
	public InputStream  showresource(String id);
	
	public DefaultResult<Pager> findProcessDefinition();
}
