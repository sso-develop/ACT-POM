package com.lambert.act.web.act.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lambert.act.biz.act.repository.ActGroupManager;
import com.lambert.act.biz.act.repository.model.GroupModel;
import com.lambert.act.biz.act.repository.query.GroupQueryObj;
import com.lambert.act.common.uitl.result.DefaultResult;
import com.lambert.act.common.uitl.result.DefaultWebUtils;
import com.lambert.act.common.uitl.result.Pager;
import com.lambert.act.common.uitl.result.ResultModel;

@Controller
public class GroupController {
	
	@Autowired
	ActGroupManager actGroupManager;

	@RequestMapping(value="/saveGroup.json",method = RequestMethod.POST)
	public @ResponseBody ResultModel saveGroup(ResultModel resultModel,GroupModel groupModel){
		DefaultResult<Boolean> result = actGroupManager.saveGroup(groupModel);
		DefaultWebUtils.putResult2ModelMap(result, resultModel);
		return resultModel;
	}
	
	@RequestMapping(value="/queryGroupByPager.json",method = RequestMethod.POST)
	public @ResponseBody ResultModel queryGroupByPager(ResultModel resultModel,GroupQueryObj queryObj){
		DefaultResult<Pager> result = actGroupManager.queryByPager(queryObj);
		DefaultWebUtils.putResult2ModelMap(result, resultModel);
		return resultModel;
	}
	
	@RequestMapping(value="/queryAllGroup.json",method = RequestMethod.POST)
	public @ResponseBody ResultModel queryAllGroup(ResultModel resultModel){
		DefaultResult<List<GroupModel>> result = actGroupManager.queryAll();
		DefaultWebUtils.putResult2ModelMap(result, resultModel);
		return resultModel;
	}
	
	@RequestMapping(value="/deleteGroup.json",method = RequestMethod.POST)
	public @ResponseBody ResultModel deleteGroup(ResultModel resultModel,String id){
		DefaultResult<Boolean> result = actGroupManager.deleteGroup(id);
		DefaultWebUtils.putResult2ModelMap(result, resultModel);
		return resultModel;
	}
}
