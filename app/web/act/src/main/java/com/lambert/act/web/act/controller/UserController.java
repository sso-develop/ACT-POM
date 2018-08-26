package com.lambert.act.web.act.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lambert.act.biz.act.repository.ActUserManager;
import com.lambert.act.biz.act.repository.model.UserModel;
import com.lambert.act.biz.act.repository.query.UserQueryObj;
import com.lambert.act.common.uitl.result.DefaultResult;
import com.lambert.act.common.uitl.result.DefaultWebUtils;
import com.lambert.act.common.uitl.result.Pager;
import com.lambert.act.common.uitl.result.ResultModel;

@Controller
public class UserController {
	@Autowired
	private ActUserManager actUserManager;
	

	@RequestMapping(value="/saveUser.json",method = RequestMethod.POST)
	public @ResponseBody ResultModel saveUser(ResultModel resultModel,UserModel userModel){
		DefaultResult<Boolean> result = actUserManager.saveUser(userModel);
		DefaultWebUtils.putResult2ModelMap(result, resultModel);
		return resultModel;
	}
	@RequestMapping(value="/queryUserByPager.json",method = RequestMethod.POST)
	public @ResponseBody ResultModel queryUserByPager(ResultModel resultModel,UserQueryObj queryObj){
		DefaultResult<Pager> result = actUserManager.queryByPager(queryObj);
		DefaultWebUtils.putResult2ModelMap(result, resultModel);
		return resultModel;
	}
	
	@RequestMapping(value="/queryAllUser.json",method = RequestMethod.POST)
	public @ResponseBody ResultModel queryAllUser(ResultModel resultModel){
		DefaultResult<List<UserModel>> result = actUserManager.queryAll();
		DefaultWebUtils.putResult2ModelMap(result, resultModel);
		return resultModel;
	}

	@RequestMapping(value="/deleteUser.json",method = RequestMethod.POST)
	public @ResponseBody ResultModel deleteUser(ResultModel resultModel,String id){
		DefaultResult<Boolean> result = actUserManager.deleteUser(id);
		DefaultWebUtils.putResult2ModelMap(result, resultModel);
		return resultModel;
	}
}
