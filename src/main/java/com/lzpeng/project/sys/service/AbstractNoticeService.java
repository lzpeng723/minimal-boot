package com.lzpeng.project.sys.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.lzpeng.framework.web.service.BaseServiceImpl;
import com.lzpeng.project.sys.domain.Notice;
import com.lzpeng.project.sys.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 通知 抽象业务层 提供基于注解的缓存配置
 *
 * @author : 李志鹏
 * @date : 2020-5-16
 * @time : 21:03:56
 */
@Service
@Transactional(rollbackOn = Exception.class)
public abstract class AbstractNoticeService extends BaseServiceImpl<Notice> {

    protected static final String ENTITY_NAME = "com.lzpeng.project.sys.domain.Notice";

    protected NoticeRepository noticeRepository;

    @Autowired
    public void setNoticeRepository(NoticeRepository noticeRepository) {
        this.baseRepository = noticeRepository;
        this.baseRepository = noticeRepository;
        this.noticeRepository = noticeRepository;
    }

    @Override
    @CachePut(value = ENTITY_NAME, key = "#result.id", unless = "#result == null")
    public Notice save(Notice notice) {
        return super.save(notice);
    }

    @Override
    @CacheEvict(value = ENTITY_NAME, key = "#id")
    public void delete(String id) {
        super.delete(id);
    }

    @Override
    @CachePut(value = ENTITY_NAME, key = "#id", unless = "#result == null")
    public Notice update(String id, Notice model) {
        return super.update(id, model);
    }

    @Override
    @Cacheable(value = ENTITY_NAME, key = "#id", unless = "#result == null")
    public Notice findById(String id) {
        return super.findById(id);
    }

    /**
     * 从 json 读取数据
     * 必须重写此方法否则,TypeReference获取不到泛型参数
     *
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    @Override
    public List<Notice> readDataFromJson(String json) throws JsonProcessingException {
        List<Notice> list = objectMapper.readValue(json, new TypeReference<List<Notice>>() {
        });
        return list;
    }

}
