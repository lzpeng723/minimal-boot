package com.lzpeng.project.${moduleName}.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.lzpeng.framework.web.service.${entityType}ServiceImpl;
import ${fullClassName};
import com.lzpeng.project.${moduleName}.repository.${simpleClassName}Repository;<#if entityType=="LeftTreeRightTable">
import ${leftTree.fullClassName};
import com.lzpeng.project.${leftTree.moduleName}.service.${leftTree.simpleClassName}Service;</#if>
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * ${chineseClassName} 抽象业务层 提供基于注解的缓存配置
 * @date : ${.now?date}
 * @time : ${.now?time}
 * @author : 李志鹏
 */
@Service
@Transactional(rollbackOn = Exception.class)
public abstract class Abstract${simpleClassName}Service extends ${entityType}ServiceImpl<<#if entityType=="LeftTreeRightTable">${leftTree.simpleClassName}, </#if>${simpleClassName}> {

    protected static final String ENTITY_NAME = "${fullClassName}";

    protected ${simpleClassName}Repository ${simpleClassName?uncap_first}Repository;<#if entityType=="LeftTreeRightTable">

    protected ${leftTree.simpleClassName}Service ${leftTree.simpleClassName?uncap_first}Service;</#if>

    @Autowired
    public void set${simpleClassName}Repository(${simpleClassName}Repository ${simpleClassName?uncap_first}Repository) {
        this.baseRepository = ${simpleClassName?uncap_first}Repository;<#if entityType??>
        this.${entityType?uncap_first}Repository = ${simpleClassName?uncap_first}Repository;</#if>
        this.${simpleClassName?uncap_first}Repository = ${simpleClassName?uncap_first}Repository;
    }<#if entityType=="LeftTreeRightTable">

    @Autowired
    public void set${leftTree.simpleClassName}Service(${leftTree.simpleClassName}Service ${leftTree.simpleClassName?uncap_first}Service) {
        this.treeService = ${leftTree.simpleClassName?uncap_first}Service;
        this.${leftTree.simpleClassName?uncap_first}Service = ${leftTree.simpleClassName?uncap_first}Service;
    }</#if>

    @Override
    @CachePut(value = ENTITY_NAME, key = "#result.id", unless = "#result == null")
    public ${simpleClassName} save(${simpleClassName} ${simpleClassName?uncap_first}) {
        return super.save(${simpleClassName?uncap_first});
    }

    @Override
    @CacheEvict(value = ENTITY_NAME, key = "#id")
    public void delete(String id) {
        super.delete(id);
    }

    @Override
    @CachePut(value = ENTITY_NAME, key = "#id", unless = "#result == null")
    public ${simpleClassName} update(String id, ${simpleClassName} model) {
        return super.update(id, model);
    }

    @Override
    @Cacheable(value = ENTITY_NAME, key = "#id", unless = "#result == null")
    public ${simpleClassName} findById(String id) {
        return super.findById(id);
    }

    /**
    * 从 json 读取数据
    * 必须重写此方法否则,TypeReference获取不到泛型参数
    * @param json
    * @return
    * @throws JsonProcessingException
    */
    @Override
    public List<${simpleClassName}> readDataFromJson(String json) throws JsonProcessingException {
        List<${simpleClassName}> list = objectMapper.readValue(json, new TypeReference<List<${simpleClassName}>>() {
        });
        return list;
    }

}
