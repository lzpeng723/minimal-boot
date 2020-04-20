/*
 * Copyright 2018 Alfresco, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lzpeng.project.process.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskAdminRuntime;
import org.activiti.api.task.runtime.TaskRuntime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 流程任务控制器
 * @date: 2020/4/19
 * @time: 02:22
 * @author:   李志鹏
 */
@Slf4j
@RestController
@RequestMapping("/process/task")
@Api(tags = "流程任务接口", value = "提供流程任务的执行关闭等")
public class TaskController {

    @Autowired
    private TaskRuntime taskRuntime;

    @Autowired
    private TaskAdminRuntime taskAdminRuntime;

    /**
     * 任务列表
     * @return
     */
    @GetMapping
    @ApiOperation("获取我的任务")
    public List<Task> getMyTasks() {
        Page<Task> tasks = taskRuntime.tasks(Pageable.of(0, 10));
        log.info("> My Available Tasks: " + tasks.getTotalItems());

        for (Task task : tasks.getContent()) {
            log.info("\t > My User Task: " + task);
        }

        return tasks.getContent();
    }

    /**
     * Need to be logged in as admin to use this call
     */
    @GetMapping("/all")
    @ApiOperation("获取所有任务")
    public List<Task> getAllTasks() {
        Page<Task> tasks = taskAdminRuntime.tasks(Pageable.of(0, 10));
        log.info("> All Available Tasks: " + tasks.getTotalItems());

        for (Task task : tasks.getContent()) {
            log.info("\t > User Task: " + task);
        }

        return tasks.getContent();
    }

    @GetMapping("/task/complete/{taskId}")
    @ApiOperation("完成任务")
    public String completeTask(@PathVariable(value="taskId") String taskId) {
        taskRuntime.complete(TaskPayloadBuilder.complete()
                .withTaskId(taskId).build());
        log.info(">>> Completed Task: " + taskId);

        return "Completed Task: " + taskId;
    }
}
