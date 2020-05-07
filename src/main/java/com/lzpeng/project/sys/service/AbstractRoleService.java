package com.lzpeng.project.sys.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.lzpeng.framework.web.service.LeftTreeRightTableServiceImpl;
import com.lzpeng.project.sys.domain.Department;
import com.lzpeng.project.sys.domain.Role;
import com.lzpeng.project.sys.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 角色 抽象业务层 提供基于注解的缓存配置
 * @date: 2020-3-22
 * @time: 10:56:59
 * @author: 李志鹏
 */
@Service
@Transactional(rollbackOn = Exception.class)
public abstract class AbstractRoleService extends LeftTreeRightTableServiceImpl<Department, Role> {

    protected static final String ENTITY_NAME = "com.lzpeng.model.domain.sys.Role";

    protected RoleRepository roleRepository;

    protected DepartmentService departmentService;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.baseRepository = roleRepository;
        this.leftTreeRightTableRepository = roleRepository;
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setDepartmentService(DepartmentService departmentService) {
        this.treeService = departmentService;
        this.departmentService = departmentService;
    }

    @Override
    @CachePut(value = ENTITY_NAME, key = "#result.id", unless = "#result == null")
    public Role save(Role role) {
        return super.save(role);
    }

    @Override
    @CacheEvict(value = ENTITY_NAME, key = "#id")
    public void delete(String id) {
        super.delete(id);
    }

    @Override
    @CachePut(value = ENTITY_NAME, key = "#id", unless = "#result == null")
    public Role update(String id, Role model) {
        return super.update(id, model);
    }

    @Override
    @Cacheable(value = ENTITY_NAME, key = "#id", unless = "#result == null")
    public Role findById(String id) {
        return super.findById(id);
    }

    /**
     * 从 json 读取数据
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    @Override
    public List<Role> readDataFromJson(String json) throws JsonProcessingException {
        List<Role> list = objectMapper.readValue(json, new TypeReference<List<Role>>() {
        });
        return list;
    }

}
