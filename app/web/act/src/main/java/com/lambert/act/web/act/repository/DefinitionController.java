package com.lambert.act.web.act.repository;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.lambert.act.biz.act.repository.ActRepositoryDefinitionManager;
import com.lambert.act.common.uitl.result.DefaultResult;
import com.lambert.act.common.uitl.result.DefaultWebUtils;
import com.lambert.act.common.uitl.result.Pager;
import com.lambert.act.common.uitl.result.ResultModel;

@Controller
public class DefinitionController {
	
	@Autowired
	ActRepositoryDefinitionManager actRepositoryDefinitionManager;
	
	
	
	@RequestMapping("/uploadworkflow.json")
	public String fileupload(ResultModel resultModel,HttpServletRequest request){
		MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
		MultipartFile file = multipartRequest.getFile("upFile");
		try{
			String filename=file.getOriginalFilename();
			InputStream is=file.getInputStream();
			actRepositoryDefinitionManager.createDeployment(filename, is);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "index";
	}
	@RequestMapping(value="/deleteDeploy.json",method = RequestMethod.POST)
	public @ResponseBody ResultModel deleteDeploy(ResultModel resultModel,String deploymentId){
		DefaultResult<Boolean> result = actRepositoryDefinitionManager.deleteDeploy(deploymentId);
		DefaultWebUtils.putResult2ModelMap(result, resultModel);
		return resultModel;
	}
	
	
	@RequestMapping(value="/findProcessDefinition.json",method = RequestMethod.POST)
	public @ResponseBody ResultModel findProcessDefinition(ResultModel resultModel){
		DefaultResult<Pager> result = actRepositoryDefinitionManager.findProcessDefinition();
		DefaultWebUtils.putResult2ModelMap(result, resultModel);
		return resultModel;
	}
	@RequestMapping(value="/showresource.json",method = RequestMethod.GET)
	public void showresource(HttpServletResponse response,String id){
		try {
			InputStream fis =  actRepositoryDefinitionManager.showresource(id);
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

}
