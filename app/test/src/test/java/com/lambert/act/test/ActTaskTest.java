package com.lambert.act.test;

import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lambert.act.test.base.JunitTest;

public class ActTaskTest extends JunitTest{
	
	@Autowired
	private TaskService taskService;
	
	  /** 
     * 查询当前办理人的个人任务 
     */  
    //@Test
    public void findPersonTaskList() {  
        // 任务办理人  
        String assignee = "1";  
        // 将任务赋值于list集合  
        List<Task> list = taskService // 与任务相关的service（正在执行）  
                .createTaskQuery() // 创建一个任务查询对象  
                .taskAssignee(assignee)
                .list();  
  
        // 对list集合进行为空判断  
        if (list != null && list.size() > 0) {  
            // 对list进行循环输出  
            for (org.activiti.engine.task.Task task : list) {  
                System.out.println("任务ID：" + task.getId());  
                System.out.println("任务办理人：" + task.getAssignee());  
                System.out.println("任务名称:" + task.getName());  
                System.out.println("任务创建时间：" + task.getCreateTime());  
                System.out.println("流程实例ID：" + task.getProcessInstanceId());  
            }  
        }  
    } 
    
    /* 完成任务（推进）*/
   // @Test 
    public void completeTask(){
    	String taskId = "2505";
    	taskService.complete(taskId);
    }

}
