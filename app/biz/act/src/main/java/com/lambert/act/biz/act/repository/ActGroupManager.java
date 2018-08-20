package com.lambert.act.biz.act.repository;

import java.util.List;

import com.lambert.act.biz.act.repository.model.GroupModel;
import com.lambert.act.biz.act.repository.query.GroupQueryObj;
import com.lambert.act.common.uitl.result.DefaultResult;
import com.lambert.act.common.uitl.result.Pager;

public interface ActGroupManager {
	
	public DefaultResult<Boolean> saveGroup(GroupModel groupModel);
	
	public DefaultResult<Boolean> deleteGroup(String id);
	
	public DefaultResult<Pager> queryByPager(GroupQueryObj queryObj);
	
	public DefaultResult<List<GroupModel>> queryAll();

}
