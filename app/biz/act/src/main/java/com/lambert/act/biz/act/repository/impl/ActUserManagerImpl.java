package com.lambert.act.biz.act.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.lambert.act.biz.act.repository.ActUserManager;
import com.lambert.act.biz.act.repository.convertor.UserConvertor;
import com.lambert.act.biz.act.repository.model.UserModel;
import com.lambert.act.biz.act.repository.query.UserQueryObj;
import com.lambert.act.common.uitl.result.DefaultResult;
import com.lambert.act.common.uitl.result.DefaultResultCode;
import com.lambert.act.common.uitl.result.Pager;

public class ActUserManagerImpl implements ActUserManager{
	private static final Logger LOGGER = LoggerFactory.getLogger(ActUserManagerImpl.class);
	@Autowired
	IdentityService identityService;
	@Autowired
	TaskService taskService;
	@Override
	public DefaultResult<Boolean> saveUser(UserModel userModel) {
		try{
			if(StringUtils.isBlank(userModel.getGroupId())){
				return new DefaultResult<Boolean>(DefaultResultCode.ILLEGAL_PARAMS,"groupId 不能为空");
			}
			UserEntity userEntity = UserConvertor.convertor2DO(userModel);
			identityService.saveUser(userEntity);
			String userId = userEntity.getId();
			identityService.createMembership(userId, userModel.getGroupId());
			return new DefaultResult<Boolean>(true);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return new DefaultResult(DefaultResultCode.SERVER_EXCEPTION, e.getMessage());
		}
	}
	@Override
	public DefaultResult<Pager> queryByPager(UserQueryObj queryObj) {
		try {
			Pager pager = new Pager();
			  // 页数、每页数量 转换为行数
	        int startRow = (queryObj.getCurrentPage() - 1) * queryObj.getPageSize();
	        int pageSize = queryObj.getPageSize();
	        List<User> users = new ArrayList<User>();
	        long count = 0;
	        if(StringUtils.isNotBlank(queryObj.getGroupId())){
	        	users = identityService.createUserQuery()
						.memberOfGroup(queryObj.getGroupId())
						.listPage(startRow, pageSize);
				count  = identityService.createUserQuery()
						.memberOfGroup(queryObj.getGroupId())
						.count();
	        }else{
	        	 users = identityService.createUserQuery()
						.listPage(startRow, pageSize);
				 count  = identityService.createUserQuery()
						.count();
	        }
			
			pager.setResult(UserConvertor.convertor2Model(users));
			pager.setTotalCount((int)count);
			pager.setPageNumber(queryObj.getCurrentPage());
			pager.setPageSize(queryObj.getPageSize());
			return new DefaultResult<Pager>(pager);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return new DefaultResult(DefaultResultCode.SERVER_EXCEPTION, e.getMessage());
		}
	}
	@Override
	public DefaultResult<Boolean> deleteUser(String id) {
		try {
			 if(taskService.createTaskQuery() 
						.taskAssignee(id).list().size() > 0){
					return new DefaultResult<Boolean>(DefaultResultCode.ILLEGAL_PARAMS,"该用户还有未审批工单，不能删除");
			 }
			identityService.deleteUser(id);
			return new DefaultResult<Boolean>(true);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return new DefaultResult(DefaultResultCode.SERVER_EXCEPTION, e.getMessage());
		}
	}
	@Override
	public DefaultResult<List<UserModel>> queryAll() {
		try {
			List<User> users = identityService.createUserQuery()
					.list();
			return new DefaultResult<List<UserModel>>(UserConvertor.convertor2Model(users));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return new DefaultResult(DefaultResultCode.SERVER_EXCEPTION, e.getMessage());
		}
	}

}
