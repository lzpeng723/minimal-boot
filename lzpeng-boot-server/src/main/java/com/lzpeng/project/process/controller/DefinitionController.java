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
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessDefinitionMeta;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 流程定义控制器
 * @date: 2020/4/19
 * @time: 02:22
 * @author:   李志鹏
 */
@Slf4j
@RestController
@RequestMapping("/process/definition")
@Api(tags = "流程定义接口", value = "提供流程定义相关操作")
public class DefinitionController {

    @Autowired
    private ProcessRuntime processRuntime;


    /**
     * 获取所有流程定义 需要具有ROLE_ACTIVITI_USER角色
     *
     * @return
     */
    @GetMapping
    @ApiOperation("获取所有流程定义信息")
    public List<ProcessDefinition> getProcessDefinitions() {

        Page<ProcessDefinition> processDefinitionPage = processRuntime.processDefinitions(Pageable.of(0, 10));
        log.info("> Available Process definitions: " + processDefinitionPage.getTotalItems());

        for (ProcessDefinition pd : processDefinitionPage.getContent()) {
            log.info("\t > Process definition: " + pd);
        }

        return processDefinitionPage.getContent();
    }

    /**
     * 获取单个流程定义 需要具有ROLE_ACTIVITI_USER角色
     *
     * @return
     */
    @GetMapping("/{processDefinitionId}")
    @ApiOperation("获取单个流程定义信息")
    public ProcessDefinitionMeta getProcessDefinition(@PathVariable("processDefinitionId") String processDefinitionId) {
        ProcessDefinitionMeta processDefinitionMeta = processRuntime.processDefinitionMeta(processDefinitionId);
        return processDefinitionMeta;
    }

}
