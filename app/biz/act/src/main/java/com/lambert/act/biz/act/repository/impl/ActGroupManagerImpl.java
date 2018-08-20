package com.lambert.act.biz.act.repository.impl;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lambert.act.biz.act.repository.ActGroupManager;
import com.lambert.act.biz.act.repository.convertor.GroupConvertor;
import com.lambert.act.biz.act.repository.model.GroupModel;
import com.lambert.act.biz.act.repository.query.GroupQueryObj;
import com.lambert.act.common.uitl.result.DefaultResult;
import com.lambert.act.common.uitl.result.DefaultResultCode;
import com.lambert.act.common.uitl.result.Pager;

public class ActGroupManagerImpl implements ActGroupManager{
	private static final Logger LOGGER = LoggerFactory.getLogger(ActGroupManagerImpl.class);
	@Autowired
	IdentityService identityService;
	
	@Override
	public DefaultResult<Boolean> saveGroup(GroupModel groupModel) {
		try {
			if(StringUtils.isBlank(groupModel.getName())){
				return new DefaultResult<Boolean>(DefaultResultCode.ILLEGAL_PARAMS,"name 不能为空");
			}
			if(StringUtils.isBlank(groupModel.getType())){
				return new DefaultResult<Boolean>(DefaultResultCode.ILLEGAL_PARAMS,"type 不能为空");
			}
			if( identityService.createGroupQuery()
					.groupName(groupModel.getName())
					.groupType(groupModel.getType())
					.list().size() > 0){
				return new DefaultResult<Boolean>(DefaultResultCode.CREAT_PROCESS_FAILE,groupModel.getName()+ " 分组已存在");
			}
			GroupEntity groupEntity = GroupConvertor.convertor2DO(groupModel);
			identityService.saveGroup(groupEntity);
			return new DefaultResult<Boolean>(true);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return new DefaultResult(DefaultResultCode.SERVER_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public DefaultResult<Pager> queryByPager(GroupQueryObj queryObj) {
		try {
			Pager pager = new Pager();
			  // 页数、每页数量 转换为行数
	        int startRow = (queryObj.getCurrentPage() - 1) * queryObj.getPageSize();
	        int pageSize = queryObj.getPageSize();
	        
			List<Group> groups = identityService.createGroupQuery()
					.orderByGroupId()
					.desc()
					.listPage(startRow, pageSize);
			long count  = identityService.createGroupQuery().count();
			pager.setResult(GroupConvertor.convertor2Model(groups));
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
	public DefaultResult<Boolean> deleteGroup(String id) {
		try {
			
			if(identityService.createUserQuery()
					.memberOfGroup(id)
					.list().size() > 0){
				return new DefaultResult<Boolean>(DefaultResultCode.ILLEGAL_PARAMS,"该分组下有成员，不能删除");
			}
			identityService.deleteGroup(id);
			return new DefaultResult<Boolean>(true);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return new DefaultResult(DefaultResultCode.SERVER_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public DefaultResult<List<GroupModel>> queryAll() {
		try {
			List<Group> groups = identityService.createGroupQuery().list();
			return new DefaultResult<List<GroupModel>>(GroupConvertor.convertor2Model(groups));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return new DefaultResult(DefaultResultCode.SERVER_EXCEPTION, e.getMessage());
		}
	}

}
