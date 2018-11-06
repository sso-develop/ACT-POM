package com.lambert.act.test.rpc;

import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;
import com.lambert.act.common.service.facade.rpc.ProcessInstanceService;

public class RpcTest {
	
		public static void main(String[] args) {
			
	 
		    //在服务器端的web.xml文件中配置的HessianServlet映射的访问URL地址  
		    String url = "http://localhost:8081/act/rpc/processInstanceService";   
		    HessianProxyFactory factory = new HessianProxyFactory();   
		    ProcessInstanceService processInstanceService = null;  
		    
		    
		    try {
		    	processInstanceService = (ProcessInstanceService) factory.create(ProcessInstanceService.class, url);
				
				processInstanceService.creactProcess(null, null);
				//System.out.println("fafa   "+a);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
		 }
		 
}
