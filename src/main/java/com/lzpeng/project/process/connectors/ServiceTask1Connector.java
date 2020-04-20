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
package com.lzpeng.project.process.connectors;

import org.activiti.api.process.model.IntegrationContext;
import org.activiti.api.process.runtime.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @date: 2020/4/19
 * @time: 02:22
 * @author:   李志鹏
 */
@Service(value = "serviceTask1Impl")
public class ServiceTask1Connector implements Connector {
    private Logger logger = LoggerFactory.getLogger(ServiceTask1Connector.class);


    @Override
    public IntegrationContext apply(IntegrationContext integrationContext) {
        logger.info("Some service task logic... [processInstanceId=" + integrationContext.getProcessInstanceId() + "]");

        return integrationContext;
    }
}
