package com.lzpeng.project.monitor.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.lzpeng.framework.web.service.BaseServiceImpl;
import com.lzpeng.project.monitor.domain.Job;
import com.lzpeng.project.monitor.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 定时任务 抽象业务层 提供基于注解的缓存配置
 * @date: 2020-4-12
 * @time: 23:31:53
 * @author: 李志鹏
 */
@Service
@Transactional(rollbackOn = Exception.class)
public abstract class AbstractJobService extends BaseServiceImpl<Job> {

    protected static final String ENTITY_NAME = "com.lzpeng.project.monitor.domain.Job";

    protected JobRepository jobRepository;

    @Autowired
    public void setJobRepository(JobRepository jobRepository) {
        this.baseRepository = jobRepository;
        this.baseRepository = jobRepository;
        this.jobRepository = jobRepository;
    }

    @Override
    @CachePut(value = ENTITY_NAME, key = "#result.id", unless = "#result == null")
    public Job save(Job job) {
        return super.save(job);
    }

    @Override
    @CacheEvict(value = ENTITY_NAME, key = "#id")
    public void delete(String id) {
        super.delete(id);
    }

    @Override
    @CachePut(value = ENTITY_NAME, key = "#id", unless = "#result == null")
    public Job update(String id, Job model) {
        return super.update(id, model);
    }

    @Override
    @Cacheable(value = ENTITY_NAME, key = "#id", unless = "#result == null")
    public Job findById(String id) {
        return super.findById(id);
    }

    /**
     * 从 json 读取数据
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    // 必须重写此方法否则,TypeReference获取不到泛型参数
    @Override
    public List<Job> readDataFromJson(String json) throws JsonProcessingException {
        List<Job> list = objectMapper.readValue(json, new TypeReference<List<Job>>() {
        });
        return list;
    }

}
