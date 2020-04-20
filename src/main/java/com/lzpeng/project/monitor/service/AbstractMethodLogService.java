package com.lzpeng.project.monitor.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.lzpeng.framework.web.service.BaseServiceImpl;
import com.lzpeng.project.monitor.domain.MethodLog;
import com.lzpeng.project.monitor.repository.MethodLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 方法日志 抽象业务层 提供基于注解的缓存配置
 * @date: 2020-4-12
 * @time: 23:31:53
 * @author: 李志鹏
 */
@Service
@Transactional(rollbackOn = Exception.class)
public abstract class AbstractMethodLogService extends BaseServiceImpl<MethodLog> {

    protected static final String ENTITY_NAME = "com.lzpeng.project.monitor.domain.MethodLog";

    protected MethodLogRepository methodLogRepository;

    @Autowired
    public void setMethodLogRepository(MethodLogRepository methodLogRepository) {
        this.baseRepository = methodLogRepository;
        this.baseRepository = methodLogRepository;
        this.methodLogRepository = methodLogRepository;
    }

    @Override
    @CachePut(value = ENTITY_NAME, key = "#result.id", unless = "#result == null")
    public MethodLog save(MethodLog methodLog) {
        return super.save(methodLog);
    }

    @Override
    @CacheEvict(value = ENTITY_NAME, key = "#id")
    public void delete(String id) {
        super.delete(id);
    }

    @Override
    @CachePut(value = ENTITY_NAME, key = "#id", unless = "#result == null")
    public MethodLog update(String id, MethodLog model) {
        return super.update(id, model);
    }

    @Override
    @Cacheable(value = ENTITY_NAME, key = "#id", unless = "#result == null")
    public MethodLog findById(String id) {
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
    public List<MethodLog> readDataFromJson(String json) throws JsonProcessingException {
        List<MethodLog> list = objectMapper.readValue(json, new TypeReference<List<MethodLog>>() {
        });
        return list;
    }

}
