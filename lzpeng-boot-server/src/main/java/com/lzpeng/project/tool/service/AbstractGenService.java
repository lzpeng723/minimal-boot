package com.lzpeng.project.tool.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.lzpeng.framework.web.service.BaseServiceImpl;
import com.lzpeng.project.tool.domain.Gen;
import com.lzpeng.project.tool.repository.GenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 代码生成模板 抽象业务层 提供基于注解的缓存配置
 * @date: 2020-4-16
 * @time: 11:47:29
 * @author: 李志鹏
 */
@Service
@Transactional(rollbackOn = Exception.class)
public abstract class AbstractGenService extends BaseServiceImpl<Gen> {

    protected static final String ENTITY_NAME = "com.lzpeng.project.tool.domain.Gen";

    protected GenRepository genRepository;

    @Autowired
    public void setGenRepository(GenRepository genRepository) {
        this.baseRepository = genRepository;
        this.baseRepository = genRepository;
        this.genRepository = genRepository;
    }

    @Override
    @CachePut(value = ENTITY_NAME, key = "#result.id", unless = "#result == null")
    public Gen save(Gen gen) {
        return super.save(gen);
    }

    @Override
    @CacheEvict(value = ENTITY_NAME, key = "#id")
    public void delete(String id) {
        super.delete(id);
    }

    @Override
    @CachePut(value = ENTITY_NAME, key = "#id", unless = "#result == null")
    public Gen update(String id, Gen model) {
        return super.update(id, model);
    }

    @Override
    @Cacheable(value = ENTITY_NAME, key = "#id", unless = "#result == null")
    public Gen findById(String id) {
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
    public List<Gen> readDataFromJson(String json) throws JsonProcessingException {
        List<Gen> list = objectMapper.readValue(json, new TypeReference<List<Gen>>() {
        });
        return list;
    }

}
