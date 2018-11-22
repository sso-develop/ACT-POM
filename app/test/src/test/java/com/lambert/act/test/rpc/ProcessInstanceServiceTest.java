package com.lambert.act.test.rpc;

import java.net.MalformedURLException;

import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.caucho.hessian.client.HessianProxyFactory;
import com.lambert.act.common.service.facade.rpc.ProcessInstanceService;

public class ProcessInstanceServiceTest {
	HessianProxyFactory factory = new HessianProxyFactory();
	ProcessInstanceService processInstanceService = null;

	@Before
	public void before() {

		// 在服务器端的web.xml文件中配置的HessianServlet映射的访问URL地址
		String url = "http://localhost:8081/act/rpc/processInstanceService";
		try {
			processInstanceService = (ProcessInstanceService) factory.create(ProcessInstanceService.class, url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void selectTaskPagerByCondition() {
		System.err.println(JSON.toJSONString(processInstanceService.selectTaskPagerByCondition(null)));
	}

}
