package com.lambert.act.biz.act.repository;

import java.util.List;

import com.lambert.act.biz.act.repository.model.UserModel;
import com.lambert.act.biz.act.repository.query.UserQueryObj;
import com.lambert.act.common.uitl.result.DefaultResult;
import com.lambert.act.common.uitl.result.Pager;

public interface ActUserManager {
	
	public DefaultResult<Boolean> saveUser(UserModel userModel);
	
	public DefaultResult<Boolean> deleteUser(String id);
	
	public DefaultResult<Pager> queryByPager(UserQueryObj queryObj);
	
	public DefaultResult<List<UserModel>> queryAll();

}
