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
package com.lzpeng.project.process.listeners;

import org.activiti.api.model.shared.event.RuntimeEvent;
import org.activiti.api.model.shared.event.VariableCreatedEvent;
import org.activiti.api.process.runtime.events.*;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @date: 2020/4/19
 * @time: 02:22
 * @author:   李志鹏
 */
@Service
public class MyProcessEventListener implements ProcessRuntimeEventListener {
    private Logger logger = LoggerFactory.getLogger(MyProcessEventListener.class);

    @Override
    public void onEvent(RuntimeEvent runtimeEvent) {

        if (runtimeEvent instanceof ProcessStartedEvent) {
            logger.info("Do something, process is started: " + runtimeEvent.toString());
        } else if (runtimeEvent instanceof ProcessCompletedEvent) {
            logger.info("Do something, process is completed: " + runtimeEvent.toString());
        } else if (runtimeEvent instanceof ProcessCancelledEvent) {
            logger.info("Do something, process is cancelled: " + runtimeEvent.toString());
        } else if (runtimeEvent instanceof ProcessSuspendedEvent) {
            logger.info("Do something, process is suspended: " + runtimeEvent.toString());
        } else if (runtimeEvent instanceof ProcessResumedEvent) {
            logger.info("Do something, process is resumed: " + runtimeEvent.toString());
        } else if (runtimeEvent instanceof ProcessCreatedEvent) {
            logger.info("Do something, process is created: " + runtimeEvent.toString());
        }
//        else if (runtimeEvent instanceof SequenceFlowTakenEvent)
//            logger.info("Do something, sequence flow is taken: " + runtimeEvent.toString());
        else if (runtimeEvent instanceof VariableCreatedEvent) {
            logger.info("Do something, variable was created: " + runtimeEvent.toString());
        } else {
            logger.info("Unknown event: " + runtimeEvent.toString());
        }

    }
}
