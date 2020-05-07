package com.lzpeng.project.sys.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.lzpeng.framework.web.service.LeftTreeRightTableServiceImpl;
import com.lzpeng.project.sys.domain.Department;
import com.lzpeng.project.sys.domain.User;
import com.lzpeng.project.sys.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 用户 抽象业务层 提供基于注解的缓存配置
 * @date: 2020-3-22
 * @time: 10:56:59
 * @author: 李志鹏
 */
@Service
@Transactional(rollbackOn = Exception.class)
public abstract class AbstractUserService  extends LeftTreeRightTableServiceImpl<Department, User> {

    protected static final String ENTITY_NAME = "com.lzpeng.model.domain.sys.User";

    protected UserRepository userRepository;

    protected DepartmentService departmentService;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.baseRepository = userRepository;
        this.leftTreeRightTableRepository = userRepository;
        this.userRepository = userRepository;
    }

    @Autowired
    public void setDepartmentService(DepartmentService departmentService) {
        this.treeService = departmentService;
        this.departmentService = departmentService;
    }

    @Override
    @CachePut(value = ENTITY_NAME, key = "#result.id", unless = "#result == null")
    public User save(User user) {
        return super.save(user);
    }

    @Override
    @CacheEvict(value = ENTITY_NAME, key = "#id")
    public void delete(String id) {
        super.delete(id);
    }

    @Override
    @CachePut(value = ENTITY_NAME, key = "#id", unless = "#result == null")
    public User update(String id, User model) {
        return super.update(id, model);
    }

    @Override
    @Cacheable(value = ENTITY_NAME, key = "#id", unless = "#result == null")
    public User findById(String id) {
        return super.findById(id);
    }

    /**
     * 从 json 读取数据
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    @Override
    public List<User> readDataFromJson(String json) throws JsonProcessingException {
        List<User> list = objectMapper.readValue(json, new TypeReference<List<User>>() {
        });
        return list;
    }

}
