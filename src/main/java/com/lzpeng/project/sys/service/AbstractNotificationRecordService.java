package com.lzpeng.project.sys.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.lzpeng.framework.web.service.BaseServiceImpl;
import com.lzpeng.project.sys.domain.NotificationRecord;
import com.lzpeng.project.sys.repository.NotificationRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 通知记录 抽象业务层 提供基于注解的缓存配置
 *
 * @author : 李志鹏
 * @date : 2020-5-17
 * @time : 0:43:53
 */
@Service
@Transactional(rollbackOn = Exception.class)
public abstract class AbstractNotificationRecordService extends BaseServiceImpl<NotificationRecord> {

    protected static final String ENTITY_NAME = "com.lzpeng.project.sys.domain.NotificationRecord";

    protected NotificationRecordRepository notificationRecordRepository;

    @Autowired
    public void setNotificationRecordRepository(NotificationRecordRepository notificationRecordRepository) {
        this.baseRepository = notificationRecordRepository;
        this.baseRepository = notificationRecordRepository;
        this.notificationRecordRepository = notificationRecordRepository;
    }

    @Override
    @CachePut(value = ENTITY_NAME, key = "#result.id", unless = "#result == null")
    public NotificationRecord save(NotificationRecord notificationRecord) {
        return super.save(notificationRecord);
    }

    @Override
    @CacheEvict(value = ENTITY_NAME, key = "#id")
    public void delete(String id) {
        super.delete(id);
    }

    @Override
    @CachePut(value = ENTITY_NAME, key = "#id", unless = "#result == null")
    public NotificationRecord update(String id, NotificationRecord model) {
        return super.update(id, model);
    }

    @Override
    @Cacheable(value = ENTITY_NAME, key = "#id", unless = "#result == null")
    public NotificationRecord findById(String id) {
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
    public List<NotificationRecord> readDataFromJson(String json) throws JsonProcessingException {
        List<NotificationRecord> list = objectMapper.readValue(json, new TypeReference<List<NotificationRecord>>() {
        });
        return list;
    }

}
