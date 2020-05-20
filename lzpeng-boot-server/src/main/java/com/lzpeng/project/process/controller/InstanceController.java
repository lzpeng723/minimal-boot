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
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.ProcessInstanceMeta;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 流程实例控制器
 * @date: 2020/4/19
 * @time: 02:22
 * @author:   李志鹏
 */
@Slf4j
@RestController
@RequestMapping("/process/instance")
@Api(tags = "流程实例接口", value = "流程实例相关操作")
public class InstanceController {

    @Autowired
    private ProcessRuntime processRuntime;

    /**
     * 获取所有流程实例
     *
     * @return
     */
    @GetMapping
    @ApiOperation("获取所有流程实例信息")
    public List<ProcessInstance> getProcessInstances() {
        List<ProcessInstance> processInstances =
                processRuntime.processInstances(Pageable.of(0, 10)).getContent();
        return processInstances;
    }

    /**
     * 获取单个流程实例信息
     *
     * @param processInstanceId
     * @return
     */
    @GetMapping("/{processInstanceId}")
    @ApiOperation("获取单个流程实例信息")
    public ProcessInstanceMeta getProcessInstanceMeta(@PathVariable(value = "processInstanceId") String processInstanceId) {
        ProcessInstanceMeta processInstanceMeta = processRuntime.processInstanceMeta(processInstanceId);
        return processInstanceMeta;
    }

    /**
     * 开启流程
     *
     * @param processDefinitionKey
     * @return
     */
    @ApiOperation("开启流程实例")
    @GetMapping("/start/{processDefinitionKey}")
    public ProcessInstance startProcess(@PathVariable("processDefinitionKey") String processDefinitionKey) {
        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey(processDefinitionKey)
                .withProcessDefinitionId(processDefinitionKey)
                .withName("Sample Process: " + new Date())
                .withVariable("someProcessVar", "someProcVarValue")
                .build());
        log.info(">>> Created Process Instance: " + processInstance);

        return processInstance;
    }

}
