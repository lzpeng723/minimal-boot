package com.lzpeng.project.monitor.service;

import cn.hutool.core.io.FileUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lzpeng.common.response.QueryResult;
import com.lzpeng.project.monitor.domain.Job;
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
 * 定时任务 业务层单元测试
 * @date: 2020-4-6
 * @time: 17:39:39
 * @author: 李志鹏
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class JobServiceTest {

    @Autowired
    private JobService jobService;

    @Test
    public void testSave() {
        Job job = new Job();
        job = jobService.save(job);
        assertNotNull(job.getId());
    }

    @Test
    public void testDelete() {
        String id = "{}";
        jobService.delete(id);
    }

    @Test
    public void testUpdate() {
        String id = "{}";
        Job job = new Job();
        job = jobService.update(id, job);
        assertEquals(job.getId(), id);
    }
    @Test
    public void testFindById() {
        String id = "{}";
        Job job = jobService.findById(id);
        assertEquals(job.getId(), id);
    }

    @Test
    public void testQuery() {
        Job job = new Job();
        QueryResult<Job> result = jobService.query(0, 10, job);
        assertNotNull(result.getList());
    }

    @Test
    public void testReadDataFromJson() throws JsonProcessingException {
        String path = ""; // json File Path
        String json = FileUtil.readString(path, Charset.defaultCharset());
        Collection<Job> jobs = jobService.readDataFromJson(json);
        assertNotNull(jobs);
        log.info("{}", jobs);
    }

    @Test
    public void testImportDataFromJson() throws JsonProcessingException {
        String path = ""; // json File Path
        String json = FileUtil.readString(path, Charset.defaultCharset());
        Collection<Job> jobs = jobService.importDataFromJson(json);
        assertNotNull(jobs);
        log.info("{}", jobs);
    }

}