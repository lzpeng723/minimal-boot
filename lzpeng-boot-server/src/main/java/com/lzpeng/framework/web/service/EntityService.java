package com.lzpeng.framework.web.service;

import cn.hutool.core.map.MapBuilder;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.lzpeng.common.response.QueryResult;
import com.lzpeng.framework.domain.BaseEntity;
import com.lzpeng.project.tool.domain.TableInfo;
import com.lzpeng.project.tool.service.TableInfoService;
import com.querydsl.core.types.Predicate;
import lombok.SneakyThrows;
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
import java.util.Map;
import java.util.Optional;

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
     * Query DSL 绑定处理
     */
    private final QuerydslBindingsFactory bindingsFactory;
    /**
     * Query DSL 条件构建器
     *
     * @see {@link org.springframework.data.web.querydsl.QuerydslPredicateArgumentResolver#predicateBuilder}
     */
    private final QuerydslPredicateBuilder predicateBuilder;

    @Autowired
    public EntityService(QuerydslBindingsFactory factory,
                         Optional<ConversionService> conversionService) {

        this.bindingsFactory = factory;
        this.predicateBuilder = new QuerydslPredicateBuilder(conversionService.orElseGet(DefaultConversionService::new),
                factory.getEntityPathResolver());
    }

    /**
     * 获取单据信息
     *
     * @param entity
     * @param column
     * @param showColumns
     * @param filter
     * @return
     */
    @SneakyThrows
    public Map<String, Object> getTableData(String entity, String column, String[] showColumns, String[] filter, MultiValueMap<String, String> parameters) {

        Class<? extends BaseEntity> entityType = (Class<? extends BaseEntity>) Class.forName(entity);
        if (!StrUtil.isBlankOrUndefined(column)) {
            entityType = (Class<? extends BaseEntity>) ReflectUtil.getField(entityType, column).getType();
        }
        TypeInformation<?> domainType = ClassTypeInformation.from(entityType).getRequiredActualType();
        QuerydslBindings bindings = bindingsFactory.createBindingsFor(domainType);
        for (String s : filter) {
            s.split("=|<|>|<=|>=|<>|in|like|start|end");
        }
        Predicate predicate = predicateBuilder.getPredicate(domainType, parameters, bindings);
        String entityName = entityType.getName();
        BaseServiceImpl service = (BaseServiceImpl) SpringUtil.getBean(Class.forName(entityName.replace("domain", "service") + "Service"));
        QueryResult result = service.query(1, 1, predicate);
        TableInfo tableInfo = tableInfoService.findByClassName(entityName);
        Map<String, Object> map = MapBuilder.<String, Object>create().put("tableData", result).put("tableInfo", tableInfo).build();
        return map;
    }
}
