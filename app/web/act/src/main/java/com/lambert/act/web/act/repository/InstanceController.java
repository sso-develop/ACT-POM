package com.lambert.act.web.act.repository;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lambert.act.biz.act.repository.ActHistoryManager;
import com.lambert.act.biz.act.repository.ActProcessInstanceMananger;
import com.lambert.act.biz.act.repository.ActTaskMananger;
import com.lambert.act.common.uitl.result.DefaultResult;
import com.lambert.act.common.uitl.result.DefaultResultCode;
import com.lambert.act.common.uitl.result.DefaultWebUtils;
import com.lambert.act.common.uitl.result.Pager;
import com.lambert.act.common.uitl.result.ResultModel;

@Controller
public class InstanceController {
	
	@Autowired
	ActProcessInstanceMananger actProcessInstanceMananger;
	@Autowired
	ActTaskMananger actTaskMananger;
	@Autowired
	ActHistoryManager actHistoryManager;
	
	@RequestMapping(value="/startProcessInstance.json",method = RequestMethod.POST)
	public @ResponseBody ResultModel startProcessInstance(ResultModel resultModel,String key,String variables){
		Map<String,Object> map = null; 
		DefaultResult<Boolean> result = null;
		try {
			JSONObject  jsonObject = JSONObject.parseObject(variables);
			map = (Map<String,Object>)jsonObject;
		} catch (Exception ex) {
			result = new DefaultResult<Boolean>(DefaultResultCode.ILLEGAL_PARAMS, ex.getMessage());
			DefaultWebUtils.putResult2ModelMap(result, resultModel);
			return resultModel;
		}
		result = actProcessInstanceMananger.startProcessInstance(key,map);
		DefaultWebUtils.putResult2ModelMap(result, resultModel);
		return resultModel;
	}
	
	@RequestMapping(value="/traceprocess.json",method = RequestMethod.GET)
	public void traceprocess(HttpServletResponse response,String definitionId,String instanceId){
		
		try {
			InputStream fis =  actProcessInstanceMananger.traceprocess(definitionId, instanceId);
			OutputStream out = new BufferedOutputStream(response.getOutputStream());
			byte[] buffer = new byte[1024];
			int len;
			while ((len = fis.read(buffer)) != -1) {
				out.write(buffer, 0, len);
				out.flush();
			}
		} catch (Exception e) {
			
		}
	}
	/*********************************************************************************/
	@RequestMapping(value="/findPersonTask.json",method = RequestMethod.POST)
	public @ResponseBody ResultModel  findPersonTask(ResultModel resultModel){
		DefaultResult<Pager> result = actTaskMananger.findPersonTask();
		DefaultWebUtils.putResult2ModelMap(result, resultModel);
		return resultModel;
	}
	@RequestMapping(value="/completeTask.json",method = RequestMethod.POST)
	public @ResponseBody ResultModel completeTask(ResultModel resultModel,String id,String processInstanceId,String variables,String comment){
		Map<String,Object> map = null; 
		DefaultResult<Boolean> result = null;
		try {
			JSONObject  jsonObject = JSONObject.parseObject(variables);
			map = (Map<String,Object>)jsonObject;
		} catch (Exception ex) {
			result = new DefaultResult<Boolean>(DefaultResultCode.ILLEGAL_PARAMS, ex.getMessage());
			DefaultWebUtils.putResult2ModelMap(result, resultModel);
			return resultModel;
		}
		
		result = actTaskMananger.completeTask(id,processInstanceId,map,comment);
		DefaultWebUtils.putResult2ModelMap(result, resultModel);
		return resultModel;
	}
	/*********************************************************************************************/
	@RequestMapping(value="/findHistoricActivityInstance.json",method = RequestMethod.POST)
	public @ResponseBody ResultModel findHistoricActivityInstance(ResultModel resultModel){
		DefaultResult<Pager> result = actHistoryManager.findHistoricActivityInstance();
		DefaultWebUtils.putResult2ModelMap(result, resultModel);
		return resultModel;
	}
}
