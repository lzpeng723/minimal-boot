package com.lzpeng.project.monitor.repository;

import com.lzpeng.project.monitor.domain.RequestLog;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * 请求日志 数据层单元测试
 * @date: 2020-4-6
 * @time: 1:57:47
 * @author: 李志鹏
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class RequestLogRepositoryTest {

    @Autowired
    private RequestLogRepository requestLogRepository;

    @Test
    public void testSave(){
        RequestLog requestLog = new RequestLog();
        requestLog = requestLogRepository.save(requestLog);
        assertNotNull(requestLog.getId());
    }

    @Test
    public void testDelete(){
        String id = "{}";
        requestLogRepository.deleteById(id);
    }

    @Test
    public void testUpdate(){
        String id = "{}";
        Optional<RequestLog> optional = requestLogRepository.findById(id);
        optional.ifPresent(requestLog -> {
            requestLog = requestLogRepository.save(requestLog);
            assertEquals(requestLog.getId(), id);
        });
    }

    @Test
    public void testFindAll(){
        Page<RequestLog> requestLogPage = requestLogRepository.findAll(PageRequest.of(0, 10));
        assertNotNull(requestLogPage.getContent());
    }

    @Test
    public void testFindById(){
        String id = "{}";
        Optional<RequestLog> optional = requestLogRepository.findById(id);
        RequestLog requestLog = optional.orElse(null);
        assertNotNull(requestLog);
    }
}