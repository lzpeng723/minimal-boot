package com.lzpeng.project.process.service;

import lombok.extern.slf4j.Slf4j;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskAdminRuntime;
import org.activiti.api.task.runtime.TaskRuntime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @date: 2020/4/19
 * @time: 18:51
 * @author:   李志鹏
 */
@Slf4j
@RunWith(SpringRunner.class)
@WithMockUser(username = "user", roles = "ACTIVITI_USER")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ProcessServiceTest {

    @Autowired
    private ProcessRuntime processRuntime;

    @Autowired
    private TaskRuntime taskRuntime;

    @Autowired
    private TaskAdminRuntime taskAdminRuntime;


    @Test
    public void getProcessDefinitions() {
        Page<ProcessDefinition> processDefinitionPage = processRuntime.processDefinitions(Pageable.of(0, 10));
        log.info("> 流程定义个数: " + processDefinitionPage.getTotalItems());
        for (ProcessDefinition pd : processDefinitionPage.getContent()) {
            log.info("\t > 流程定义: " + pd);
        }
    }

    @Test
    public void getProcessDefinition() {
    }

    @Test
    public void getProcessInstances() {
        List<ProcessInstance> processInstances =
                processRuntime.processInstances(Pageable.of(0, 10)).getContent();
        log.info("> 流程实例个数: " + processInstances.size());
        for (ProcessInstance pi : processInstances) {
            log.info("\t > 流程实例: " + pi);
        }
    }

    @Test
    public void getProcessInstanceMeta() {
    }

    @Test
    public void startProcess() {
        String processDefinitionKey = "sampleproc-e9b76ff9-6f70-42c9-8dee-f6116c533a6d";
        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey(processDefinitionKey)
                .withName("Sample Process: " + new Date())
                .withVariable("someProcessVar", "someProcVarValue")
                .build());
        log.info(">>> 启动流程实例: " + processInstance);
    }

    @Test
    @WithMockUser(username = "testuser", roles = "ACTIVITI_USER")
    public void getMyTasks() {
        Page<Task> tasks = taskRuntime.tasks(Pageable.of(0, 10));
        log.info("> 任务个数: " + tasks.getTotalItems());

        for (Task task : tasks.getContent()) {
            log.info("\t >  任务: " + task);
        }
    }

    @Test
    @WithMockUser(username = "administrator", roles = "ACTIVITI_ADMIN")
    public void getAllTasks() {
        Page<Task> tasks = taskAdminRuntime.tasks(Pageable.of(0, 10));
        log.info("> 所有任务个数: " + tasks.getTotalItems());

        for (Task task : tasks.getContent()) {
            log.info("\t > 任务: " + task);
        }
    }

    @Test
    @WithMockUser(username = "testuser", roles = "ACTIVITI_USER")
    public void completeTask() {
        String taskId = "1f491953-81b1-11ea-b6b8-00ff062f50b6";
        // 任务拾取
        taskRuntime.claim(TaskPayloadBuilder.claim()
                .withTaskId(taskId).build());
        // 任务完成
        taskRuntime.complete(TaskPayloadBuilder.complete()
                .withTaskId(taskId).build());
        log.info(">>> Completed Task: " + taskId);

    }
}