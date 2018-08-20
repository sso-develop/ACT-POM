package com.lambert.act.biz.act.repository.convertor;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;

import com.lambert.act.biz.act.repository.model.GroupModel;

public class GroupConvertor {
	
	public static GroupModel convertor2Model(Group group){
		GroupModel model = new GroupModel();
		model.setName(group.getName());
		model.setType(group.getType());
		model.setId(group.getId());
		return model;
	}
	public static List<GroupModel> convertor2Model(List<Group> groups){
		List<GroupModel> list = new ArrayList<GroupModel>();
		for (Group g : groups) {
			list.add( convertor2Model(g));
		}
		return list;
	}
	
	public static GroupEntity convertor2DO(GroupModel groupModel){
		GroupEntity model = new GroupEntity();
		model.setName(groupModel.getName());
		model.setType(groupModel.getType());
		model.setId(groupModel.getId());
		return model;
	}

}
