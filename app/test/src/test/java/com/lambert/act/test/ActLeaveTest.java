package com.lambert.act.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lambert.act.test.base.JunitTest;

public class ActLeaveTest extends JunitTest{
	
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
	
	  /**
     * 部署流程定义
     */
    //@Test
    public void deploy(){
        repositoryService.createDeployment().name("请假").addClasspathResource("process/leave.bpmn").deploy();
        System.out.println("***************部署流程定义完成***************");
    }
    
    /**
     * 启动一个请假流程
     */
   //@Test
    public void start() {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("employeeId", "Kermit"); //请假人
        String processId = runtimeService.startProcessInstanceByKey("leave", variables).getId();
        System.out.println("***************启动一个请假流程完成***************" + processId);
    }

    /**
     * 提交请假申请
     */
    //@Test
    public void apply(){
        System.out.println("***************提交请假申请开始***************");
        List<Task> tasks = taskService.createTaskQuery().taskAssignee("Kermit").list();
        System.out.println(tasks.size());
        for (Task task : tasks) {
            System.out.println("Kermit的任务taskname:" + task.getName() + ",id:" + task.getId());
            taskService.setVariable(task.getId(), "day", 4);//设置请假天数
            taskService.complete(task.getId());//完成任务  
            System.out.println("请假4 天");
        }
        System.out.println("***************提交请假申请完成***************");
    }
    
   // @Test
    public void step2manager() {
        System.out.println("***************经理组审批流程开始***************");
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("manager").list();// 经理组的任务
        System.out.println("经理组的任务：" + tasks.size());
        for (Task task : tasks) {
            System.out.println("经理组的任务taskname:" + task.getName() + ",id:" + task.getId());
            taskService.claim(task.getId(), "李四");//申领任务 
            taskService.setVariable(task.getId(), "flag", false);//true批准，false不批准
            Object applyUser = taskService.getVariable(task.getId(), "employeeId");
            Object day = taskService.getVariable(task.getId(), "day");
            System.out.println(String.format("%s请假%s天", applyUser, day));
            taskService.complete(task.getId());//完成任务 
        }
        System.out.println("***************经理组审批流程结束***************");
    }
    
    //@Test
    public void step2Boss() {
        System.out.println("***************总经理组审批流程开始***************");
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("boss").list();// 总经理组的任务
        System.out.println("总经理组的任务：" + tasks.size());
        for (Task task : tasks) {
            System.out.println("manager的任务taskname:" + task.getName() + ",id:" + task.getId());
            taskService.claim(task.getId(), "王五");//申领任务 
            taskService.setVariable(task.getId(), "flag", true);//true批准,false不批准
            Object applyUser = taskService.getVariable(task.getId(), "employeeId");
            Object day = taskService.getVariable(task.getId(), "day");
            System.out.println(String.format("%s请假%s天", applyUser, day));
            taskService.complete(task.getId());//完成任务 
        }
        System.out.println("***************总经理组审批流程结束***************");
    }

}
