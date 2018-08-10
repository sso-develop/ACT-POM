package com.lambert.act.test;

import java.util.List;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lambert.act.test.base.JunitTest;

public class ActTest extends JunitTest {
	
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private FormService formService;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private ManagementService managementService;
	
	/* 部署流程定义 （从classpath） */
	//@Test
	public void  deploymentClasspath(){
		 Deployment deployment = repositoryService // 与流程定义和部署对象相关的Service
                                 .createDeployment() // 创建一个部署对象
				                 .name("个人任务") // 设置对应流程的名称
				                 .addClasspathResource("process/leave.bpmn") // 从Classpath的资源中加载，一次只能加载一个文件(windows与linux下面要区分)
				                 .deploy(); // 完成部署

		 System.out.println("部署Id："+deployment.getId()); // 部署Id：1
	     System.out.println("部署名称："+deployment.getName()); // 部署名称：个人任务
	}

	/*  查询流程定义 */
    //Test
    public void findProcessDefinition(){
    	 List<ProcessDefinition> list =   repositoryService
								    	  .createProcessDefinitionQuery()//创建一个流程定义查询
								          /*指定查询条件,where条件*/
								          //.deploymentId(deploymentId)//使用部署对象ID查询
								          //.processDefinitionId(processDefinitionId)//使用流程定义ID查询
								          //.processDefinitionKey(processDefinitionKey)//使用流程定义的KEY查询
								          //.processDefinitionNameLike(processDefinitionNameLike)//使用流程定义的名称模糊查询
								          
								          /*排序*/
								          .orderByProcessDefinitionVersion().asc()//按照版本的升序排列
								          //.orderByProcessDefinitionName().desc()//按照流程定义的名称降序排列
								          
								          .list();//返回一个集合列表，封装流程定义
								          //.singleResult();//返回唯一结果集
								          //.count();//返回结果集数量
								          //.listPage(firstResult, maxResults)//分页查询
    	 
    	 if(list != null && list.size()>0){
             for(ProcessDefinition processDefinition:list){
                 System.out.println("流程定义ID:"+processDefinition.getId());//流程定义的key+版本+随机生成数
                 System.out.println("流程定义名称:"+processDefinition.getName());//对应HelloWorld.bpmn文件中的name属性值
                 System.out.println("流程定义的key:"+processDefinition.getKey());//对应HelloWorld.bpmn文件中的id属性值
                 System.out.println("流程定义的版本:"+processDefinition.getVersion());//当流程定义的key值相同的情况下，版本升级，默认从1开始
                 System.out.println("资源名称bpmn文件:"+processDefinition.getResourceName());
                 System.out.println("资源名称png文件:"+processDefinition.getDiagramResourceName());
                 System.out.println("部署对象ID:"+processDefinition.getDeploymentId());
                 System.out.println("################################");
             }
         }
    }
	
	
	//@Test
	public void test(){
		List<HistoricActivityInstance> list =  historyService.createHistoricActivityInstanceQuery()//
    	.list();
		for (HistoricActivityInstance historicActivityInstance : list) {
   		 System.out.println("任务ID：" + historicActivityInstance.getId());
   		 System.out.println("开始时间：" + historicActivityInstance.getStartTime());  
   		 System.out.println("结束时间：" + historicActivityInstance.getEndTime());  
   		 System.out.println("################################");
		}
	}
}
