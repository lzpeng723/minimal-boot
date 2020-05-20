package com.lzpeng.project.monitor.service;

import cn.hutool.core.io.FileUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lzpeng.common.response.QueryResult;
import com.lzpeng.project.monitor.domain.MethodLog;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.Charset;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * 方法日志 业务层单元测试
 * @date: 2020-4-6
 * @time: 1:57:47
 * @author: 李志鹏
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MethodLogServiceTest {

    @Autowired
    private MethodLogService methodLogService;

    @Test
    public void testSave() {
        MethodLog methodLog = new MethodLog();
        methodLog = methodLogService.save(methodLog);
        assertNotNull(methodLog.getId());
    }

    @Test
    public void testDelete() {
        String id = "{}";
        methodLogService.delete(id);
    }

    @Test
    public void testUpdate() {
        String id = "{}";
        MethodLog methodLog = new MethodLog();
        methodLog = methodLogService.update(id, methodLog);
        assertEquals(methodLog.getId(), id);
    }
    @Test
    public void testFindById() {
        String id = "{}";
        MethodLog methodLog = methodLogService.findById(id);
        assertEquals(methodLog.getId(), id);
    }

    @Test
    public void testQuery() {
        MethodLog methodLog = new MethodLog();
        QueryResult<MethodLog> result = methodLogService.query(0, 10, methodLog);
        assertNotNull(result.getList());
    }

    @Test
    public void testReadDataFromJson() throws JsonProcessingException {
        String path = ""; // json File Path
        String json = FileUtil.readString(path, Charset.defaultCharset());
        Collection<MethodLog> methodLogs = methodLogService.readDataFromJson(json);
        assertNotNull(methodLogs);
        log.info("{}", methodLogs);
    }

    @Test
    public void testImportDataFromJson() throws JsonProcessingException {
        String path = ""; // json File Path
        String json = FileUtil.readString(path, Charset.defaultCharset());
        Collection<MethodLog> methodLogs = methodLogService.importDataFromJson(json);
        assertNotNull(methodLogs);
        log.info("{}", methodLogs);
    }

}