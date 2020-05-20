package com.lzpeng.project.monitor.repository;

import com.lzpeng.project.monitor.domain.MethodLog;
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
 * 方法日志 数据层单元测试
 * @date: 2020-4-6
 * @time: 1:57:47
 * @author: 李志鹏
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MethodLogRepositoryTest {

    @Autowired
    private MethodLogRepository methodLogRepository;

    @Test
    public void testSave(){
        MethodLog methodLog = new MethodLog();
        methodLog = methodLogRepository.save(methodLog);
        assertNotNull(methodLog.getId());
    }

    @Test
    public void testDelete(){
        String id = "{}";
        methodLogRepository.deleteById(id);
    }

    @Test
    public void testUpdate(){
        String id = "{}";
        Optional<MethodLog> optional = methodLogRepository.findById(id);
        optional.ifPresent(methodLog -> {
            methodLog = methodLogRepository.save(methodLog);
            assertEquals(methodLog.getId(), id);
        });
    }

    @Test
    public void testFindAll(){
        Page<MethodLog> methodLogPage = methodLogRepository.findAll(PageRequest.of(0, 10));
        assertNotNull(methodLogPage.getContent());
    }

    @Test
    public void testFindById(){
        String id = "{}";
        Optional<MethodLog> optional = methodLogRepository.findById(id);
        MethodLog methodLog = optional.orElse(null);
        assertNotNull(methodLog);
    }
}