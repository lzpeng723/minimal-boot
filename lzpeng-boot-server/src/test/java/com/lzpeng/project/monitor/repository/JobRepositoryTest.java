package com.lzpeng.project.monitor.repository;

import com.lzpeng.project.monitor.domain.Job;
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
 * 定时任务 数据层单元测试
 * @date: 2020-4-6
 * @time: 17:39:39
 * @author: 李志鹏
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class JobRepositoryTest {

    @Autowired
    private JobRepository jobRepository;

    @Test
    public void testSave(){
        Job job = new Job();
        job = jobRepository.save(job);
        assertNotNull(job.getId());
    }

    @Test
    public void testDelete(){
        String id = "{}";
        jobRepository.deleteById(id);
    }

    @Test
    public void testUpdate(){
        String id = "{}";
        Optional<Job> optional = jobRepository.findById(id);
        optional.ifPresent(job -> {
            job = jobRepository.save(job);
            assertEquals(job.getId(), id);
        });
    }

    @Test
    public void testFindAll(){
        Page<Job> jobPage = jobRepository.findAll(PageRequest.of(0, 10));
        assertNotNull(jobPage.getContent());
    }

    @Test
    public void testFindById(){
        String id = "{}";
        Optional<Job> optional = jobRepository.findById(id);
        Job job = optional.orElse(null);
        assertNotNull(job);
    }
}