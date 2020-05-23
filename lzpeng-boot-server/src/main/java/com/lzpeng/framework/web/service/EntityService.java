package com.lzpeng.framework.web.service;

import cn.hutool.core.map.MapBuilder;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.lzpeng.common.response.QueryResult;
import com.lzpeng.framework.domain.BaseEntity;
import com.lzpeng.framework.util.DefaultSqlLoader;
import com.lzpeng.project.tool.domain.TableInfo;
import com.lzpeng.project.tool.service.TableInfoService;
import com.querydsl.core.types.Predicate;
import lombok.SneakyThrows;
import org.hibernate.SQLQuery;
import org.hibernate.query.internal.QueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.QuerydslBindingsFactory;
import org.springframework.data.querydsl.binding.QuerydslPredicateBuilder;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 通用实体服务
 *
 * @date: 2020/5/18
 * @time: 23:10
 * @author: 李志鹏
 */
@Service
public class EntityService {

    /**
     * 注入的是实体管理器,执行持久化操作
     */
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TableInfoService tableInfoService;

    /**
     * 获取单据信息
     * @param entity 实体类型
     * @param column 字段
     * @param showColumns 查询列
     * @param filter 过滤条件
     * @return
     */
    @SneakyThrows
    public Map<String, Object> getTableData(String entity, String column, String[] showColumns, String filter, MultiValueMap<String, String> parameters) {

        Class<? extends BaseEntity> entityType = (Class<? extends BaseEntity>) Class.forName(entity);
        if (!StrUtil.isBlankOrUndefined(column)) {
            entityType = (Class<? extends BaseEntity>) ReflectUtil.getField(entityType, column).getType();
        }
        List<String> showColumnsList = Arrays.asList(showColumns);
        List<String> resultColumnsList = new ArrayList<>();
        // 是否有 id 字段
        if (!showColumnsList.contains("id")) {
            resultColumnsList.add("id");
        }
        resultColumnsList.addAll(showColumnsList);
        String whereCondition = DefaultSqlLoader.parseFilter(filter);
        String jpql = new StringBuilder()
                .append("SELECT ")
                .append(resultColumnsList.stream().collect(Collectors.joining(", ")))
                .append(" FROM ")
                .append(entityType.getName())
                .append(" WHERE ")
                .append(whereCondition)
                .toString();
        // jpa 返回 Map  https://www.jb51.net/article/131863.htm
        Query query = entityManager.createQuery(jpql);
        // query.unwrap(QueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List resultList = query.getResultList();
        resultList = (List) resultList.stream().map(objs -> {
            Object[] arr = (Object[]) objs;
            Map<String, Object> map = new HashMap<>(resultColumnsList.size());
            for (int i = 0; i < resultColumnsList.size(); i++) {
                map.put(resultColumnsList.get(i), arr[i]);
            }
            return map;
        }).collect(Collectors.toList());
        TableInfo tableInfo = tableInfoService.findByClassName(entityType.getName());
        Map<String, Object> map = MapBuilder.<String, Object>create().put("tableData", resultList).put("tableInfo", tableInfo).build();
        return map;
    }
}
