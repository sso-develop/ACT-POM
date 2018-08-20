package com.lambert.act.biz.act.repository.convertor;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.UserEntity;

import com.lambert.act.biz.act.repository.model.UserModel;

public class UserConvertor {
	
	public static UserEntity convertor2DO(UserModel model){
		UserEntity entity = new UserEntity();
		entity.setEmail(model.getEmail());
		entity.setFirstName(model.getFirstName());
		entity.setId(model.getId());
		entity.setLastName(model.getLastName());
		entity.setPassword(model.getPassword());
		return entity;
		
	}
	
	public static UserModel convertor2Model(User user){
		UserModel model = new UserModel();
		model.setEmail(user.getEmail());
		model.setFirstName(user.getFirstName());
		model.setId(user.getId());
		model.setLastName(user.getLastName());
		return model;
		
	}
	
	public static List<UserModel> convertor2Model(List<User> users){
		List<UserModel> list = new ArrayList<UserModel>();
		for (User user : users) {
			list.add(convertor2Model(user));
		}
		return list;
	}

}
