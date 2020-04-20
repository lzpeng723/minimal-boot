package com.lzpeng.project.tool.utils;

import com.lzpeng.project.monitor.domain.Job;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @date: 2020/4/17
 * @time: 0:27
 * @author:   李志鹏
 */
@Slf4j
public class EntityClassUtilTest {

    @Test
    public void getModuleName() {
        log.info("moduleName: {}", EntityClassUtil.getModuleName(Job.class));
    }
}