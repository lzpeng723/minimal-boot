package com.lzpeng.project.tool.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.lzpeng.framework.web.service.BaseServiceImpl;
import com.lzpeng.project.tool.domain.TableInfo;
import com.lzpeng.project.tool.repository.TableInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 表信息 抽象业务层 提供基于注解的缓存配置
 * @date: 2020-4-16
 * @time: 11:47:29
 * @author: 李志鹏
 */
@Service
@Transactional(rollbackOn = Exception.class)
public abstract class AbstractTableInfoService extends BaseServiceImpl<TableInfo> {

    protected static final String ENTITY_NAME = "com.lzpeng.project.tool.domain.TableInfo";

    protected TableInfoRepository tableInfoRepository;

    @Autowired
    public void setTableInfoRepository(TableInfoRepository tableInfoRepository) {
        this.baseRepository = tableInfoRepository;
        this.baseRepository = tableInfoRepository;
        this.tableInfoRepository = tableInfoRepository;
    }

    @Override
    @CachePut(value = ENTITY_NAME, key = "#result.id", unless = "#result == null")
    public TableInfo save(TableInfo tableInfo) {
        return super.save(tableInfo);
    }

    @Override
    @CacheEvict(value = ENTITY_NAME, key = "#id")
    public void delete(String id) {
        super.delete(id);
    }

    @Override
    @CachePut(value = ENTITY_NAME, key = "#id", unless = "#result == null")
    public TableInfo update(String id, TableInfo model) {
        return super.update(id, model);
    }

    @Override
    @Cacheable(value = ENTITY_NAME, key = "#id", unless = "#result == null")
    public TableInfo findById(String id) {
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
    public List<TableInfo> readDataFromJson(String json) throws JsonProcessingException {
        List<TableInfo> list = objectMapper.readValue(json, new TypeReference<List<TableInfo>>() {
        });
        return list;
    }

}
