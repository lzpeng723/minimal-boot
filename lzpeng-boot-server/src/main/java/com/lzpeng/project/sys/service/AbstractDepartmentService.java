package com.lzpeng.project.sys.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.lzpeng.framework.web.service.TreeServiceImpl;
import com.lzpeng.project.sys.domain.Department;
import com.lzpeng.project.sys.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 部门 抽象业务层 提供基于注解的缓存配置
 * @date: 2020-3-22
 * @time: 10:56:58
 * @author: 李志鹏
 */
@Service
@Transactional(rollbackOn = Exception.class)
public abstract class AbstractDepartmentService extends TreeServiceImpl<Department> {

    protected static final String ENTITY_NAME = "com.lzpeng.model.domain.sys.Department";

    protected DepartmentRepository departmentRepository;

    @Autowired
    public void setDepartmentRepository(DepartmentRepository departmentRepository) {
        this.baseRepository = departmentRepository;
        this.treeRepository = departmentRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    @CachePut(value = ENTITY_NAME, key = "#result.id", unless = "#result == null")
    public Department save(Department department) {
        return super.save(department);
    }

    @Override
    @CacheEvict(value = ENTITY_NAME, key = "#id")
    public void delete(String id) {
        super.delete(id);
    }

    @Override
    @CachePut(value = ENTITY_NAME, key = "#id", unless = "#result == null")
    public Department update(String id, Department model) {
        return super.update(id, model);
    }

    @Override
    @Cacheable(value = ENTITY_NAME, key = "#id", unless = "#result == null")
    public Department findById(String id) {
        return super.findById(id);
    }

    /**
     * 从 json 读取数据
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    @Override
    public List<Department> readDataFromJson(String json) throws JsonProcessingException {
        List<Department> list = objectMapper.readValue(json, new TypeReference<List<Department>>() {
        });
        return list;
    }

}
