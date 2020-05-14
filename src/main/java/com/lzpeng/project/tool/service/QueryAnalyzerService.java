package com.lzpeng.project.tool.service;

import cn.hutool.core.util.ReflectUtil;
import com.lzpeng.framework.domain.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 查询分析器Service
 *
 * @date: 2020/5/14
 * @time: 22:47
 * @author: 李志鹏
 */
@Slf4j
@Service
@Transactional(rollbackOn = Exception.class)
public class QueryAnalyzerService {


    /**
     * 注入的是实体管理器,执行持久化操作
     */
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 执行SQL
     * @param sql
     * @return
     */
    public Object executeSQL(String sql) {
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
        return resultList;
    }

    /**
     * 执行 JPQL
     * @param jpql
     * @return
     */
    public Object executeJPQL(String jpql) {
        Query query = entityManager.createQuery(jpql);
        List<BaseEntity> resultList = query.getResultList();
        for (BaseEntity entity : resultList) {
            // TODO 过滤集合属性 OR 转JSON时 过滤集合属性
            Class<? extends BaseEntity> entityClass = entity.getClass();
            Field[] fields = ReflectUtil.getFields(entityClass);
            for (Field field : fields) {
                if (Collection.class.isAssignableFrom(field.getType())) {
                    ReflectUtil.setFieldValue(entity, field, null);
                }
            }
        }
        return resultList;
    }
}
