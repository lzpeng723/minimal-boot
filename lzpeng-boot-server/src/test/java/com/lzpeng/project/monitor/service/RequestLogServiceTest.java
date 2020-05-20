package com.lzpeng.project.monitor.service;

import cn.hutool.core.io.FileUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lzpeng.common.response.QueryResult;
import com.lzpeng.project.monitor.domain.RequestLog;
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
 * 请求日志 业务层单元测试
 * @date: 2020-4-6
 * @time: 1:57:47
 * @author: 李志鹏
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class RequestLogServiceTest {

    @Autowired
    private RequestLogService requestLogService;

    @Test
    public void testSave() {
        RequestLog requestLog = new RequestLog();
        requestLog = requestLogService.save(requestLog);
        assertNotNull(requestLog.getId());
    }

    @Test
    public void testDelete() {
        String id = "{}";
        requestLogService.delete(id);
    }

    @Test
    public void testUpdate() {
        String id = "{}";
        RequestLog requestLog = new RequestLog();
        requestLog = requestLogService.update(id, requestLog);
        assertEquals(requestLog.getId(), id);
    }
    @Test
    public void testFindById() {
        String id = "{}";
        RequestLog requestLog = requestLogService.findById(id);
        assertEquals(requestLog.getId(), id);
    }

    @Test
    public void testQuery() {
        RequestLog requestLog = new RequestLog();
        QueryResult<RequestLog> result = requestLogService.query(0, 10, requestLog);
        assertNotNull(result.getList());
    }

    @Test
    public void testReadDataFromJson() throws JsonProcessingException {
        String path = ""; // json File Path
        String json = FileUtil.readString(path, Charset.defaultCharset());
        Collection<RequestLog> requestLogs = requestLogService.readDataFromJson(json);
        assertNotNull(requestLogs);
        log.info("{}", requestLogs);
    }

    @Test
    public void testImportDataFromJson() throws JsonProcessingException {
        String path = ""; // json File Path
        String json = FileUtil.readString(path, Charset.defaultCharset());
        Collection<RequestLog> requestLogs = requestLogService.importDataFromJson(json);
        assertNotNull(requestLogs);
        log.info("{}", requestLogs);
    }

}