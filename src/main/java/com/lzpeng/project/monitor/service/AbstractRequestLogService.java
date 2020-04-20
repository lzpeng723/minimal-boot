package com.lzpeng.project.monitor.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.lzpeng.framework.web.service.BaseServiceImpl;
import com.lzpeng.project.monitor.domain.RequestLog;
import com.lzpeng.project.monitor.repository.RequestLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 请求日志 抽象业务层 提供基于注解的缓存配置
 * @date: 2020-4-13
 * @time: 21:30:02
 * @author: 李志鹏
 */
@Service
@Transactional(rollbackOn = Exception.class)
public abstract class AbstractRequestLogService extends BaseServiceImpl<RequestLog> {

    protected static final String ENTITY_NAME = "com.lzpeng.project.monitor.domain.RequestLog";

    protected RequestLogRepository requestLogRepository;

    @Autowired
    public void setRequestLogRepository(RequestLogRepository requestLogRepository) {
        this.baseRepository = requestLogRepository;
        this.baseRepository = requestLogRepository;
        this.requestLogRepository = requestLogRepository;
    }

    @Override
    @CachePut(value = ENTITY_NAME, key = "#result.id", unless = "#result == null")
    public RequestLog save(RequestLog requestLog) {
        return super.save(requestLog);
    }

    @Override
    @CacheEvict(value = ENTITY_NAME, key = "#id")
    public void delete(String id) {
        super.delete(id);
    }

    @Override
    @CachePut(value = ENTITY_NAME, key = "#id", unless = "#result == null")
    public RequestLog update(String id, RequestLog model) {
        return super.update(id, model);
    }

    @Override
    @Cacheable(value = ENTITY_NAME, key = "#id", unless = "#result == null")
    public RequestLog findById(String id) {
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
    public List<RequestLog> readDataFromJson(String json) throws JsonProcessingException {
        List<RequestLog> list = objectMapper.readValue(json, new TypeReference<List<RequestLog>>() {
        });
        return list;
    }

}
