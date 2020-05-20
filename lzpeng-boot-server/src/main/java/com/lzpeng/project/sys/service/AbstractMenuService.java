package com.lzpeng.project.sys.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.lzpeng.framework.web.service.TreeServiceImpl;
import com.lzpeng.project.sys.domain.Menu;
import com.lzpeng.project.sys.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 权限菜单 抽象业务层 提供基于注解的缓存配置
 * @date: 2020-3-22
 * @time: 10:56:59
 * @author: 李志鹏
 */
@Service
@Transactional(rollbackOn = Exception.class)
public abstract class AbstractMenuService extends TreeServiceImpl<Menu> {

    protected static final String ENTITY_NAME = "com.lzpeng.model.domain.sys.Menu";

    protected MenuRepository menuRepository;

    @Autowired
    public void setMenuRepository(MenuRepository menuRepository) {
        this.baseRepository = menuRepository;
        this.treeRepository = menuRepository;
        this.menuRepository = menuRepository;
    }

    @Override
    @CachePut(value = ENTITY_NAME, key = "#result.id", unless = "#result == null")
    public Menu save(Menu menu) {
        return super.save(menu);
    }

    @Override
    @CacheEvict(value = ENTITY_NAME, key = "#id")
    public void delete(String id) {
        super.delete(id);
    }

    @Override
    @CachePut(value = ENTITY_NAME, key = "#id", unless = "#result == null")
    public Menu update(String id, Menu model) {
        return super.update(id, model);
    }

    @Override
    @Cacheable(value = ENTITY_NAME, key = "#id", unless = "#result == null")
    public Menu findById(String id) {
        return super.findById(id);
    }

    /**
     * 从 json 读取数据
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    @Override
    public List<Menu> readDataFromJson(String json) throws JsonProcessingException {
        List<Menu> list = objectMapper.readValue(json, new TypeReference<List<Menu>>() {
        });
        return list;
    }

}
